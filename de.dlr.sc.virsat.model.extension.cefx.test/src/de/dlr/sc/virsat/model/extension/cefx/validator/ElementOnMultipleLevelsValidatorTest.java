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
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.structure.VirSatProjectCommons;

/**
 * Test class for level validators
 */
public class ElementOnMultipleLevelsValidatorTest extends AConceptProjectTestCase {
	
	private static final String CONCEPT_ID_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	private static final String CONCEPT_ID_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	
	private IResource fileSys;
	
	private Concept conceptCEFX;
	private Concept conceptPS;
	
	private ElementConfiguration sys;
	
	@Before
	public void setUp() throws CoreException {
		super.setUp();

		repository = DVLMFactory.eINSTANCE.createRepository();
		VirSatResourceSet resourceSet = VirSatResourceSet.createUnmanagedResourceSet(testProject);
		resourceSet.getRepositoryResource().getContents().add(repository);
		
		conceptPS = loadConceptFromPlugin(CONCEPT_ID_PS);
		conceptCEFX = loadConceptFromPlugin(CONCEPT_ID_CEFX);

		ActiveConceptHelper.getCategory(conceptCEFX, SystemParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentParameters.class.getSimpleName()).setIsApplicableForAll(true);
		
		sys = new ElementConfiguration(conceptPS);
		
		SystemParameters systemParameters = new SystemParameters(conceptCEFX);
		sys.add(systemParameters);
		
		// And finally we use the project commons to create the correct workspace paths
		// for the individual resources of the SEIs. We still have to attach the SEI eObjects
		// to their EMF Resources and save them
		VirSatProjectCommons projectCommons = new VirSatProjectCommons(testProject);
		fileSys = projectCommons.getStructuralElementInstanceFile(sys.getStructuralElementInstance());
		
		Resource resSys = resourceSet.getStructuralElementInstanceResource(sys.getStructuralElementInstance());
		resSys.getContents().add(sys.getStructuralElementInstance());
		
		resourceSet.saveAllResources(null);
	}

	@Test
	public void testSystemWithEquipmentParameters() throws Exception {
		ElementOnMultipleLevelsValidator validator = new ElementOnMultipleLevelsValidator();

		assertTrue(validator.validate(sys.getStructuralElementInstance()));
		int numberOfMarkers = fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		assertEquals(0, numberOfMarkers);
		
		EquipmentParameters equipmentParameters = new EquipmentParameters(conceptCEFX);
		sys.add(equipmentParameters);
		
		assertFalse(validator.validate(sys.getStructuralElementInstance()));
		numberOfMarkers = fileSys.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length;
		assertEquals("Two markers expected for two conflicting CAs", 2, numberOfMarkers);
	}
}
