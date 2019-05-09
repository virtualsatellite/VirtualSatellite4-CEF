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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.graphics.Image;

import de.dlr.sc.virsat.build.marker.ui.MarkerImageProvider;
import de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.util.PropertyInstanceValueSwitch;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.cefx.ui.snippet.AUiSnippetTableSystemMode;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.ui.propertyinstance.util.PreferencedPropertyInstanceValueSwitchFactory;
import de.dlr.sc.virsat.project.markers.VirSatProblemMarkerHelper;
import de.dlr.sc.virsat.project.ui.labelProvider.VirSatTransactionalAdapterFactoryLabelProvider;

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
public class UiSnippetTableSystemMode extends AUiSnippetTableSystemMode {
	
	
	protected TableViewerColumn colModeDuration;
	private static final String COLUMN_TEXT_MODEDURATION = "Mode Duration";
	private MarkerImageProvider mip = new MarkerImageProvider(new VirSatProblemMarkerHelper());
		
	@Override
	protected void createTableColumns(EditingDomain editingDomain) {
	
		super.createTableColumns(editingDomain);
		colModeDuration = (TableViewerColumn) createDefaultColumn(COLUMN_TEXT_MODEDURATION);
		
	}
	
	@Override
	protected ITableLabelProvider getTableLabelProvider() {
	
		VirSatTransactionalAdapterFactoryLabelProvider labelProvider = new VirSatTransactionalAdapterFactoryLabelProvider(adapterFactory) { 
			PropertyInstanceValueSwitch valueSwitch = PreferencedPropertyInstanceValueSwitchFactory.createInstance();
			@Override
			public String getColumnText(Object object, int columnIndex) {
				CategoryAssignment ca = (CategoryAssignment) object;
				if (ca == null || ca.eContainer() == null) {
					return "";
				} else {
					// COlumn 0 is always the name where as column 1 means the first property thus accessing it by 0
					if (columnIndex == 0) {
						return ca.getName();
					} else if (columnIndex == 1) {
						APropertyInstance propertyInstance = ca.getPropertyInstances().get(0);
						redirectNotification(propertyInstance, object);
						ATypeInstance ti = valueSwitch.doSwitch(propertyInstance);
						redirectNotification(ti, object);
						return valueSwitch.getValueString(propertyInstance);
					} else if (columnIndex == 2) {
						EObject obj = ca.eContainer();
						StructuralElementInstance sei = (StructuralElementInstance) obj;
						ConfigurationTree system = new ConfigurationTree(sei);
						SystemParameters systemParameters = system.getFirst(SystemParameters.class);
						
						if (systemParameters != null) {
							Parameter modeDuration = systemParameters.getModeDuration();
							List<Value> modeValues = modeDuration.getModeValues();
							redirectNotification(modeDuration, object, true);
							for (Value modeValue : modeValues) {
								SystemMode mode = modeValue.getMode();
								APropertyInstance rpi = modeValue.getTypeReferenceProperty();
								redirectNotification(rpi, object);
								if (mode != null && mode.getName().matches(ca.getName())) {
									APropertyInstance propertyInstance = modeValue.getValueBean().getTypeInstance();
									redirectNotification(propertyInstance, object);
									Double modeDurationValue = modeValue.getValue();
									return modeDurationValue.toString();
								}
							}
						}
					}
				}
				return "";
			}
			
			@Override
			public Image getColumnImage(Object object, int columnIndex) {
				if (columnIndex == 0) {
					Image problemImage = mip.getProblemImageForEObject((EObject) object);
					return (problemImage != null) ? problemImage : super.getColumnImage(object, columnIndex);
				} else if (columnIndex == 2) {
					UnitValuePropertyInstance uvpi = PropertyinstancesFactory.eINSTANCE.createUnitValuePropertyInstance(); 
					return super.getColumnImage(uvpi, columnIndex);
				}
				return super.getColumnImage(object, columnIndex);
			}
		}; 
		return labelProvider;
	}
}
