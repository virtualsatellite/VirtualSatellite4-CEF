/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.model;

// *****************************************************************
// * Import Statements
// *****************************************************************
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;


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
public abstract class AEquipmentDataParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.EquipmentDataParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_DATADUTYCYCLE = "DataDutyCycle";
	public static final String PROPERTY_DATAPERUNITON = "DataPerUnitOn";
	public static final String PROPERTY_DATAPERUNITSTBY = "DataPerUnitStby";
	public static final String PROPERTY_DATAPERUNITONWITHMARGIN = "DataPerUnitOnWithMargin";
	public static final String PROPERTY_DATAPERUNITSTBYWITHMARGIN = "DataPerUnitStbyWithMargin";
	public static final String PROPERTY_DATAPERUNITAVGWITHMARGIN = "DataPerUnitAvgWithMargin";
	public static final String PROPERTY_DATAAVGWITHMARGIN = "DataAvgWithMargin";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentDataParameters() {
	}
	
	public AEquipmentDataParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "EquipmentDataParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "EquipmentDataParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public AEquipmentDataParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: DataDutyCycle
	// *****************************************************************
	private Parameter DataDutyCycle = new Parameter();
	
	private void safeAccessDataDutyCycle() {
		if (DataDutyCycle.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataDutyCycle");
			DataDutyCycle.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataDutyCycle () {
		safeAccessDataDutyCycle();
		return DataDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitOn
	// *****************************************************************
	private Parameter DataPerUnitOn = new Parameter();
	
	private void safeAccessDataPerUnitOn() {
		if (DataPerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitOn");
			DataPerUnitOn.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataPerUnitOn () {
		safeAccessDataPerUnitOn();
		return DataPerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitStby
	// *****************************************************************
	private Parameter DataPerUnitStby = new Parameter();
	
	private void safeAccessDataPerUnitStby() {
		if (DataPerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitStby");
			DataPerUnitStby.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataPerUnitStby () {
		safeAccessDataPerUnitStby();
		return DataPerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitOnWithMargin
	// *****************************************************************
	private Parameter DataPerUnitOnWithMargin = new Parameter();
	
	private void safeAccessDataPerUnitOnWithMargin() {
		if (DataPerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitOnWithMargin");
			DataPerUnitOnWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataPerUnitOnWithMargin () {
		safeAccessDataPerUnitOnWithMargin();
		return DataPerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitStbyWithMargin
	// *****************************************************************
	private Parameter DataPerUnitStbyWithMargin = new Parameter();
	
	private void safeAccessDataPerUnitStbyWithMargin() {
		if (DataPerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitStbyWithMargin");
			DataPerUnitStbyWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataPerUnitStbyWithMargin () {
		safeAccessDataPerUnitStbyWithMargin();
		return DataPerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitAvgWithMargin
	// *****************************************************************
	private Parameter DataPerUnitAvgWithMargin = new Parameter();
	
	private void safeAccessDataPerUnitAvgWithMargin() {
		if (DataPerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitAvgWithMargin");
			DataPerUnitAvgWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataPerUnitAvgWithMargin () {
		safeAccessDataPerUnitAvgWithMargin();
		return DataPerUnitAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataAvgWithMargin
	// *****************************************************************
	private Parameter DataAvgWithMargin = new Parameter();
	
	private void safeAccessDataAvgWithMargin() {
		if (DataAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataAvgWithMargin");
			DataAvgWithMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getDataAvgWithMargin () {
		safeAccessDataAvgWithMargin();
		return DataAvgWithMargin;
	}
	
	
}
