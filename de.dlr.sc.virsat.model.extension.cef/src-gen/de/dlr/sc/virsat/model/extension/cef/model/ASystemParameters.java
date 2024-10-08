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
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyFloat;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;


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
public abstract class ASystemParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cef.SystemParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_SYSTEMMARGIN = "systemMargin";
	public static final String PROPERTY_MODEDURATION = "modeDuration";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemParameters() {
	}
	
	public ASystemParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: systemMargin
	// *****************************************************************
	private BeanPropertyFloat systemMargin = new BeanPropertyFloat();
	
	private void safeAccessSystemMargin() {
		if (systemMargin.getTypeInstance() == null) {
			systemMargin.setTypeInstance((UnitValuePropertyInstance) helper.getPropertyInstance("systemMargin"));
		}
	}
	
	public Command setSystemMargin(EditingDomain ed, double value) {
		safeAccessSystemMargin();
		return this.systemMargin.setValue(ed, value);
	}
	
	public void setSystemMargin(double value) {
		safeAccessSystemMargin();
		this.systemMargin.setValue(value);
	}
	
	public double getSystemMargin() {
		safeAccessSystemMargin();
		return systemMargin.getValue();
	}
	
	public boolean isSetSystemMargin() {
		safeAccessSystemMargin();
		return systemMargin.isSet();
	}
	
	@XmlElement
	public BeanPropertyFloat getSystemMarginBean() {
		safeAccessSystemMargin();
		return systemMargin;
	}
	
	// *****************************************************************
	// * Attribute: modeDuration
	// *****************************************************************
	private BeanPropertyComposed<Parameter> modeDuration = new BeanPropertyComposed<>();
	
	private void safeAccessModeDuration() {
		if (modeDuration.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("modeDuration");
			modeDuration.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getModeDuration() {
		safeAccessModeDuration();
		return modeDuration.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getModeDurationBean() {
		safeAccessModeDuration();
		return modeDuration;
	}
	
	
}
