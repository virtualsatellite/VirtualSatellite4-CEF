/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.calculation.modes;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.dvlm.calculation.CalculationFactory;
import de.dlr.sc.virsat.model.dvlm.calculation.NumberLiteral;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesFactory;
import de.dlr.sc.virsat.model.extension.cefx.calculation.ModeVectorResult;
import de.dlr.sc.virsat.model.extension.cefx.calculation.ParameterSetter;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;

/**
 * test class for testing the parameter setter setting a specific parameter value for a mode in the CEF concept.
 * @author fisc_ph
 *
 */
public class ParameterSetterTest extends ATestCase {

	private static final double INIT_DEFAULT_VALUE = 10;
	private static final double INIT_IDLE_VALUE = 5;
	
	private Parameter parameter;
	private SystemMode idle;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	    // Create a parameter with some default data
	    
		parameter = new Parameter(conceptCEFX);
		parameter.setDefaultValue(INIT_DEFAULT_VALUE);
		
		Value idleValue = new Value(conceptCEFX);
		idle = new SystemMode(conceptCEFX);
		idleValue.setValue(INIT_IDLE_VALUE);
		idleValue.setMode(idle);
		parameter.getModeValues().add(idleValue);
	}
	
	@Test
	public void testSetNumberLiteral() {
		
		ParameterSetter parameterSetter = new ParameterSetter();
		
		/* Case parameter = numberLiteral */
		NumberLiteral nl = CalculationFactory.eINSTANCE.createNumberLiteral();
		nl.setValue("3");
		NumberLiteralResult nlr = new NumberLiteralResult(nl);
		
		parameterSetter.set(parameter.getTypeInstance(), nlr);
		assertEquals("Case parameter = numberLiteral: Default Value Set Correctly", Double.valueOf(nl.getValue()), parameter.getDefaultValue(), TEST_EPSILON);
		double newIdleValue = parameter.getModeValues().get(0).getValue();
		assertEquals("Case parameter = numberLiteral: Idle Value Set Correctly", Double.valueOf(nl.getValue()), newIdleValue, TEST_EPSILON);
		assertEquals("Make sure equation engine sets overwrite flag", parameter.getDefaultValueBean().getOverride(), true);
	}
	
	@Test
	public void testSetModeVector() {
		
		ParameterSetter parameterSetter = new ParameterSetter();

		/* Case parameter = modeVector */
		NumberLiteral defaultValue = CalculationFactory.eINSTANCE.createNumberLiteral();
		defaultValue.setValue("20");
		NumberLiteralResult defaultResult = new NumberLiteralResult(defaultValue);
		
		NumberLiteral idleValue = CalculationFactory.eINSTANCE.createNumberLiteral();
		idleValue.setValue("20");
		NumberLiteralResult idleResult = new NumberLiteralResult(idleValue);
		
		ModeVectorResult modeVector = new ModeVectorResult(defaultResult, null);
		modeVector.getResults().put(idleResult, idle.getTypeInstance());
		
		parameterSetter.set(parameter.getTypeInstance(), modeVector);
		assertEquals("Case parameter = modeVector: Default Value Set Correctly", Double.valueOf(defaultValue.getValue()), parameter.getDefaultValue(), TEST_EPSILON);
		double newIdleValue = parameter.getModeValues().get(0).getValue();
		Value newModeValue = parameter.getModeValues().get(0);
		
		assertEquals("Case parameter = modeVector: Idle Value Set Correctly", Double.valueOf(idleValue.getValue()), newIdleValue, TEST_EPSILON);
		
		parameterSetter.set(parameter.getTypeInstance(), modeVector);
		Value sameModeValue = parameter.getModeValues().get(0);

		assertEquals("The ModeValues should be exatcly the same", newModeValue, sameModeValue);
		assertEquals("There should be not additional Mode Value", 1, parameter.getModeValues().size());
		
		modeVector.getResults().remove(idleResult);
		
		parameterSetter.set(parameter.getTypeInstance(), modeVector);
		assertTrue("There should be no Mode Values", parameter.getModeValues().isEmpty());
	}

	@Test
	public void testIsApplicableFor() {
		ParameterSetter parameterSetter = new ParameterSetter();
		
		assertTrue("Applicable for CategoryAssignments of name Parameter", parameterSetter.isApplicableFor(parameter.getTypeInstance()));
		assertFalse("Not Applicable for ValuePropertyInstances", parameterSetter.isApplicableFor(parameter.getDefaultValueBean().getATypeInstance()));
		
		ComposedPropertyInstance cpi = PropertyinstancesFactory.eINSTANCE.createComposedPropertyInstance();
		cpi.setTypeInstance(parameter.getTypeInstance());
		
		assertTrue("Applicable for ComposedPropertyInstances containing a parameter", parameterSetter.isApplicableFor(cpi));
	}

}
