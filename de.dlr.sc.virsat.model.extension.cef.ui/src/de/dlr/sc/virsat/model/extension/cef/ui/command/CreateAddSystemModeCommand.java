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

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.System;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cef.util.CefModeHelper;

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class CreateAddSystemModeCommand extends ACreateAddSystemModeCommand {
	@Override
public Command create(EditingDomain editingDomain, EObject owner, Concept activeConcept) {
		
		Command addSystemModeCommand = super.create(editingDomain, owner, activeConcept);
		CompoundCommand cc = new CompoundCommand();
		cc.append(addSystemModeCommand);

		Command addSystemParametersCommand = new CreateAddSystemParametersCommand().create(editingDomain, owner, activeConcept);
		cc.appendIfCanExecute(addSystemParametersCommand);
		
		cc.append(new CommandWrapper() {
			@Override
			protected Command createCommand() {
				
				Parameter modeDuration;
				
				Collection<?> addSystemParametersCommandResults = addSystemParametersCommand.getResult();
				
				if (addSystemParametersCommandResults.isEmpty()) {
					System system = new System((StructuralElementInstance) owner);
					SystemParameters sysParams = system.getFirst(SystemParameters.class);
					modeDuration = sysParams.getModeDuration();
				} else {
					CategoryAssignment caSystemParameters = (CategoryAssignment) addSystemParametersCommandResults.iterator().next();
					SystemParameters newSystemParameters = new SystemParameters(caSystemParameters);
					modeDuration = newSystemParameters.getModeDuration();
				}
				
				Collection<?> addSystemModeCommandResults = addSystemModeCommand.getResult();
				CategoryAssignment caSystemMode = (CategoryAssignment) addSystemModeCommandResults.iterator().next();
				SystemMode newSystemMode = new SystemMode(caSystemMode);
				
				CefModeHelper cefModeHelper = new CefModeHelper();
				return cefModeHelper.addModeValue(editingDomain, modeDuration, newSystemMode);
				
			}
		});
		
		return cc;
	}
}
