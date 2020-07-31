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
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyString;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyInt;


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
public abstract class AAInterfaceEnd extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.AInterfaceEnd";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_QUANTITY = "quantity";
	public static final String PROPERTY_NOTE = "note";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AAInterfaceEnd() {
	}
	
	public AAInterfaceEnd(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "AInterfaceEnd");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "AInterfaceEnd");
		setTypeInstance(categoryAssignement);
	}
	
	public AAInterfaceEnd(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: quantity
	// *****************************************************************
	private BeanPropertyInt quantity = new BeanPropertyInt();
	
	private void safeAccessQuantity() {
		if (quantity.getTypeInstance() == null) {
			quantity.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("quantity"));
		}
	}
	
	public Command setQuantity(EditingDomain ed, long value) {
		safeAccessQuantity();
		return this.quantity.setValue(ed, value);
	}
	
	public void setQuantity(long value) {
		safeAccessQuantity();
		this.quantity.setValue(value);
	}
	
	public long getQuantity() {
		safeAccessQuantity();
		return quantity.getValue();
	}
	
	public boolean isSetQuantity() {
		safeAccessQuantity();
		return quantity.isSet();
	}
	
	public BeanPropertyInt getQuantityBean() {
		safeAccessQuantity();
		return quantity;
	}
	
	// *****************************************************************
	// * Attribute: note
	// *****************************************************************
	private BeanPropertyString note = new BeanPropertyString();
	
	private void safeAccessNote() {
		if (note.getTypeInstance() == null) {
			note.setTypeInstance((ValuePropertyInstance) helper.getPropertyInstance("note"));
		}
	}
	
	public Command setNote(EditingDomain ed, String value) {
		safeAccessNote();
		return this.note.setValue(ed, value);
	}
	
	public void setNote(String value) {
		safeAccessNote();
		this.note.setValue(value);
	}
	
	public String getNote() {
		safeAccessNote();
		return note.getValue();
	}
	
	public BeanPropertyString getNoteBean() {
		safeAccessNote();
		return note;
	}
	
	
}
