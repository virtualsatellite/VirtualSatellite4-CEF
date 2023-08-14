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

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.calculation.compute.EquationHelper;
import de.dlr.sc.virsat.model.calculation.compute.IExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.dvlm.calculation.ALeftOpRightExpression;
import de.dlr.sc.virsat.model.dvlm.calculation.CalculationFactory;
import de.dlr.sc.virsat.model.dvlm.calculation.Equation;
import de.dlr.sc.virsat.model.dvlm.calculation.Function;
import de.dlr.sc.virsat.model.dvlm.calculation.IEquationSectionContainer;
import de.dlr.sc.virsat.model.dvlm.calculation.MathOperator;
import de.dlr.sc.virsat.model.dvlm.calculation.NumberLiteral;
import de.dlr.sc.virsat.model.dvlm.calculation.SetFunction;
import de.dlr.sc.virsat.model.dvlm.categories.CategoriesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.roles.UserRegistry;
import de.dlr.sc.virsat.model.extension.cefx.calculation.ModeVectorEvaluator;
import de.dlr.sc.virsat.model.extension.cefx.calculation.ModeVectorResult;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

/**
 * Test implementation of ModeVectorEvaluator
 * @author muel_s8
 *
 */

public class ModeVectorEvaluatorTest extends ATestCase {
	
	private static final double INIT_DEFAULT_VALUE = 45;
	private static final double INIT_IDLE_VALUE = 215;
	private static final double VALUE = -3;
	
	private NumberLiteral defaultValue;
	private CategoryAssignment idleMode;
	private NumberLiteral idleValue;
	
	private NumberLiteralResult defaultResult;
	private NumberLiteralResult idleResult;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		// Create values for default and idle mode
		defaultValue = CalculationFactory.eINSTANCE.createNumberLiteral();
		defaultValue.setValue(String.valueOf(INIT_DEFAULT_VALUE));
		
		idleMode = CategoriesFactory.eINSTANCE.createCategoryAssignment();
		idleValue = CalculationFactory.eINSTANCE.createNumberLiteral();
		idleValue.setValue(String.valueOf(INIT_IDLE_VALUE));
		
		defaultResult = new NumberLiteralResult(defaultValue);
		idleResult = new NumberLiteralResult(idleValue);
		
		UserRegistry.getInstance().setSuperUser(true);
	}
	
	@After
	public void tearDown() throws CoreException {
		UserRegistry.getInstance().setSuperUser(false);
	}
	
	@Test
	public void testCaseAOpRightExpression() {

		ModeVectorEvaluator evaluator = new ModeVectorEvaluator();
		
		ModeVectorResult modeVector = new ModeVectorResult(defaultResult, null);
		modeVector.getResults().put(idleResult, idleMode);
		
		Function rightExpression = CalculationFactory.eINSTANCE.createFunction();
		rightExpression.setOperator(MathOperator.SQRT);
		
		/* Case: operation modeVector */
		modeVector = (ModeVectorResult) evaluator.caseAOpRightExpression(rightExpression, modeVector);
		assertEquals("Case operation on single mode vector: Default Value", Math.sqrt(INIT_DEFAULT_VALUE), Double.valueOf(modeVector.getResult(null).getNumberLiteral().getValue()), TEST_EPSILON);
		assertEquals("Case operation on single mode vector: Idle Value", Math.sqrt(INIT_IDLE_VALUE), Double.valueOf(modeVector.getResult(idleMode).getNumberLiteral().getValue()), TEST_EPSILON);
	}

	@Test
	public void testCaseALeftOpRightExpression() {
		ModeVectorEvaluator evaluator = new ModeVectorEvaluator();
		
		NumberLiteral value = CalculationFactory.eINSTANCE.createNumberLiteral();
		value.setValue(String.valueOf(VALUE));
		NumberLiteralResult numberLiteral = new NumberLiteralResult(value);
		
		ModeVectorResult modeVector1 = new ModeVectorResult(defaultResult, null);
		modeVector1.getResults().put(idleResult, idleMode);
		ModeVectorResult modeVector2 = new ModeVectorResult(defaultResult, null);
		
		ALeftOpRightExpression leftOpRightExpression = CalculationFactory.eINSTANCE.createMultiplicationAndDivision();
		leftOpRightExpression.setOperator(MathOperator.MULTIPLY);
		
		/* Case: modeVector operation modeVector */
		ModeVectorResult result = (ModeVectorResult) evaluator.caseALeftOpRightExpression(leftOpRightExpression, modeVector1, modeVector2);
		assertEquals("Case operation between 2 mode vectors: Default Value", INIT_DEFAULT_VALUE * INIT_DEFAULT_VALUE, Double.valueOf(result.getResult(null).getNumberLiteral().getValue()), TEST_EPSILON);
		assertEquals("Case operation between 2 mode vectors: Idle Value", INIT_IDLE_VALUE * INIT_DEFAULT_VALUE, Double.valueOf(result.getResult(idleMode).getNumberLiteral().getValue()), TEST_EPSILON);
	
		/* Case: numberLiteral operation modeVector */
		result = (ModeVectorResult) evaluator.caseALeftOpRightExpression(leftOpRightExpression, numberLiteral, modeVector1);
		assertEquals("Case operation between mode vector and number literal: Default Value", INIT_DEFAULT_VALUE * VALUE, Double.valueOf(result.getResult(null).getNumberLiteral().getValue()), TEST_EPSILON);
		assertEquals("Case operation between mode vector and number literal: Idle Value", INIT_IDLE_VALUE * VALUE, Double.valueOf(result.getResult(idleMode).getNumberLiteral().getValue()), TEST_EPSILON);
		
		/* Case: modeVector operation numberLiteral */
		result = (ModeVectorResult) evaluator.caseALeftOpRightExpression(leftOpRightExpression, modeVector1, numberLiteral);
		assertEquals("Case operation between mode vector and number literal (swapped): Default Value", INIT_DEFAULT_VALUE * VALUE, Double.valueOf(result.getResult(null).getNumberLiteral().getValue()), TEST_EPSILON);
		assertEquals("Case operation between mode vector and number literal (swapped): Idle Value", INIT_IDLE_VALUE * VALUE, Double.valueOf(result.getResult(idleMode).getNumberLiteral().getValue()), TEST_EPSILON);
	}
	
	@Test
	public void testCaseAAdvancesFunction() {
		ModeVectorEvaluator evaluator = new ModeVectorEvaluator();
		
		ModeVectorResult modeVector1 = new ModeVectorResult(defaultResult, null);
		modeVector1.getResults().put(idleResult, idleMode);
		ModeVectorResult modeVector2 = new ModeVectorResult(defaultResult, null);
		List<IExpressionResult> set = Arrays.asList(modeVector1, modeVector2);
		
		SetFunction setFunction = CalculationFactory.eINSTANCE.createSetFunction();
		setFunction.setOperator("summary");
		
		/* Case: modeVector operation modeVector */
		ModeVectorResult result = (ModeVectorResult) evaluator.caseAAdvancedFunction(setFunction, set);
		assertEquals("Case set operation with 2 mode vectors: Default Value", INIT_DEFAULT_VALUE + INIT_DEFAULT_VALUE, Double.valueOf(result.getResult(null).getNumberLiteral().getValue()), TEST_EPSILON);
		assertEquals("Case set operation with 2 mode vectors: Idle Value", INIT_IDLE_VALUE + INIT_DEFAULT_VALUE, Double.valueOf(result.getResult(idleMode).getNumberLiteral().getValue()), TEST_EPSILON);
	}
	
	private static final double DEFAULT_VALUE = 50;
	private static final double IDLE_VALUE = 10;
	private static final double DEFAULT_MARGIN = 0.1;
	private static final double EXPECTED_DEFAULT = DEFAULT_VALUE * (1 + DEFAULT_MARGIN);
	private static final double EXPECTED_IDLE = IDLE_VALUE * (1 + DEFAULT_MARGIN);
	
	@Test
	public void testWithConcept1() {
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentMassParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, SystemMode.class.getSimpleName()).setIsApplicableForAll(true);
		
		ElementConfiguration equipment = new ElementConfiguration(conceptPS);
		
		EquipmentMassParameters equipMassParams = new EquipmentMassParameters(conceptCEFX);
		EquipmentParameters equipParams = new EquipmentParameters(conceptCEFX);
		SystemMode idleMode = new SystemMode(conceptCEFX);
		
		equipment.add(equipParams);
		equipment.add(equipMassParams);
		
		equipMassParams.getMass().setDefaultValue(DEFAULT_VALUE);
		equipParams.setMarginMaturity(DEFAULT_MARGIN);
		
		Value idleValue = new Value(conceptCEFX);
		idleValue.setMode(idleMode);
		idleValue.setValue(IDLE_VALUE);
		equipMassParams.getMass().getModeValues().add(idleValue);
		
		IEquationSectionContainer container = (IEquationSectionContainer) equipMassParams.getATypeInstance();
		List<Equation> equations = container.getEquationSection().getEquations();
		
		EquationHelper eqHelper = new EquationHelper();
		eqHelper.evaluate(equations, UserRegistry.getInstance());
		
		assertEquals("Default value correct", EXPECTED_DEFAULT, equipMassParams.getMassTotalWithMargin().getDefaultValue(), TEST_EPSILON);
		assertEquals("Idle value correct", EXPECTED_IDLE, equipMassParams.getMassTotalWithMargin().getModeValues().get(0).getValue(), TEST_EPSILON);
	}
	
	@Test
	public void testWithConcept2() {
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentPowerParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, SystemMode.class.getSimpleName()).setIsApplicableForAll(true);
		
		ElementConfiguration equipment = new ElementConfiguration(conceptPS);
		EquipmentParameters equipParams = new EquipmentParameters(conceptCEFX);
		EquipmentPowerParameters equipPowerParams = new EquipmentPowerParameters(conceptCEFX);
		SystemMode idleMode = new SystemMode(conceptCEFX);
		
		equipPowerParams.getPowerPerUnitOn().setDefaultValue(DEFAULT_VALUE);
		equipParams.setMarginMaturity(DEFAULT_MARGIN);
		
		equipment.add(equipParams);
		equipment.add(equipPowerParams);
		
		Value idleValue = new Value(conceptCEFX);
		idleValue.setMode(idleMode);
		idleValue.setValue(IDLE_VALUE);
		equipPowerParams.getPowerPerUnitOn().getModeValues().add(idleValue);
		
		IEquationSectionContainer container = (IEquationSectionContainer) equipPowerParams.getATypeInstance();
		List<Equation> equations = container.getEquationSection().getEquations();
		
		EquationHelper eqHelper = new EquationHelper();
		eqHelper.evaluate(equations, UserRegistry.getInstance());
		
		assertEquals("Default value correct", EXPECTED_DEFAULT, equipPowerParams.getPowerPerUnitOnWithMargin().getDefaultValue(), TEST_EPSILON);
		assertEquals("Idle value correct", EXPECTED_IDLE, equipPowerParams.getPowerPerUnitOnWithMargin().getModeValues().get(0).getValue(), TEST_EPSILON);
	}
	
	@Test
	public void testWithConceptNaN() {
		SystemMassParameters systemMassParams = new SystemMassParameters(conceptCEFX);
		
		// Since SystemParams is nowhere defined, performing the calculations should yield NaN as in particular
		// the system margin is not defined
		
		IEquationSectionContainer container = (IEquationSectionContainer) systemMassParams.getATypeInstance();
		List<Equation> equations = container.getEquationSection().getEquations();
		
		EquationHelper eqHelper = new EquationHelper();
		eqHelper.evaluate(equations, UserRegistry.getInstance());
		
		// Check if NaN has spread correctly
		
		assertEquals("Default value NaN", Double.NaN, systemMassParams.getMassLaunch().getDefaultValue(), TEST_EPSILON);
	}

}
