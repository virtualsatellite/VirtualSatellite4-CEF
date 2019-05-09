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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.qudv.SystemOfUnits;
import de.dlr.sc.virsat.model.dvlm.qudv.util.QudvUnitHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

/**
 * Base class for masssumamry tables
 * @author muel_s8
 *
 */

public abstract class AUiSnippetTable {

	protected static final int DEFAULT_TABLE_SIZE = 200;
	protected static final int DEFAULT_COLUMN_SIZE = 150;
	
	protected static final int COLUMN_SEI = 0;
	protected static final int COLUMN_LABEL = 0;
	protected static final int COLUMN_MWOMARGIN = 1;
	protected static final int COLUMN_MARGIN_PERCENT = 2;
	protected static final int COLUMN_MARGIN_KG = 3;
	protected static final int COLUMN_MWMARGINKG = 4;
	protected static final int COLUMN_PERCENT_DRY_MASS = 5;
	
	protected static final String UNDEFINED = "undefined";
	protected static final String NUMBER_FORMAT = "#0.00";
	protected static final String PERCENT = "Percent";
	protected static final String NO_UNIT = "No Unit";
	protected static final String KILOGRAM = "Kilogram";
	
	protected ComposedAdapterFactory adapterFactory;
	protected TableViewer tableViewer;
	protected StructuralElementInstance sei;
	protected VirSatTransactionalEditingDomain domain;
	
	private DecimalFormat decimalFormat;
	
	/**
	 * Public constructor
	 */
	
	public AUiSnippetTable() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		
		decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		decimalFormat.applyPattern(NUMBER_FORMAT);
	}

	/**
	 * Creates the actual swt widget
	 * @param toolkit the toolkit to create the widget
	 * @param parentComposite parent of the widget
	 */
	public void createSwt(FormToolkit toolkit, Composite parentComposite) {
		// Start placing the table
		GridData gridDataTable = new GridData();
		gridDataTable.horizontalAlignment = GridData.FILL;
		gridDataTable.grabExcessHorizontalSpace = true;
		gridDataTable.horizontalSpan = 1;
		gridDataTable.minimumHeight = DEFAULT_TABLE_SIZE;
		gridDataTable.heightHint = DEFAULT_TABLE_SIZE;

		Table table = toolkit.createTable(parentComposite, SWT.MULTI | SWT.FULL_SELECTION);
		table.setLayoutData(gridDataTable);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableViewer = new TableViewer(table);
		
		createColumns();
		
		tableViewer.setContentProvider(getContentProvider());
		tableViewer.setLabelProvider(getLabelProvider());
	}

	/**
	 * Bind this snippet to a model
	 * @param domain 
	 * @param sei the model to bind the snippet to
	 */
	
	public void setDataBinding(VirSatTransactionalEditingDomain domain, StructuralElementInstance sei) {
		this.domain = domain;
		this.sei = sei;
		tableViewer.setInput(sei);
	}

	/**
	 * Forced refresh of the table
	 */
	
	public void update() {
		tableViewer.refresh();
	}
	
	/**
	 * Print a parameter entry of a table
	 * @param parameter the parameter to be printed
	 * @param targetUnitName the unit in which the parameter should be converted for the print
	 * @return a string representation of the default value of the passed parameter
	 */
	
	protected String printParameter(Parameter parameter, String targetUnitName) {
		if (parameter != null) {
			return printBean(parameter.getDefaultValueBean(), targetUnitName);
		} else {
			return UNDEFINED;
		}
	}

	/**
	 * Returns a string for a parameter's default value multiplied by margin and converted to the target unit
	 * @param parameter 
	 * @param marginBean 
	 * @param targetUnitName 
	 * @return string for a parameter's default value multiplied by margin and converted to the target unit
	 */
	protected String printParameterWithMargin(Parameter parameter, BeanPropertyFloat marginBean, String targetUnitName) {
		if (parameter != null && parameter.isSetDefaultValue() && marginBean != null && marginBean.isSet()) {
			double valueWithoutMargin = getBeanValueInTargetUnit(parameter.getDefaultValueBean(), targetUnitName);
			double margin = marginBean.getValueToBaseUnit();
			return printDouble(valueWithoutMargin * margin);
		} else {
			return UNDEFINED;
		}
	}
	
	/**
	 * Print a beanproperty entry of a table
	 * @param bean the bean to be printed
	 * @param targetUnitName the unit in which the parameter should be converted for the print
	 * @return a string representation of the default value of the passed parameter
	 */
	
	protected String printBean(BeanPropertyFloat bean, String targetUnitName) {
		if (bean != null && bean.isSet()) {
			double value = getBeanValueInTargetUnit(bean, targetUnitName);
			return printDouble(value);
		} else {
			return UNDEFINED;
		}
	}

	/**
	 * Converts the value of the bean into target unit
	 * @param bean 
	 * @param targetUnitName 
	 * @return value in target unit
	 */
	private double getBeanValueInTargetUnit(BeanPropertyFloat bean, String targetUnitName) {
		double value = bean.getValue();
		
		if (targetUnitName != null) {
			// Perform unit conversion if necessary
			
			SystemOfUnits sou = domain.getResourceSet().getUnitManagement().getSystemOfUnit();
			AUnit targetUnit = QudvUnitHelper.getInstance().getUnitByName(sou, targetUnitName);
			AUnit sourceUnit = bean.getTypeInstance().getUnit();
			
			value = QudvUnitHelper.getInstance().convertFromSourceToTargetUnit(sourceUnit, value, targetUnit);
		}
		return value;
	}
	
	/**
	 * Get a string representation of a double considering formatting
	 * @param value the value to turn into a string
	 * @return the formatted string of the double
	 */
	protected String printDouble(double value) {
		if (!Double.isNaN(value) && Double.isFinite(value)) {
			return decimalFormat.format(value);
		} else {
			return UNDEFINED;
		}
	}

	/**
	 * Get a string representation of a double in a given unit
	 * @param value the value to turn into a string in base unit
	 * @param targetUnitName desired unit
	 * @return the formatted string of the double
	 */
	protected String printDoubleInUnit(double value, String targetUnitName) {

		SystemOfUnits sou = domain.getResourceSet().getUnitManagement().getSystemOfUnit();
		AUnit targetUnit = QudvUnitHelper.getInstance().getUnitByName(sou, targetUnitName);
		value = QudvUnitHelper.getInstance().convertFromBaseUnitToTargetUnit(targetUnit, value);
		return printDouble(value);
	}
	
	/**
	 * Get the content provider
	 * @return content provider for the table
	 */
	public abstract IStructuredContentProvider getContentProvider();
	
	/**
	 * Get the label provider
	 * @return label provider for the table
	 */
	public abstract ITableLabelProvider getLabelProvider();
	
	/**
	 * Create the table columns
	 */
	protected abstract void createColumns();
}
