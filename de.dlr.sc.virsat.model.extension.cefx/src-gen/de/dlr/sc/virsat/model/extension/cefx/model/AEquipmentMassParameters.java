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
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.category.ABeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.Category;


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
public abstract class AEquipmentMassParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.EquipmentMassParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MASSPERUNIT = "massPerUnit";
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
	// * Attribute: massPerUnit
	// *****************************************************************
	private Parameter massPerUnit = new Parameter();
	
	private void safeAccessMassPerUnit() {
		if (massPerUnit.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massPerUnit");
			massPerUnit.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassPerUnit () {
		safeAccessMassPerUnit();
		return massPerUnit;
	}
	
	// *****************************************************************
	// * Attribute: massTotal
	// *****************************************************************
	private Parameter massTotal = new Parameter();
	
	private void safeAccessMassTotal() {
		if (massTotal.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotal");
			massTotal.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassTotal () {
		safeAccessMassTotal();
		return massTotal;
	}
	
	// *****************************************************************
	// * Attribute: massTotalWithMargin
	// *****************************************************************
	private Parameter massTotalWithMargin = new Parameter();
	
	private void safeAccessMassTotalWithMargin() {
		if (massTotalWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotalWithMargin");
			massTotalWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassTotalWithMargin () {
		safeAccessMassTotalWithMargin();
		return massTotalWithMargin;
	}
	
	
}
