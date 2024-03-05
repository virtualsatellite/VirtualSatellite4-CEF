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
import javax.xml.bind.annotation.XmlAccessorType;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import javax.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import javax.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import javax.xml.bind.annotation.XmlElement;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AEquipmentParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.EquipmentParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MARGINMATURITY = "marginMaturity";
	public static final String PROPERTY_UNITQUANTITY = "unitQuantity";
	public static final String EQUIPMENT_PARAMETERS = "EquipmentParameters";
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AEquipmentParameters() {
	}
	
	public AEquipmentParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, EQUIPMENT_PARAMETERS);
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, EQUIPMENT_PARAMETERS);
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
			marginMaturity.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance(PROPERTY_MARGINMATURITY));
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
	
	@XmlElement
	public BeanPropertyFloat getMarginMaturityBean() {
		safeAccessMarginMaturity();
		return marginMaturity;
	}
	
	// *****************************************************************
	// * Attribute: unitQuantity
	// *****************************************************************
	private BeanPropertyInt unitQuantity = new BeanPropertyInt();
	
	private void safeAccessUnitQuantity() {
		if (unitQuantity.getTypeInstance() == null) {
			unitQuantity.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance(PROPERTY_UNITQUANTITY));
		}
	}
	
	public Command setUnitQuantity(EditingDomain ed, long value) {
		safeAccessUnitQuantity();
		return this.unitQuantity.setValue(ed, value);
	}
	
	public void setUnitQuantity(long value) {
		safeAccessUnitQuantity();
		this.unitQuantity.setValue(value);
	}
	
	public long getUnitQuantity() {
		safeAccessUnitQuantity();
		return unitQuantity.getValue();
	}
	
	public boolean isSetUnitQuantity() {
		safeAccessUnitQuantity();
		return unitQuantity.isSet();
	}
	
	@XmlElement
	public BeanPropertyInt getUnitQuantityBean() {
		safeAccessUnitQuantity();
		return unitQuantity;
	}
	
	
}
