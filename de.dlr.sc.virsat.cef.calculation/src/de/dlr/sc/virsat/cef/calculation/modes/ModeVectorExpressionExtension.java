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

import java.util.Arrays;
import java.util.List;

import de.dlr.sc.virsat.model.calculation.compute.IExpressionEvaluator;
import de.dlr.sc.virsat.model.calculation.compute.IExpressionExtender;
import de.dlr.sc.virsat.model.calculation.compute.IInputGetter;
import de.dlr.sc.virsat.model.calculation.compute.IResultSetter;

/**
 * Registers the extensions necessary to work with mode vectors
 * @author muel_s8
 *
 */

public class ModeVectorExpressionExtension implements IExpressionExtender {
	@Override
	public List<IResultSetter> getTypeInstanceSetters() {
		return Arrays.asList(new ParameterSetter());
	}

	@Override
	public List<IInputGetter> getInputGetters() {
		return Arrays.asList(new ParameterGetter());
	}

	@Override
	public List<IExpressionEvaluator> getExpressionEvaluators() {
		return Arrays.asList(new ModeVectorEvaluator());
	}
}
