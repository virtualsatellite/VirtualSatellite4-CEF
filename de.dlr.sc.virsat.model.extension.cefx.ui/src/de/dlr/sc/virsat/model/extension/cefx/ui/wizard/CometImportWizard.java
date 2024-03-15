/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.wizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.dlr.sc.virsat.model.extension.cefx.ui.Activator;

/**
 * A wizard for the import of requirements from a CSV file
 *
 */
public class CometImportWizard extends Wizard implements IWorkbenchWizard {

	public static final String ID = "de.dlr.sc.virsat.model.extension.cefx.ui.wizard.cometImportWizard";

	private CometConfigurationSelectionPage importPage;
	private IContainer model;


	/**
	 * Default constructor
	 */
	public CometImportWizard() {
		super();
		
		// Setup persistency
		IDialogSettings pluginSettings = Activator.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = pluginSettings.getSection(ID);
		if (wizardSettings == null) {
			wizardSettings = new DialogSettings(ID);
			pluginSettings.addSection(wizardSettings);
		}
		setDialogSettings(wizardSettings);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.model = ResourcesPlugin.getWorkspace().getRoot();
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean canFinish() {
		return super.canFinish() || importPage.canFinish();
	}
	
	@Override
	public boolean performFinish() {


		return true;
	}

	/**
	 * Do an initial import, this method also creates a mapping of specifications and configures the import configuration
	 * 
	 * @param editingDomain the editing domain
	 * @param reqIfContent the content to import
	 * @param specMapping the mapping as map
	 * @param configurationContainer the container in which the import configuration shall be created
	 * @param typeContainer the container element for new requirement types imported from ReqIF
	 * @param groupSupport If true using RequirementGroups for requirement objects with children
	 * @param monitor the progress monitor
	 */

	

	@Override
	public void addPages() {
		importPage = new CometConfigurationSelectionPage(model);
		addPage(importPage);
	}

}
