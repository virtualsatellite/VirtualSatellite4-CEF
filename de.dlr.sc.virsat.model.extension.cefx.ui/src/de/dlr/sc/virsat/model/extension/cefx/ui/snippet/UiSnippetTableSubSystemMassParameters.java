/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.snippet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;

import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.hierarchy.CefxHierarchyLevelChecker;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.ui.itemprovider.VirSatCefTreeContentProvider;
import de.dlr.sc.virsat.model.extension.cefx.ui.snippet.tableimpl.UiSnippetCefTreeTableImpl;

// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class UiSnippetTableSubSystemMassParameters extends AUiSnippetTableSubSystemMassParameters {
	@Override
	protected UiSnippetGenericTableImpl createImplementation() {
		return new UiSnippetCefTreeTableImpl(this);
	}
	
	@Override
	protected IStructuredContentProvider getTableContentProvider() {
		return new VirSatCefTreeContentProvider(adapterFactory, SubSystemMassParameters.FULL_QUALIFIED_CATEGORY_NAME);
	}
	
	@Override
	public boolean isActive(EObject model) {
		if (super.isActive(model)) {
			if (model instanceof StructuralElementInstance) {
				BeanStructuralElementInstance bean = new BeanStructuralElementInstance((StructuralElementInstance) model);
				return new CefxHierarchyLevelChecker().canAddSubSystemCategory(bean);
			}
		}
		return false;
	}
}
