/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.templates;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.extension.cef.model.Equipment;
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
	 * Create a recording command to create a DLR/CEF study template
	 * @param parent the parent object
	 * @param concept the cefx concept
	 * @param domain the editing domain
	 * @return the created command
	 */
	public static CompoundCommand create(EObject parent, Concept concept, VirSatTransactionalEditingDomain domain) {
		Equipment equipment = DLRCEFXStudyCommandHelper.createEquipmentBean(concept);
		
		CompoundCommand cmd = new CompoundCommand();
		cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, equipment.getStructuralElementInstance(), domain));
		cmd.append(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				DLRCEFXStudyCommandHelper.addEquipmentParameters(concept, equipment);
			}
		});
		
		return cmd;
	}
}