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
public abstract class ASubSystemPowerParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SubSystemPowerParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_POWERTOTALAVGWITHMARGIN = "powerTotalAvgWithMargin";
	public static final String PROPERTY_POWERTOTALENERGYWITHMARGIN = "powerTotalEnergyWithMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASubSystemPowerParameters() {
	}
	
	public ASubSystemPowerParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SubSystemPowerParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SubSystemPowerParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASubSystemPowerParameters(CategoryAssignment categoryAssignement) {
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
	
	
}
