/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptProjectTestCase;
import de.dlr.sc.virsat.model.dvlm.DVLMFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.roles.UserRegistry;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.ParameterRange;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.structure.VirSatProjectCommons;

/**
 * Test Case for Validator of CEF properties
 * 
 * @author desh_me
 *
 */

public class ValidatorTest extends AConceptProjectTestCase {
	
	private static final String CONCEPT_ID_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	private static final String CONCEPT_ID_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	
	private IResource fileSys;
	
	private static final Double VALUEZERO = 0.0;
	private static final Double VALUEFIVE = 5.0;
	private static final Double VALUETHREE = 3.0;
	private static final Double VALUETEN = 10.0;
	private static final Double VALUETWELVE = 12.0;
	
	private Concept conceptCEFX;
	private Concept conceptPS;
	
	private ConfigurationTree sys;
	private Parameter paramOne;
	
	@Before
	public void setUp() throws CoreException {
		super.setUp();

		repository = DVLMFactory.eINSTANCE.createRepository();
		VirSatResourceSet resourceSet = VirSatResourceSet.createUnmanagedResourceSet(testProject);
		resourceSet.getRepositoryResource().getContents().add(repository);
		
		conceptPS = loadConceptFromPlugin(CONCEPT_ID_PS);
		conceptCEFX = loadConceptFromPlugin(CONCEPT_ID_CEFX);
		
		// Here we start to create the Test Model
		sys = new ConfigurationTree(conceptPS);
		sys.setName("TestSatellite");
		
		paramOne = new Parameter(conceptCEFX);
		paramOne.setName("ParamOne");
		
		ParameterRange range = new ParameterRange(conceptCEFX);
		paramOne.getRangeValues().add(range);
		
		sys.add(paramOne);
		
		// And finally we use the project commons to create the correct workspace paths
		// for the individual resources of the SEIs. We still have to attach the SEI eObjects
		// to their EMF Resources and save them
		VirSatProjectCommons projectCommons = new VirSatProjectCommons(testProject);
		fileSys = projectCommons.getStructuralElementInstanceFile(sys.getStructuralElementInstance());
		
		Resource resSys = resourceSet.getStructuralElementInstanceResource(sys.getStructuralElementInstance());
		resSys.getContents().add(sys.getStructuralElementInstance());
		
		resourceSet.saveAllResources(null, UserRegistry.getInstance());
	}
	
	@After
	public void tearDown() throws CoreException {
		super.tearDown();
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