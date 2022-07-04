/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.cef.calculation.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.dlr.sc.virsat.cef.calculation.modes.ModeVectorEvaluatorTest;
import de.dlr.sc.virsat.cef.calculation.modes.ModeVectorResultTest;
import de.dlr.sc.virsat.cef.calculation.modes.ParameterGetterTest;
import de.dlr.sc.virsat.cef.calculation.modes.ParameterSetterTest;
import junit.framework.JUnit4TestAdapter;

/**
 * 
 * @author scha_vo
 *
 */
@RunWith(Suite.class)

@SuiteClasses({
	ModeVectorResultTest.class,
	ParameterSetterTest.class, 
	ParameterGetterTest.class,
	ModeVectorEvaluatorTest.class
})

public class AllTests {
	
	/**
	 * Constructor for test class
	 */
	private AllTests() {
	}

	/**
	 * entry method to tests
	 * @return junit testsuite
	 */
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}	
}