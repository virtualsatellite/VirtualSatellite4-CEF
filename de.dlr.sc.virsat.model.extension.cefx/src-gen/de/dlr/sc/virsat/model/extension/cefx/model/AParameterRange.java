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
import javax.xml.bind.annotation.XmlAccessorType;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
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
public abstract class AParameterRange extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.ParameterRange";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MINVALUE = "minValue";
	public static final String PROPERTY_MAXVALUE = "maxValue";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AParameterRange() {
	}
	
	public AParameterRange(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "ParameterRange");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "ParameterRange");
		setTypeInstance(categoryAssignement);
	}
	
	public AParameterRange(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: minValue
	// *****************************************************************
	private BeanPropertyFloat minValue = new BeanPropertyFloat();
	
	private void safeAccessMinValue() {
		if (minValue.getTypeInstance() == null) {
			minValue.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("minValue"));
		}
	}
	
	public Command setMinValue(EditingDomain ed, double value) {
		safeAccessMinValue();
		return this.minValue.setValue(ed, value);
	}
	
	public void setMinValue(double value) {
		safeAccessMinValue();
		this.minValue.setValue(value);
	}
	
	public double getMinValue() {
		safeAccessMinValue();
		return minValue.getValue();
	}
	
	public boolean isSetMinValue() {
		safeAccessMinValue();
		return minValue.isSet();
	}
	
	@XmlElement
	public BeanPropertyFloat getMinValueBean() {
		safeAccessMinValue();
		return minValue;
	}
	
	// *****************************************************************
	// * Attribute: maxValue
	// *****************************************************************
	private BeanPropertyFloat maxValue = new BeanPropertyFloat();
	
	private void safeAccessMaxValue() {
		if (maxValue.getTypeInstance() == null) {
			maxValue.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("maxValue"));
		}
	}
	
	public Command setMaxValue(EditingDomain ed, double value) {
		safeAccessMaxValue();
		return this.maxValue.setValue(ed, value);
	}
	
	public void setMaxValue(double value) {
		safeAccessMaxValue();
		this.maxValue.setValue(value);
	}
	
	public double getMaxValue() {
		safeAccessMaxValue();
		return maxValue.getValue();
	}
	
	public boolean isSetMaxValue() {
		safeAccessMaxValue();
		return maxValue.isSet();
	}
	
	@XmlElement
	public BeanPropertyFloat getMaxValueBean() {
		safeAccessMaxValue();
		return maxValue;
	}
	
	
}
