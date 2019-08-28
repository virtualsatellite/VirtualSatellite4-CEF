/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.test;

// *****************************************************************
// * Import Statements
// *****************************************************************

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;

import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.ParameterRangeTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemModeTest;
import de.dlr.sc.virsat.model.extension.cefx.model.ParameterTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.ExcelCalculationTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.ValueTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParametersTest;
import de.dlr.sc.virsat.model.extension.cefx.migrator.Migrator1v0Test;

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
	ExcelCalculationTest.class,
	Migrator1v0Test.class,
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
