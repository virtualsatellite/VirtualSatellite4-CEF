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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.dlr.sc.virsat.model.calculation.compute.IExpressionResult;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralHelper;
import de.dlr.sc.virsat.model.calculation.compute.extensions.NumberLiteralResult;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;

/**
 * Represents a mode vector, that is a vector of number literals with their associated mode.
 * If no mode or null is associated with a number literal, then the default mode is associated 
 * with it.
 * @author muel_s8
 *
 */

public class ModeVectorResult implements IExpressionResult {
	
	public static final CategoryAssignment DEFAULT_MODE = null;
	public static final String DEFAULT_MODE_NAME = "default";
	
	private Map<NumberLiteralResult, CategoryAssignment> results = new LinkedHashMap<>();
	
	/**
	 * Empty mode vector
	 */
	public ModeVectorResult() {
		
	}
	
	/**
	 * Mode vector with an initial number and associated mode.
	 * Typically for initialzing the value for the default mode
	 * @param literalResult number literal result
	 * @param mode the mode associated with the literal result
	 */
	public ModeVectorResult(NumberLiteralResult literalResult, CategoryAssignment mode) {
		results.put(literalResult, mode);
	}

	/**
	 * Get all results wrapped by this class
	 * @return all number results and their associated modes
	 */
	public Map<NumberLiteralResult, CategoryAssignment> getResults() {
		return results;
	}
	
	/**
	 * Get the result of a specific mode
	 * @param mode the mode we wish to know the result of
	 * @return the number literal we have calculated for this mode. null if no fitting
	 * number literal is found.
	 */
	public NumberLiteralResult getResult(CategoryAssignment mode) {
		for (Entry<NumberLiteralResult, CategoryAssignment> entry : results.entrySet()) {
			if (entry.getValue() == mode) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	@Override
	public boolean equals(IExpressionResult obj, double eps) {
		if (obj instanceof ModeVectorResult) {
			ModeVectorResult mvr = (ModeVectorResult) obj;
			
			Set<CategoryAssignment> modes = new HashSet<>();
			modes.addAll(getResults().values());
			modes.addAll(mvr.getResults().values());
			
			for (CategoryAssignment mode : modes) {
				NumberLiteralResult result1 = getResult(mode);
				NumberLiteralResult result2 = mvr.getResult(mode);
				
				// If the value isn't defined in either one of them then they are not equal
				if (result1 == null || result2 == null) {
					return false;
				}
				
				// If they differ in value then they are not equal
				if (!result1.equals(result2, eps)) {
					return false;
				}
			}
			
			return true;
		} else if (obj instanceof NumberLiteralResult) {
			NumberLiteralResult nlr = (NumberLiteralResult) obj;
			
			for (CategoryAssignment mode : getResults().values()) {
				NumberLiteralResult result = getResult(mode);

				// If they differ in value then they are not equal
				if (!result.equals(nlr, eps)) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		if (results.size() > 1) {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ");
			
			List<NumberLiteralResult> resultsSorted = new ArrayList<>(results.keySet());
			Collections.sort(resultsSorted, resultComparator);
			
			resultsSorted.forEach((literalResult) -> {
				if (sb.length() > 2) {
					sb.append(", ");
				}
				
				CategoryAssignment mode = results.get(literalResult);
				String modeName = getModeName(mode);
				sb.append(modeName);
				
				sb.append(": ");
				sb.append(new NumberLiteralHelper(literalResult.getNumberLiteral()).getValue());
			});
			sb.append(" }");
			return sb.toString();
		} else {
			NumberLiteralResult defaultLiteralResult = results.keySet().iterator().next();
			return defaultLiteralResult.getNumberLiteral().getValue();
		}
	}
	
	/**
	 * Gets the name of the mode while also taking the existence of the DEFAULT_MODE (=null)
	 * into consideration.
	 * @param mode the mode we want the name of
	 * @return the name of the mode, "default" if it is the default mode
	 */
	private String getModeName(CategoryAssignment mode) {
		if (mode != DEFAULT_MODE) {
			return mode.getName();
		} else {
			return DEFAULT_MODE_NAME;
		}
	}
	
	private Comparator<NumberLiteralResult> resultComparator = new Comparator<NumberLiteralResult>() {
		@Override
		public int compare(NumberLiteralResult result1, NumberLiteralResult result2) {
			// We sort the results according to their associated modes
			String modeName1 = getModeName(results.get(result1));
			String modeName2 = getModeName(results.get(result2));
			
			// The default mode has priority and comes first
			if (modeName1.equals(DEFAULT_MODE_NAME)) {
				return -1;
			} else if (modeName2.equals(DEFAULT_MODE_NAME)) {
				return 1;
			} else {
				// And then we sort according to the lexicographical order of the mode names
				return modeName1.compareTo(modeName2);
			}
		}
	};
	
}
