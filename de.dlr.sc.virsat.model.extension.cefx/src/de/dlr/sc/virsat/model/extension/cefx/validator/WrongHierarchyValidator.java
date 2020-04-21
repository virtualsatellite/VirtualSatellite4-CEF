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
import de.dlr.sc.virsat.model.dvlm.validator.IStructuralElementInstanceValidator;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.hierarchy.CefxHierarchyLevelChecker;

/**
 * Checks that an element with a level is in the correct place of the hierarchy.
 */
public class WrongHierarchyValidator implements IStructuralElementInstanceValidator {

	private VirSatValidationMarkerHelper vvmHelper = new VirSatValidationMarkerHelper();
	private CefxHierarchyLevelChecker levelChecker = new CefxHierarchyLevelChecker();

	@Override
	public boolean validate(StructuralElementInstance sei) {
		BeanStructuralElementInstance bean = new BeanStructuralElementInstance(sei);
		if (levelChecker.beanHasInapplicableLevel(bean)) {
			for (IBeanCategoryAssignment invalidCaBean : levelChecker.getLevelDefiningCategoryAssignments(bean)) {
				vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING,
						"Element is in the wrong place in the [System > SubSystem > Equipment] hierarchy.",
						invalidCaBean.getTypeInstance());
			}
			return false;
		}
		return true;
	}
}
