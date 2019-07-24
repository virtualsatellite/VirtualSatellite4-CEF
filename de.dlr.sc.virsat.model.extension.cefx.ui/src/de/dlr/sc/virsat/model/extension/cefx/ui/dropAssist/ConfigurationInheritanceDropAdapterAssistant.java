/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.dropAssist;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;

import de.dlr.sc.virsat.model.dvlm.inheritance.IInheritsFrom;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.ui.navigator.dropAssist.DVLMObjectDropAdapterAssistant;

/**
 * This drop assistant should detect if a ElementDefinition is dropped on a ElementConfiguration.
 * Depending on the combination of shift and ctrl, the inheritance should be adjusted.
 *
 */
public class ConfigurationInheritanceDropAdapterAssistant extends DVLMObjectDropAdapterAssistant {

	
	/**
	 * Method to create a Drop Command based on the Inheritance Mechanism
	 * @param target The object to witch to drop to
	 * @param operation the type of DND operation. Copy and Paste will be reinterpreted
	 * @return the command if it is constructable or null in case it could not be created
	 */
	private Command createDropCommand(Object target, int operation) {
		Command dropCommand = null;
		
		if (target instanceof IInheritsFrom) {
			IInheritsFrom iif = (IInheritsFrom) target;
			VirSatTransactionalEditingDomain virSatEd = VirSatEditingDomainRegistry.INSTANCE.getEd(iif);
			ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structerdSelection = (IStructuredSelection) selection;
				
				@SuppressWarnings("unchecked")
				List<Object> dndObjects = structerdSelection.toList();
					
				// In case it is a single object selected, which is
				
				if (operation == DND.DROP_MOVE) {
					AddCommand.
				}
				
				if (operation == DND.DROP_COPY) {
					dropCommand = VirSatPasteFromClipboardCommand.create(virSatEd, eObject, dndObjects, ClipboardState.COPY);
				} else if (operation == DND.DROP_MOVE) {
					dropCommand = VirSatPasteFromClipboardCommand.create(virSatEd, eObject, dndObjects, ClipboardState.CUT);
				}
			}
		}
		return dropCommand;
	}
	
	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		transferType.
		
		// TODO Auto-generated method stub
		
		return Status.OK_STATUS;
	}
	
	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {
		// TODO Auto-generated method stub
		return super.handleDrop(aDropAdapter, aDropTargetEvent, aTarget);
	}
}
