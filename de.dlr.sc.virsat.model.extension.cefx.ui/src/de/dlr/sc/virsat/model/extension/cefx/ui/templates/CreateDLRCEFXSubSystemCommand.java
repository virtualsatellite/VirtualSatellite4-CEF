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
 * Creates SubSystem -> Equipment data to enable a quick study.
 *
 */
public class CreateDLRCEFXSubSystemCommand {

	/**
	 * Private constructor.
	 */
	private CreateDLRCEFXSubSystemCommand() {	
	}

	/**
	 * Creates a CompoundCommand for adding a new DLR/CEFX study template structure based on the given Concept.
	 * 
	 * <p>This method generates a DLR/CEFX study template structure consisting of a SubSystem and an Equipment.
	 * It then creates commands to add these structural elements as child elements in a hierarchical manner.
	 * Finally, it records commands to add sub-system and equipment parameters using the DLRCEFXStudyCommandHelper.</p>
	 * 
	 * @param parent The parent EObject to which the DLR/CEFX study template structure will be added.
	 * @param concept The Concept used to create the DLR/CEFX study template structure.
	 * @param domain The VirSatTransactionalEditingDomain in which the command will be executed.
	 * @return A CompoundCommand representing the operation to create the DLR/CEFX study template structure.

	 */
	public static CompoundCommand create(EObject parent, Concept conceptPs, Concept conceptCefx, VirSatTransactionalEditingDomain domain) {
	    // Create sub-system and equipment elements
	    ElementConfiguration subSystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    ElementConfiguration equipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);

	    // Create a CompoundCommand to store sub-commands
	    CompoundCommand cmd = new CompoundCommand();

	    // Append commands to add child structural elements
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, subSystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(subSystem.getStructuralElementInstance(), equipment.getStructuralElementInstance(), domain));

	    // Append recording command to add sub-system and equipment parameters
	    cmd.append(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, subSystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, equipment);
	        }
	    });

	    // Return the CompoundCommand representing the overall operation
	    return cmd;
	}

}
