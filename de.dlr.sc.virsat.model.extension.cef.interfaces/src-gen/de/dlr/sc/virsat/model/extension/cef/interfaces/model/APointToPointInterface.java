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
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyReference;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import org.eclipse.emf.edit.domain.EditingDomain;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;


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
public abstract class APointToPointInterface extends AInterface implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.PointToPointInterface";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_FROMINTERFACEEND = "fromInterfaceEnd";
	public static final String PROPERTY_TOINTERFCEEND = "toInterfceEnd";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public APointToPointInterface() {
	}
	
	public APointToPointInterface(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "PointToPointInterface");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "PointToPointInterface");
		setTypeInstance(categoryAssignement);
	}
	
	public APointToPointInterface(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: fromInterfaceEnd
	// *****************************************************************
	private BeanPropertyReference<AInterfaceEnd> fromInterfaceEnd = new BeanPropertyReference<>();
	
	private void safeAccessFromInterfaceEnd() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("fromInterfaceEnd");
		fromInterfaceEnd.setTypeInstance(propertyInstance);
	}
	
	public AInterfaceEnd getFromInterfaceEnd() {
		safeAccessFromInterfaceEnd();
		return fromInterfaceEnd.getValue();
	}
	
	public Command setFromInterfaceEnd(EditingDomain ed, AInterfaceEnd value) {
		safeAccessFromInterfaceEnd();
		return fromInterfaceEnd.setValue(ed, value);
	}
	
	public void setFromInterfaceEnd(AInterfaceEnd value) {
		safeAccessFromInterfaceEnd();
		fromInterfaceEnd.setValue(value);
	}
	
	public BeanPropertyReference<AInterfaceEnd> getFromInterfaceEndBean() {
		safeAccessFromInterfaceEnd();
		return fromInterfaceEnd;
	}
	
	// *****************************************************************
	// * Attribute: toInterfceEnd
	// *****************************************************************
	private BeanPropertyReference<AInterfaceEnd> toInterfceEnd = new BeanPropertyReference<>();
	
	private void safeAccessToInterfceEnd() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("toInterfceEnd");
		toInterfceEnd.setTypeInstance(propertyInstance);
	}
	
	public AInterfaceEnd getToInterfceEnd() {
		safeAccessToInterfceEnd();
		return toInterfceEnd.getValue();
	}
	
	public Command setToInterfceEnd(EditingDomain ed, AInterfaceEnd value) {
		safeAccessToInterfceEnd();
		return toInterfceEnd.setValue(ed, value);
	}
	
	public void setToInterfceEnd(AInterfaceEnd value) {
		safeAccessToInterfceEnd();
		toInterfceEnd.setValue(value);
	}
	
	public BeanPropertyReference<AInterfaceEnd> getToInterfceEndBean() {
		safeAccessToInterfceEnd();
		return toInterfceEnd;
	}
	
	
}
