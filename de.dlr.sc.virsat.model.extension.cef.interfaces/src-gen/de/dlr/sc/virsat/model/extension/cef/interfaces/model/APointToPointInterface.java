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
import org.eclipse.core.runtime.CoreException;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesPackage;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.concept.types.factory.BeanCategoryAssignmentFactory;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.AInterfaceEnd;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.extension.cef.interfaces.model.AInterface;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;


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
	private AInterfaceEnd fromInterfaceEnd;
	
	private void safeAccessFromInterfaceEnd() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("fromInterfaceEnd");
		CategoryAssignment ca = (CategoryAssignment) propertyInstance.getReference();
		
		if (ca != null) {
			if (fromInterfaceEnd == null) {
				createFromInterfaceEnd(ca);
			}
			fromInterfaceEnd.setTypeInstance(ca);
		} else {
			fromInterfaceEnd = null;
		}
	}
	
	private void createFromInterfaceEnd(CategoryAssignment ca) {
		try {
			BeanCategoryAssignmentFactory beanFactory = new BeanCategoryAssignmentFactory();
			fromInterfaceEnd = (AInterfaceEnd) beanFactory.getInstanceFor(ca);
		} catch (CoreException e) {
			
		}
	}
					
	public AInterfaceEnd getFromInterfaceEnd() {
		safeAccessFromInterfaceEnd();
		return fromInterfaceEnd;
	}
	
	public Command setFromInterfaceEnd(EditingDomain ed, AInterfaceEnd value) {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("fromInterfaceEnd");
		CategoryAssignment ca = value.getTypeInstance();
		return SetCommand.create(ed, propertyInstance, PropertyinstancesPackage.Literals.REFERENCE_PROPERTY_INSTANCE__REFERENCE, ca);
	}
	
	public void setFromInterfaceEnd(AInterfaceEnd value) {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("fromInterfaceEnd");
		if (value != null) {
			propertyInstance.setReference(value.getTypeInstance());
		} else {
			propertyInstance.setReference(null);
		}
	}
	
	// *****************************************************************
	// * Attribute: toInterfceEnd
	// *****************************************************************
	private AInterfaceEnd toInterfceEnd;
	
	private void safeAccessToInterfceEnd() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("toInterfceEnd");
		CategoryAssignment ca = (CategoryAssignment) propertyInstance.getReference();
		
		if (ca != null) {
			if (toInterfceEnd == null) {
				createToInterfceEnd(ca);
			}
			toInterfceEnd.setTypeInstance(ca);
		} else {
			toInterfceEnd = null;
		}
	}
	
	private void createToInterfceEnd(CategoryAssignment ca) {
		try {
			BeanCategoryAssignmentFactory beanFactory = new BeanCategoryAssignmentFactory();
			toInterfceEnd = (AInterfaceEnd) beanFactory.getInstanceFor(ca);
		} catch (CoreException e) {
			
		}
	}
					
	public AInterfaceEnd getToInterfceEnd() {
		safeAccessToInterfceEnd();
		return toInterfceEnd;
	}
	
	public Command setToInterfceEnd(EditingDomain ed, AInterfaceEnd value) {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("toInterfceEnd");
		CategoryAssignment ca = value.getTypeInstance();
		return SetCommand.create(ed, propertyInstance, PropertyinstancesPackage.Literals.REFERENCE_PROPERTY_INSTANCE__REFERENCE, ca);
	}
	
	public void setToInterfceEnd(AInterfaceEnd value) {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("toInterfceEnd");
		if (value != null) {
			propertyInstance.setReference(value.getTypeInstance());
		} else {
			propertyInstance.setReference(null);
		}
	}
	
	
}
