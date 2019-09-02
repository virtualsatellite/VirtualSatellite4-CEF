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
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptProjectTestCase;
import de.dlr.sc.virsat.model.dvlm.DVLMFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.structure.VirSatProjectCommons;

/**
 * Test class for level validators
 */
public class WrongHierarchyValidatorTest extends AConceptProjectTestCase {
	
	private static final String CONCEPT_ID_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	private static final String CONCEPT_ID_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	
	private IResource fileSys1;
	private IResource fileSys2;
	
	private Concept conceptCEFX;
	private Concept conceptPS;
	
	private ConfigurationTree sys1;
	private ElementConfiguration sys2;
	
	@Before
	public void setUp() throws CoreException {
		super.setUp();

		repository = DVLMFactory.eINSTANCE.createRepository();
		VirSatResourceSet resourceSet = VirSatResourceSet.createUnmanagedResourceSet(testProject);
		resourceSet.getRepositoryResource().getContents().add(repository);
		
		conceptPS = loadConceptFromPlugin(CONCEPT_ID_PS);
		conceptCEFX = loadConceptFromPlugin(CONCEPT_ID_CEFX);

		ActiveConceptHelper.getCategory(conceptCEFX, SystemParameters.class.getSimpleName()).setIsApplicableForAll(true);
		
		sys1 = new ConfigurationTree(conceptPS);
		
		SystemParameters systemParameters = new SystemParameters(conceptCEFX);
		sys1.add(systemParameters);

		sys2 = new ElementConfiguration(conceptPS);
		sys1.add(sys2);
		
		// And finally we use the project commons to create the correct workspace paths
		// for the individual resources of the SEIs. We still have to attach the SEI eObjects
		// to their EMF Resources and save them
		VirSatProjectCommons projectCommons = new VirSatProjectCommons(testProject);
		fileSys1 = projectCommons.getStructuralElementInstanceFile(sys1.getStructuralElementInstance());
		
		Resource resSys1 = resourceSet.getStructuralElementInstanceResource(sys1.getStructuralElementInstance());
		resSys1.getContents().add(sys1.getStructuralElementInstance());

		fileSys2 = projectCommons.getStructuralElementInstanceFile(sys2.getStructuralElementInstance());
		
		Resource resSys2 = resourceSet.getStructuralElementInstanceResource(sys2.getStructuralElementInstance());
		resSys2.getContents().add(sys2.getStructuralElementInstance());
		
		resourceSet.saveAllResources(null);
	}

	@Test
	public void testSystemUnderSystem() throws Exception {
		WrongHierarchyValidator validator = new WrongHierarchyValidator();

		assertTrue(validator.validate(sys1.getStructuralElementInstance()));
		assertTrue(validator.validate(sys2.getStructuralElementInstance()));
		int numberOfMarkersSys1 = fileSys1.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		int numberOfMarkersSys2 = fileSys2.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		assertEquals(0, numberOfMarkersSys1);
		assertEquals(0, numberOfMarkersSys2);

		SystemParameters sp = new SystemParameters(conceptCEFX);
		sys2.add(sp);

		assertFalse("System under system is not allowed", validator.validate(sys1.getStructuralElementInstance()));
		assertFalse("System under system is not allowed", validator.validate(sys2.getStructuralElementInstance()));
		numberOfMarkersSys1 = fileSys1.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		numberOfMarkersSys2 = fileSys2.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		assertEquals(1, numberOfMarkersSys1);
		assertEquals(1, numberOfMarkersSys2);
	}
}
