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
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance;
import jakarta.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyString;
import jakarta.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
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
public abstract class ASystemMode extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemMode";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_NOTE = "note";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemMode() {
	}
	
	public ASystemMode(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemMode");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemMode");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemMode(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
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
	
	@XmlElement
	public BeanPropertyString getNoteBean() {
		safeAccessNote();
		return note;
	}
	
	
}
