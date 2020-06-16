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
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
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
	private Parameter activeUnits = new Parameter();
	
	private void safeAccessActiveUnits() {
		if (activeUnits.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("activeUnits");
			activeUnits.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getActiveUnits () {
		safeAccessActiveUnits();
		return activeUnits;
	}
	
	// *****************************************************************
	// * Attribute: powerDutyCycle
	// *****************************************************************
	private Parameter powerDutyCycle = new Parameter();
	
	private void safeAccessPowerDutyCycle() {
		if (powerDutyCycle.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerDutyCycle");
			powerDutyCycle.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerDutyCycle () {
		safeAccessPowerDutyCycle();
		return powerDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitOn
	// *****************************************************************
	private Parameter PowerPerUnitOn = new Parameter();
	
	private void safeAccessPowerPerUnitOn() {
		if (PowerPerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitOn");
			PowerPerUnitOn.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerPerUnitOn () {
		safeAccessPowerPerUnitOn();
		return PowerPerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitStby
	// *****************************************************************
	private Parameter PowerPerUnitStby = new Parameter();
	
	private void safeAccessPowerPerUnitStby() {
		if (PowerPerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitStby");
			PowerPerUnitStby.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerPerUnitStby () {
		safeAccessPowerPerUnitStby();
		return PowerPerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitOnWithMargin
	// *****************************************************************
	private Parameter PowerPerUnitOnWithMargin = new Parameter();
	
	private void safeAccessPowerPerUnitOnWithMargin() {
		if (PowerPerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitOnWithMargin");
			PowerPerUnitOnWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerPerUnitOnWithMargin () {
		safeAccessPowerPerUnitOnWithMargin();
		return PowerPerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitStbyWithMargin
	// *****************************************************************
	private Parameter PowerPerUnitStbyWithMargin = new Parameter();
	
	private void safeAccessPowerPerUnitStbyWithMargin() {
		if (PowerPerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitStbyWithMargin");
			PowerPerUnitStbyWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerPerUnitStbyWithMargin () {
		safeAccessPowerPerUnitStbyWithMargin();
		return PowerPerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerPerUnitAvgWithMargin
	// *****************************************************************
	private Parameter PowerPerUnitAvgWithMargin = new Parameter();
	
	private void safeAccessPowerPerUnitAvgWithMargin() {
		if (PowerPerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerPerUnitAvgWithMargin");
			PowerPerUnitAvgWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerPerUnitAvgWithMargin () {
		safeAccessPowerPerUnitAvgWithMargin();
		return PowerPerUnitAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerAvgWithMargin
	// *****************************************************************
	private Parameter PowerAvgWithMargin = new Parameter();
	
	private void safeAccessPowerAvgWithMargin() {
		if (PowerAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerAvgWithMargin");
			PowerAvgWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerAvgWithMargin () {
		safeAccessPowerAvgWithMargin();
		return PowerAvgWithMargin;
	}
	
	
}
