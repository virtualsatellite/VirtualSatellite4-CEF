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
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


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

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.SystemPowerParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_POWERAVGWITHMARGIN = "powerAvgWithMargin";
	public static final String PROPERTY_POWERAVGWITHSYSTEMMARGIN = "powerAvgWithSystemMargin";
	public static final String PROPERTY_POWERENERGYWITHMARGIN = "powerEnergyWithMargin";
	public static final String PROPERTY_POWERENERGYWITHSYSTEMMARGIN = "powerEnergyWithSystemMargin";
	
	
	
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
	// * Attribute: powerAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerAvgWithMargin() {
		if (powerAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerAvgWithMargin");
			powerAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerAvgWithMargin() {
		safeAccessPowerAvgWithMargin();
		return powerAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerAvgWithMarginBean() {
		safeAccessPowerAvgWithMargin();
		return powerAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerAvgWithSystemMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerAvgWithSystemMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerAvgWithSystemMargin() {
		if (powerAvgWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerAvgWithSystemMargin");
			powerAvgWithSystemMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerAvgWithSystemMargin() {
		safeAccessPowerAvgWithSystemMargin();
		return powerAvgWithSystemMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerAvgWithSystemMarginBean() {
		safeAccessPowerAvgWithSystemMargin();
		return powerAvgWithSystemMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerEnergyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerEnergyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerEnergyWithMargin() {
		if (powerEnergyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerEnergyWithMargin");
			powerEnergyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerEnergyWithMargin() {
		safeAccessPowerEnergyWithMargin();
		return powerEnergyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerEnergyWithMarginBean() {
		safeAccessPowerEnergyWithMargin();
		return powerEnergyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerEnergyWithSystemMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> powerEnergyWithSystemMargin = new BeanPropertyComposed<>();
	
	private void safeAccessPowerEnergyWithSystemMargin() {
		if (powerEnergyWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerEnergyWithSystemMargin");
			powerEnergyWithSystemMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getPowerEnergyWithSystemMargin() {
		safeAccessPowerEnergyWithSystemMargin();
		return powerEnergyWithSystemMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getPowerEnergyWithSystemMarginBean() {
		safeAccessPowerEnergyWithSystemMargin();
		return powerEnergyWithSystemMargin;
	}
	
	
}
