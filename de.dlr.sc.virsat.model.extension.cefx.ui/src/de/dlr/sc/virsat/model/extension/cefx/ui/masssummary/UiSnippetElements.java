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

import java.util.ArrayList;

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

import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.tree.BeanStructuralTreeTraverser;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.qudv.util.QudvUnitHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.ui.masssummary.MassSummaryView.TreeTraverserMatcherSubSystemEquipment;
import de.dlr.sc.virsat.model.extension.cefx.ui.masssummary.MassSummaryView.TreeTraverserMatcherSubSystemMass;
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

			private BeanStructuralTreeTraverser treeTraverser = new BeanStructuralTreeTraverser();
			
			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof StructuralElementInstance) {
					BeanStructuralElementInstance beanSei = new BeanStructuralElementInstance((StructuralElementInstance) inputElement);
					SystemMassParameters systemMassParameters = beanSei.getFirst(SystemMassParameters.class);
					SubSystemMassParameters subSystemMassParameters = beanSei.getFirst(SubSystemMassParameters.class);

					ArrayList<StructuralElementInstance> children = new ArrayList<>();
					
					if (systemMassParameters != null) {
						treeTraverser.traverse(beanSei, new TreeTraverserMatcherSubSystemMass() {
							@Override
							public void processMatch(IBeanStructuralElementInstance childBeanSei, IBeanStructuralElementInstance matchingParent) {
								children.add(childBeanSei.getStructuralElementInstance());
							}
						});
					} else if (subSystemMassParameters != null) {
						treeTraverser.traverse(beanSei, new TreeTraverserMatcherSubSystemEquipment() {
							@Override
							public void processMatch(IBeanStructuralElementInstance childBeanSei, IBeanStructuralElementInstance matchingParent) {
								children.add(childBeanSei.getStructuralElementInstance());
							}
						});
					}
					
					return children.toArray();
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

				// First try to convert into a bean since it eases access to all relevant information
				StructuralElementInstance sei = (StructuralElementInstance) element;
				BeanStructuralElementInstance beanSei = new BeanStructuralElementInstance(sei);
				
				// Here we are displaying masses, since it is the mass view. THus we can check for the correct 
				// Categories being assigned rather than checking the SE type names. The new Product structures are less
				// semantically defined in terms of hierarchy. Therefore this different way of accessing the information is
				// needed.
				
				// Now lets see if the children are indirectly typed by Mass Subsystem parameters
				SubSystemMassParameters subSystemMassParameters = beanSei.getFirst(SubSystemMassParameters.class);
				EquipmentMassParameters equipmentMassParameters = beanSei.getFirst(EquipmentMassParameters.class);
				
				if (subSystemMassParameters != null) {
					
					switch (columnIndex) {
						case COLUMN_SEI:
							return beanSei.getName();
						case COLUMN_MWOMARGIN:
							return printParameter(subSystemMassParameters.getMassTotal(), KILOGRAM);
						case COLUMN_MARGIN_PERCENT:
							return printParameter(subSystemMassParameters.getMassMarginPercentage(), PERCENT);
						case COLUMN_MARGIN_KG:
							return printParameter(subSystemMassParameters.getMassMargin(), KILOGRAM);
						case COLUMN_MWMARGINKG:
							return printParameter(subSystemMassParameters.getMassTotal(), KILOGRAM);
						case COLUMN_PERCENT_DRY_MASS:
							SystemMassParameters systemMassParameters = getSystemMassParameters(beanSei);

							if (systemMassParameters != null
									&& systemMassParameters.getMassTotalWithMarginWithSystemMargin().isSetDefaultValue() 
									&& subSystemMassParameters.getMassTotalWithMargin().isSetDefaultValue()) {
								
								double value = subSystemMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit() 
										/ systemMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();

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
				} else if (equipmentMassParameters != null) {
					
					double totalMassWithoutMargin = equipmentMassParameters.getMassTotal().getDefaultValueBean().getValueToBaseUnit();
					double totalMassWithMargin = equipmentMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
					
					double marginMass = totalMassWithMargin - totalMassWithoutMargin;
					double marginPercent = marginMass / totalMassWithoutMargin;
					
					switch (columnIndex) {
						case COLUMN_SEI:
							return beanSei.getName();
						case COLUMN_MWOMARGIN:
							return printDoubleInUnit(totalMassWithoutMargin, KILOGRAM);
						case COLUMN_MARGIN_PERCENT:
							return printDoubleInUnit(marginPercent, PERCENT);
						case COLUMN_MARGIN_KG:
							return printDoubleInUnit(marginMass, KILOGRAM);
						case COLUMN_MWMARGINKG:
							return printDoubleInUnit(totalMassWithMargin, KILOGRAM);
						case COLUMN_PERCENT_DRY_MASS:
							SystemMassParameters systemMassParameters = getSystemMassParameters(beanSei);

							if (systemMassParameters != null 
									&& systemMassParameters.getMassTotalWithMarginWithSystemMargin().isSetDefaultValue() 
									&& !Double.isNaN(totalMassWithMargin)) {
								double value = totalMassWithMargin / systemMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
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

	/**
	 * This method starts crawling the tree upwards from the given Sei in order to find
	 * a CA for SystemMassParameters. If it is found it will be handed back.
	 * @param beanSei the BeanSei from where to start searching for
	 * @return the BeanCategory for the Visualization or null if it was not found
	 */
	private SystemMassParameters getSystemMassParameters(BeanStructuralElementInstance beanSei) {
		IBeanStructuralElementInstance parentBeanSei = beanSei.getParent(); 
		
		while (parentBeanSei != null) {
			SystemMassParameters systemMassParameters = parentBeanSei.getFirst(SystemMassParameters.class);
			if (systemMassParameters != null) {
				return systemMassParameters;
			}
			parentBeanSei = parentBeanSei.getParent();
		}
		
		return null;
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
