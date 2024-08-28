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
import javax.xml.bind.annotation.XmlType;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;

// *****************************************************************
// * Class Declaration
// *****************************************************************

@XmlType(name = APointToPointInterface.FULL_QUALIFIED_CATEGORY_NAME)
/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public  class PointToPointInterface extends APointToPointInterface {
	
	/**
	 * Constructor of Concept Class
	 */
	public PointToPointInterface() {
		super();
	}

	/**
	 * Constructor of Concept Class which will instantiate 
	 * a CategoryAssignment in the background from the given concept
	 * @param concept the concept where it will find the correct Category to instantiate from
	 */
	public PointToPointInterface(Concept concept) {
		super(concept);
	}	

	/**
	 * Constructor of Concept Class that can be initialized manually by a given Category Assignment
	 * @param categoryAssignment The category Assignment to be used for background initialization of the Category bean
	 */
	public PointToPointInterface(CategoryAssignment categoryAssignment) {
		super(categoryAssignment);
	}
}
