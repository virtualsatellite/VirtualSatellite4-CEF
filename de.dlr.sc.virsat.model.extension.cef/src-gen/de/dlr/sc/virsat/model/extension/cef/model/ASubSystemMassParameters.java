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
public abstract class ASubSystemMassParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.SubSystemMassParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MASSTOTAL = "massTotal";
	public static final String PROPERTY_MASSTOTALWITHMARGIN = "massTotalWithMargin";
	public static final String PROPERTY_MASSMARGINPERCENTAGE = "massMarginPercentage";
	public static final String PROPERTY_MASSMARGIN = "massMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASubSystemMassParameters() {
	}
	
	public ASubSystemMassParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SubSystemMassParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SubSystemMassParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASubSystemMassParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
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
	
	// *****************************************************************
	// * Attribute: massMarginPercentage
	// *****************************************************************
	private Parameter massMarginPercentage = new Parameter();
	
	private void safeAccessMassMarginPercentage() {
		if (massMarginPercentage.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massMarginPercentage");
			massMarginPercentage.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassMarginPercentage () {
		safeAccessMassMarginPercentage();
		return massMarginPercentage;
	}
	
	// *****************************************************************
	// * Attribute: massMargin
	// *****************************************************************
	private Parameter massMargin = new Parameter();
	
	private void safeAccessMassMargin() {
		if (massMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massMargin");
			massMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassMargin () {
		safeAccessMassMargin();
		return massMargin;
	}
	
	
}
