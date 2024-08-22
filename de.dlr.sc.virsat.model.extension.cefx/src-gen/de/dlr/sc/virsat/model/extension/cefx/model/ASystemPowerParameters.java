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
public abstract class ASystemPowerParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemPowerParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_POWERTOTALAVGWITHMARGIN = "powerTotalAvgWithMargin";
	public static final String PROPERTY_POWERTOTALAVGWITHSYSTEMMARGIN = "powerTotalAvgWithSystemMargin";
	public static final String PROPERTY_POWERTOTALENERGYWITHMARGIN = "powerTotalEnergyWithMargin";
	public static final String PROPERTY_POWERTOTALENERGYWITHSYSTEMMARGIN = "powerTotalEnergyWithSystemMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemPowerParameters() {
	}
	
	public ASystemPowerParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemPowerParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemPowerParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemPowerParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
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
	
	// *****************************************************************
	// * Attribute: powerTotalAvgWithSystemMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerTotalAvgWithSystemMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerTotalAvgWithSystemMargin() {
		if (powerTotalAvgWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerTotalAvgWithSystemMargin");
			powerTotalAvgWithSystemMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerTotalAvgWithSystemMargin() {
		safeAccessPowerTotalAvgWithSystemMargin();
		return powerTotalAvgWithSystemMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerTotalAvgWithSystemMarginBean() {
		safeAccessPowerTotalAvgWithSystemMargin();
		return powerTotalAvgWithSystemMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerTotalEnergyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerTotalEnergyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerTotalEnergyWithMargin() {
		if (powerTotalEnergyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerTotalEnergyWithMargin");
			powerTotalEnergyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerTotalEnergyWithMargin() {
		safeAccessPowerTotalEnergyWithMargin();
		return powerTotalEnergyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerTotalEnergyWithMarginBean() {
		safeAccessPowerTotalEnergyWithMargin();
		return powerTotalEnergyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerTotalEnergyWithSystemMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerTotalEnergyWithSystemMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerTotalEnergyWithSystemMargin() {
		if (powerTotalEnergyWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerTotalEnergyWithSystemMargin");
			powerTotalEnergyWithSystemMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerTotalEnergyWithSystemMargin() {
		safeAccessPowerTotalEnergyWithSystemMargin();
		return powerTotalEnergyWithSystemMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerTotalEnergyWithSystemMarginBean() {
		safeAccessPowerTotalEnergyWithSystemMargin();
		return powerTotalEnergyWithSystemMargin;
	}
	
	
}
