/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.command;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class CreateAddSystemMassParametersCommand extends ACreateAddSystemMassParametersCommand {
	
	@Override
	public Command create(EditingDomain editingDomain, EObject owner, Concept activeConcept) {
		Command addSystemMassParametersCommand = super.create(editingDomain, owner, activeConcept);
		Command addSystemParametersCommand = new CreateAddSystemParametersCommand().create(editingDomain, owner, activeConcept);
		
		CompoundCommand cc = new CompoundCommand(addSystemMassParametersCommand.getLabel());
		cc.appendIfCanExecute(addSystemParametersCommand);
		cc.append(addSystemMassParametersCommand);

		return cc;
	}
}
