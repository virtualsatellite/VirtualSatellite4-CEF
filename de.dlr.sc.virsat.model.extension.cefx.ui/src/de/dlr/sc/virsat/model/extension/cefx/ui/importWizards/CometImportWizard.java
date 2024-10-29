/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.importWizards;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.Activator;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This wizard handles the import of data from a Comet server.
 * It guides the user through server configuration and initiates the data import process.
 */
public class CometImportWizard extends Wizard implements IImportWizard {
    
    private CometImportWizardPage mainPage; 
    private ImportTargetSelection targetSelectionPage;
    private IContainer model;

    public CometImportWizard() {
        super();
        setWindowTitle("CometImportWizard");
        setNeedsProgressMonitor(true);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
    	this.model = ResourcesPlugin.getWorkspace().getRoot();
        mainPage = new CometImportWizardPage("Configure Comet Server");
        targetSelectionPage = new ImportTargetSelection(model);

        addPage(mainPage);
        addPage(targetSelectionPage);
    }

    @Override
    public boolean performFinish() {
        // Retrieve selected source tree items from the first page
        List<TreeItem> selectedItems = mainPage.getCheckedItems();

        // Retrieve the selected target location from the second page
        StructuralElementInstance targetInstance = (StructuralElementInstance) targetSelectionPage.getSelection();

        // Perform the actual import based on the selected source and target
        for (TreeItem item : selectedItems) {
            ConfigurationTree configurationTree = importElements(item);
            saveConfigurationTree(configurationTree, targetInstance); 
        }

        return true;
    }
  
    public StructuralElementInstance getSelectedTarget() {
        return (StructuralElementInstance) getSelectedTarget();
    }
    
    private ConfigurationTree importElements(TreeItem rootItem) {
        ImportHandler importHandler = new ImportHandler();
        return importHandler.importElements(rootItem);
    }

    /**
     * Saves the imported ConfigurationTree to the editing domain.
     */
    
    public void saveConfigurationTree(ConfigurationTree configurationTree, StructuralElementInstance targetInstance) {
        if (configurationTree == null) {
            throw new IllegalStateException("ConfigurationTree is null. Import failed or was not executed correctly.");
        }

        StructuralElementInstance rootInstance = configurationTree.getStructuralElementInstance();
        if (rootInstance == null) {
            throw new IllegalStateException("StructuralElementInstance is null in the ConfigurationTree.");
        }

        // Retrieve the editing domain from the registry
        VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(rootInstance);
        Repository currentRepository = editingDomain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);

        System.out.println("Editing domain retrieved successfully: " + editingDomain);

        // Proceed with saving and executing commands
        try {
            Command addCommand = AddCommand.create(editingDomain, targetInstance, null, rootInstance);
            editingDomain.getCommandStack().execute(addCommand);
            editingDomain.saveAll();
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }



    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        return super.getNextPage(page);
    }

}