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
import org.eclipse.emf.edit.domain.EditingDomain;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.category.ABeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
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
public abstract class AEquipmentParameters extends ABeanCategoryAssignment implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.EquipmentParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MARGINMATURITY = "marginMaturity";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentParameters() {
	}
	
	public AEquipmentParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "EquipmentParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "EquipmentParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public AEquipmentParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: marginMaturity
	// *****************************************************************
	private BeanPropertyFloat marginMaturity = new BeanPropertyFloat();
	
	private void safeAccessMarginMaturity() {
		if (marginMaturity.getTypeInstance() == null) {
			marginMaturity.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("marginMaturity"));
		}
	}
	
	public Command setMarginMaturity(EditingDomain ed, double value) {
		safeAccessMarginMaturity();
		return this.marginMaturity.setValue(ed, value);
	}
	
	public void setMarginMaturity(double value) {
		safeAccessMarginMaturity();
		this.marginMaturity.setValue(value);
	}
	
	public double getMarginMaturity() {
		safeAccessMarginMaturity();
		return marginMaturity.getValue();
	}
	
	public boolean isSetMarginMaturity() {
		safeAccessMarginMaturity();
		return marginMaturity.isSet();
	}
	
	public BeanPropertyFloat getMarginMaturityBean() {
		safeAccessMarginMaturity();
		return marginMaturity;
	}
	
	
}