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

	Set<Class<? extends IBeanCategoryAssignment>> categoryBeans;
	
	/**
	 * 
	 * @param beanClasses a list of category bean classes that define a level 
	 */
	public CefxHierarchyLevel(Collection<Class<? extends IBeanCategoryAssignment>> beanClasses) {
		categoryBeans = new HashSet<>(beanClasses);
	}
	
	/**
	 * 
	 * @param beanClass
	 * @return
	 */
	public boolean categoryBelongsToLevel(Class<? extends IBeanCategoryAssignment> beanClass) {
		return categoryBeans.contains(beanClass);
	}
	
	@Override
	public boolean isOnLevel(IBeanStructuralElementInstance bean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBeNested() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOptional() {
		// TODO Auto-generated method stub
		return false;
	}

}
