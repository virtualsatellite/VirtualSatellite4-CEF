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

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.project.ui.editingDomain.handler.AEditingDomainCommandHandler;

public abstract class CreateDLRCEFXTemplateHandler extends AEditingDomainCommandHandler {
	
	protected Concept activeConceptCefx;
	protected Concept activeConceptPs;
	
	@Override
	protected void initializeFieldsFromSelection(ISelection selection) {
		super.initializeFieldsFromSelection(selection);
		activeConceptCefx = DLRCEFXStudyCommandHelper.getCefxConcept(ed);
		activeConceptPs = DLRCEFXStudyCommandHelper.getPsConcept(ed);
	}

	@Override
	public void execute() {
		ed.getCommandStack().execute(getCommand());
	}
	
	@Override
	public boolean isEnabled() {
		
		ISelectionService  selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelection selection = selectionService.getSelection();
		initializeFieldsFromSelection(selection);
		
		if (activeConceptCefx == null || activeConceptPs == null) {
			return false;
		}
		return getCommand().canExecute();
	}
	
	/**
	 * Get command that creates the necessary template elements in the study
	 * @return command
	 */
	protected abstract Command getCommand();
}
