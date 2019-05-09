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

import org.eclipse.emf.edit.domain.EditingDomain;

import de.dlr.sc.virsat.model.extension.cefx.ui.snippet.AUiSnippetTableParameterRangeValuesParameterRange;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.IUiSnippet;


/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * Add minimum and/or Maximum value for the parameter
 * 
 */
public class UiSnippetTableParameterRangeValuesParameterRange extends AUiSnippetTableParameterRangeValuesParameterRange implements IUiSnippet {
	@Override
	protected void createTableColumns(EditingDomain editingDomain) {
		hideNameColumn = true;
		super.createTableColumns(editingDomain);
	}
}
