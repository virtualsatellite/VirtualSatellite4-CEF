/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.test;

// *****************************************************************
// * Import Statements
// *****************************************************************

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;

import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v4Test;
import de.dlr.sc.virsat.model.extension.cef.model.SystemOfSystemsTest;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentTest;
import de.dlr.sc.virsat.model.extension.cef.model.SystemPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentTemperatureParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.SystemModeTest;
import de.dlr.sc.virsat.model.extension.cef.model.ExcelCalculationTest;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMassParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.SystemParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemTest;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v0Test;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v5Test;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemMassParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentParametersTest;
import de.dlr.sc.virsat.model.extension.cef.model.ParameterRangeTest;
import de.dlr.sc.virsat.model.extension.cef.model.ValueTest;
import de.dlr.sc.virsat.model.extension.cef.model.ParameterTest;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v2Test;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v1Test;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v6Test;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cef.migrator.Migrator1v3Test;
import de.dlr.sc.virsat.model.extension.cef.model.DocumentTest;
import de.dlr.sc.virsat.model.extension.cef.model.SystemTest;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentMassParametersTest;

/**
 * 
 */
@RunWith(Suite.class)

@SuiteClasses({
	SystemModeTest.class,
	ParameterTest.class,
	ValueTest.class,
	SystemParametersTest.class,
	SystemMassParametersTest.class,
	SystemPowerParametersTest.class,
	SubSystemMassParametersTest.class,
	SubSystemPowerParametersTest.class,
	EquipmentParametersTest.class,
	EquipmentMassParametersTest.class,
	EquipmentPowerParametersTest.class,
	EquipmentTemperatureParametersTest.class,
	ParameterRangeTest.class,
	DocumentTest.class,
	ExcelCalculationTest.class,
	SystemOfSystemsTest.class,
	SystemTest.class,
	SubSystemTest.class,
	EquipmentTest.class,
	Migrator1v0Test.class,
	Migrator1v1Test.class,
	Migrator1v2Test.class,
	Migrator1v3Test.class,
	Migrator1v4Test.class,
	Migrator1v5Test.class,
	Migrator1v6Test.class,
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
