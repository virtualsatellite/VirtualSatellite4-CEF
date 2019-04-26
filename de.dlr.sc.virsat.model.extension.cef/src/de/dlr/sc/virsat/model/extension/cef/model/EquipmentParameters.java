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
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
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
public  class EquipmentParameters extends AEquipmentParameters {
	
	/**
	 * Constructor of Concept Class
	 */
	public EquipmentParameters() {
		super();
	}

	/**
	 * Constructor of Concept Class which will instantiate 
	 * a CategoryAssignment in the background from the given concept
	 * @param concept the concept where it will find the correct Category to instantiate from
	 */
	public EquipmentParameters(Concept concept) {
		super(concept);
	}	

	/**
	 * Constructor of Concept Class that can be initialized manually by a given Category Assignment
	 * @param categoryAssignment The category Assignment to be used for background initialization of the Category bean
	 */
	public EquipmentParameters(CategoryAssignment categoryAssignment) {
		super(categoryAssignment);
	}

	/**
	 * This method hands back the global Unit Quantitiy. It is the product
	 * of this quantity times all the ones which are defined in parents. This is
	 * used e.g when defining Equipments with sub or even sub sub equipment.
	 * @return the global unit quantity as an integer
	 */
	public long getGlobalUnitQuantity() {
		long globalUnitQuantity = 1;
		try {
			globalUnitQuantity *= getUnitQuantity();
		} catch (NumberFormatException e) {
			globalUnitQuantity *= 1;
		}
		EquipmentParameters parentEquipmentParameters = getCaBeanFromParentSei(EquipmentParameters.class);
		if (parentEquipmentParameters != null) {
			globalUnitQuantity *= parentEquipmentParameters.getGlobalUnitQuantity();
		}
		return globalUnitQuantity;
	}
}
