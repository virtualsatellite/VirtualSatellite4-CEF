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

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;

import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.AProperty;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.GeneralPackage;
import de.dlr.sc.virsat.model.extension.cefx.Activator;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.cefx.ui.itemprovider.VirSatCefTreeLabelProvider;
import de.dlr.sc.virsat.model.extension.cefx.ui.util.CefUiHelper;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.aproperties.MultiPropertyEditingSupport;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.aproperties.QudvUnitCellEditingSupport;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.aproperties.ValuePropertyCellEditingSupport;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.emfattributes.EStringCellEditingSupport;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.AUiSnippetGenericCategoryAssignmentTable;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.AUiSnippetGenericTable;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.tableimpl.UiSnippetGenericTreeTableImpl;

/**
 * the very CEF specific implementation of the Tree table Implementation.
 * Main change is on the way the label providers and context menus are filled
 * with their corresponding information.
 * @author fisc_ph
 *
 */
public class UiSnippetCefTreeTableImpl extends UiSnippetGenericTreeTableImpl {

	protected TreeViewerColumn colName;
	protected TreeViewerColumn colUnit;
	protected TreeViewerColumn colValue;
	protected AUiSnippetGenericTable thisTable;

	/**
	 * Constructor of the CEF Tree Table Implementation
	 * @param genericCategoryTable Set the current table here
	 */
	public UiSnippetCefTreeTableImpl(AUiSnippetGenericCategoryAssignmentTable genericCategoryTable) {
		super(genericCategoryTable);
		this.thisTable = genericCategoryTable;
	}

	@Override
	protected void createTableColumns(EditingDomain editingDomain) {
		ColumnViewer columnViewer = getColumnViewer();
		ActiveConceptHelper acHelper = getActiveConceptHelper();
		
		colName = (TreeViewerColumn) createDefaultColumn("Name");
		colName.setEditingSupport(new EStringCellEditingSupport(editingDomain, columnViewer, GeneralPackage.Literals.INAME__NAME) {
			@Override
			protected boolean canEdit(Object element) {
				if (element instanceof CategoryAssignment && ((CategoryAssignment) element).getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
					return super.canEdit(element);
				}
				return false;
			}
		});
	
		colValue = (TreeViewerColumn) createDefaultColumn("Value");
		MultiPropertyEditingSupport editingSupportValue = new MultiPropertyEditingSupport(columnViewer);
	
		AProperty defaultValueProperty = acHelper.getProperty(Activator.PLUGIN_ID, Parameter.class.getSimpleName(), Parameter.PROPERTY_DEFAULTVALUE); //Parameter.class.getSimpleName() = non fully qualified name, e.g. "Parameter"
		editingSupportValue.registerEditingSupport(new ValuePropertyCellEditingSupport(editingDomain, columnViewer, defaultValueProperty));
	
		AProperty valueProperty = acHelper.getProperty(Activator.PLUGIN_ID, Value.class.getSimpleName(), Value.PROPERTY_VALUE);
		editingSupportValue.registerEditingSupport(new ValuePropertyCellEditingSupport(editingDomain, columnViewer, valueProperty));
		colValue.setEditingSupport(editingSupportValue);
		
		colUnit = (TreeViewerColumn) createDefaultColumn("Unit");
		MultiPropertyEditingSupport editingSupportUnit = new MultiPropertyEditingSupport(columnViewer);
		editingSupportUnit.registerEditingSupport(new QudvUnitCellEditingSupport(editingDomain, columnViewer, defaultValueProperty));
		editingSupportUnit.registerEditingSupport(new QudvUnitCellEditingSupport(editingDomain, columnViewer, valueProperty));
		colUnit.setEditingSupport(editingSupportUnit);
	}

	@Override
	protected void fillContextMenuAdditions(EditingDomain editingDomain, IMenuManager manager) {
		super.fillContextMenuAdditions(editingDomain, manager);
		new CefUiHelper().fillContextMenuModeValues(editingDomain, manager, thisTable);
	}

	@Override
	protected ITableLabelProvider getTableLabelProvider() {
		ColumnViewer columnViewer = getColumnViewer();
		ComposedAdapterFactory adapterFactory = getAdapterFactory();
		return new VirSatCefTreeLabelProvider(adapterFactory, columnViewer, colName, colValue, colUnit, emip);
	}
}
