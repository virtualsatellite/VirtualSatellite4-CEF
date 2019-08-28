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


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.dlr.sc.virsat.model.extension.cefx.calculation.modes.ModeVectorEvaluatorTest;
import de.dlr.sc.virsat.model.extension.cefx.calculation.modes.ModeVectorResultTest;
import de.dlr.sc.virsat.model.extension.cefx.calculation.modes.ParameterGetterTest;
import de.dlr.sc.virsat.model.extension.cefx.calculation.modes.ParameterSetterTest;
import de.dlr.sc.virsat.model.extension.cefx.excel.ExcelUpdaterTest;
import de.dlr.sc.virsat.model.extension.cefx.util.CefModeHelperTest;
import de.dlr.sc.virsat.model.extension.cefx.validator.ValidatorTest;
import junit.framework.JUnit4TestAdapter;

/**
 * 
 * @author muel_s8
 *
 */
@RunWith(Suite.class)

@SuiteClasses({ 
	ExcelUpdaterTest.class,
	ValidatorTest.class,
	CefModeHelperTest.class,
	ModeVectorEvaluatorTest.class,
	ModeVectorResultTest.class,
	ParameterGetterTest.class,
	ParameterSetterTest.class,
	})

public class AllTests {
	
	/**
	 * Constructor for Test Suite
	 */
	private AllTests() {
	}

	/**
	 * entry point for test suite
	 * @return the test suite
	 */
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}	
}