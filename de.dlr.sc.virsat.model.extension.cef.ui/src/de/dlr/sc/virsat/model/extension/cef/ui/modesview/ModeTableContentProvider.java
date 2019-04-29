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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;

/**
 * This provider hands back all parameters of a systemComponent which are mode
 * dependent. In case they are the parameter is checked if it
 * @author fisc_ph
 *
 */
public class ModeTableContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// In case the input is a SystemComponent
		// we get all its parameters and we check which ones
		// are mode dependent and which ones are not
		if (inputElement instanceof StructuralElementInstance) {
			
			StructuralElementInstance sei = (StructuralElementInstance) inputElement;
			BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
			List<Parameter> parameters = beanCaHelper.getAllNestedBeanCategories(sei, Parameter.class);
			
			List<Parameter> modeParams = new ArrayList<Parameter>();
			
			// Sort out all mode dependent parameters
			for (Parameter parameter : parameters) {
				if (!parameter.getModeValues().isEmpty()) {
					modeParams.add(parameter);
				}
			}
			
			// and now return the parameters
			return modeParams.toArray();
		}
		
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Parameter) {
			ContributionHelper contrHelper = new ContributionHelper((Parameter) parentElement);
			return contrHelper.getSourceParameters().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object parentElement) {
		if (parentElement instanceof Parameter) {
			ContributionHelper contrHelper = new ContributionHelper((Parameter) parentElement);
			return contrHelper.getSourceParameters().toArray().length > 0;
		}		
		return false;
	}
}