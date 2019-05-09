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
public abstract class ASystemMassParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemMassParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MASSLAUNCH = "massLaunch";
	public static final String PROPERTY_MASSTOTAL = "massTotal";
	public static final String PROPERTY_MASSTOTALWITHMARGIN = "massTotalWithMargin";
	public static final String PROPERTY_MASSTOTALWITHMARGINWITHSYSTEMMARGIN = "massTotalWithMarginWithSystemMargin";
	public static final String PROPERTY_MASSADAPTER = "massAdapter";
	public static final String PROPERTY_MASSPROPELLANT = "massPropellant";
	public static final String PROPERTY_MASSLAUNCHMAX = "massLaunchMax";
	public static final String PROPERTY_MASSBUFFER = "massBuffer";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemMassParameters() {
	}
	
	public ASystemMassParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemMassParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemMassParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemMassParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: massLaunch
	// *****************************************************************
	private Parameter massLaunch = new Parameter();
	
	private void safeAccessMassLaunch() {
		if (massLaunch.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massLaunch");
			massLaunch.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassLaunch () {
		safeAccessMassLaunch();
		return massLaunch;
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
	// * Attribute: massTotalWithMarginWithSystemMargin
	// *****************************************************************
	private Parameter massTotalWithMarginWithSystemMargin = new Parameter();
	
	private void safeAccessMassTotalWithMarginWithSystemMargin() {
		if (massTotalWithMarginWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotalWithMarginWithSystemMargin");
			massTotalWithMarginWithSystemMargin.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassTotalWithMarginWithSystemMargin () {
		safeAccessMassTotalWithMarginWithSystemMargin();
		return massTotalWithMarginWithSystemMargin;
	}
	
	// *****************************************************************
	// * Attribute: massAdapter
	// *****************************************************************
	private Parameter massAdapter = new Parameter();
	
	private void safeAccessMassAdapter() {
		if (massAdapter.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massAdapter");
			massAdapter.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassAdapter () {
		safeAccessMassAdapter();
		return massAdapter;
	}
	
	// *****************************************************************
	// * Attribute: massPropellant
	// *****************************************************************
	private Parameter massPropellant = new Parameter();
	
	private void safeAccessMassPropellant() {
		if (massPropellant.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massPropellant");
			massPropellant.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassPropellant () {
		safeAccessMassPropellant();
		return massPropellant;
	}
	
	// *****************************************************************
	// * Attribute: massLaunchMax
	// *****************************************************************
	private Parameter massLaunchMax = new Parameter();
	
	private void safeAccessMassLaunchMax() {
		if (massLaunchMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massLaunchMax");
			massLaunchMax.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassLaunchMax () {
		safeAccessMassLaunchMax();
		return massLaunchMax;
	}
	
	// *****************************************************************
	// * Attribute: massBuffer
	// *****************************************************************
	private Parameter massBuffer = new Parameter();
	
	private void safeAccessMassBuffer() {
		if (massBuffer.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massBuffer");
			massBuffer.setTypeInstance(propertyInstance.getTypeInstance());
		}
	}
	
	public Parameter getMassBuffer () {
		safeAccessMassBuffer();
		return massBuffer;
	}
	
	
}
