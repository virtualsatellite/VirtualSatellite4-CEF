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

import org.eclipse.emf.ecore.EObject;

import de.dlr.sc.virsat.model.calculation.compute.IExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.IInputGetter;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.ValuePropertyGetter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;

/**
 * Getter implementation for getting the mode vector of a parameter
 * @author muel_s8
 *
 */

public class ParameterGetter implements IInputGetter {	
	
	private ValuePropertyGetter vpg = new ValuePropertyGetter();
	
	@Override
	public IExpressionResult get(EObject input) {
		if (input instanceof CategoryAssignment) {
			return get((CategoryAssignment) input);
		}
		return null;
	}
	
	/**
	 * Handles the case where we have a parameter get
	 * @param ca the parameter
	 * @return the mode vector of the parameter
	 */
	
	private IExpressionResult get(CategoryAssignment ca) {
		// Create a value for the default mode and then for each additionally specified mode
		if (ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
			Parameter parameter = new Parameter(ca);
			
			// Grab the value for the default mode
			
			ValuePropertyInstance pviDefault = parameter.getDefaultValueBean().getTypeInstance();
			NumberLiteralResult defaultResult = (NumberLiteralResult) vpg.get(pviDefault);
			ModeVectorResult expressionResult = new ModeVectorResult(defaultResult, null);
			
			// For each array entry add a number literal and annotate its meta data with the mode
			parameter.getModeValues().forEach((value) -> { 
				// Extract the value and the mode reference
				ValuePropertyInstance pviModes = value.getValueBean().getTypeInstance();
			
				NumberLiteralResult result = (NumberLiteralResult) vpg.get(pviModes);
				
				// Memorize the information in the result
				CategoryAssignment mode = (CategoryAssignment) value.getMode().getTypeInstance();
				expressionResult.getResults().put(result, mode);
			});
			
			return expressionResult;
		}
		
		return null;
	}

}
