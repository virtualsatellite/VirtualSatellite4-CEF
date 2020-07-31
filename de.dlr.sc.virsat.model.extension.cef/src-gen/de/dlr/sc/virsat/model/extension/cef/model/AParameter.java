/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.model;

// *****************************************************************
// * Import Statements
// *****************************************************************
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.concept.list.IBeanList;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyString;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.concept.list.TypeSafeComposedPropertyBeanList;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.concept.list.TypeSafeComposedPropertyInstanceList;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;


// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Abstract Generator Gap Class
 * 
 * Don't Manually modify this class
 * 
 * 
 * 
 */	
public abstract class AParameter extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.Parameter";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_DEFAULTVALUE = "defaultValue";
	public static final String PROPERTY_MODEVALUES = "modeValues";
	public static final String PROPERTY_RANGEVALUES = "rangeValues";
	public static final String PROPERTY_NOTE = "note";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AParameter() {
	}
	
	public AParameter(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "Parameter");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "Parameter");
		setTypeInstance(categoryAssignement);
	}
	
	public AParameter(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: defaultValue
	// *****************************************************************
	private BeanPropertyFloat defaultValue = new BeanPropertyFloat();
	
	private void safeAccessDefaultValue() {
		if (defaultValue.getTypeInstance() == null) {
			defaultValue.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("defaultValue"));
		}
	}
	
	public Command setDefaultValue(EditingDomain ed, double value) {
		safeAccessDefaultValue();
		return this.defaultValue.setValue(ed, value);
	}
	
	public void setDefaultValue(double value) {
		safeAccessDefaultValue();
		this.defaultValue.setValue(value);
	}
	
	public double getDefaultValue() {
		safeAccessDefaultValue();
		return defaultValue.getValue();
	}
	
	public boolean isSetDefaultValue() {
		safeAccessDefaultValue();
		return defaultValue.isSet();
	}
	
	public BeanPropertyFloat getDefaultValueBean() {
		safeAccessDefaultValue();
		return defaultValue;
	}
	
	// *****************************************************************
	// * Array Attribute: modeValues
	// *****************************************************************
	private IBeanList<Value> modeValues = new TypeSafeComposedPropertyInstanceList<>(Value.class);
	
	private void safeAccessModeValues() {
		if (modeValues.getArrayInstance() == null) {
			modeValues.setArrayInstance((ArrayInstance) helper.getPropertyInstance("modeValues"));
		}
	}
	
	public IBeanList<Value> getModeValues() {
		safeAccessModeValues();
		return modeValues;
	}
	
	private IBeanList<BeanPropertyComposed<Value>> modeValuesBean = new TypeSafeComposedPropertyBeanList<>();
	
	private void safeAccessModeValuesBean() {
		if (modeValuesBean.getArrayInstance() == null) {
			modeValuesBean.setArrayInstance((ArrayInstance) helper.getPropertyInstance("modeValues"));
		}
	}
	
	public IBeanList<BeanPropertyComposed<Value>> getModeValuesBean() {
		safeAccessModeValuesBean();
		return modeValuesBean;
	}
	
	// *****************************************************************
	// * Array Attribute: rangeValues
	// *****************************************************************
	private IBeanList<ParameterRange> rangeValues = new TypeSafeComposedPropertyInstanceList<>(ParameterRange.class);
	
	private void safeAccessRangeValues() {
		if (rangeValues.getArrayInstance() == null) {
			rangeValues.setArrayInstance((ArrayInstance) helper.getPropertyInstance("rangeValues"));
		}
	}
	
	public IBeanList<ParameterRange> getRangeValues() {
		safeAccessRangeValues();
		return rangeValues;
	}
	
	private IBeanList<BeanPropertyComposed<ParameterRange>> rangeValuesBean = new TypeSafeComposedPropertyBeanList<>();
	
	private void safeAccessRangeValuesBean() {
		if (rangeValuesBean.getArrayInstance() == null) {
			rangeValuesBean.setArrayInstance((ArrayInstance) helper.getPropertyInstance("rangeValues"));
		}
	}
	
	public IBeanList<BeanPropertyComposed<ParameterRange>> getRangeValuesBean() {
		safeAccessRangeValuesBean();
		return rangeValuesBean;
	}
	
	// *****************************************************************
	// * Attribute: note
	// *****************************************************************
	private BeanPropertyString note = new BeanPropertyString();
	
	private void safeAccessNote() {
		if (note.getTypeInstance() == null) {
			note.setTypeInstance((ValuePropertyInstance) helper.getPropertyInstance("note"));
		}
	}
	
	public Command setNote(EditingDomain ed, String value) {
		safeAccessNote();
		return this.note.setValue(ed, value);
	}
	
	public void setNote(String value) {
		safeAccessNote();
		this.note.setValue(value);
	}
	
	public String getNote() {
		safeAccessNote();
		return note.getValue();
	}
	
	public BeanPropertyString getNoteBean() {
		safeAccessNote();
		return note;
	}
	
	
}
