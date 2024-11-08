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
import de.dlr.sc.virsat.model.dvlm.structural.StructuralPackage;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
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

    
	public Concept getPsConcept(VirSatTransactionalEditingDomain domain) {
		Repository currentRepository = domain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
		Concept activeConcept = acHelper.getConcept(de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId());
		return activeConcept;
	}
    @Override
    public boolean performFinish() {
        // Retrieve selected source tree items from the first page
        List<TreeItem> selectedItems = mainPage.getCheckedItems();

        // Retrieve the selected target location from the second page
        StructuralElementInstance targetInstance = (StructuralElementInstance) targetSelectionPage.getSelection();
        
        VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(targetInstance); 
        
        Concept psConcept = getPsConcept(editingDomain);
        
        // Perform the actual import based on the selected source and target
        //for (TreeItem item : selectedItems) {
            //ConfigurationTree configurationTree = importElements(item);
        	
        	ElementConfiguration elementConfigOfElementFromComet = new ElementConfiguration(psConcept);
        	elementConfigOfElementFromComet.setName(selectedItems.get(0).getText());
    		saveDataFromComet(elementConfigOfElementFromComet.getStructuralElementInstance(), targetInstance); 
       // }

        return true;
    }
  
    public StructuralElementInstance getSelectedTarget() {
        return (StructuralElementInstance) getSelectedTarget();
    }  
    
    /**
     * Imports elements from a specified root item in the source tree into a new
     * ConfigurationTree structure. This method first checks if the target
     * selected on the ImportTargetSelection page is a valid instance of
     *  StructuralElementInstance. If valid, it initializes a new
     * ConfigurationTree using this target element as its root.
     */
//    private ConfigurationTree importElements(TreeItem rootItem) {
//
//    	if (targetSelectionPage.getSelection()instanceof StructuralElementInstance) {
//
//    		ConfigurationTree confTreeOfElementFromComet = new ConfigurationTree();
//    		confTreeOfElementFromComet.setName(rootItem.getText(0));
//
//    		return 	new ConfigurationTree(confTreeOfElementFromComet.getStructuralElementInstance());
//    	}
//
//    	return null;
//    }

    /**
     * Saves the imported ConfigurationTree to the editing domain.
     */
    
    public void saveDataFromComet(StructuralElementInstance elementFromComet, StructuralElementInstance targetInstance) {

        if (elementFromComet == null) {
            throw new IllegalStateException("StructuralElementInstance is null in the ConfigurationTree.");
        }

        // Retrieve the editing domain from the registry
        VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(targetInstance);

        System.out.println("Editing domain retrieved successfully: " + editingDomain);

        // Proceed with saving and executing commands
        
        try {
            Command addCommand = AddCommand.create(editingDomain, targetInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CHILDREN, elementFromComet);
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