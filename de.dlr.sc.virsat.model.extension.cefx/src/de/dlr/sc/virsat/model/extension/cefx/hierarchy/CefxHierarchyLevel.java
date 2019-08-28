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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.level.IHierarchyLevel;

/**
 * 
 */
public class CefxHierarchyLevel implements IHierarchyLevel {

	protected Set<Class<? extends IBeanCategoryAssignment>> categoryBeanClasses;
	protected boolean canBeNested = false;
	protected boolean optional = false;
	
	
	/**
	 * 
	 * @param beanClasses a list of category bean classes that define a level 
	 */
	public CefxHierarchyLevel(Collection<Class<? extends IBeanCategoryAssignment>> beanClasses) {
		categoryBeanClasses = new HashSet<>(beanClasses);
	}
	
	/**
	 * Check if a category bean class belongs to the definition of this level
	 * @param beanClass the bean class
	 * @return true if bean class belongs to definition of level
	 */
	public boolean categoryBelongsToLevel(Class<? extends IBeanCategoryAssignment> beanClass) {
		return categoryBeanClasses.contains(beanClass);
	}
	
	@Override
	public boolean isOnLevel(IBeanStructuralElementInstance bean) {
		boolean isOnLevel = false;
		for (Class<? extends IBeanCategoryAssignment> beanClass : categoryBeanClasses) {
			isOnLevel |= bean.getFirst(beanClass) != null;
		}
		return isOnLevel;
	}

	@Override
	public boolean canBeNested() {
		return canBeNested;
	}

	@Override
	public boolean isOptional() {
		return optional;
	}

	/**
	 * Specify if the level can be nested
	 * @param canBeNested true if it can be nested
	 */
	public void setCanBeNested(boolean canBeNested) {
		this.canBeNested = canBeNested;
	}

	/**
	 * Specify if the level is optional
	 * @param optional true if the level is optional
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	

}
