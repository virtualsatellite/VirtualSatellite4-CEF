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

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.dvlm.calculation.CalculationFactory;
import de.dlr.sc.virsat.model.dvlm.calculation.NumberLiteral;
import de.dlr.sc.virsat.model.dvlm.categories.CategoriesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.extension.cefx.calculation.ModeVectorResult;

/**
 * Test Case for ModeVectorResult implementation
 * @author muel_s8
 *
 */

public class ModeVectorResultTest extends ATestCase {

	private NumberLiteral n1;
	private NumberLiteral n2;
	private NumberLiteral n3;
	
	private NumberLiteralResult n1Result;
	private NumberLiteralResult n2Result;
	private NumberLiteralResult n3Result;
	
	private CategoryAssignment idleMode;
	private CategoryAssignment leopMode;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		n1 = CalculationFactory.eINSTANCE.createNumberLiteral();
		n1.setValue("10");
		
		n2 = CalculationFactory.eINSTANCE.createNumberLiteral();
		n2.setValue("42");
		
		n3 = CalculationFactory.eINSTANCE.createNumberLiteral();
		n3.setValue("0.33");
		
		n1Result = new NumberLiteralResult(n1);
		n2Result = new NumberLiteralResult(n2);
		n3Result = new NumberLiteralResult(n3);
		
		idleMode = CategoriesFactory.eINSTANCE.createCategoryAssignment();
		idleMode.setName("idle");
		
		leopMode = CategoriesFactory.eINSTANCE.createCategoryAssignment();
		leopMode.setName("leop");
	}
	
	@Test
	public void testGetResult() {
		ModeVectorResult modeVector = new ModeVectorResult(n1Result, null);
		modeVector.getResults().put(n2Result, idleMode);
		
		/* Check that everything we added can be retrieved */
		assertEquals("Case default mode", n1.getValue(), modeVector.getResult(null).getNumberLiteral().getValue());
		assertEquals("Case idle mode", n2.getValue(), modeVector.getResult(idleMode).getNumberLiteral().getValue());
	}

	@Test
	public void testToString() {
		ModeVectorResult modeVector = new ModeVectorResult(n1Result, null);
		modeVector.getResults().put(n2Result, idleMode);
		modeVector.getResults().put(n3Result, leopMode);
		
		String expected = "{ default: " + Double.valueOf(n1.getValue()) +  ", " 
				+ idleMode.getName() + ": " +  Double.valueOf(n2.getValue()) + ", " 
				+ leopMode.getName() + ": " +  Double.valueOf(n3.getValue()) + " }";
		assertEquals("String representation correct", expected, modeVector.toString());
	}

}
