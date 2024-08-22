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
public abstract class AEquipmentTemperatureParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.EquipmentTemperatureParameters";
	
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
	private BeanPropertyComposed<Parameter> temperatureNoOpsMax = new BeanPropertyComposed<>();
	
	private void safeAccessTemperatureNoOpsMax() {
		if (temperatureNoOpsMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureNoOpsMax");
			temperatureNoOpsMax.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getTemperatureNoOpsMax() {
		safeAccessTemperatureNoOpsMax();
		return temperatureNoOpsMax.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getTemperatureNoOpsMaxBean() {
		safeAccessTemperatureNoOpsMax();
		return temperatureNoOpsMax;
	}
	
	// *****************************************************************
	// * Attribute: temperatureNoOpsMin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> temperatureNoOpsMin = new BeanPropertyComposed<>();
	
	private void safeAccessTemperatureNoOpsMin() {
		if (temperatureNoOpsMin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureNoOpsMin");
			temperatureNoOpsMin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getTemperatureNoOpsMin() {
		safeAccessTemperatureNoOpsMin();
		return temperatureNoOpsMin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getTemperatureNoOpsMinBean() {
		safeAccessTemperatureNoOpsMin();
		return temperatureNoOpsMin;
	}
	
	// *****************************************************************
	// * Attribute: temperatureOpsMax
	// *****************************************************************
	private BeanPropertyComposed<Parameter> temperatureOpsMax = new BeanPropertyComposed<>();
	
	private void safeAccessTemperatureOpsMax() {
		if (temperatureOpsMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureOpsMax");
			temperatureOpsMax.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getTemperatureOpsMax() {
		safeAccessTemperatureOpsMax();
		return temperatureOpsMax.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getTemperatureOpsMaxBean() {
		safeAccessTemperatureOpsMax();
		return temperatureOpsMax;
	}
	
	// *****************************************************************
	// * Attribute: temperatureOpsMin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> temperatureOpsMin = new BeanPropertyComposed<>();
	
	private void safeAccessTemperatureOpsMin() {
		if (temperatureOpsMin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("temperatureOpsMin");
			temperatureOpsMin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getTemperatureOpsMin() {
		safeAccessTemperatureOpsMin();
		return temperatureOpsMin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getTemperatureOpsMinBean() {
		safeAccessTemperatureOpsMin();
		return temperatureOpsMin;
	}
	
	
}
