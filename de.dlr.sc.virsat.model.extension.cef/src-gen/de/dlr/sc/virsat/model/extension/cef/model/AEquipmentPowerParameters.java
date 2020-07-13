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
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
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
public abstract class AEquipmentPowerParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.EquipmentPowerParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_ACTIVEUNITS = "activeUnits";
	public static final String PROPERTY_POWERDUTYCYCLE = "powerDutyCycle";
	public static final String PROPERTY_POWERPERUNITON = "PowerPerUnitOn";
	public static final String PROPERTY_POWERPERUNITSTBY = "PowerPerUnitStby";
	public static final String PROPERTY_POWERPERUNITONWITHMARGIN = "PowerPerUnitOnWithMargin";
	public static final String PROPERTY_POWERPERUNITSTBYWITHMARGIN = "PowerPerUnitStbyWithMargin";
	public static final String PROPERTY_POWERPERUNITAVGWITHMARGIN = "PowerPerUnitAvgWithMargin";
	public static final String PROPERTY_POWERAVGWITHMARGIN = "PowerAvgWithMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentPowerParameters() {
	}
	
	public AEquipmentPowerParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "EquipmentPowerParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "EquipmentPowerParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public AEquipmentPowerParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: activeUnits
	// *****************************************************************
	private BeanPropertyComposed<Parameter> activeUnits = new BeanPropertyComposed<>();
	
	private void safeAccessActiveUnits() {
		if (activeUnits.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("activeUnits");
			activeUnits.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getActiveUnits() {
		safeAccessActiveUnits();
		return activeUnits.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getActiveUnitsBean() {
		safeAccessActiveUnits();
		return activeUnits;
	}
	
	// *****************************************************************
	// * Attribute: powerDutyCycle
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerDutyCycle = new BeanPropertyComposed<>();
	
	private void safeAccessPowerDutyCycle() {
		if (powerDutyCycle.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerDutyCycle");
			powerDutyCycle.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerDutyCycle() {
		safeAccessPowerDutyCycle();
		return powerDutyCycle.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerDutyCycleBean() {
		safeAccessPowerDutyCycle();
		return powerDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitOn
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerPerUnitOn = new BeanPropertyComposed<>();
	
	private void safeAccessPowerPerUnitOn() {
		if (PowerPerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitOn");
			PowerPerUnitOn.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerPerUnitOn() {
		safeAccessPowerPerUnitOn();
		return PowerPerUnitOn.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerPerUnitOnBean() {
		safeAccessPowerPerUnitOn();
		return PowerPerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitStby
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerPerUnitStby = new BeanPropertyComposed<>();
	
	private void safeAccessPowerPerUnitStby() {
		if (PowerPerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitStby");
			PowerPerUnitStby.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerPerUnitStby() {
		safeAccessPowerPerUnitStby();
		return PowerPerUnitStby.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerPerUnitStbyBean() {
		safeAccessPowerPerUnitStby();
		return PowerPerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitOnWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerPerUnitOnWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerPerUnitOnWithMargin() {
		if (PowerPerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitOnWithMargin");
			PowerPerUnitOnWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerPerUnitOnWithMargin() {
		safeAccessPowerPerUnitOnWithMargin();
		return PowerPerUnitOnWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerPerUnitOnWithMarginBean() {
		safeAccessPowerPerUnitOnWithMargin();
		return PowerPerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitStbyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerPerUnitStbyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerPerUnitStbyWithMargin() {
		if (PowerPerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitStbyWithMargin");
			PowerPerUnitStbyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerPerUnitStbyWithMargin() {
		safeAccessPowerPerUnitStbyWithMargin();
		return PowerPerUnitStbyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerPerUnitStbyWithMarginBean() {
		safeAccessPowerPerUnitStbyWithMargin();
		return PowerPerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerPerUnitAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerPerUnitAvgWithMargin() {
		if (PowerPerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitAvgWithMargin");
			PowerPerUnitAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerPerUnitAvgWithMargin() {
		safeAccessPowerPerUnitAvgWithMargin();
		return PowerPerUnitAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerPerUnitAvgWithMarginBean() {
		safeAccessPowerPerUnitAvgWithMargin();
		return PowerPerUnitAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerAvgWithMargin() {
		if (PowerAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerAvgWithMargin");
			PowerAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	public Parameter getPowerAvgWithMargin() {
		safeAccessPowerAvgWithMargin();
		return PowerAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerAvgWithMarginBean() {
		safeAccessPowerAvgWithMargin();
		return PowerAvgWithMargin;
	}
	
	
}
