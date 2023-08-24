/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.itemprovider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeColumn;

import de.dlr.sc.virsat.build.marker.ui.EsfMarkerImageProvider;
import de.dlr.sc.virsat.build.marker.ui.MarkerImageProvider;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.APropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.IUnitPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.util.PropertyInstanceHelper;
import de.dlr.sc.virsat.model.dvlm.general.GeneralPackage;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.cefx.ui.Activator;
import de.dlr.sc.virsat.model.extension.cefx.ui.snippet.tableimpl.UiSnippetCefTreeTableImpl;
import de.dlr.sc.virsat.project.markers.VirSatProblemMarkerHelper;
import de.dlr.sc.virsat.project.ui.labelProvider.VirSatTransactionalAdapterFactoryLabelProvider;
import de.dlr.sc.virsat.uieingine.ui.DVLMEditorPlugin;

/**
  * A Label provider that is used for VuirSat Lists that should display some items such as in
  * the reference Selection Dialog. This provider wraps another provider allowing to reuse
  * the EMF generated providers.
  * @author fisc_ph
  *
  */
 
public class VirSatCefTreeLabelProvider extends VirSatTransactionalAdapterFactoryLabelProvider {

	private MarkerImageProvider mip = new MarkerImageProvider(new VirSatProblemMarkerHelper());
	private EsfMarkerImageProvider emip;
	private static final String NO_MODE_SELECTED = "[No Mode Selected]";
	private static final String CALCULATED_STRING = "<<calculated>>";
	private static final String OVERRIDE_IMAGE_PATH = "resources/icons/Override.gif";
	private static final String INHERIT_IMAGE_PATH = "resources/icons/Inherit.gif";
	private static final int OVERRIDE_COLUMN = 3;
	private ColumnViewer columnViewer;
	private Image imageCalculated;
	private Image imageOverride;
	private Image imageInherited;
	
	TreeViewerColumn colOne;
	TreeViewerColumn colTwo;
	TreeViewerColumn colThree;
	TreeViewerColumn colOverrie;
	
	
	/**
	 * The constructor
	 * @param adapterFactory 
	 * @param columnViewer 
	 * @param colOne 
	 * @param colTwo 
	 * @param colThree  
	 * @param emip 
	 */
	public VirSatCefTreeLabelProvider(AdapterFactory adapterFactory, ColumnViewer columnViewer, TreeViewerColumn colOne, TreeViewerColumn colOverride, TreeViewerColumn colTwo, TreeViewerColumn colThree, EsfMarkerImageProvider emip) {
		super(adapterFactory);
		this.columnViewer = columnViewer;
		this.colOne = colOne;
		this.colOverrie = colOverride;
		this.colTwo = colTwo;
		this.colThree = colThree;
		this.emip = emip;
		imageOverride = ExtendedImageRegistry.INSTANCE.getImage(Activator.getImageDescriptor(OVERRIDE_IMAGE_PATH));
		imageInherited = ExtendedImageRegistry.INSTANCE.getImage(Activator.getImageDescriptor(INHERIT_IMAGE_PATH));
		imageCalculated = DVLMEditorPlugin.getPlugin().getImageRegistry().get(DVLMEditorPlugin.IMAGE_CALCULATED);
	}

	@Override
	public String getColumnText(Object object, int columnIndex) {
	
		TreeColumn column = ((TreeViewer) columnViewer).getTree().getColumn(columnIndex);
		CategoryAssignment ca = getCategoryAssignment(object);
		redirectNotification(ca, object);
		
		if (ca == null || ca.getType() == null) {
			return null;
		}
		
		if (ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
			if (ca.getSuperTis().isEmpty()) {
				colOverrie.getColumn().setWidth(0);
			}
			Parameter parameterBean = new Parameter(ca);
			redirectNotification(parameterBean.getDefaultValueBean().getTypeInstance(), ca);
			if (column == colOne.getColumn()) {
				return ca.getName();
			} else if (column == colTwo.getColumn()) {
				return parameterBean.getDefaultValueBean().getFormattedValueWithoutUnit();
			} else if (column == colThree.getColumn()) {
				AUnit unit = parameterBean.getDefaultValueBean().getTypeInstance().getUnit();
				return super.getText(unit);
			} else if (column == colOverrie.getColumn()) {
				column.setWidth(UiSnippetCefTreeTableImpl.OVERRIDE_COLUMN_SIZE);
				Boolean overwrite = parameterBean.getDefaultValueBean().getTypeInstance().isOverride();
				if (parameterBean.getDefaultValueBean().getIsCalculated()) {
					return CALCULATED_STRING;
				}
				return overwrite.toString();
			}
		} else if (ca.getType().getFullQualifiedName().equals(Value.FULL_QUALIFIED_CATEGORY_NAME)) {
			Value valueBean = new Value(ca);
			redirectNotification(valueBean, ca, true);
			SystemMode mode = valueBean.getMode();
			if (mode != null) {
				redirectNotification(mode, ca, true);
			}
			BeanPropertyFloat valuePropertyBean = valueBean.getValueBean();
			if (column == colOne.getColumn()) {
				return (mode == null) ? NO_MODE_SELECTED : mode.getName();
			} else if (column == colTwo.getColumn()) {
				return valuePropertyBean.getFormattedValueWithoutUnit();
			} else if (column == colThree.getColumn()) {
				AUnit unit = valuePropertyBean.getTypeInstance().getUnit();
				return super.getText(unit);
			}
		}	else if (ca.getType().getFullQualifiedName().equals(SystemParameters.FULL_QUALIFIED_CATEGORY_NAME)) {
			UnitValuePropertyInstance uvi = (UnitValuePropertyInstance) object;
			if (column == colOne.getColumn()) {
				return (uvi.getType().getName());
			} else if (column == colTwo.getColumn()) {
				return uvi.getValue();
			} else if (column == colThree.getColumn()) {
				AUnit unit = uvi.getUnit();
				return super.getText(unit);
			}

		} else if (ca.getType().getFullQualifiedName().equals(EquipmentParameters.FULL_QUALIFIED_CATEGORY_NAME)) {
			UnitValuePropertyInstance uvi = (UnitValuePropertyInstance) object;
			if (column == colOne.getColumn()) {
				return (uvi.getType().getName());
			} else if (column == colTwo.getColumn()) {
				return uvi.getValue();
			} else if (column == colThree.getColumn()) {
				AUnit unit = uvi.getUnit();
				return super.getText(unit);
			}	
		}
		
		return null;
	}
	
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
	
		CategoryAssignment ca = getCategoryAssignment(object);
		if (ca != null) {
			if (ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
				return getColumnImageForParameter(ca, columnIndex);
			} else if (ca.getType().getFullQualifiedName().equals(Value.FULL_QUALIFIED_CATEGORY_NAME)) {
				return getColumnImageForModeValue(ca, columnIndex);
			}  else if (ca.getType().getFullQualifiedName().equals(SystemParameters.FULL_QUALIFIED_CATEGORY_NAME)) {
				return getColumnImageForSystemParameter(ca, columnIndex);
			} else if (ca.getType().getFullQualifiedName().equals(EquipmentParameters.FULL_QUALIFIED_CATEGORY_NAME)) {
				return getColumnImageForEquipmentParameter(ca, columnIndex);
			} 
		} 
		return super.getColumnImage(object, columnIndex);		
	}
	
	/**
	 * Get the category assignment of the object
	 * @param object 
	 * @return {@link CategoryAssignment} 
	 */
	private CategoryAssignment getCategoryAssignment(Object object) {
		CategoryAssignment ca = null;
		if (object instanceof ComposedPropertyInstance) {
			ComposedPropertyInstance cpi = (ComposedPropertyInstance) object;
			ca = cpi.getTypeInstance();		
		} else if (object instanceof IUnitPropertyInstance) {
			IUnitPropertyInstance iupi = (IUnitPropertyInstance) object;
			ca = (CategoryAssignment) iupi.eContainer();
		} else if (object instanceof CategoryAssignment) {
			ca = (CategoryAssignment) object;
		}
		return ca;
	}

	/**
	 * Get image for columns displaying mode values if there is any problem marker associated with it otherwise return null
	 * @param ca Category Assignment of the object to be displayed 
	 * @param columnIndex column index
	 * @return image problem image or null image
	 */
	private Image getColumnImageForParameter(CategoryAssignment ca, int columnIndex) {
		PropertyInstanceHelper piHelper = new PropertyInstanceHelper();
		if (columnIndex == 0) {
			Image problemImage = emip.getProblemImageForStructuralFeatureInEobject(ca, GeneralPackage.Literals.INAME__NAME);
			if (problemImage != null) {
				return problemImage;
			} else {
				return super.getColumnImage(ca, columnIndex);
			}
		} else if (columnIndex == 1) {
			APropertyInstance propertyInstance = ca.getPropertyInstances().get(0);
			
			Image problemImage = mip.getProblemImageForEObject(propertyInstance);
			if (problemImage != null) {
				return problemImage;
			} else if (piHelper.isCalculated(ca)) {
				return imageCalculated;
			} 
		} else if (columnIndex == OVERRIDE_COLUMN) {
			if (ca.getType().getFullQualifiedName().equals(Parameter.FULL_QUALIFIED_CATEGORY_NAME)) {
				Parameter parameterBean = new Parameter(ca);
				Boolean override = parameterBean.getDefaultValueBean().getTypeInstance().isOverride();
				if (!piHelper.isCalculated(ca)) {
					return (override) ? imageOverride : imageInherited;
				}
			}
			
		}
		return null;
	}
	
	/**
	 * Get image for columns displaying mode values if there is any problem marker associated with it otherwise return null
	 * @param ca Category Assignment of the object to be displayed 
	 * @param columnIndex column index
	 * @return image problem image or null image
	 */
	private Image getColumnImageForSystemParameter(CategoryAssignment ca, int columnIndex) {
		if (columnIndex == 0) {
			Image problemImage = emip.getProblemImageForStructuralFeatureInEobject(ca, GeneralPackage.Literals.INAME__NAME);
			return (problemImage != null) ? problemImage : super.getColumnImage(ca, columnIndex);
		} else if (columnIndex == 1) {
			APropertyInstance propertyInstance = ca.getPropertyInstances().get(0);
			Image problemImage = mip.getProblemImageForEObject(propertyInstance);
			return (problemImage != null) ? problemImage : 	null;
		}
		return null;
	}
	
	/**
	 * Get image for columns displaying mode values if there is any problem marker associated with it otherwise return null
	 * @param ca Category Assignment of the object to be displayed 
	 * @param columnIndex column index
	 * @return image problem image or null image
	 */
	private Image getColumnImageForModeValue(CategoryAssignment ca, int columnIndex) {
		if (columnIndex == 0) {
			APropertyInstance propertyInstance = ca.getPropertyInstances().get(1);
			Image problemImage = mip.getProblemImageForEObject(propertyInstance);
			return (problemImage != null) ? problemImage : super.getColumnImage(ca, columnIndex);
		} else if (columnIndex == 1) {
			APropertyInstance propertyInstance = ca.getPropertyInstances().get(0);
			Image problemImage = mip.getProblemImageForEObject(propertyInstance);
			return (problemImage != null) ? problemImage : 	null;
		} 
		return null;
	}
	
	/**
	 * Get image for columns displaying mode values if there is any problem marker associated with it otherwise return null
	 * @param ca Category Assignment of the object to be displayed 
	 * @param columnIndex column index
	 * @return image problem image or null image
	 */
	private Image getColumnImageForEquipmentParameter(CategoryAssignment ca, int columnIndex) {
		if (columnIndex == 0) {
			Image problemImage = emip.getProblemImageForStructuralFeatureInEobject(ca, GeneralPackage.Literals.INAME__NAME);
			return (problemImage != null) ? problemImage : super.getColumnImage(ca, columnIndex);
		} else if (columnIndex == 1) {
			APropertyInstance propertyInstance = ca.getPropertyInstances().get(0);
			Image problemImage = mip.getProblemImageForEObject(propertyInstance);
			return (problemImage != null) ? problemImage : 	null;
		} 
		return null;
	}

}
