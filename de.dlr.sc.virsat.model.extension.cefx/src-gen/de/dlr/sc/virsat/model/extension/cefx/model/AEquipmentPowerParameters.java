/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.model;

// *****************************************************************
// * Import Statements
// *****************************************************************
import jakarta.xml.bind.annotation.XmlAccessorType;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import jakarta.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import jakarta.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import jakarta.xml.bind.annotation.XmlElement;


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
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AEquipmentPowerParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.EquipmentPowerParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_POWERUNITISINACTIVE = "powerUnitIsInactive";
	public static final String PROPERTY_POWERUNITDUTYCYCLE = "powerUnitDutyCycle";
	public static final String PROPERTY_POWERUNITON = "powerUnitOn";
	public static final String PROPERTY_POWERUNITSTBY = "powerUnitStby";
	public static final String PROPERTY_POWERUNITONWITHMARGIN = "powerUnitOnWithMargin";
	public static final String PROPERTY_POWERUNITSTBYWITHMARGIN = "powerUnitStbyWithMargin";
	public static final String PROPERTY_POWERUNITAVGWITHMARGIN = "powerUnitAvgWithMargin";
	public static final String PROPERTY_POWERTOTALAVGWITHMARGIN = "powerTotalAvgWithMargin";
	
	
	
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
	// * Attribute: powerUnitIsInactive
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitIsInactive = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitIsInactive() {
		if (powerUnitIsInactive.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitIsInactive");
			powerUnitIsInactive.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitIsInactive() {
		safeAccessPowerUnitIsInactive();
		return powerUnitIsInactive.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitIsInactiveBean() {
		safeAccessPowerUnitIsInactive();
		return powerUnitIsInactive;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitDutyCycle
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitDutyCycle = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitDutyCycle() {
		if (powerUnitDutyCycle.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitDutyCycle");
			powerUnitDutyCycle.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitDutyCycle() {
		safeAccessPowerUnitDutyCycle();
		return powerUnitDutyCycle.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitDutyCycleBean() {
		safeAccessPowerUnitDutyCycle();
		return powerUnitDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitOn
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitOn = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitOn() {
		if (powerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitOn");
			powerUnitOn.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitOn() {
		safeAccessPowerUnitOn();
		return powerUnitOn.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitOnBean() {
		safeAccessPowerUnitOn();
		return powerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitStby
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitStby = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitStby() {
		if (powerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitStby");
			powerUnitStby.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitStby() {
		safeAccessPowerUnitStby();
		return powerUnitStby.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitStbyBean() {
		safeAccessPowerUnitStby();
		return powerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitOnWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitOnWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitOnWithMargin() {
		if (powerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitOnWithMargin");
			powerUnitOnWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitOnWithMargin() {
		safeAccessPowerUnitOnWithMargin();
		return powerUnitOnWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitOnWithMarginBean() {
		safeAccessPowerUnitOnWithMargin();
		return powerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitStbyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitStbyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitStbyWithMargin() {
		if (powerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitStbyWithMargin");
			powerUnitStbyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitStbyWithMargin() {
		safeAccessPowerUnitStbyWithMargin();
		return powerUnitStbyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitStbyWithMarginBean() {
		safeAccessPowerUnitStbyWithMargin();
		return powerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerUnitAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitAvgWithMargin() {
		if (powerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitAvgWithMargin");
			powerUnitAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitAvgWithMargin() {
		safeAccessPowerUnitAvgWithMargin();
		return powerUnitAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitAvgWithMarginBean() {
		safeAccessPowerUnitAvgWithMargin();
		return powerUnitAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerTotalAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerTotalAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerTotalAvgWithMargin() {
		if (powerTotalAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerTotalAvgWithMargin");
			powerTotalAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerTotalAvgWithMargin() {
		safeAccessPowerTotalAvgWithMargin();
		return powerTotalAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerTotalAvgWithMarginBean() {
		safeAccessPowerTotalAvgWithMargin();
		return powerTotalAvgWithMargin;
	}
	
	
}
