/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.snippet.tableimpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.cefx.util.CefModeHelper;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.emfattributes.EBooleanCellEditingSupport;

public class OverrideFlagCellEditingSupport extends EBooleanCellEditingSupport {
	
	private CellEditor editor;
	public static final String[] OVERRIDE_LITERALS = { "Inherited", "Override" };
	
	public OverrideFlagCellEditingSupport(EditingDomain editingDomain, ColumnViewer viewer, EAttribute emfAttribute) {
		super(editingDomain, viewer, emfAttribute);
		this.editor = new ComboBoxCellEditor((Composite) viewer.getControl(), OVERRIDE_LITERALS);
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof ComposedPropertyInstance) {
			Parameter param = new Parameter(((ComposedPropertyInstance) element).getTypeInstance());
			return super.getValue(param.getDefaultValueBean().getTypeInstance());
		} else if (element instanceof CategoryAssignment) {
			Value value = new Value((CategoryAssignment) element);
			return super.getValue(value.getValueBean().getTypeInstance());
		}
		return super.getValue(element);
	}
	
	@Override
	protected void setValue(Object element, Object userInputValue) {
		if (element instanceof ComposedPropertyInstance) {
			Parameter param = new Parameter(((ComposedPropertyInstance) element).getTypeInstance());
			super.setValue(param.getDefaultValueBean().getTypeInstance(), userInputValue);
		} else if (element instanceof CategoryAssignment) {
			Value value = new Value((CategoryAssignment) element);
			super.setValue(value.getValueBean().getTypeInstance(), userInputValue);
		} else {
			super.setValue(element, userInputValue);
		}
	}
	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof ComposedPropertyInstance) {
			Parameter param = new Parameter(((ComposedPropertyInstance) element).getTypeInstance());
			if (param.getDefaultValueBean().getIsCalculated()) {
				return false;
			}
			return super.canEdit(param.getDefaultValueBean().getTypeInstance());
		} else if (element instanceof CategoryAssignment 
					&&  ((CategoryAssignment) element).getType().getFullQualifiedName().equals(Value.FULL_QUALIFIED_CATEGORY_NAME)) {
			if (!(new CefModeHelper().isValueCalculated((CategoryAssignment) element))) {
				return true;
			}
				
		}
		return false;
	}
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

}
