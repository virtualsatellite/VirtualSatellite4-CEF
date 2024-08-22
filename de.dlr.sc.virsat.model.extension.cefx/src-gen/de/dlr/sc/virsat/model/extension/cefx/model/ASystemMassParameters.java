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
import jakarta.xml.bind.annotation.XmlRootElement;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import jakarta.xml.bind.annotation.XmlAccessType;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
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
public abstract class ASystemMassParameters extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.SystemMassParameters";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_MASSLAUNCH = "massLaunch";
	public static final String PROPERTY_MASSTOTAL = "massTotal";
	public static final String PROPERTY_MASSTOTALWITHMARGIN = "massTotalWithMargin";
	public static final String PROPERTY_MASSTOTALWITHMARGINWITHSYSTEMMARGIN = "massTotalWithMarginWithSystemMargin";
	public static final String PROPERTY_MASSADAPTER = "massAdapter";
	public static final String PROPERTY_MASSPROPELLANT = "massPropellant";
	public static final String PROPERTY_MASSLAUNCHMAX = "massLaunchMax";
	public static final String PROPERTY_MASSBUFFER = "massBuffer";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public ASystemMassParameters() {
	}
	
	public ASystemMassParameters(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "SystemMassParameters");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "SystemMassParameters");
		setTypeInstance(categoryAssignement);
	}
	
	public ASystemMassParameters(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: massLaunch
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massLaunch = new BeanPropertyComposed<>();
	
	private void safeAccessMassLaunch() {
		if (massLaunch.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massLaunch");
			massLaunch.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassLaunch() {
		safeAccessMassLaunch();
		return massLaunch.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassLaunchBean() {
		safeAccessMassLaunch();
		return massLaunch;
	}
	
	// *****************************************************************
	// * Attribute: massTotal
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massTotal = new BeanPropertyComposed<>();
	
	private void safeAccessMassTotal() {
		if (massTotal.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotal");
			massTotal.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassTotal() {
		safeAccessMassTotal();
		return massTotal.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassTotalBean() {
		safeAccessMassTotal();
		return massTotal;
	}
	
	// *****************************************************************
	// * Attribute: massTotalWithMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massTotalWithMargin = new BeanPropertyComposed<>();
	
	private void safeAccessMassTotalWithMargin() {
		if (massTotalWithMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotalWithMargin");
			massTotalWithMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassTotalWithMargin() {
		safeAccessMassTotalWithMargin();
		return massTotalWithMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassTotalWithMarginBean() {
		safeAccessMassTotalWithMargin();
		return massTotalWithMargin;
	}
	
	// *****************************************************************
	// * Attribute: massTotalWithMarginWithSystemMargin
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massTotalWithMarginWithSystemMargin = new BeanPropertyComposed<>();
	
	private void safeAccessMassTotalWithMarginWithSystemMargin() {
		if (massTotalWithMarginWithSystemMargin.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massTotalWithMarginWithSystemMargin");
			massTotalWithMarginWithSystemMargin.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassTotalWithMarginWithSystemMargin() {
		safeAccessMassTotalWithMarginWithSystemMargin();
		return massTotalWithMarginWithSystemMargin.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassTotalWithMarginWithSystemMarginBean() {
		safeAccessMassTotalWithMarginWithSystemMargin();
		return massTotalWithMarginWithSystemMargin;
	}
	
	// *****************************************************************
	// * Attribute: massAdapter
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massAdapter = new BeanPropertyComposed<>();
	
	private void safeAccessMassAdapter() {
		if (massAdapter.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massAdapter");
			massAdapter.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassAdapter() {
		safeAccessMassAdapter();
		return massAdapter.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassAdapterBean() {
		safeAccessMassAdapter();
		return massAdapter;
	}
	
	// *****************************************************************
	// * Attribute: massPropellant
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massPropellant = new BeanPropertyComposed<>();
	
	private void safeAccessMassPropellant() {
		if (massPropellant.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massPropellant");
			massPropellant.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassPropellant() {
		safeAccessMassPropellant();
		return massPropellant.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassPropellantBean() {
		safeAccessMassPropellant();
		return massPropellant;
	}
	
	// *****************************************************************
	// * Attribute: massLaunchMax
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massLaunchMax = new BeanPropertyComposed<>();
	
	private void safeAccessMassLaunchMax() {
		if (massLaunchMax.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massLaunchMax");
			massLaunchMax.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassLaunchMax() {
		safeAccessMassLaunchMax();
		return massLaunchMax.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassLaunchMaxBean() {
		safeAccessMassLaunchMax();
		return massLaunchMax;
	}
	
	// *****************************************************************
	// * Attribute: massBuffer
	// *****************************************************************
	private BeanPropertyComposed<Parameter> massBuffer = new BeanPropertyComposed<>();
	
	private void safeAccessMassBuffer() {
		if (massBuffer.getTypeInstance() == null) {
			ComposedPropertyInstance propertyInstance = (ComposedPropertyInstance) helper.getPropertyInstance("massBuffer");
			massBuffer.setTypeInstance(propertyInstance);
		}
	}
	
	@XmlElement(nillable = true)
	public Parameter getMassBuffer() {
		safeAccessMassBuffer();
		return massBuffer.getValue();
	}
	
	public BeanPropertyComposed<Parameter> getMassBufferBean() {
		safeAccessMassBuffer();
		return massBuffer;
	}
	
	
}
