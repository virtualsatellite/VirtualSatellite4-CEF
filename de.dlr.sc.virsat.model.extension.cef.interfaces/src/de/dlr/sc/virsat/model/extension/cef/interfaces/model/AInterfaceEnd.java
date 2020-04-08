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

import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
// *****************************************************************
// * Import Statements
// *****************************************************************
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentParameters;

// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public abstract class AInterfaceEnd extends AAInterfaceEnd {
	
	/**
	 * Constructor of Concept Class
	 */
	public AInterfaceEnd() {
		super();
	}

	/**
	 * Constructor of Concept Class which will instantiate 
	 * a CategoryAssignment in the background from the given concept
	 * @param concept the concept where it will find the correct Category to instantiate from
	 */
	public AInterfaceEnd(Concept concept) {
		super(concept);
	}	

	/**
	 * Constructor of Concept Class that can be initialized manually by a given Category Assignment
	 * @param categoryAssignment The category Assignment to be used for background initialization of the Category bean
	 */
	public AInterfaceEnd(CategoryAssignment categoryAssignment) {
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

		globalUnitQuantity *= isSetQuantity() ? getQuantity() : 1;
		
		IBeanStructuralElementInstance equipment = getParent();
		if (equipment != null) {
			EquipmentParameters equipmentParameters = equipment.getFirst(EquipmentParameters.class);
			if (equipmentParameters != null) {
				globalUnitQuantity *= equipmentParameters.getGlobalUnitQuantity();
			} else {
				EquipmentParameters parentEquipmentParameters = getCaBeanFromParentSei(EquipmentParameters.class);
				if (parentEquipmentParameters != null) {
					globalUnitQuantity *= parentEquipmentParameters.getGlobalUnitQuantity();
				}
			}
		}
			
		return globalUnitQuantity;
	}
}
