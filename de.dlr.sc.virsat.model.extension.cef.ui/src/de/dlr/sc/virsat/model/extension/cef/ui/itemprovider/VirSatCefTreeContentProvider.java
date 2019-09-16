/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.itemprovider;

import java.util.Collections;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.viewers.ITreeContentProvider;

import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatTransactionalAdapterFactoryContentProvider;

/**
 * This class provides a Content provider for CEF tree based tables that allows to filter content to specific
 * classes or categories and assignments of a given ID. it wraps another ContentProvider
 * that needs to be handed over to this given one 
 * 
 * 
 * @author desh_me
 *
 */
public class VirSatCefTreeContentProvider extends VirSatFilteredWrappedTreeContentProvider implements ITreeContentProvider {

	private String catFullQualifiedName = null;
	
	/**
	 * The constructor
	 * @param adapterFactory Adapter
	 * @param catFullQualifiedName the name of the category which will be displayed in the table
	 */
	public VirSatCefTreeContentProvider(AdapterFactory adapterFactory, String catFullQualifiedName) {
		super(adapterFactory);
		this.catFullQualifiedName = catFullQualifiedName;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		filterElementClasses.add(CategoryAssignment.class);
		Object[] elements = super.getElements(inputElement);
		elements = filterIds(elements, Collections.singleton(catFullQualifiedName), Collections.emptySet());
	
		if (catFullQualifiedName.equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
			return elements;
		} else 	if (elements.length > 0) {
			CategoryAssignment ca = (CategoryAssignment) elements[0];
			elements = ca.getPropertyInstances().toArray();
			return elements;
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object modeValuesArray = null;

		CategoryAssignment ca = getCategoryAssignment(parentElement);
			
		for (Object child : super.getChildren(ca)) {
			if (child instanceof ArrayInstance) {
				if (((ArrayInstance) child).getType().getName().equals(Parameter.PROPERTY_MODEVALUES)) {
					modeValuesArray = child;
					((VirSatTransactionalAdapterFactoryContentProvider) wrappedContentProvider).redirectNotification(modeValuesArray, parentElement);
				}
			}
		} 

		Object[] arrayComposedChildren = super.getChildren(modeValuesArray);
		Object[] children = new Object[arrayComposedChildren.length];
		for (int i = 0; i < arrayComposedChildren.length; i++) {
			children[i] = ((ComposedPropertyInstance) arrayComposedChildren[i]).getTypeInstance();
		}

		return children;
	}
	/**
	 * Get the category assignment of the object
	 * @param object 
	 * @return {@link CategoryAssignment} 
	 */
	private CategoryAssignment getCategoryAssignment(Object object) {
		CategoryAssignment ca = null;
		if (object instanceof ComposedPropertyInstance) {
			ComposedPropertyInstance cpi = (ComposedPropertyInstance) object;
			ca = cpi.getTypeInstance();		
		}   else if (object instanceof UnitValuePropertyInstance) {
			UnitValuePropertyInstance uvPi = (UnitValuePropertyInstance) object;
			ca = (CategoryAssignment) uvPi.eContainer();
		} else {
			ca = (CategoryAssignment) object;
		}
		return ca;
	}

}
