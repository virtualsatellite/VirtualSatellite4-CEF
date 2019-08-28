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

import org.eclipse.jface.viewers.IStructuredContentProvider;

import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
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
public class UiSnippetTableParameter extends AUiSnippetTableParameter {
	
	@Override
	protected UiSnippetGenericTableImpl createImplementation() {
		return new UiSnippetCefTreeTableImpl(this);
	}
	
	
	@Override
	protected IStructuredContentProvider getTableContentProvider() {
		return new VirSatCefTreeContentProvider(adapterFactory, Parameter.FULL_QUALIFIED_CATEGORY_NAME);
	}
}
