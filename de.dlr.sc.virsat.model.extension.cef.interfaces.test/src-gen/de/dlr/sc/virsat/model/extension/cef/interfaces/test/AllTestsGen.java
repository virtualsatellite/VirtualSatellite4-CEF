/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.test;

// *****************************************************************
// * Import Statements
// *****************************************************************

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;

import de.dlr.sc.virsat.model.extension.cef.interfaces.model.EquipmentDataParametersTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.migrator.Migrator1v0Test;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.BusInterfaceTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.PointToPointInterfaceTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.migrator.Migrator1v1Test;
import de.dlr.sc.virsat.model.extension.cef.interfaces.validator.InterfacesValidatorTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.DataInterfaceTypesTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.DataInterfaceTypeCollectionTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.PowerInterfaceEndTest;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.DataInterfaceEndTest;

/**
 * 
 */
@RunWith(Suite.class)

@SuiteClasses({
	EquipmentDataParametersTest.class,
	DataInterfaceTypesTest.class,
	PowerInterfaceEndTest.class,
	DataInterfaceEndTest.class,
	PointToPointInterfaceTest.class,
	BusInterfaceTest.class,
	DataInterfaceTypeCollectionTest.class,
	Migrator1v0Test.class,
	Migrator1v1Test.class,
	InterfacesValidatorTest.class,
				})

/**
 * 
 * Test Collection
 *
 */
public class AllTestsGen {

	/**
	 * Constructor
	 */
	private AllTestsGen() {
	}
	
	/**
	 * Test Adapter
	 * @return Executable JUnit Tests
	 */
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}	
}
