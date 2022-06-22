/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.modesview;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.dlr.sc.virsat.model.dvlm.categories.ICategoryAssignmentContainer;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.cefx.util.CefModeHelper;

/**
 * Label Provider for the ModeTableView
 * @author muel_s8
 *
 */

class ModeTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	
	private static final int TABLE_DISPLAY_DECIMALS = 3;
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Parameter) {
			Parameter param = (Parameter) element;
			double displayValue = 0;
			
			if (columnIndex == ModeTableView.TABLE_COLUMN_SEI_INDX) {
				ICategoryAssignmentContainer caContainer = param.getATypeInstance().getCategoryAssignmentContainer();
				return ((StructuralElementInstance) caContainer).getName();
			} else if (columnIndex == ModeTableView.TABLE_COLUMN_PARAM_INDX) {
				return param.getName();
			} else if (columnIndex == ModeTableView.TABLE_COLUMN_UNIT_INDX) {
				AUnit unit = param.getDefaultValueBean().getTypeInstance().getUnit();
				if (unit != null) {
					return unit.getSymbol();
				} else {
					return "";
				}
			} else if (columnIndex == ModeTableView.TABLE_COLUMN_DEF_INDX) {
				displayValue = param.getDefaultValue();
			} else if (columnIndex >= ModeTableView.TABLE_COLUMN_AMOUNT) {
				// First retrieve the mode corresponding to the index of the table
				int columnMode = columnIndex - ModeTableView.TABLE_COLUMN_AMOUNT;
				CefModeHelper cefModeHelper = new CefModeHelper();
				
				List<SystemMode> modes = cefModeHelper.getAllModes(param);
				SystemMode mode = modes.get(columnMode);
			
				Value modeValue = cefModeHelper.getModeValue(param, mode);
				
				if (modeValue != null) {
					displayValue = modeValue.getValue();
				} else {
					displayValue = param.getDefaultValue();
				}
			}
			
			if (!Double.isNaN(displayValue)) {
				return new BigDecimal(displayValue).setScale(TABLE_DISPLAY_DECIMALS, RoundingMode.HALF_UP).toPlainString();
			} else {
				return Double.toString(displayValue);
			}
		}
		return null;
	}
}