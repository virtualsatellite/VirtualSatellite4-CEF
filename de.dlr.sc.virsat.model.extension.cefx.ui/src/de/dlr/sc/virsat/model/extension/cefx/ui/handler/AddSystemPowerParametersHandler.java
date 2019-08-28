/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.handler;

import org.eclipse.emf.ecore.EObject;

import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.hierarchy.CefxHierarchyLevelChecker;

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class AddSystemPowerParametersHandler extends AAddSystemPowerParametersHandler {
	
	@Override
	public boolean isEnabled() {
		if (super.isEnabled()) {
			EObject element = getFirstSelectedObject();
			if (element instanceof StructuralElementInstance) {
				BeanStructuralElementInstance bean = new BeanStructuralElementInstance((StructuralElementInstance) element);
				return new CefxHierarchyLevelChecker().canAddSystemCategory(bean);
			}
		}
		return false;
	}
	
}
