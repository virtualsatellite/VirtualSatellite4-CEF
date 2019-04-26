/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.masssummary;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.categories.ICategoryAssignmentContainer;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.qudv.util.QudvUnitHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.util.StructuralElementInstanceHelper;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMassParameters;
import de.dlr.sc.virsat.model.ui.editor.input.VirSatUriEditorInput;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatTransactionalAdapterFactoryContentProvider;
import de.dlr.sc.virsat.project.ui.labelProvider.VirSatTransactionalAdapterFactoryLabelProvider;

/**
 * Table displaying the mass summary of the children of a system
 * @author muel_s8
 *
 */

public class UiSnippetElements extends AUiSnippetTable {
	
	@Override
	public void createSwt(FormToolkit toolkit, Composite parentComposite) {
		super.createSwt(toolkit, parentComposite);
		
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick(event);
			}
		});
	}
	
	/**
	 * Handles double click events by opening the editor page for the selected
	 * element.
	 * @param anEvent double click event
	 */
	protected void handleDoubleClick(DoubleClickEvent anEvent) {
		IStructuredSelection selection = (IStructuredSelection) anEvent.getSelection();
		Object element = selection.getFirstElement();
		
		if (element instanceof StructuralElementInstance) {
			VirSatUriEditorInput.openDrillDownEditor((EObject) element); 
		}
	}
	
	@Override
	public IStructuredContentProvider getContentProvider() {
		return new VirSatTransactionalAdapterFactoryContentProvider(adapterFactory) {
			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof StructuralElementInstance) {
					return ((StructuralElementInstance) inputElement).getChildren().stream().filter(
							sei ->
								!CategoryAssignmentHelper.getCategoryAssignmentsOfType(sei, SubSystemMassParameters.FULL_QUALIFIED_CATEGORY_NAME).isEmpty()
								||
								!CategoryAssignmentHelper.getCategoryAssignmentsOfType(sei, EquipmentMassParameters.FULL_QUALIFIED_CATEGORY_NAME).isEmpty()
							).toArray();
				}
				return super.getElements(inputElement);
			}
		};
	}

	@Override
	public ITableLabelProvider getLabelProvider() {
		return new VirSatTransactionalAdapterFactoryLabelProvider(adapterFactory) {
			
			@Override
			public Image getColumnImage(Object object, int columnIndex) {
				if (columnIndex == COLUMN_SEI) {
					return super.getColumnImage(object, columnIndex);
				} else {
					return null;
				}
			}
			
			@Override
			public String getColumnText(Object element, int columnIndex) {
				if (!(element instanceof StructuralElementInstance)) {
					return null;
				}
				
				StructuralElementInstance child = (StructuralElementInstance) element;
				BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
				
				if (child.getType().getName().matches("SubSystem")) {
					
				
					List<SubSystemMassParameters> massParamsList = beanCaHelper.getAllBeanCategories(child, SubSystemMassParameters.class);
					SubSystemMassParameters massParams = massParamsList.size() > 0 ? massParamsList.get(0) : null;
					
					switch (columnIndex) {
						case COLUMN_SEI:
							return child.getName();
						case COLUMN_MWOMARGIN:
							return printParameter(massParams != null ? massParams.getMassTotal() : null, KILOGRAM);
						case COLUMN_MARGIN_PERCENT:
							return printParameter(massParams != null ? massParams.getMassMarginPercentage() : null, PERCENT);
						case COLUMN_MARGIN_KG:
							return printParameter(massParams != null ? massParams.getMassMargin() : null, KILOGRAM);
						case COLUMN_MWMARGINKG:
							return printParameter(massParams != null ? massParams.getMassTotalWithMargin() : null, KILOGRAM);
						case COLUMN_PERCENT_DRY_MASS:
							if (sei == null) {
								return UNDEFINED;
							}

							ICategoryAssignmentContainer caContainerSystemParent = new StructuralElementInstanceHelper(sei).getRoot(); 
							
							List<SystemMassParameters> systemMassParamsList = beanCaHelper.getAllBeanCategories(caContainerSystemParent, SystemMassParameters.class);
							SystemMassParameters systemMassParams = systemMassParamsList.size() > 0 ? systemMassParamsList.get(0) : null;

							if (systemMassParams != null && massParams != null && systemMassParams.getMassTotalWithMarginWithSystemMargin().isSetDefaultValue() && massParams.getMassTotalWithMargin().isSetDefaultValue()) {
								double value = massParams.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit() / systemMassParams.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();

								AUnit targetUnit = QudvUnitHelper.getInstance().getUnitByName(domain.getResourceSet().getUnitManagement().getSystemOfUnit(), PERCENT);
								AUnit sourceUnit = QudvUnitHelper.getInstance().getUnitByName(domain.getResourceSet().getUnitManagement().getSystemOfUnit(), NO_UNIT);
								value = QudvUnitHelper.getInstance().convertFromSourceToTargetUnit(sourceUnit, value, targetUnit);

								return printDouble(value);
							} else {
								return UNDEFINED;
							}
						default:
							return super.getColumnText(element, columnIndex);
					}
				} else if (child.getType().getName().matches("Equipment")) {
					EquipmentMassParameters ownMassParams = beanCaHelper.getFirstBeanCategory(child, EquipmentMassParameters.class);
					
					double totalMassWithoutMargin = Double.NaN;
					double totalMassWithMargin = Double.NaN;
					if (ownMassParams != null) {
						totalMassWithoutMargin = ownMassParams.getMassTotal().getDefaultValueBean().getValueToBaseUnit();
						totalMassWithMargin = ownMassParams.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
					}
					
					double marginMass = totalMassWithMargin - totalMassWithoutMargin;
					double marginPercent = marginMass / totalMassWithoutMargin;
					
					switch (columnIndex) {
						case COLUMN_SEI:
							return child.getName();
						case COLUMN_MWOMARGIN:
							return Double.isNaN(totalMassWithoutMargin) ? UNDEFINED
									: printDoubleInUnit(totalMassWithoutMargin, KILOGRAM);
						case COLUMN_MARGIN_PERCENT:
							return Double.isNaN(marginPercent) ? UNDEFINED
									: printDoubleInUnit(marginPercent, PERCENT);
						case COLUMN_MARGIN_KG:
							return Double.isNaN(marginMass) ? UNDEFINED
									: printDoubleInUnit(marginMass, KILOGRAM);
						case COLUMN_MWMARGINKG:
							return Double.isNaN(totalMassWithMargin) ? UNDEFINED
									: printDoubleInUnit(totalMassWithMargin, KILOGRAM);
						case COLUMN_PERCENT_DRY_MASS:
							if (sei == null) {
								return UNDEFINED;
							}
							
							ICategoryAssignmentContainer caContainerSystemParent = new StructuralElementInstanceHelper(sei).getRoot();
							
							List<SystemMassParameters> systemMassParamsList = beanCaHelper.getAllBeanCategories(caContainerSystemParent, SystemMassParameters.class);
							SystemMassParameters systemMassParams = systemMassParamsList.size() > 0 ? systemMassParamsList.get(0) : null;

							if (systemMassParams != null && systemMassParams.getMassTotalWithMarginWithSystemMargin().isSetDefaultValue() && !Double.isNaN(totalMassWithMargin)) {
								double value = totalMassWithMargin / systemMassParams.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
								return printDoubleInUnit(value, PERCENT);
							} else {
								return UNDEFINED;
							}
						default:
							return super.getColumnText(element, columnIndex);
					}
				} else {
					return null;
				}
			}
		};
	}

	@Override
	protected void createColumns() {
	    // defining the first column: 
	    TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
	    col.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
	    col.getColumn().setText("");
		
	    // defining the second column: mass w/o margin [kg]
	    TableViewerColumn colMass = new TableViewerColumn(tableViewer, SWT.NONE);
	    colMass.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
	    colMass.getColumn().setText("Mass w/o margin [kg]");
	
		//define third column: Margin [%]
		TableViewerColumn colMarginPercent = new TableViewerColumn(tableViewer, SWT.NONE);
		colMarginPercent.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colMarginPercent.getColumn().setText("Margin [%]");
		
		//define fourth column: Margin [kg]
		TableViewerColumn colMarginKg = new TableViewerColumn(tableViewer, SWT.NONE);
		colMarginKg.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colMarginKg.getColumn().setText("Margin [kg]");
		
		//define fifth column: MassWithMargin [kg]
		TableViewerColumn colMassWithMarginKg = new TableViewerColumn(tableViewer, SWT.NONE);
		colMassWithMarginKg.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colMassWithMarginKg.getColumn().setText("Mass with margin [kg]");
		
		//define sixth column: % of total dry mass [kg]
		TableViewerColumn colPercentOfTotalDryMassKg = new TableViewerColumn(tableViewer, SWT.NONE);
		colPercentOfTotalDryMassKg.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colPercentOfTotalDryMassKg.getColumn().setText("Percent of total dry mass [%]");
	}
}
