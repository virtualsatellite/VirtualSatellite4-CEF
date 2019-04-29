/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.model;

// *****************************************************************
// * Import Statements
// *****************************************************************
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystem;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralFactory;
import java.lang.Exception;


// *****************************************************************
// * Class Declaration
// *****************************************************************

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Auto Generated Abstract Generator Gap Class
 * 
 * Don't Manually modify this class
 * 
 * 
 * 
 */	
public abstract class ASubSystemTest {

	protected Concept concept;
	
	@Before
	public void setUp() throws Exception {
		String conceptXmiPluginPath = "de.dlr.sc.virsat.model.extension.cef/concept/concept.xmi";
		concept = de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader.loadConceptFromPlugin(conceptXmiPluginPath);
	}

	@After
	public void tearDown() throws Exception {
	}
	
		
	// *****************************************************************
	// * Constructor Test Cases
	// *****************************************************************
	
	@Test
	public void testSubSystem() {
		SubSystem testSubSystem = new SubSystem();
	
		assertNull("There is no internal DVLM object", testSubSystem.getStructuralElementInstance());
	}
	
	@Test
	public void testSubSystemConcept() {
		SubSystem testSubSystem = new SubSystem(concept);
		
		assertNotNull("There is an internal DVLM object", testSubSystem.getStructuralElementInstance());
	}
	
	@Test
	public void testSubSystemStructuralElementInstance() {
		StructuralElementInstance testSei = StructuralFactory.eINSTANCE.createStructuralElementInstance();
		SubSystem testSubSystem = new SubSystem(testSei);
		
		assertEquals("DVLM object has been set as specified", testSei, testSubSystem.getStructuralElementInstance());
	}
	
}
