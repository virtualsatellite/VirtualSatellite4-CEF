/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.masssummary;

import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.PlatformUI;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatTransactionalAdapterFactoryContentProvider;
import de.dlr.sc.virsat.project.ui.labelProvider.VirSatTransactionalAdapterFactoryLabelProvider;

/**
 * Table displaying the mass summary of a system
 * @author muel_s8
 *
 */

public class UiSnippetSystem extends AUiSnippetTable {

	private static final int DRY_MASS_ROW = 0;
	private static final int SYSTEM_MARGIN_ROW = 1;
	private static final int TOTAL_DRY_MASS_WITH_SYSTEM_MARGIN_ROW = 2;
	private static final int PROPELLANT_ROW = 3;
	private static final int ADAPTER_MASS_ROW = 4;
	private static final int LAUNCH_MASS_ROW = 5;
	private static final int MAX_LAUNCHER_CAPACITY_ROW = 6;
	private static final int BUFFER_TO_LAUNCH_MASS_ROW = 7;
	
	private static final String[] ROWS = {
		"Total dry mass: ",
		"System margin: ",
		"Total dry mass with system margin:",
		"Propellant: ",
		"Adapter mass: ",
		"Launch mass: ",
		"Max launcher capacity: ",
		"Buffer to launch mass: "
	};
	
	@Override
	public IStructuredContentProvider getContentProvider() {
		return new VirSatTransactionalAdapterFactoryContentProvider(adapterFactory) {
			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof StructuralElementInstance) {
					return ROWS;
				}
				return super.getElements(inputElement);
			}
		};
	}

	@Override
	public ITableLabelProvider getLabelProvider() {
		return new VirSatTransactionalAdapterFactoryLabelProvider(adapterFactory) {
			private Color bufferColor;

			@Override
			public String getColumnText(Object element, int columnIndex) {
				if (sei == null) {
					return null;
				}
				
				BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
				
				List<SystemParameters> systemParamsList = beanCaHelper.getAllBeanCategories(sei, SystemParameters.class);
				SystemParameters systemParams = systemParamsList.size() > 0 ? systemParamsList.get(0) : null;
				
				List<SystemMassParameters> systemMassParamsList = beanCaHelper.getAllBeanCategories(sei, SystemMassParameters.class);
				SystemMassParameters systemMassParams = systemMassParamsList.size() > 0 ? systemMassParamsList.get(0) : null;
				
				switch (columnIndex) {
					case COLUMN_LABEL:
						return (String) element;
					case COLUMN_MWOMARGIN:
						if (element.equals(ROWS[0])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassTotal() : null, KILOGRAM);
						} else {
							return "";
						}
					case COLUMN_MARGIN_PERCENT:
						if (element.equals(ROWS[1])) {
							return printBean(systemParams != null ? systemParams.getSystemMarginBean() : null, PERCENT);
						} else {
							return "";
						}
					case COLUMN_MARGIN_KG:
						if (element.equals(ROWS[DRY_MASS_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassTotalWithMargin() : null, KILOGRAM);
						} else if (element.equals(ROWS[SYSTEM_MARGIN_ROW])) {
							if (systemParams != null && systemParams.isSetSystemMargin() && systemMassParams != null && systemMassParams.getMassTotalWithMargin().isSetDefaultValue()) {
								return printParameterWithMargin(systemMassParams.getMassTotalWithMargin(), systemParams.getSystemMarginBean(), KILOGRAM);
							} else {
								return UNDEFINED;
							}
						} else if (element.equals(ROWS[TOTAL_DRY_MASS_WITH_SYSTEM_MARGIN_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassTotalWithMarginWithSystemMargin() : null, KILOGRAM);
						} else if (element.equals(ROWS[PROPELLANT_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassPropellant() : null, KILOGRAM);
						} else if (element.equals(ROWS[ADAPTER_MASS_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassAdapter() : null, KILOGRAM);
						} else if (element.equals(ROWS[LAUNCH_MASS_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassLaunch() : null, KILOGRAM);
						} else if (element.equals(ROWS[MAX_LAUNCHER_CAPACITY_ROW])) {
							return printParameter(systemMassParams != null ? systemMassParams.getMassLaunchMax() : null, KILOGRAM);
						} else if (element.equals(ROWS[BUFFER_TO_LAUNCH_MASS_ROW])) {
							String print = printParameter(systemMassParams != null ? systemMassParams.getMassBuffer() : null, KILOGRAM);
							
							boolean undefined = print.equals(UNDEFINED);
							
							if (!undefined && Double.parseDouble(print) > 0) {
								bufferColor = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN);
							} else if (!undefined && Double.valueOf(print) < 0)	{
								bufferColor = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_DARK_RED);
							} else {
								bufferColor = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_BLACK);
							}
							return print;
						} else {
							return "";
						}
					default:
						return super.getColumnText(element, columnIndex);
				}
			}
			
			@Override
			public Color getForeground(Object object) {
				if (object.equals(ROWS[BUFFER_TO_LAUNCH_MASS_ROW])) {
					return bufferColor;
				} else {
					return super.getForeground(object);
				}
			}
			
			@Override
			public Font getFont(Object object) {
				if (object.equals(ROWS[TOTAL_DRY_MASS_WITH_SYSTEM_MARGIN_ROW]) || object.equals(ROWS[LAUNCH_MASS_ROW])) {
					return JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
				} else {
					return super.getFont(object);
				}
			}
		};
	}
	
	@Override
	protected void createColumns() {
	    // defining the first column: 
	    TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
	    col.getColumn().setWidth(DEFAULT_COLUMN_SIZE * 2);
	    col.getColumn().setText("");
		
	    // defining the second column: mass w/o margin [kg]
	    TableViewerColumn colMass = new TableViewerColumn(tableViewer, SWT.NONE);
	    colMass.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
	    colMass.getColumn().setText("Mass w/o margin [kg]");
	
		//define third column: Margin [%]
		TableViewerColumn colMarginPercent = new TableViewerColumn(tableViewer, SWT.NONE);
		colMarginPercent.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colMarginPercent.getColumn().setText("Margin [%]");
		
		//define fifth column: Mass with margin [kg]
		TableViewerColumn colMassWithMarginKg = new TableViewerColumn(tableViewer, SWT.NONE);
		colMassWithMarginKg.getColumn().setWidth(DEFAULT_COLUMN_SIZE);
		colMassWithMarginKg.getColumn().setText("Mass with margin [kg]");
	}

}
