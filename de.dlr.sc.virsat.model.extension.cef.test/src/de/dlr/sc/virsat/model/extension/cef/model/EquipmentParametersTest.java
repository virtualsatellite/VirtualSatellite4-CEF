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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

// *****************************************************************
// * Import Statements
// *****************************************************************



// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Abstract Generator Gap Class
 * 
 * Don't Manually modify this class
 * 
 * 
 * 
 */	
public class EquipmentParametersTest extends AEquipmentParametersTest {
	
	//CHECKSTYLE:OFF
	@Test
	public void testGetGlobalQuantityKindNoNesting() {
		EquipmentParameters eqp = new EquipmentParameters(concept);
		
		// Set quantityKind of 1 and see if we get it back
		eqp.setUnitQuantity(1);
		long globalQuantityKind = eqp.getGlobalUnitQuantity();
		
		assertEquals("Got correct QuantityKind", 1, globalQuantityKind);
	
		// Set the quantity kind to another value
		eqp.setUnitQuantity(3);
		globalQuantityKind = eqp.getGlobalUnitQuantity();
	
		assertEquals("Got correct QuantityKind", 3, globalQuantityKind);
		
		// now try to unset the quantity kind and check we get back a one
		eqp.getUnitQuantityBean().getTypeInstance().setValue("");
		globalQuantityKind = eqp.getGlobalUnitQuantity();
		
		assertEquals("Got correct QuantityKind", 1, globalQuantityKind);
	}

	@Test
	public void testGetGlobalQuantityKindNestedSubEquipment() {
		Equipment eqRw = new Equipment(concept);
		Equipment eqRwElectronics = new Equipment(concept);
		
		EquipmentParameters paramRw = new EquipmentParameters(concept);
		EquipmentParameters paramRwElectronics = new EquipmentParameters(concept);
	
		eqRw.add(paramRw);
		eqRwElectronics.add(paramRwElectronics);
		
		eqRw.add(eqRwElectronics);
		
		// First test is 1xRW with 2x Electronics
		paramRw.setUnitQuantity(1);
		paramRwElectronics.setUnitQuantity(2);
		
		assertEquals("Global Quantity should be 2", 2, paramRwElectronics.getGlobalUnitQuantity());
	
		// First test is 2xRW with 2x Electronics
		paramRw.setUnitQuantity(2);
		paramRwElectronics.setUnitQuantity(2);
		
		assertEquals("Global Quantity should be 4", 4, paramRwElectronics.getGlobalUnitQuantity());
	}

	@Test
	public void testGetGlobalQuantityKindNestedSubSubEquipment() {
		Equipment eqRw = new Equipment(concept);
		Equipment eqRwElectronics = new Equipment(concept);
		Equipment eqRwElectronicsCpu = new Equipment(concept);
		
		EquipmentParameters paramRw = new EquipmentParameters(concept);
		EquipmentParameters paramRwElectronics = new EquipmentParameters(concept);
		EquipmentParameters paramRwElectronicsCpu = new EquipmentParameters(concept);
		
		// DO not yet assign the Electronics Quantities
		// This will happen later in the test
		eqRw.add(paramRw);
		//eqRwElectronics.add(paramRwElectronics);
		eqRwElectronicsCpu.add(paramRwElectronicsCpu);
		
		eqRw.add(eqRwElectronics);
		eqRwElectronics.add(eqRwElectronicsCpu);
		
		// First test is 1xRW with Electronics and 2X CPU
		paramRw.setUnitQuantity(2);
		paramRwElectronics.setUnitQuantity(2);
		paramRwElectronicsCpu.setUnitQuantity(2);
		
		assertEquals("Global Quantity should be 4", 4, paramRwElectronicsCpu.getGlobalUnitQuantity());
	
		// First test is 2xRW with 2x Electronics and 2x CPU
		eqRwElectronics.add(paramRwElectronics);
		paramRw.setUnitQuantity(2);
		paramRwElectronics.setUnitQuantity(2);
		paramRwElectronicsCpu.setUnitQuantity(2);
		
		assertEquals("Global Quantity should be 8", 8, paramRwElectronicsCpu.getGlobalUnitQuantity());
	}
	//CHECKSTYLE:ON
}
