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
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
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
    
    private static final int GRAMS_TO_KILOGRAMS_CONVERSION_FACTOR = 1000;
    private static final int  NUMBER_FOR_COMPUTATION = 3;
    
    /**
     * Default constructor for the CometImportWizard.
     * Initializes the wizard with a title and sets the progress monitor flag.
     */
    public CometImportWizard() {
        super();
        setWindowTitle("Comet Import Wizard");
        setNeedsProgressMonitor(true);
    }

    /**
     * Initializes the wizard with the workbench and selected resource.
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.model = ResourcesPlugin.getWorkspace().getRoot();
        mainPage = new CometImportWizardPage("Configure Comet Server");
        targetSelectionPage = new ImportTargetSelection(model);

        addPage(mainPage);
        addPage(targetSelectionPage);
    }
    /**
     * Retrieves the active Concept associated with PS.
     */
    public Concept getPsConcept(VirSatTransactionalEditingDomain domain) {
        Repository currentRepository = domain.getResourceSet().getRepository();
        ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
        return acHelper.getConcept(de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId());
    }

    /**
     * Executes the "Finish" operation of the wizard.
     * Processes the selected tree nodes and performs the required import operations.
     */
    @Override
    public boolean performFinish() {
        // Retrieve selected source tree items from the first page
        List<TreeNode> selectedItems = mainPage.getCheckedTreeNodes();
        
        if (selectedItems == null || selectedItems.isEmpty()) {
        	
            DLRLogger.showErrorDialog("Operation Aborted", "No items selected.");
            DLRLogger.logError("No items selected. Aborting operation.", null);
            
            return false;
        }

        // Retrieve the selected target location from the second page
        StructuralElementInstance targetInstance = (StructuralElementInstance) targetSelectionPage.getSelection();
        
        if (targetInstance == null) {
        	
        	DLRLogger.showErrorDialog("Operation Aborted", "No target instance selected.");
        	DLRLogger.logError("No target instance selected. Aborting operation.", null);
            return false;
        }

        VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(targetInstance);

        // Process each selected item
        try {
            for (TreeNode item : selectedItems) {
            	
                createElementConfigurationHierarchy(item, targetInstance, editingDomain);
            }
        } catch (Exception e) {
        	
        	DLRLogger.logError("Error occurred while creating configuration hierarchy", e);
        	DLRLogger.showErrorDialog("Error", "An error occurred during processing. See logs for details.");
            return false;
        }

        DLRLogger.logInfo("All items processed successfully.");
        return true;
    }
    
    /**
     * Creates a hierarchy of ElementConfiguration instances corresponding to the structure of the provided TreeNode.
     */
    private void createElementConfigurationHierarchy(TreeNode item, StructuralElementInstance parentInstance, VirSatTransactionalEditingDomain editingDomain) {
        if (item == null) {
        	DLRLogger.logError("TreeNode is null. Skipping...", null);
            return;
        }

        // Log the current processing item
        DLRLogger.logInfo("Processing TreeNode: " + item.getOriginalName());

        // Check if this node represents a mass value
        double massValue = extractMassValue(item.getOriginalName());
        if (massValue > 0) {     
        	boolean isMargin = item.getOriginalName().toLowerCase().contains("margin");
        	addMassParameter(parentInstance, editingDomain, massValue, isMargin);
        	System.out.println("Added mass parameter with value: " + massValue + " for TreeNode: " + item.getOriginalName());
        	return;

        }

        // Create an ElementConfiguration for non-mass nodes
        ElementConfiguration elementConfig = new ElementConfiguration(getPsConcept(editingDomain));
        elementConfig.setName(item.getCleanedName());

        try {
        	
            // Save this ElementConfiguration under the parent instance
            saveDataFromComet(elementConfig.getStructuralElementInstance(), parentInstance, editingDomain);
            DLRLogger.logInfo("Saved ElementConfiguration: " + elementConfig.getName() + " under parent.");
            
        } catch (Exception e) {
        	
        	DLRLogger.logError("Failed to save ElementConfiguration: " + elementConfig.getName(), e);
            e.printStackTrace();
            return;
        }

        // Recursively process child nodes
        for (TreeNode childItem : item.getChildren()) {
            createElementConfigurationHierarchy(childItem, elementConfig.getStructuralElementInstance(), editingDomain);
        }
    }

    /**
     * Adds the mass parameter and mass margin to the parent instance.
     * This method creates and inserts both EquipmentParameters and EquipmentMassParameters.
     */
    private void addMassParameter(StructuralElementInstance parentInstance, VirSatTransactionalEditingDomain editingDomain, double massValue, boolean isMargin) {
        try {
            // Create EquipmentParameters
            EquipmentParameters equipmentParameters = new EquipmentParameters(getPsConcept(editingDomain));
            equipmentParameters.setName("EquipmentParameters");

            // Create EquipmentMassParameters
            EquipmentMassParameters equipmentMassParameters = new EquipmentMassParameters(getPsConcept(editingDomain));
            equipmentMassParameters.setName("EquipmentMassParameters");

            if (isMargin) {
                // Add the value to massTotalWithMargin in EquipmentMassParameters
            	equipmentParameters.getMarginMaturityBean().setValue(massValue);

            	DLRLogger.logInfo("Added Mass Total with Margin: " + massValue);
            } else {
                // Add the value to mass in EquipmentMassParameters
             
                Parameter mass = equipmentMassParameters.getMassBean().getValue();
                mass.setDefaultValue(massValue);
                
                DLRLogger.logInfo("Added Mass: " + massValue);
            }

            // Add EquipmentParameters to the parent instance
            Command addEquipmentParamsCommand = AddCommand.create(editingDomain, parentInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CATEGORY_ASSIGNMENTS, equipmentParameters);
            editingDomain.getCommandStack().execute(addEquipmentParamsCommand);

            // Add EquipmentMassParameters to the parent instance
            Command addEquipmentMassParamsCommand = AddCommand.create(editingDomain, parentInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CATEGORY_ASSIGNMENTS, equipmentMassParameters);
            editingDomain.getCommandStack().execute(addEquipmentMassParamsCommand);

        } catch (Exception e) {
        	DLRLogger.logError("Error adding mass parameter", e);
        }
    }

    /**
     * Extracts the numeric mass value from a given text string.
     */
    private double extractMassValue(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        try {
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*(kg|g)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                double value = Double.parseDouble(matcher.group(1));
                if (matcher.group(NUMBER_FOR_COMPUTATION).equalsIgnoreCase("g")) {
                	value /= GRAMS_TO_KILOGRAMS_CONVERSION_FACTOR; 
                }
                return value;
            }
        } catch (Exception e) {
        	DLRLogger.logError("Error parsing mass value", e);
        }
        return 0;
    }
       
    /**
     * Saves the data represented by a StructuralElementInstance to a target parent instance.
     */
    private void saveDataFromComet(StructuralElementInstance elementFromComet, StructuralElementInstance targetInstance, VirSatTransactionalEditingDomain editingDomain) {
        if (elementFromComet == null) {
            throw new IllegalStateException("StructuralElementInstance is null in the Configuration Tree.");
        }

        elementFromComet.setAssignedDiscipline(targetInstance.getAssignedDiscipline());

        try {
            CompoundCommand cmd = new CompoundCommand();

            cmd.append(DLRStructureManager.createAddChildSEICommand(targetInstance, elementFromComet, editingDomain));

            // Execute the compound command
            editingDomain.getCommandStack().execute(cmd);

            // Save changes and refresh workspace
            editingDomain.saveAll();
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            

        } catch (CoreException e) {
        	DLRLogger.logError("Error occurred during saveDataFromComet operation", e);
        }
    }


    /**
     * Retrieves the next wizard page after the current one.
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        return super.getNextPage(page);
    }
    
}
