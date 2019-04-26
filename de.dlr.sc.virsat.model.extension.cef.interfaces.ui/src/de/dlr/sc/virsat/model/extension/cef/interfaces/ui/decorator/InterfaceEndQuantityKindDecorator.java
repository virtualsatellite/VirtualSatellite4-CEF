/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.ui.decorator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.factory.BeanCategoryAssignmentFactory;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.AInterfaceEnd;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.DataInterfaceEnd;
import de.dlr.sc.virsat.model.extension.cef.ui.decorator.QuantityKindDecorator;

/**
 * This class provides a decorator in the navigator to display the number of Interfaces 
 * 
 * @author fisc_ph
 *
 */

public class InterfaceEndQuantityKindDecorator extends QuantityKindDecorator implements ILightweightLabelDecorator {

	BeanCategoryAssignmentFactory bcaFactory = new BeanCategoryAssignmentFactory(); 

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof CategoryAssignment) {
			CategoryAssignment ca = (CategoryAssignment) element;
			
			try {
				IBeanCategoryAssignment iBca = bcaFactory.getInstanceFor(ca);
			
				if ((iBca != null) && (iBca instanceof DataInterfaceEnd)) {
					DataInterfaceEnd dIfe = (DataInterfaceEnd) iBca;
					String iftName = dIfe.getDataInterfaceType() != null ? dIfe.getDataInterfaceType().getName() : "N/A";
					decorateQuantityAndType(decoration, dIfe.getQuantity(), iftName);
				} else if ((iBca != null) && (iBca instanceof AInterfaceEnd)) {
					AInterfaceEnd aIfe = (AInterfaceEnd) iBca;
					decorateQuantity(decoration, aIfe.getQuantity());
				}
			} catch (CoreException e1) {
			}
		}
	}
	
	/**
	 * This method is doing the uniform decoration of the quantity and adds it as text
	 * to the tree widget in the navigator in the way such as (x 4 Can) it also
	 * adds the type in case it is given
	 * @param decoration the decoration to be used to add the quantity
	 * @param quantity the actual quantity to be added
	 * @param type the interface type
	 */
	protected void decorateQuantityAndType(IDecoration decoration, long quantity, String type) {
		try {
			decoration.addSuffix(String.format(" ( x %d %s)", quantity, type));
		} catch (NumberFormatException e) {
			decoration.addSuffix(" ( x NaN N/A)");
		}
	}
}
