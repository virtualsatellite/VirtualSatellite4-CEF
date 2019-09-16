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
public abstract class AEquipmentTemperatureParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.EquipmentTemperatureParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_TEMPERATURENOOPSMAX = "temperatureNoOpsMax";
	public static final String PROPERTY_TEMPERATURENOOPSMIN = "temperatureNoOpsMin";
	public static final String PROPERTY_TEMPERATUREOPSMAX = "temperatureOpsMax";
	public static final String PROPERTY_TEMPERATUREOPSMIN = "temperatureOpsMin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentTemperatureParameters() {
	}
	
	public AEquipmentTemperatureParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "EquipmentTemperatureParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "EquipmentTemperatureParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public AEquipmentTemperatureParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: temperatureNoOpsMax
	// *****************************************************************
	private Parameter temperatureNoOpsMax = new Parameter();
	
	private void safeAccessTemperatureNoOpsMax() {
		if (temperatureNoOpsMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureNoOpsMax");
			temperatureNoOpsMax.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getTemperatureNoOpsMax () {
		safeAccessTemperatureNoOpsMax();
		return temperatureNoOpsMax;
	}
	
	// *****************************************************************
	// * Attribute: temperatureNoOpsMin
	// *****************************************************************
	private Parameter temperatureNoOpsMin = new Parameter();
	
	private void safeAccessTemperatureNoOpsMin() {
		if (temperatureNoOpsMin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureNoOpsMin");
			temperatureNoOpsMin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getTemperatureNoOpsMin () {
		safeAccessTemperatureNoOpsMin();
		return temperatureNoOpsMin;
	}
	
	// *****************************************************************
	// * Attribute: temperatureOpsMax
	// *****************************************************************
	private Parameter temperatureOpsMax = new Parameter();
	
	private void safeAccessTemperatureOpsMax() {
		if (temperatureOpsMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureOpsMax");
			temperatureOpsMax.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getTemperatureOpsMax () {
		safeAccessTemperatureOpsMax();
		return temperatureOpsMax;
	}
	
	// *****************************************************************
	// * Attribute: temperatureOpsMin
	// *****************************************************************
	private Parameter temperatureOpsMin = new Parameter();
	
	private void safeAccessTemperatureOpsMin() {
		if (temperatureOpsMin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureOpsMin");
			temperatureOpsMin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getTemperatureOpsMin () {
		safeAccessTemperatureOpsMin();
		return temperatureOpsMin;
	}
	
	
}
