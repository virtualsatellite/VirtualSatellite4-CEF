/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.util;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.factory.BeanCategoryAssignmentFactory;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;
import de.dlr.sc.virsat.model.extension.cef.ui.Activator;
import de.dlr.sc.virsat.model.extension.cef.util.CefModeHelper;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.AUiSnippetGenericTable;

/**
 * A Helper for CEF specific UI such as the context menu showing
 * the available modes and the already selected mode values for a given parameter
 * @author fisc_ph
 *
 */
public class CefUiHelper {

	private static final String ACTION_NAME_MODE_PARAMETER = "Mode Value: ";

	public static final String MODE_VALUE_ADDITIONS = "modeValueAdditions";
	
	/**
	 * Call this method to create the context menu actions for a given parameter.
	 * Usually these are the ModeValues that are either attached to the parameter 
	 * or can be attached with the action
	 * @param editingDomain The editing domain in which to act
	 * @param manager the menu manager where to add the actions
	 * @param parameter the parameter for which the context menu should be created
	 */
	private void createContextMenuActions(EditingDomain editingDomain, IMenuManager manager, Parameter parameter) {
		CefModeHelper cefModeHelper = new CefModeHelper();
		List<SystemMode> systemModes = cefModeHelper.getAllModes(parameter);
		List<SystemMode> activeSystemModes = cefModeHelper.getAllActiveModes(parameter);
		
		manager.add(new Separator(MODE_VALUE_ADDITIONS));
		
		systemModes.forEach((systemMode) -> {
			manager.add(new Action(ACTION_NAME_MODE_PARAMETER + systemMode.getName(), Action.AS_CHECK_BOX) {
				@Override
				public boolean isChecked() {
					return activeSystemModes.contains(systemMode);
				}
	
				@Override
				public boolean isEnabled() {
					return getModeValueCommand().canExecute();
				}
				
				@Override
				public void run() {
					Command command = getModeValueCommand();
					editingDomain.getCommandStack().execute(command);
				}
				
				private Command getModeValueCommand() {
					if (isChecked()) {
						return cefModeHelper.removeModeValue(editingDomain, parameter, systemMode);
					} else {
						return cefModeHelper.addModeValue(editingDomain, parameter, systemMode);
					}
				}
			});
		});
	}

	/**
	 * Call this method to actually create the context menu for a parameter
	 * This method adds the CheckBox entries for the mode values
	 * @param editingDomain the editing domain in which to act
	 * @param manager the menu manager of the context menu which should be extended
	 * @param uiSnippetTable the uiSnippetTable from where to get the current selection
	 */
	public void fillContextMenuModeValues(EditingDomain editingDomain, IMenuManager manager, AUiSnippetGenericTable uiSnippetTable) {
		try {
			Object firstSelection = uiSnippetTable.getStructuredSelection().getFirstElement();
			if (firstSelection instanceof ComposedPropertyInstance) {
				firstSelection = ((ComposedPropertyInstance) firstSelection).getTypeInstance();
			}
			
			if (firstSelection instanceof CategoryAssignment) {
				BeanCategoryAssignmentFactory bCaFactory = new BeanCategoryAssignmentFactory();
				IBeanCategoryAssignment beanCa  = bCaFactory.getInstanceFor((CategoryAssignment) firstSelection);
				
				if (beanCa instanceof Value) {
					beanCa = new Parameter((CategoryAssignment) beanCa.getTypeInstance().eContainer().eContainer().eContainer());
				}
				
				if (beanCa instanceof Parameter) {
					Parameter parameter = (Parameter) beanCa;
					createContextMenuActions(editingDomain, manager, parameter);
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to fill context menu", e));
		}
	}
}
