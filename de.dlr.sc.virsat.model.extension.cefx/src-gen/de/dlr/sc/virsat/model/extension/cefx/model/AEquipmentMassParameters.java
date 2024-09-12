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
public abstract class AEquipmentMassParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.EquipmentMassParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MASS = "mass";
	public static final String PROPERTY_MASSTOTAL = "massTotal";
	public static final String PROPERTY_MASSTOTALWITHMARGIN = "massTotalWithMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentMassParameters() {
	}
	
	public AEquipmentMassParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "EquipmentMassParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "EquipmentMassParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public AEquipmentMassParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: mass
	// *****************************************************************
	private BeanPropertyComposed<Parameter> mass = new BeanPropertyComposed<>();
	
	private void safeAccessMass() {
		if (mass.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("mass");
			mass.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMass() {
		safeAccessMass();
		return mass.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassBean() {
		safeAccessMass();
		return mass;
	}
	
	// *****************************************************************
	// * Attribute: massTotal
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massTotal = new BeanPropertyComposed<>();
	
	private void safeAccessMassTotal() {
		if (massTotal.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotal");
			massTotal.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassTotal() {
		safeAccessMassTotal();
		return massTotal.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassTotalBean() {
		safeAccessMassTotal();
		return massTotal;
	}
	
	// *****************************************************************
	// * Attribute: massTotalWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massTotalWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessMassTotalWithMargin() {
		if (massTotalWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotalWithMargin");
			massTotalWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassTotalWithMargin() {
		safeAccessMassTotalWithMargin();
		return massTotalWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassTotalWithMarginBean() {
		safeAccessMassTotalWithMargin();
		return massTotalWithMargin;
	}
	
	
}
