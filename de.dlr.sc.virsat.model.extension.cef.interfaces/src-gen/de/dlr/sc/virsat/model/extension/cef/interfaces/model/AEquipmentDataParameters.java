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
import javax.xml.bind.annotation.XmlAccessorType;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import javax.xml.bind.annotation.XmlElement;


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
	private BeanPropertyComposed<Parameter> DataDutyCycle = new BeanPropertyComposed<>();
	
	private void safeAccessDataDutyCycle() {
		if (DataDutyCycle.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataDutyCycle");
			DataDutyCycle.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataDutyCycle() {
		safeAccessDataDutyCycle();
		return DataDutyCycle.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataDutyCycleBean() {
		safeAccessDataDutyCycle();
		return DataDutyCycle;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitOn
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataPerUnitOn = new BeanPropertyComposed<>();
	
	private void safeAccessDataPerUnitOn() {
		if (DataPerUnitOn.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitOn");
			DataPerUnitOn.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataPerUnitOn() {
		safeAccessDataPerUnitOn();
		return DataPerUnitOn.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataPerUnitOnBean() {
		safeAccessDataPerUnitOn();
		return DataPerUnitOn;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitStby
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataPerUnitStby = new BeanPropertyComposed<>();
	
	private void safeAccessDataPerUnitStby() {
		if (DataPerUnitStby.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitStby");
			DataPerUnitStby.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataPerUnitStby() {
		safeAccessDataPerUnitStby();
		return DataPerUnitStby.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataPerUnitStbyBean() {
		safeAccessDataPerUnitStby();
		return DataPerUnitStby;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitOnWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataPerUnitOnWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessDataPerUnitOnWithMargin() {
		if (DataPerUnitOnWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitOnWithMargin");
			DataPerUnitOnWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataPerUnitOnWithMargin() {
		safeAccessDataPerUnitOnWithMargin();
		return DataPerUnitOnWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataPerUnitOnWithMarginBean() {
		safeAccessDataPerUnitOnWithMargin();
		return DataPerUnitOnWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitStbyWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataPerUnitStbyWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessDataPerUnitStbyWithMargin() {
		if (DataPerUnitStbyWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitStbyWithMargin");
			DataPerUnitStbyWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataPerUnitStbyWithMargin() {
		safeAccessDataPerUnitStbyWithMargin();
		return DataPerUnitStbyWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataPerUnitStbyWithMarginBean() {
		safeAccessDataPerUnitStbyWithMargin();
		return DataPerUnitStbyWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataPerUnitAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataPerUnitAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessDataPerUnitAvgWithMargin() {
		if (DataPerUnitAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataPerUnitAvgWithMargin");
			DataPerUnitAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataPerUnitAvgWithMargin() {
		safeAccessDataPerUnitAvgWithMargin();
		return DataPerUnitAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataPerUnitAvgWithMarginBean() {
		safeAccessDataPerUnitAvgWithMargin();
		return DataPerUnitAvgWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: DataAvgWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> DataAvgWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessDataAvgWithMargin() {
		if (DataAvgWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("DataAvgWithMargin");
			DataAvgWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getDataAvgWithMargin() {
		safeAccessDataAvgWithMargin();
		return DataAvgWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getDataAvgWithMarginBean() {
		safeAccessDataAvgWithMargin();
		return DataAvgWithMargin;
	}
	
	
}
