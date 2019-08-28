/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.hierarchy;

import java.util.ArrayList;

import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.level.HierarchyLevelChecker;
import de.dlr.sc.virsat.model.concept.types.structural.level.IHierarchyLevel;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParameters;

/**
 * Class to check the model tree hierarchy of SYSTEM, SUBSYSTEM and EQUIPMENT
 * 
 */
public class CefxHierarchyLevelChecker {

	private HierarchyLevelChecker levelChecker;
	private CefxHierarchyLevel systemLevel;
	private CefxHierarchyLevel subSystemLevel;
	private CefxHierarchyLevel equipmentLevel;

	/**
	 * Default constructor
	 */
	public CefxHierarchyLevelChecker() {
		ArrayList<IHierarchyLevel> levelList = new ArrayList<>();
		systemLevel = createSystemLevel();
		subSystemLevel = createSubSystemLevel();
		equipmentLevel = createEquipmentLevel();
		levelList.add(systemLevel);
		levelList.add(subSystemLevel);
		levelList.add(equipmentLevel);
		
		levelChecker = new HierarchyLevelChecker(levelList);
	}

	/**
	 * Method that checks if a specific category assignment can be added to a structural element bean
	 * considering the level hierarchy. 
	 * @param bean the structural element bean 
	 * @param categoryBeanClass the bean class of the category assignment that should be checked
	 * @return true if it can be added, false if otherwise
	 */
	public boolean canAdd(IBeanStructuralElementInstance bean,
			Class<? extends IBeanCategoryAssignment> categoryBeanClass) {
		for (IHierarchyLevel level : levelChecker.getApplicableLevels(bean)) {
			if (((CefxHierarchyLevel) level).categoryBelongsToLevel(categoryBeanClass)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if a structural element has category assignment of multiple levels assigned 
	 * @param bean the structural element to validate
	 * @return true if the element belongs to multiple levels, false if it is valid
	 */
	public boolean hasMultipleLevels(IBeanStructuralElementInstance bean) {
		return !levelChecker.validateUniqueLevel(bean);
	}
	
	/**
	 * Check if a structural element has an invalid level - its level is invalid if it contains a
	 * category assignment that does not fit the specified tree hierarchy
	 * @param bean the bean of the structural element to validate
	 * @return true if the element has an invalid level
	 */
	public boolean hasWrongLevel(IBeanStructuralElementInstance bean) {
		return !levelChecker.validateApplicableLevel(bean);
	}
	
	/**
	 * Create a system hierarchy level by assigning its category assignment bean classes 
	 * @return the level object for the system hierarchy level
	 */
	private CefxHierarchyLevel createSystemLevel() {
		ArrayList<Class<? extends IBeanCategoryAssignment>> beanClassList = new ArrayList<>();
		beanClassList.add(SystemMassParameters.class);
		beanClassList.add(SystemPowerParameters.class);
		beanClassList.add(SystemParameters.class);
		return new CefxHierarchyLevel(beanClassList);
	}
	
	/**
	 * Create a sub system hierarchy level by assigning its category assignment bean classes 
	 * @return the level object for the sub system hierarchy level
	 */
	private CefxHierarchyLevel createSubSystemLevel() {
		ArrayList<Class<? extends IBeanCategoryAssignment>> beanClassList = new ArrayList<>();
		beanClassList.add(SubSystemMassParameters.class);
		beanClassList.add(SubSystemPowerParameters.class);
		return new CefxHierarchyLevel(beanClassList);
	}
	
	/**
	 * Create a equipment hierarchy level by assigning its category assignment bean classes 
	 * @return the level object for the equipment hierarchy level
	 */
	private CefxHierarchyLevel createEquipmentLevel() {
		ArrayList<Class<? extends IBeanCategoryAssignment>> beanClassList = new ArrayList<>();
		beanClassList.add(EquipmentParameters.class);
		beanClassList.add(EquipmentMassParameters.class);
		beanClassList.add(EquipmentPowerParameters.class);
		beanClassList.add(EquipmentTemperatureParameters.class);
		CefxHierarchyLevel equipmentLevel = new CefxHierarchyLevel(beanClassList);
		equipmentLevel.setCanBeNested(true);
		return equipmentLevel;
	}
}
