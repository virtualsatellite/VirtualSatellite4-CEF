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
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import org.eclipse.emf.edit.domain.EditingDomain;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import org.eclipse.emf.common.command.Command;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
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
public abstract class APowerInterfaceEnd extends AInterfaceEnd implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.interfaces.PowerInterfaceEnd";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_VOLTAGEMIN = "voltageMin";
	public static final String PROPERTY_VOLTAGENOMINAL = "voltageNominal";
	public static final String PROPERTY_VOLTAGEMAX = "voltageMax";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public APowerInterfaceEnd() {
	}
	
	public APowerInterfaceEnd(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "PowerInterfaceEnd");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "PowerInterfaceEnd");
		setTypeInstance(categoryAssignement);
	}
	
	public APowerInterfaceEnd(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: voltageMin
	// *****************************************************************
	private BeanPropertyFloat voltageMin = new BeanPropertyFloat();
	
	private void safeAccessVoltageMin() {
		if (voltageMin.getTypeInstance() == null) {
			voltageMin.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("voltageMin"));
		}
	}
	
	public Command setVoltageMin(EditingDomain ed, double value) {
		safeAccessVoltageMin();
		return this.voltageMin.setValue(ed, value);
	}
	
	public void setVoltageMin(double value) {
		safeAccessVoltageMin();
		this.voltageMin.setValue(value);
	}
	
	public double getVoltageMin() {
		safeAccessVoltageMin();
		return voltageMin.getValue();
	}
	
	public boolean isSetVoltageMin() {
		safeAccessVoltageMin();
		return voltageMin.isSet();
	}
	
	public BeanPropertyFloat getVoltageMinBean() {
		safeAccessVoltageMin();
		return voltageMin;
	}
	
	// *****************************************************************
	// * Attribute: voltageNominal
	// *****************************************************************
	private BeanPropertyFloat voltageNominal = new BeanPropertyFloat();
	
	private void safeAccessVoltageNominal() {
		if (voltageNominal.getTypeInstance() == null) {
			voltageNominal.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("voltageNominal"));
		}
	}
	
	public Command setVoltageNominal(EditingDomain ed, double value) {
		safeAccessVoltageNominal();
		return this.voltageNominal.setValue(ed, value);
	}
	
	public void setVoltageNominal(double value) {
		safeAccessVoltageNominal();
		this.voltageNominal.setValue(value);
	}
	
	public double getVoltageNominal() {
		safeAccessVoltageNominal();
		return voltageNominal.getValue();
	}
	
	public boolean isSetVoltageNominal() {
		safeAccessVoltageNominal();
		return voltageNominal.isSet();
	}
	
	public BeanPropertyFloat getVoltageNominalBean() {
		safeAccessVoltageNominal();
		return voltageNominal;
	}
	
	// *****************************************************************
	// * Attribute: voltageMax
	// *****************************************************************
	private BeanPropertyFloat voltageMax = new BeanPropertyFloat();
	
	private void safeAccessVoltageMax() {
		if (voltageMax.getTypeInstance() == null) {
			voltageMax.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("voltageMax"));
		}
	}
	
	public Command setVoltageMax(EditingDomain ed, double value) {
		safeAccessVoltageMax();
		return this.voltageMax.setValue(ed, value);
	}
	
	public void setVoltageMax(double value) {
		safeAccessVoltageMax();
		this.voltageMax.setValue(value);
	}
	
	public double getVoltageMax() {
		safeAccessVoltageMax();
		return voltageMax.getValue();
	}
	
	public boolean isSetVoltageMax() {
		safeAccessVoltageMax();
		return voltageMax.isSet();
	}
	
	public BeanPropertyFloat getVoltageMaxBean() {
		safeAccessVoltageMax();
		return voltageMax;
	}
	
	
}
