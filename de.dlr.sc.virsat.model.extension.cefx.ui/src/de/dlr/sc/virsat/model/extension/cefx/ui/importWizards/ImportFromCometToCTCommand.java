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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.swt.widgets.TreeItem;

import java.util.Collections;
import java.util.List;

public class ImportFromCometToCTCommand {

    private static final int SYSTEM_MARGIN_DEFAULT = 20;

    private VirSatTransactionalEditingDomain domain;
    private StructuralElementInstance parentInstance;
    private Concept activeConceptCefx;
    private Concept activeConceptPs;
    private List<TreeItem> selectedItems;

    /**
     * Constructor to initialize the ImportFromCometToCTCommand object.
     */
    public ImportFromCometToCTCommand(VirSatTransactionalEditingDomain domain, StructuralElementInstance parentInstance, Concept activeConceptCefx, Concept activeConceptPs, List<TreeItem> selectedItems) {
        if (domain == null || parentInstance == null || activeConceptCefx == null || activeConceptPs == null || selectedItems == null || selectedItems.isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments provided to ImportFromCometToCTCommand constructor.");
        }

        this.domain = domain;
        this.parentInstance = parentInstance;
        this.activeConceptCefx = activeConceptCefx;
        this.activeConceptPs = activeConceptPs;
        this.selectedItems = selectedItems;
    }

    /**
     * Creates a command that imports the selected elements from the Comet tool into the ConfigurationTree.
     */
    public Command createImportCommand() {
        if (selectedItems.isEmpty()) {
            throw new IllegalStateException("No selected items to import.");
        }

        // A compound command to hold all the subcommands
        CompoundCommand compoundCommand = new CompoundCommand();

        try {
            // Create a new ConfigurationTree using the root name of the first selected item
            String rootName = selectedItems.get(0).getText();
            if (rootName == null || rootName.isEmpty()) {
                throw new IllegalStateException("Root name of the selected item is null or empty.");
            }

            ConfigurationTree newCT = new ConfigurationTree(activeConceptPs);
            newCT.setName(rootName);

            // Create a structural instance for the new ConfigurationTree and set its parent
            StructuralElementInstance newCTInstance = newCT.getStructuralElementInstance();
            newCTInstance.setParent(parentInstance);

            // Add the new ConfigurationTree instance to the parent instance
            Command addCTCommand = AddCommand.create(domain, parentInstance, null, Collections.singleton(newCTInstance));
            compoundCommand.append(addCTCommand);

            // For each selected item, add it to the ConfigurationTree
            for (TreeItem item : selectedItems) {
                String itemName = item.getText();
                if (itemName == null || itemName.isEmpty()) {
                    continue; 
                }

                // Create a new ElementConfiguration for the selected item
                ElementConfiguration config = new ElementConfiguration(activeConceptPs);
                config.setName(itemName);

                // Create a structural instance for the configuration and set its parent
                StructuralElementInstance configInstance = config.getStructuralElementInstance();
                configInstance.setParent(newCTInstance);

                // Add the configuration instance to the new ConfigurationTree instance
                Command addConfigCommand = AddCommand.create(domain, newCTInstance, null, Collections.singleton(configInstance));
                compoundCommand.append(addConfigCommand);

                // Add subsystem-specific parameters using a recording command
                compoundCommand.append(new RecordingCommand(domain) {
                    @Override
                    protected void doExecute() {
                        addSubSystemParameters(activeConceptCefx, config);
                    }
                });
            }

            // Add system parameters to the ConfigurationTree
            compoundCommand.append(new RecordingCommand(domain) {
                @Override
                protected void doExecute() {
                    addSystemParameters(activeConceptCefx, newCT);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create import command: " + e.getMessage(), e);
        }

        // Return the compound command that can be executed
        return compoundCommand;
    }

    /**
     * Adds system parameters to the specified ConfigurationTree.
     */
    private void addSystemParameters(Concept concept, ConfigurationTree system) {
        SystemParameters systemParameters = new SystemParameters(concept);
        systemParameters.setName("SystemParameters");
        systemParameters.setSystemMargin(SYSTEM_MARGIN_DEFAULT);
        system.add(systemParameters);
    }

    /**
     * Adds subsystem-specific mass parameters to the specified ElementConfiguration.
     */
    private void addSubSystemParameters(Concept concept, ElementConfiguration subSystem) {
        SubSystemMassParameters subSystemMassParameters = new SubSystemMassParameters(concept);
        subSystemMassParameters.setName("MassParameters");
        subSystem.add(subSystemMassParameters);
    }
}

