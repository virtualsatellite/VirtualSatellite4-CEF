/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.DVLMFactory;
import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.provider.PropertydefinitionsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.provider.PropertyinstancesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.provider.DVLMCategoriesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.provider.ConceptsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.registry.ActiveConceptConfigurationElement;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.provider.GeneralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.provider.DVLMItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.resource.provider.DVLMResourceItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.roles.UserRegistry;
import de.dlr.sc.virsat.model.dvlm.structural.provider.DVLMStructuralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.ParameterRange;
import de.dlr.sc.virsat.model.extension.cef.model.System;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.structure.VirSatProjectCommons;

/**
 * Test Case for Validator of CEF properties
 * 
 * @author desh_me
 *
 */

public class ValidatorTest {
	
	private static final String CONCEPT_ID_CEF = "de.dlr.sc.virsat.model.extension.cef";
	private IResource fileSys;
	
	private static final Double VALUEZERO = 0.0;
	private static final Double VALUEFIVE = 5.0;
	private static final Double VALUETHREE = 3.0;
	private static final Double VALUETEN = 10.0;
	private static final Double VALUETWELVE = 12.0;
	
	IProject project;
	VirSatResourceSet resSet;
	Repository repository;
	EditingDomain ed;
	Concept conceptCef;
	
	System sys;
	Parameter paramOne;
	
	@Before
	public void setUp() throws CoreException, IOException {
		UserRegistry.getInstance().setSuperUser(true);
		
		// Create an Editing Domain
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new DVLMResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMStructuralItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new GeneralItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConceptsItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMCategoriesItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new PropertydefinitionsItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new PropertyinstancesItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		ed = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack());

		// Now get the workspace and create a new Project. Deactivate the auto-building to no t let
		// the eclipse platform place markers to our resources
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IWorkspaceDescription wsd = ResourcesPlugin.getWorkspace().getDescription();
		wsd.setAutoBuilding(false);
		ResourcesPlugin.getWorkspace().setDescription(wsd);
		wsRoot.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		project = wsRoot.getProject("s2tepFeaTests");
		if (project.exists()) {
			project.delete(true, null);
		}
		project.create(null);
		project.open(null);

		// Now create a repository object and attach it to a resource
		// use the VirSatProjectCommons to follow our directory structure etc.
		VirSatProjectCommons projectCommons = new VirSatProjectCommons(project);

		repository = DVLMFactory.eINSTANCE.createRepository();
		resSet = VirSatResourceSet.createUnmanagedResourceSet(project);
		resSet.getResources().clear();
		resSet.getRepositoryResource().getContents().add(repository);

		//CHECKSTYLE:OFF
		ActiveConceptConfigurationElement acceCef = new ActiveConceptConfigurationElement(null) {
			public String getXmi() { return "concept/concept.xmi"; };
			public String getId() { return CONCEPT_ID_CEF; };
		};

		//CHECKSTYLE:ON
		
		// Now load the PS and IF concept into the repository
		// we need the full set of repository loaded concepts etc to provide
		// correctly set up workspace resources for setting and detecting the markers
		acceCef.createAddActiveConceptCommand(ed, repository).execute();
		
		ActiveConceptHelper acHelper = new ActiveConceptHelper(repository);
		conceptCef = acHelper.getConcept(CONCEPT_ID_CEF);
		

		// Check that concepts are correctly loaded
		// We used to have badly connected concepts, due to the persistence of resources
		// on the second run of the test case the repository resource was already containing
		// a resource and the test setup was adding a second one. We now changed the code, that
		// the whole project in the workspace gets deleted first and then recreated.
		//CHECKSTYLE:OFF
		
		//check later if this check is still needed or not
		/* seEc = conceptPs.getStructuralElements().get(4);
		catIf = conceptFea.getCategories().get(2);
		assertThat("Concepts correctly connected", catIf.getApplicableFor(), hasItem(seEc));*/
		
		
		//CHECKSTYLE:ON
		
		// Here we start to create the Test Model
		
		sys = new System(conceptCef);
		sys.setName("TestSatellite");
		
		
		paramOne = new Parameter(conceptCef);
		paramOne.setName("ParamOne");
		
		ParameterRange range = new ParameterRange(conceptCef);
		paramOne.getRangeValues().add(range);
		
		
		sys.add(paramOne);
		
		// And finally we use the project commons to create the correct workspace paths
		// for the individual resources of the SEIs. We still have to attach the SEI eObjects
		// to their EMF Resources and save them
		fileSys = projectCommons.getStructuralElementInstanceFile(sys.getStructuralElementInstance());
		
		Resource resRepo = resSet.getRepositoryResource();
		Resource resSys = resSet.getStructuralElementInstanceResource(sys.getStructuralElementInstance());
		
		resSys.getContents().add(sys.getStructuralElementInstance());
		
		resRepo.save(Collections.EMPTY_MAP);
		resSys.save(Collections.EMPTY_MAP);
	}
	
	@After
	public void tearDown() {
		UserRegistry.getInstance().setSuperUser(false);
	}
	
	@Test
	public void testParameterOutOfRange() throws Exception {

		StructuralElementInstanceValidator seiValidator = new StructuralElementInstanceValidator();

		Boolean validate = seiValidator.validate(sys.getStructuralElementInstance());
		assertTrue("validator brings no error", seiValidator.validate(sys.getStructuralElementInstance()));
		assertEquals("There are no markers yet", 0,	fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length);
		
		paramOne.setDefaultValue(VALUETEN);
		paramOne.getRangeValues().get(0).setMaxValue(VALUEFIVE);
		
		validate = seiValidator.validate(sys.getStructuralElementInstance());
		assertFalse("validator brings no error", validate);
		assertEquals("Default Value of Parameter is out of range", 1, fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length);
		
		fileSys.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		paramOne.getRangeValues().get(0).setMaxValue(VALUETWELVE);
		
		validate = seiValidator.validate(sys.getStructuralElementInstance());
		assertTrue("validator brings no error", seiValidator.validate(sys.getStructuralElementInstance()));
		assertEquals("There are no markers yet", 0,	fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length);
		
		paramOne.setDefaultValue(VALUETHREE);
		paramOne.getRangeValues().get(0).setMinValue(VALUEFIVE);
		
		validate = seiValidator.validate(sys.getStructuralElementInstance());
		assertFalse("validator brings no error", validate);
		assertEquals("Default Value of Parameter is out of range", 1, fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length);
		
		fileSys.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		paramOne.getRangeValues().get(0).setMinValue(VALUEZERO);
		
		validate = seiValidator.validate(sys.getStructuralElementInstance());
		assertTrue("validator brings no error", seiValidator.validate(sys.getStructuralElementInstance()));
		assertEquals("There are no markers yet", 0,	fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length);
		
	}
		
}