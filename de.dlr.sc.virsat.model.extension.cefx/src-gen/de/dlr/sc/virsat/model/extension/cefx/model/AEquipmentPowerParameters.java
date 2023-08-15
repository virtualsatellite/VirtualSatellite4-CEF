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
import javax.xml.bind.annotation.XmlAccessorType;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import javax.xml.bind.annotation.XmlElement;


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
	public static final String PROPERTY_POWERUNITISACTIVE = "powerUnitIsActive";
	public static final String PROPERTY_POWERDUTYCYCLE = "powerDutyCycle";
	public static final String PROPERTY_POWERUNITON = "PowerUnitOn";
	public static final String PROPERTY_POWERUNITSTBY = "PowerUnitStby";
	public static final String PROPERTY_POWERUNITONWITHMARGIN = "PowerUnitOnWithMargin";
	public static final String PROPERTY_POWERUNITSTBYWITHMARGIN = "PowerUnitStbyWithMargin";
	public static final String PROPERTY_POWERUNITAVGWITHMARGIN = "PowerUnitAvgWithMargin";
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
	// * Attribute: powerUnitIsActive
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerUnitIsActive = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitIsActive() {
		if (powerUnitIsActive.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerUnitIsActive");
			powerUnitIsActive.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitIsActive() {
		safeAccessPowerUnitIsActive();
		return powerUnitIsActive.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitIsActiveBean() {
		safeAccessPowerUnitIsActive();
		return powerUnitIsActive;
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
	
	@XmlElement(nillable = true)
	public Parameter getPowerDutyCycle() {
		safeAccessPowerDutyCycle();
		return powerDutyCycle.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerDutyCycleBean() {
		safeAccessPowerDutyCycle();
		return powerDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: PowerUnitOn
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerUnitOn = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitOn() {
		if (PowerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerUnitOn");
			PowerUnitOn.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitOn() {
		safeAccessPowerUnitOn();
		return PowerUnitOn.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitOnBean() {
		safeAccessPowerUnitOn();
		return PowerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: PowerUnitStby
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerUnitStby = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitStby() {
		if (PowerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerUnitStby");
			PowerUnitStby.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitStby() {
		safeAccessPowerUnitStby();
		return PowerUnitStby.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitStbyBean() {
		safeAccessPowerUnitStby();
		return PowerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: PowerUnitOnWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerUnitOnWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitOnWithMargin() {
		if (PowerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerUnitOnWithMargin");
			PowerUnitOnWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitOnWithMargin() {
		safeAccessPowerUnitOnWithMargin();
		return PowerUnitOnWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitOnWithMarginBean() {
		safeAccessPowerUnitOnWithMargin();
		return PowerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerUnitStbyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerUnitStbyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitStbyWithMargin() {
		if (PowerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerUnitStbyWithMargin");
			PowerUnitStbyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitStbyWithMargin() {
		safeAccessPowerUnitStbyWithMargin();
		return PowerUnitStbyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitStbyWithMarginBean() {
		safeAccessPowerUnitStbyWithMargin();
		return PowerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: PowerUnitAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> PowerUnitAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerUnitAvgWithMargin() {
		if (PowerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("PowerUnitAvgWithMargin");
			PowerUnitAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerUnitAvgWithMargin() {
		safeAccessPowerUnitAvgWithMargin();
		return PowerUnitAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerUnitAvgWithMarginBean() {
		safeAccessPowerUnitAvgWithMargin();
		return PowerUnitAvgWithMargin;
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
	
	@XmlElement(nillable = true)
	public Parameter getPowerAvgWithMargin() {
		safeAccessPowerAvgWithMargin();
		return PowerAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerAvgWithMarginBean() {
		safeAccessPowerAvgWithMargin();
		return PowerAvgWithMargin;
	}
	
	
}
