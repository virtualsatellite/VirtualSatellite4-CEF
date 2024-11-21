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
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralPackage;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
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
        setWindowTitle("Comet Import Wizard");
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

    public Concept getPsConcept(VirSatTransactionalEditingDomain domain) {
        Repository currentRepository = domain.getResourceSet().getRepository();
        ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
        return acHelper.getConcept(de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId());
    }

    @Override
    public boolean performFinish() {
        // Retrieve selected source tree items from the first page
        List<TreeNode> selectedItems = mainPage.getCheckedTreeNodes();
        if (selectedItems == null || selectedItems.isEmpty()) {
            System.out.println("No items selected. Aborting operation.");
            return false;
        }
        
        // Retrieve the selected target location from the second page
        StructuralElementInstance targetInstance = (StructuralElementInstance) targetSelectionPage.getSelection();
        
        if (targetInstance == null) {
            System.out.println("No target instance selected. Aborting operation.");
            return false;
        }
        
        VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(targetInstance);

        // Process each selected item
        try {
            for (TreeNode item : selectedItems) {
                createElementConfigurationHierarchy(item, targetInstance, editingDomain);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while creating configuration hierarchy: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        System.out.println("All items processed successfully.");
        return true;
    }
    
    /**
     * Creates a hierarchy of ElementConfiguration instances corresponding to the structure of the provided TreeItem.
     */    

    private void createElementConfigurationHierarchy(TreeNode item, StructuralElementInstance parentInstance, VirSatTransactionalEditingDomain editingDomain) {
        if (item == null) {
            System.out.println("TreeNode is null. Skipping...");
            return;
        }
        System.out.println("Processing TreeNode: " + item.getName());
    	
    	// Create an ElementConfiguration for the current tree item
        ElementConfiguration elementConfig = new ElementConfiguration(getPsConcept(editingDomain));
        elementConfig.setName(item.getName());
        System.out.println("Created ElementConfiguration: " + elementConfig.getName());

        // Save this ElementConfiguratiookn under the parentInstance
        try {
            saveDataFromComet(elementConfig.getStructuralElementInstance(), parentInstance, editingDomain);
            System.out.println("Saved ElementConfiguration: " + elementConfig.getName() + " under parent.");
        } catch (Exception e) {
            System.err.println("Failed to save ElementConfiguration: " + elementConfig.getName());
            e.printStackTrace();
            return;
        }

        // Recursively process child nodes to maintain the nested structure
        for (TreeNode childItem : item.getChildren()) {
            createElementConfigurationHierarchy(childItem, elementConfig.getStructuralElementInstance(), editingDomain);

        }
    }

    /**
     * Saves the data represented by a StructuralElementInstance to a target parent instance.
     * This method creates and executes an AddCommand to attach the provided 
     * element as a child to the specified target instance.
     */
    private void saveDataFromComet(StructuralElementInstance elementFromComet, StructuralElementInstance targetInstance, VirSatTransactionalEditingDomain editingDomain) {
        if (elementFromComet == null) {
            throw new IllegalStateException("StructuralElementInstance is null in the Configuration Tree.");
        }

        try {
            Command addCommand = AddCommand.create(editingDomain, targetInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CHILDREN, elementFromComet);
            targetInstance.getAssignedDiscipline();
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