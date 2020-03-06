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
public abstract class ASystemPowerParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemPowerParameters";
	
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
	private Parameter powerAvgWithMargin = new Parameter();
	
	private void safeAccessPowerAvgWithMargin() {
		if (powerAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerAvgWithMargin");
			powerAvgWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerAvgWithMargin () {
		safeAccessPowerAvgWithMargin();
		return powerAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerAvgWithSystemMargin
	// *****************************************************************
	private Parameter powerAvgWithSystemMargin = new Parameter();
	
	private void safeAccessPowerAvgWithSystemMargin() {
		if (powerAvgWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerAvgWithSystemMargin");
			powerAvgWithSystemMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerAvgWithSystemMargin () {
		safeAccessPowerAvgWithSystemMargin();
		return powerAvgWithSystemMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerEnergyWithMargin
	// *****************************************************************
	private Parameter powerEnergyWithMargin = new Parameter();
	
	private void safeAccessPowerEnergyWithMargin() {
		if (powerEnergyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerEnergyWithMargin");
			powerEnergyWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerEnergyWithMargin () {
		safeAccessPowerEnergyWithMargin();
		return powerEnergyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: powerEnergyWithSystemMargin
	// *****************************************************************
	private Parameter powerEnergyWithSystemMargin = new Parameter();
	
	private void safeAccessPowerEnergyWithSystemMargin() {
		if (powerEnergyWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("powerEnergyWithSystemMargin");
			powerEnergyWithSystemMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getPowerEnergyWithSystemMargin () {
		safeAccessPowerEnergyWithSystemMargin();
		return powerEnergyWithSystemMargin;
	}
	
	
}
