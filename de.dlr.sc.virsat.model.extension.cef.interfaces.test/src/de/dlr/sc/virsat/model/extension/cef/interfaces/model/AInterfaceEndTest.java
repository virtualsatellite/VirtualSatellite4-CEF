/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.structural.IApplicableFor;
import de.dlr.sc.virsat.model.extension.cef.model.Equipment;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentParameters;

// *****************************************************************
// * Import Statements
// *****************************************************************



// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class AInterfaceEndTest {
	protected Concept conceptCefInterfaces;
	protected Concept conceptCef;
	
	@Before
	public void setUp() throws Exception {
		String conceptCefInterfacesXmiPluginPath = "de.dlr.sc.virsat.model.extension.cef.interfaces/concept/concept.xmi";
		conceptCefInterfaces = de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader.loadConceptFromPlugin(conceptCefInterfacesXmiPluginPath);
		String conceptCefXmiPluginPath = "de.dlr.sc.virsat.model.extension.cef/concept/concept.xmi";
		conceptCef = de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader.loadConceptFromPlugin(conceptCefXmiPluginPath);
	}

	@Test
	public void testInterfaceQuantities() {
		DataInterfaceEnd dife = new DataInterfaceEnd(conceptCefInterfaces);
		PowerInterfaceEnd pife = new PowerInterfaceEnd(conceptCefInterfaces);
		
		//CHECKSTYLE:OFF
		dife.setQuantity(2);
		pife.setQuantity(3);
		
		assertEquals("Quantitiy is correct", 2, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 3, pife.getGlobalUnitQuantity());
		//CHECKSTYLE:ON
		
		dife.getQuantityBean().getTypeInstance().setValue("");
		pife.getQuantityBean().getTypeInstance().setValue("");
		
		assertEquals("Quantitiy is correct", 1, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 1, pife.getGlobalUnitQuantity());
	}
	
	
	@Test
	public void testInterfaceQuantitiesOnEquipments() {
		DataInterfaceEnd dife = new DataInterfaceEnd(conceptCefInterfaces);
		PowerInterfaceEnd pife = new PowerInterfaceEnd(conceptCefInterfaces);
		
		((IApplicableFor) dife.getTypeInstance().getType()).setIsApplicableForAll(true);
		((IApplicableFor) pife.getTypeInstance().getType()).setIsApplicableForAll(true);
		
		//CHECKSTYLE:OFF
		dife.setQuantity(2);
		pife.setQuantity(3);
		
		assertEquals("Quantitiy is correct", 2, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 3, pife.getGlobalUnitQuantity());
		
		Equipment equipment = new Equipment(conceptCef);
		Equipment subEquipment = new Equipment(conceptCef);
		equipment.add(subEquipment);
		
		subEquipment.add(pife);
		subEquipment.add(dife);
		
		assertEquals("Quantitiy is correct", 2, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 3, pife.getGlobalUnitQuantity());
		
		EquipmentParameters equipmentParameters = new EquipmentParameters(conceptCef);
		EquipmentParameters subEquipmentParameters = new EquipmentParameters(conceptCef);
		
		equipment.add(equipmentParameters);
		equipmentParameters.setUnitQuantity(2);
		
		assertEquals("Quantitiy is correct", 4, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 6, pife.getGlobalUnitQuantity());
		
		subEquipment.add(subEquipmentParameters);
		subEquipmentParameters.setUnitQuantity(3);
		
		assertEquals("Quantitiy is correct", 12, dife.getGlobalUnitQuantity());
		assertEquals("Quantitiy is correct", 18, pife.getGlobalUnitQuantity());
		//CHECKSTYLE:ON
	}
	
	@After
	public void tearDown() throws Exception {
	}
}
