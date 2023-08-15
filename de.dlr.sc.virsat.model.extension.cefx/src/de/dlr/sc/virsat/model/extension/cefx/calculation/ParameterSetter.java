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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.dlr.sc.virsat.model.calculation.compute.IExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.IResultSetter;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralSetter;
import de.dlr.sc.virsat.model.calculation.compute.extensions.UnresolvedExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.problem.EvaluationProblem;
import de.dlr.sc.virsat.model.calculation.compute.problem.UnknownExpressionProblem;
import de.dlr.sc.virsat.model.dvlm.calculation.CalculationFactory;
import de.dlr.sc.virsat.model.dvlm.calculation.NumberLiteral;
import de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;

/**
 * A setter implementation for assigning "Parameter" Categories the result of some
 * calculation
 * @author muel_s8
 *
 */

public class ParameterSetter implements IResultSetter {
	
	@Override
	public List<EvaluationProblem> set(ATypeInstance instance, IExpressionResult result) {
		CategoryAssignment ca;
		if (instance instanceof ComposedPropertyInstance) {
			ComposedPropertyInstance cpi = (ComposedPropertyInstance) instance;
			ca = cpi.getTypeInstance();
		} else {
			ca = (CategoryAssignment) instance;
		}
		
		if (result instanceof NumberLiteralResult) {
			return set(ca, (NumberLiteralResult) result);
		} else if (result instanceof ModeVectorResult) {
			return set(ca, (ModeVectorResult) result);
		} else if (result instanceof UnresolvedExpressionResult) {
			NumberLiteral nl = CalculationFactory.eINSTANCE.createNumberLiteral();
			nl.setValue(String.valueOf(Double.NaN));
			return set(ca, new NumberLiteralResult(nl));
		}
		
		return Collections.singletonList(new UnknownExpressionProblem(instance, result));
	}
	
	/**
	 * Handles the case of assgning a single literal to a parameter
	 * @param ca the parameter
	 * @param result the result we want to assign
	 * @return a set of problems that has occurred when trying to perform the set, empty if none occurred
	 */
	
	private List<EvaluationProblem> set(CategoryAssignment ca, NumberLiteralResult result) {	
		List<EvaluationProblem> setProblems = new ArrayList<>();
		NumberLiteralSetter nls = new NumberLiteralSetter();
		Parameter parameter = new Parameter(ca);
		
		// Assign the value to the default mode
		
		UnitValuePropertyInstance pviDefault = (UnitValuePropertyInstance) parameter.getDefaultValueBean().getTypeInstance();
		setProblems.addAll(nls.set(pviDefault, result));
		pviDefault.setOverride(true);
		
		// Assign the value to the additional modes
		
		parameter.getModeValues().forEach((value) -> {
			UnitValuePropertyInstance vpi = value.getValueBean().getTypeInstance();
			
			vpi.setUnit(pviDefault.getUnit());
			vpi.setOverride(true);
			setProblems.addAll(nls.set(vpi, result));
		});
		
		return setProblems;
	}
	
	/**
	 * Handles the case where we want to assign a parameter a mode vector
	 * @param ca the parameter
	 * @param result the result we want to assign
	 * @return a set of problems that has occurred when trying to perform the set, empty if none occurred
	 */
	private List<EvaluationProblem> set(CategoryAssignment ca, ModeVectorResult result) {
		List<EvaluationProblem> setProblems = new ArrayList<>();
		NumberLiteralSetter nls = new NumberLiteralSetter();
		Parameter parameter = new Parameter(ca);
		Concept concept = parameter.getConcept();
		
		// Assign the value to the default mode
		
		UnitValuePropertyInstance pviDefault = parameter.getDefaultValueBean().getTypeInstance();
		setProblems.addAll(nls.set(pviDefault, result.getResult(ModeVectorResult.DEFAULT_MODE)));
		pviDefault.setOverride(true);
		
		// Assign the values to the other modes
		Collection<CategoryAssignment> modes = result.getResults().values();
		List<Value> values = parameter.getModeValues();
		
		Set<Value> insertedPis = new HashSet<>();
		
		for (CategoryAssignment mode : modes) {
			if (mode != ModeVectorResult.DEFAULT_MODE) {
				// Grab the Value CategoryAssignment for each array entry
				SystemMode systemMode = new SystemMode(mode);
				
				// Check if we already have a mode value for this mode, if not, create it
				Value modeValue = values.stream().filter(value -> {
					return value.getMode() != null && value.getMode().equals(systemMode);
				}).findFirst().orElseGet(() -> new Value(concept));
				
				insertedPis.add(modeValue);
				values.add(modeValue);
				
				// Extract the mode and value properties
				modeValue.setMode(systemMode);
				
				UnitValuePropertyInstance vpi = modeValue.getValueBean().getTypeInstance();
				NumberLiteralResult literalResult = result.getResult(mode);
				if (literalResult != null) {
					vpi.setUnit(pviDefault.getUnit());
					vpi.setOverride(true);
					setProblems.addAll(nls.set(vpi, literalResult));
				}
			}
		}
		
		// Remove old mode values that have not been modified
		values.retainAll(insertedPis);
		
		return setProblems;
	}
	
	@Override
	public boolean isApplicableFor(ATypeInstance instance) {
		if (instance instanceof CategoryAssignment) {
			CategoryAssignment ca = (CategoryAssignment) instance;
			return ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME);
		} else if (instance instanceof ComposedPropertyInstance) {
			ComposedPropertyInstance cpi = (ComposedPropertyInstance) instance;
			CategoryAssignment ca = cpi.getTypeInstance();
			return ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME);
		}
		return false;
	}

	@Override
	public List<ATypeInstance> getAffectedTypeInstances(ATypeInstance instance) {
		Parameter parameter = new Parameter((CategoryAssignment) instance);
		
		List<ATypeInstance> affectedTypeInstances = new ArrayList<>();
		affectedTypeInstances.add(parameter.getDefaultValueBean().getTypeInstance());
		affectedTypeInstances.add(parameter.getTypeInstance());
		
		for (Value value : parameter.getModeValues()) {
			affectedTypeInstances.add(value.getValueBean().getTypeInstance());
		}
		
		return affectedTypeInstances;
	}
}
