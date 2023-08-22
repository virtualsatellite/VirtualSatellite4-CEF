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
import java.util.List;
import java.util.stream.Collectors;

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
import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;

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
	 * Method that checks if a specific category assignment can be added to a
	 * structural element bean considering the level hierarchy.
	 * 
	 * @param bean
	 *            the structural element bean
	 * @param categoryBeanClass
	 *            the bean class of the category assignment that should be checked
	 * @return true if it can be added, false if otherwise
	 */
	public boolean canAdd(IBeanStructuralElementInstance bean,
			Class<? extends IBeanCategoryAssignment> categoryBeanClass) {
		
		// In product tree levels don't make sense
		if (bean.getStructuralElementInstance().getType().getFullQualifiedName().equals(ElementDefinition.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME)
				&& equipmentLevel.categoryBelongsToLevel(categoryBeanClass)) {
			return true;
		}
				
		try {
			for (IHierarchyLevel level : levelChecker.getApplicableLevels(bean)) {
				if (((CefxHierarchyLevel) level).categoryBelongsToLevel(categoryBeanClass)) {
					return true;
				}
			}
		} catch (IllegalArgumentException e) {
			// If the model is broken then we accept any change here.
			// Validators should notify the user about the broken model.
			return true;
		}
		return false;
	}

	/**
	 * Method that checks if a category that belongs to the system level can be
	 * added to the structural element bean
	 * 
	 * @param bean
	 *            the structural element bean
	 * @return true if a system related category assignment can be added
	 */
	public boolean canAddSystemCategory(IBeanStructuralElementInstance bean) {
		try {
			return levelChecker.checkApplicable(bean, systemLevel);
		} catch (IllegalArgumentException e) {
			// If the model is broken then we accept any change here.
			// Validators should notify the user about the broken model.
			return true;
		}
	}

	/**
	 * Method that checks if a category that belongs to the sub system level can be
	 * added to the structural element bean
	 * 
	 * @param bean
	 *            the structural element bean
	 * @return true if a sub system related category assignment can be added
	 */
	public boolean canAddSubSystemCategory(IBeanStructuralElementInstance bean) {
		try {
			return levelChecker.checkApplicable(bean, subSystemLevel);
		} catch (IllegalArgumentException e) {
			// If the model is broken then we accept any change here.
			// Validators should notify the user about the broken model.
			return true;
		}
	}

	/**
	 * Method that checks if a category that belongs to the equipment level can be
	 * added to the structural element bean
	 * 
	 * @param bean
	 *            the structural element bean
	 * @return true if a equipment related category assignment can be added
	 */
	public boolean canAddEquipmentCategory(IBeanStructuralElementInstance bean) {
		
		// In product tree levels don't make sense
		if (bean.getStructuralElementInstance().getType().getFullQualifiedName().equals(ElementDefinition.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME)) {
			return true;
		}
		
		try {
			return levelChecker.checkApplicable(bean, equipmentLevel);
		} catch (IllegalArgumentException e) {
			// If the model is broken then we accept any change here.
			// Validators should notify the user about the broken model.
			return true;
		}
	}

	/**
	 * Get all CA beans of a given structural bean that define its hierarchy level
	 * @param bean structural bean
	 * @return level-defining CA beans
	 */
	public List<IBeanCategoryAssignment> getLevelDefiningCategoryAssignments(IBeanStructuralElementInstance bean) {
		return bean.getAll(IBeanCategoryAssignment.class).stream()
				.filter(ca -> systemLevel.categoryBelongsToLevel(ca.getClass())
								|| subSystemLevel.categoryBelongsToLevel(ca.getClass())
								|| equipmentLevel.categoryBelongsToLevel(ca.getClass()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Check if a structural element has category assignment of multiple levels
	 * assigned
	 * 
	 * @param bean
	 *            the structural element to validate
	 * @return true if the element belongs to multiple levels, false if it is valid
	 */
	public boolean beanHasAmbiguousLevel(IBeanStructuralElementInstance bean) {
		return levelChecker.beanHasAmbiguousLevel(bean);
	}

	/**
	 * Check if a structural element has an invalid level - its level is invalid if
	 * it contains a category assignment that does not fit the specified tree
	 * hierarchy
	 * 
	 * @param bean
	 *            the bean of the structural element to validate
	 * @return true if the element has an invalid level
	 */
	public boolean beanHasInapplicableLevel(IBeanStructuralElementInstance bean) {
		return levelChecker.beanHasInapplicableLevel(bean);
	}

	/**
	 * Create a system hierarchy level by assigning its category assignment bean
	 * classes
	 * 
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
	 * Create a sub system hierarchy level by assigning its category assignment bean
	 * classes
	 * 
	 * @return the level object for the sub system hierarchy level
	 */
	private CefxHierarchyLevel createSubSystemLevel() {
		ArrayList<Class<? extends IBeanCategoryAssignment>> beanClassList = new ArrayList<>();
		beanClassList.add(SubSystemMassParameters.class);
		beanClassList.add(SubSystemPowerParameters.class);
		return new CefxHierarchyLevel(beanClassList);
	}

	/**
	 * Create a equipment hierarchy level by assigning its category assignment bean
	 * classes
	 * 
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
