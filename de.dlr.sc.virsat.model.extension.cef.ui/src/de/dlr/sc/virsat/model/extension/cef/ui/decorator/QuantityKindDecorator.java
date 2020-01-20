/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.decorator;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentParameters;

/**
 * This class provides a decorator in the navigator to display the number of equipment units 
 * 
 * @author fisc_ph
 *
 */

public class QuantityKindDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	private BeanCategoryAssignmentHelper bCahelper = new BeanCategoryAssignmentHelper();
	
	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof StructuralElementInstance) {
			StructuralElementInstance sei = (StructuralElementInstance) element;
			EquipmentParameters eps = bCahelper.getFirstBeanCategory(sei, EquipmentParameters.class);
			if (eps != null) {
				decorateQuantity(decoration, eps.getUnitQuantity());
			}
		}
	}
	
	/**
	 * This method is doing the uniform decoration of the quantity and adds it as text
	 * to the tree widget in the navigator in the way such as (x 4)
	 * @param decoration the decoration to be used to add the quantity
	 * @param quantity the actual quantity to be added
	 */
	protected void decorateQuantity(IDecoration decoration, long quantity) {
		try {
			decoration.addSuffix(String.format(" ( x %d)", quantity));
		} catch (NumberFormatException e) {
			decoration.addSuffix(" ( x NaN)");
		}
	}
}
