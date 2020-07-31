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
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
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
public abstract class AValue extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.Value";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_MODE = "mode";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AValue() {
	}
	
	public AValue(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "Value");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "Value");
		setTypeInstance(categoryAssignement);
	}
	
	public AValue(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: value
	// *****************************************************************
	private BeanPropertyFloat value = new BeanPropertyFloat();
	
	private void safeAccessValue() {
		if (value.getTypeInstance() == null) {
			value.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("value"));
		}
	}
	
	public Command setValue(EditingDomain ed, double value) {
		safeAccessValue();
		return this.value.setValue(ed, value);
	}
	
	public void setValue(double value) {
		safeAccessValue();
		this.value.setValue(value);
	}
	
	public double getValue() {
		safeAccessValue();
		return value.getValue();
	}
	
	public boolean isSetValue() {
		safeAccessValue();
		return value.isSet();
	}
	
	public BeanPropertyFloat getValueBean() {
		safeAccessValue();
		return value;
	}
	
	// *****************************************************************
	// * Attribute: mode
	// *****************************************************************
	private BeanPropertyReference<SystemMode> mode = new BeanPropertyReference<>();
	
	private void safeAccessMode() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("mode");
		mode.setTypeInstance(propertyInstance);
	}
	
	public SystemMode getMode() {
		safeAccessMode();
		return mode.getValue();
	}
	
	public Command setMode(EditingDomain ed, SystemMode value) {
		safeAccessMode();
		return mode.setValue(ed, value);
	}
	
	public void setMode(SystemMode value) {
		safeAccessMode();
		mode.setValue(value);
	}
	
	public BeanPropertyReference<SystemMode> getModeBean() {
		safeAccessMode();
		return mode;
	}
	
	
}
