/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.cef.calculation.modes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;

/**
 * Tests the ParameterGetter
 * @author muel_s8
 *
 */

public class ParameterGetterTest extends ATestCase {

	
	private static final double DEFAULT_VALUE = 12;
	private static final double IDLE_VALUE = -5;
	
	private Parameter parameter;
	private SystemMode idle;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
	    // Create a parameter with some default data
	    
		parameter = new Parameter(concept);
		parameter.setDefaultValue(DEFAULT_VALUE);
		
		Value idleValue = new Value(concept);
		idle = new SystemMode(concept);
		idleValue.setValue(IDLE_VALUE);
		idleValue.setMode(idle);
		parameter.getModeValues().add(idleValue);
	}

	@Test
	public void testGet() {
		ParameterGetter getter = new ParameterGetter();
		ModeVectorResult modeVector = (ModeVectorResult) getter.get(parameter.getATypeInstance());
		
		assertEquals("Default value correct", Double.parseDouble(modeVector.getResult(null).getNumberLiteral().getValue()), DEFAULT_VALUE, TEST_EPSILON);
		double idleValue = Double.parseDouble(modeVector.getResult(idle.getTypeInstance()).getNumberLiteral().getValue());
		assertEquals("Idle value correct", idleValue, IDLE_VALUE, TEST_EPSILON);
	}

}
