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
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
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
     * Creates a hierarchy of ElementConfiguration instances corresponding to the structure of the provided TreeNode.
     */
    private void createElementConfigurationHierarchy(TreeNode item, StructuralElementInstance parentInstance, VirSatTransactionalEditingDomain editingDomain) {
        if (item == null) {
            System.out.println("TreeNode is null. Skipping...");
            return;
        }

        // Log the current processing item
        System.out.println("Processing TreeNode: " + item.getOriginalName());

        // Check if this node represents a mass value
        double massValue = extractMassValue(item.getOriginalName());
        if (massValue > 0) {
            // If this is a mass node, add it as a parameter and skip further processing
            addMassParameter(parentInstance, editingDomain, massValue);
            System.out.println("Added mass parameter with value: " + massValue + " for TreeNode: " + item.getOriginalName());
            return; 
        }

        // Create an ElementConfiguration for non-mass nodes
        ElementConfiguration elementConfig = new ElementConfiguration(getPsConcept(editingDomain));
        elementConfig.setName(item.getCleanedName());

        try {
            // Save this ElementConfiguration under the parent instance
            saveDataFromComet(elementConfig.getStructuralElementInstance(), parentInstance, editingDomain);
            System.out.println("Saved ElementConfiguration: " + elementConfig.getName() + " under parent.");
        } catch (Exception e) {
            System.err.println("Failed to save ElementConfiguration: " + elementConfig.getName());
            e.printStackTrace();
            return;
        }

        // Recursively process child nodes
        for (TreeNode childItem : item.getChildren()) {
            createElementConfigurationHierarchy(childItem, elementConfig.getStructuralElementInstance(), editingDomain);
        }
    }

    /**
     * Adds a mass parameter to the parent instance based on the extracted mass value.
     */
    private void addMassParameter(StructuralElementInstance parentInstance, VirSatTransactionalEditingDomain editingDomain, double massValue) {
        try {
            // Check if the parent instance represents a SubSystem or System
            String parentName = parentInstance.getName().toLowerCase();
            if (parentName.contains("subsystem")) {
                // Create and configure SubSystemMassParameters
                SubSystemMassParameters subSystemMassParameters = new SubSystemMassParameters(getPsConcept(editingDomain));
                subSystemMassParameters.setName("SubSystemMassParameter");
                Parameter massTotal = subSystemMassParameters.getMassTotalBean().getValue();
                if (massTotal == null) {
                    massTotal = new Parameter(); // Create a new Parameter instance
                }
                massTotal.setDefaultValue(massValue);
                subSystemMassParameters.getMassTotalBean().setValue(massTotal);

                // Add the SubSystemMassParameters to the parent instance
                Command addCommand = AddCommand.create(editingDomain, parentInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CHILDREN, subSystemMassParameters);
                editingDomain.getCommandStack().execute(addCommand);
                System.out.println("Added SubSystemMassParameter with value: " + massValue);
            } else {
                // Create and configure SystemMassParameters
                SystemMassParameters systemMassParameters = new SystemMassParameters(getPsConcept(editingDomain));
                systemMassParameters.setName("SystemMassParameter");
                Parameter massTotal = systemMassParameters.getMassTotalBean().getValue();
                if (massTotal == null) {
                    massTotal = new Parameter(); // Create a new Parameter instance
                }
                massTotal.setDefaultValue(massValue);
                systemMassParameters.getMassTotalBean().setValue(massTotal);

                // Add the SystemMassParameters to the parent instance
                Command addCommand = AddCommand.create(editingDomain, parentInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CHILDREN, systemMassParameters);
                editingDomain.getCommandStack().execute(addCommand);
                System.out.println("Added SystemMassParameter with value: " + massValue);
            }
        } catch (Exception e) {
            System.err.println("Error adding mass parameter: " + e.getMessage());
            e.printStackTrace();
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
            // Regex to detect numbers followed by "kg"
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*kg", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
            	// Extract and parse the numeric value
                return Double.parseDouble(matcher.group(1)); 
            }
        } catch (Exception e) {
            System.err.println("Error parsing mass value: " + e.getMessage());
        }
        // Return 0 if no valid mass is found
        return 0; 
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
        elementFromComet.setAssignedDiscipline(targetInstance.getAssignedDiscipline());
        try {
            Command addCommand = AddCommand.create(editingDomain, targetInstance, StructuralPackage.STRUCTURAL_ELEMENT_INSTANCE__CHILDREN, elementFromComet);
            editingDomain.getCommandStack().execute(addCommand);
            editingDomain.saveAll();
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            e.printStackTrace();
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
