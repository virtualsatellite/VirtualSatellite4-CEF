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
import de.dlr.sc.virsat.model.concept.list.TypeSafeReferencePropertyBeanList;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.concept.list.IBeanList;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyReference;
import de.dlr.sc.virsat.model.concept.list.TypeSafeReferencePropertyInstanceList;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
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
public abstract class ABusInterface extends AInterface implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.BusInterface";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_CONECTEDINTERFACEENDS = "conectedInterfaceEnds";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ABusInterface() {
	}
	
	public ABusInterface(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "BusInterface");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "BusInterface");
		setTypeInstance(categoryAssignement);
	}
	
	public ABusInterface(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Array Attribute: conectedInterfaceEnds
	// *****************************************************************
		private IBeanList<AInterfaceEnd> conectedInterfaceEnds = new TypeSafeReferencePropertyInstanceList<>(AInterfaceEnd.class);
	
		private void safeAccessConectedInterfaceEnds() {
			if (conectedInterfaceEnds.getArrayInstance() == null) {
				conectedInterfaceEnds.setArrayInstance((ArrayInstance) helper.getPropertyInstance("conectedInterfaceEnds"));
			}
		}
	
		public IBeanList<AInterfaceEnd> getConectedInterfaceEnds() {
			safeAccessConectedInterfaceEnds();
			return conectedInterfaceEnds;
		}
		
		private IBeanList<BeanPropertyReference<AInterfaceEnd>> conectedInterfaceEndsBean = new TypeSafeReferencePropertyBeanList<>();
		
		private void safeAccessConectedInterfaceEndsBean() {
			if (conectedInterfaceEndsBean.getArrayInstance() == null) {
				conectedInterfaceEndsBean.setArrayInstance((ArrayInstance) helper.getPropertyInstance("conectedInterfaceEnds"));
			}
		}
		
		@XmlElement
		public IBeanList<BeanPropertyReference<AInterfaceEnd>> getConectedInterfaceEndsBean() {
			safeAccessConectedInterfaceEndsBean();
			return conectedInterfaceEndsBean;
		}
	
	
}
