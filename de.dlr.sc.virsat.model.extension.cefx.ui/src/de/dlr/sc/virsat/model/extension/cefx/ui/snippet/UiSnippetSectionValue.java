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

import org.eclipse.swt.widgets.Text;

import de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.util.PropertyInstanceHelper;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.IUiSnippet;


/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class UiSnippetSectionValue extends AUiSnippetSectionValue implements IUiSnippet {
	private static final String VALUE_PROPERTY = "de.dlr.sc.virsat.model.extension.cefx.Value.value";

	@Override
	public void updateState(boolean state) {
		super.updateState(state);
		
		Text defaultValueText = mapPropertyToTextValue.get(VALUE_PROPERTY);
		boolean isEnabled = defaultValueText.isEnabled();
		
		if (isEnabled) {
			PropertyInstanceHelper piHelper = new PropertyInstanceHelper();
			ATypeInstance parameter = (ATypeInstance) model.eContainer().eContainer().eContainer();
			boolean isCalculated = piHelper.isCalculated((ATypeInstance) model) || piHelper.isCalculated(parameter);
			defaultValueText.setEnabled(!isCalculated);
		}
	}
}
