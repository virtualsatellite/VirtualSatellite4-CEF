/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.templates;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

/**
 * Create Equipment data to enable a quick study.
 *
 */
public class CreateDLRCEFXEquipmentCommand {

	/**
	 * Private constructor.
	 */
	private CreateDLRCEFXEquipmentCommand() {
	}

	/**
	 * Creates a CompoundCommand for adding a new ElementConfiguration to a parent EObject.
	 * 
	 * @param parent The parent EObject to which the ElementConfiguration will be added.
	 * @param concept The Concept used to create the ElementConfiguration.
	 * @param domain The VirSatTransactionalEditingDomain in which the command will be executed.
	 * @return A CompoundCommand representing the operation.
	 */
	public static CompoundCommand create(EObject parent, Concept concept, VirSatTransactionalEditingDomain domain) {
	    // Create ElementConfiguration for the given Concept
	    ElementConfiguration equipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(concept);

	    // Create a CompoundCommand to store sub-commands
	    CompoundCommand cmd = new CompoundCommand();

	    // Append command to add child structural element instance
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, equipment.getStructuralElementInstance(), domain));

	    // Append recording command to add equipment parameters
	    cmd.append(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(concept, equipment);
	        }
	    });
	    // Return the CompoundCommand representing the overall operation
	    return cmd;
	}

}
