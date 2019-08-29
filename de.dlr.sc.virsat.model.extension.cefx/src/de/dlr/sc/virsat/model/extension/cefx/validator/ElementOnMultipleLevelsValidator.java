/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.validator;

import org.eclipse.core.resources.IMarker;

import de.dlr.sc.virsat.build.marker.util.VirSatValidationMarkerHelper;
import de.dlr.sc.virsat.build.validator.external.IStructuralElementInstanceValidator;
import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.hierarchy.CefxHierarchyLevelChecker;

/**
 * Checks that an element doesn't have categories from different conceptual levels together
 * e.g. System parameters and SubSystem parameters
 */
public class ElementOnMultipleLevelsValidator implements IStructuralElementInstanceValidator {

	private VirSatValidationMarkerHelper vvmHelper = new VirSatValidationMarkerHelper();
	private CefxHierarchyLevelChecker levelChecker = new CefxHierarchyLevelChecker();

	@Override
	public boolean validate(StructuralElementInstance sei) {
		if (levelChecker.beanHasAmbiguousLevel(new BeanStructuralElementInstance(sei))) {
			vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, "Element mixes parameters from different levels (System, SubSystem, Equipment).", sei);
			return false;
		}
		return true;
	}
}
