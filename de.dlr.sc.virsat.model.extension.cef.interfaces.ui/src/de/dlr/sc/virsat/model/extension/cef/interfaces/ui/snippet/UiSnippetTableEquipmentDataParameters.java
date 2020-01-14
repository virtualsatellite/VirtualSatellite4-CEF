/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.ui.snippet;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dlr.sc.virsat.model.extension.cef.interfaces.model.EquipmentDataParameters;
import de.dlr.sc.virsat.model.extension.cef.ui.itemprovider.VirSatCefTreeContentProvider;
import de.dlr.sc.virsat.model.extension.cef.ui.snippet.tableimpl.UiSnippetCefTreeTableImpl;

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
public class UiSnippetTableEquipmentDataParameters extends AUiSnippetTableEquipmentDataParameters {
	@Override
	protected UiSnippetGenericTableImpl createImplementation() {
		return new UiSnippetCefTreeTableImpl(this) {
		};
	}
	
	@Override
	protected IStructuredContentProvider getTableContentProvider() {
		return new VirSatCefTreeContentProvider(adapterFactory, EquipmentDataParameters.FULL_QUALIFIED_CATEGORY_NAME) { 
			@Override
			public Object[] getElements(Object inputElement) {
				return super.getElements(inputElement);
			}
		
			@Override
			public Object[] getChildren(Object parentElement) {
				return super.getChildren(parentElement);
			}
		};
		
		
	}
	
	@Override
	protected void setTableViewerInput() {
		super.setTableViewerInput();
	}
	
	@Override
	protected void setUpTableViewer(EditingDomain editingDomain, FormToolkit toolkit) {
		super.setUpTableViewer(editingDomain, toolkit);
	}
}
