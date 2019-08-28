/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.calculation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.dlr.sc.virsat.model.calculation.compute.IExpressionEvaluator;
import de.dlr.sc.virsat.model.calculation.compute.IExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResultHelper;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralSetFunctionHelper;
import de.dlr.sc.virsat.model.dvlm.calculation.AAdvancedFunction;
import de.dlr.sc.virsat.model.dvlm.calculation.ALeftOpRightExpression;
import de.dlr.sc.virsat.model.dvlm.calculation.AOpRightExpression;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;

/**
 * An implementation handling the evaluaiton of operations for mode vectors
 * @author muel_s8
 *
 */

public class ModeVectorEvaluator implements IExpressionEvaluator {
	
	@Override
	public IExpressionResult caseAOpRightExpression(AOpRightExpression object, IExpressionResult right) {
		if (right instanceof ModeVectorResult) {
			return caseAOpRightExpression(object, (ModeVectorResult) right);
		}
		
		return null;
	}

	@Override
	public IExpressionResult caseALeftOpRightExpression(ALeftOpRightExpression object, IExpressionResult left, IExpressionResult right) {
		if (left instanceof NumberLiteralResult && right instanceof ModeVectorResult) {
			return caseALeftOpRightExpression(object, (NumberLiteralResult) left, (ModeVectorResult) right);
		} else if (right instanceof NumberLiteralResult && left instanceof ModeVectorResult) {
			return caseALeftOpRightExpression(object, (ModeVectorResult) left, (NumberLiteralResult) right);
		} else if (right instanceof ModeVectorResult && left instanceof ModeVectorResult) {
			return caseALeftOpRightExpression(object, (ModeVectorResult) left, (ModeVectorResult) right);
		}
		return null;
	}
	
	@Override
	public IExpressionResult caseAAdvancedFunction(AAdvancedFunction advancedFunction, List<IExpressionResult> set) {
		
		// Grab all the modes and if one of the inputs is not a mode vector result or a number literal result, reject
		// to perform the computation with this evaluator
		Set<CategoryAssignment> modes = new HashSet<>();
		for (IExpressionResult input : set) {
			if (input instanceof ModeVectorResult) {
				modes.addAll(((ModeVectorResult) input).getResults().values());
			} else if (input instanceof NumberLiteralResult) {
				modes.add(ModeVectorResult.DEFAULT_MODE);
			} else {
				return null;
			}
		}
		
		ModeVectorResult result = new ModeVectorResult();
		
		// Now we want to apply the set function to all numbers sharing a mode
		// or if the mode is not there, use the default mode
		modes.forEach((mode) -> {
			List<NumberLiteralResult> numberLiteralResults = new ArrayList<>();
			
			// Grab all the fitting literals and throw them into a collection
			set.forEach((input) -> {
				NumberLiteralResult literalResult = null;
				
				if (input instanceof ModeVectorResult) {
					ModeVectorResult modeVector = (ModeVectorResult) input;
					literalResult = modeVector.getResult(mode);
					
					if (literalResult == null) {
						literalResult = modeVector.getResult(ModeVectorResult.DEFAULT_MODE);
					}
				} else {
					literalResult = (NumberLiteralResult) input;
				}
				
				numberLiteralResults.add(literalResult);
			});
			
			// Perform the actual computation
			NumberLiteralSetFunctionHelper setHelper = new NumberLiteralSetFunctionHelper(numberLiteralResults);
			NumberLiteralResult literalResult = setHelper.applySetOperator(advancedFunction);
			
			// Memorize the result together with the associated mode
			result.getResults().put(literalResult, mode);
		});

		return result;
	}

	
	/**
	 * Handles the case (operation modeVector)
	 * @param object the operation
	 * @param right the mode vector
	 * @return the result of the calculation
	 */
	
	private IExpressionResult caseAOpRightExpression(AOpRightExpression object, ModeVectorResult right) {
		ModeVectorResult result = new ModeVectorResult();
		
		right.getResults().keySet().forEach((rightResult) -> {
			NumberLiteralResultHelper numbResultHelper = new NumberLiteralResultHelper(rightResult);
			NumberLiteralResult resultLiteral = numbResultHelper.applyMathOperator(object.getOperator());
			result.getResults().put(resultLiteral, right.getResults().get(rightResult));
		});
		
		return result;
	}
	
	/**
	 * Handles the case (modeVector1 operation modeVector2)
	 * @param object the operation
	 * @param left left hand side mode Vector
	 * @param right right hand side mode Vector
	 * @return the result of the calculation
	 */
	
	private IExpressionResult caseALeftOpRightExpression(ALeftOpRightExpression object, ModeVectorResult left, ModeVectorResult right) {
		ModeVectorResult result = new ModeVectorResult();
		
		Set<CategoryAssignment> modes = new HashSet<>();
		modes.addAll(left.getResults().values());
		modes.addAll(right.getResults().values());
		
		// For all modes apply the operation to values on both sides having the mode
		// Or if a side does not have the mode then combine it with the default mode
		modes.forEach((mode) -> {
			
			NumberLiteralResult leftResult = left.getResult(mode);
			NumberLiteralResult rightResult = right.getResult(mode);
			
			if (leftResult == null) {
				leftResult = left.getResult(ModeVectorResult.DEFAULT_MODE);
			}
			
			if (rightResult == null) {
				rightResult = right.getResult(ModeVectorResult.DEFAULT_MODE);
			}
			
			NumberLiteralResultHelper numbResultHelper = new NumberLiteralResultHelper(leftResult);
			NumberLiteralResult resultLiteral = numbResultHelper.applyMathOperator(object.getOperator(), rightResult);
			result.getResults().put(resultLiteral, mode);
		});
		
		return result;
	}
	
	/**
	 * Handles the case (numberLiteral operation modeVector)
	 * @param object the operation
	 * @param left the numberLiteral
	 * @param right the modeVector
	 * @return the result of the calculation
	 */
	
	private IExpressionResult caseALeftOpRightExpression(ALeftOpRightExpression object, NumberLiteralResult left, ModeVectorResult right) {
		ModeVectorResult result = new ModeVectorResult();
		
		NumberLiteralResultHelper numbHelper = new NumberLiteralResultHelper(left);
		
		right.getResults().keySet().forEach((resultRight) -> {
			NumberLiteralResult resultLiteral = numbHelper.applyMathOperator(object.getOperator(), resultRight);
			result.getResults().put(resultLiteral, right.getResults().get(resultRight));
		});
		
		return result;
	}
	
	/**
	 * Handles the case (numberLiteral operation modeVector)
	 * @param object the operation
	 * @param left the numberLiteral
	 * @param right the modeVector
	 * @return the result of the calculation
	 */
	
	private IExpressionResult caseALeftOpRightExpression(ALeftOpRightExpression object, ModeVectorResult left, NumberLiteralResult right) {
		ModeVectorResult result = new ModeVectorResult();
		
		left.getResults().keySet().forEach((resultLeft) -> {
			NumberLiteralResultHelper numbHelper = new NumberLiteralResultHelper(resultLeft);
			NumberLiteralResult resultLiteral = numbHelper.applyMathOperator(object.getOperator(), right);
			result.getResults().put(resultLiteral, left.getResults().get(resultLeft));
		});
		
		return result;
	}
}
