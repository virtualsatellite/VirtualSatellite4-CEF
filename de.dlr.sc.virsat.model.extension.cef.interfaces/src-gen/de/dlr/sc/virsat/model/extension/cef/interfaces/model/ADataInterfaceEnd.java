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
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.json.ABeanObjectAdapter;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
public abstract class ADataInterfaceEnd extends AInterfaceEnd implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.DataInterfaceEnd";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_DATAINTERFACETYPE = "dataInterfaceType";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ADataInterfaceEnd() {
	}
	
	public ADataInterfaceEnd(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "DataInterfaceEnd");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "DataInterfaceEnd");
		setTypeInstance(categoryAssignement);
	}
	
	public ADataInterfaceEnd(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: dataInterfaceType
	// *****************************************************************
	private BeanPropertyReference<DataInterfaceTypes> dataInterfaceType = new BeanPropertyReference<>();
	
	private void safeAccessDataInterfaceType() {
		ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) helper.getPropertyInstance("dataInterfaceType");
		dataInterfaceType.setTypeInstance(propertyInstance);
	}
	
	@XmlElement(nillable = true)
	@XmlJavaTypeAdapter(ABeanObjectAdapter.class)
	public DataInterfaceTypes getDataInterfaceType() {
		safeAccessDataInterfaceType();
		return dataInterfaceType.getValue();
	}
	
	public Command setDataInterfaceType(EditingDomain ed, DataInterfaceTypes value) {
		safeAccessDataInterfaceType();
		return dataInterfaceType.setValue(ed, value);
	}
	
	public void setDataInterfaceType(DataInterfaceTypes value) {
		safeAccessDataInterfaceType();
		dataInterfaceType.setValue(value);
	}
	
	public BeanPropertyReference<DataInterfaceTypes> getDataInterfaceTypeBean() {
		safeAccessDataInterfaceType();
		return dataInterfaceType;
	}
	
	
}
