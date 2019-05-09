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
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.category.ABeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;


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
public abstract class ASystemParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_SYSTEMMARGIN = "systemMargin";
	public static final String PROPERTY_MODEDURATION = "modeDuration";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemParameters() {
	}
	
	public ASystemParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: systemMargin
	// *****************************************************************
	private BeanPropertyFloat systemMargin = new BeanPropertyFloat();
	
	private void safeAccessSystemMargin() {
		if (systemMargin.getTypeInstance() == null) {
			systemMargin.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("systemMargin"));
		}
	}
	
	public Command setSystemMargin(EditingDomain ed, double value) {
		safeAccessSystemMargin();
		return this.systemMargin.setValue(ed, value);
	}
	
	public void setSystemMargin(double value) {
		safeAccessSystemMargin();
		this.systemMargin.setValue(value);
	}
	
	public double getSystemMargin() {
		safeAccessSystemMargin();
		return systemMargin.getValue();
	}
	
	public boolean isSetSystemMargin() {
		safeAccessSystemMargin();
		return systemMargin.isSet();
	}
	
	public BeanPropertyFloat getSystemMarginBean() {
		safeAccessSystemMargin();
		return systemMargin;
	}
	
	// *****************************************************************
	// * Attribute: modeDuration
	// *****************************************************************
	private Parameter modeDuration = new Parameter();
	
	private void safeAccessModeDuration() {
		if (modeDuration.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("modeDuration");
			modeDuration.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getModeDuration () {
		safeAccessModeDuration();
		return modeDuration;
	}
	
	
}
