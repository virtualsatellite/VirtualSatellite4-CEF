/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.modesview;

import java.util.List;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;

/**
 * Helper class for system modes
 * @author muel_s8
 *
 */

public class ModeHelper {
	
	private static final String SYSTEM = "System";
	
	/**
	 * Private constructor
	 */
	
	private ModeHelper() {
		
	}
	
	/**
	 * Get the system modes of the System the passed sei is in
	 * @param sei the sei of some System
	 * @return All system modes of the sei of the passed system
	 */
	
	public static List<SystemMode> getSystemModes(StructuralElementInstance sei) {
		while (!sei.getType().getName().equals(SYSTEM) && sei.eContainer() != null) {
			sei = (StructuralElementInstance) sei.eContainer();
		}
		
		BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
		return beanCaHelper.getAllBeanCategories(sei, SystemMode.class);
	}
	
	/**
	 * Get a system mode by name
	 * @param sei the structural element instance of the current system
	 * @param modeName name of the mode
	 * @return null if no mode found in the system, the mode otherwise
	 */
	public static SystemMode getModeByName(StructuralElementInstance sei, String modeName) {
		List<SystemMode> modes = getSystemModes(sei);
		
		for (SystemMode mode : modes) {
			if (mode.getName().equals(modeName)) {
				return mode;
			}
		}
		
		return null;
	}
}
