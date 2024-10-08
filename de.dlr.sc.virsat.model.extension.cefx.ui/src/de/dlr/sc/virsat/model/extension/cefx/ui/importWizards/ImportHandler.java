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

import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElement;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralFactory;
import org.eclipse.swt.widgets.TreeItem;

public class ImportHandler {

    /**
     * Imports the selected tree items and maps them.
     */
    public ConfigurationTree importElements(TreeItem rootItem) {
        // Create the root element as a ConfigurationTree
        ConfigurationTree rootConfigurationTree = new ConfigurationTree();
        StructuralElementInstance rootInstance = createStructuralElementInstance(rootItem.getText());

        if (rootInstance == null) {
            throw new IllegalStateException("Root StructuralElementInstance could not be created.");
        }

        rootConfigurationTree.setStructuralElementInstance(rootInstance);
        rootConfigurationTree.setName(rootItem.getText());

        // Recursively add children as ElementConfigurations
        addChildren(rootConfigurationTree, rootItem);

        return rootConfigurationTree;
    }

    /**
     * Recursively adds children as ElementConfiguration instances.
     */
    private void addChildren(IBeanStructuralElementInstance parentConfig, TreeItem parentItem) {
        for (TreeItem childItem : parentItem.getItems()) {
            // Create a new ElementConfiguration for each child
            ElementConfiguration elementConfig = new ElementConfiguration();
            StructuralElementInstance childInstance = createStructuralElementInstance(childItem.getText());

            if (childInstance == null) {
                throw new IllegalStateException("Child StructuralElementInstance could not be created.");
            }

            elementConfig.setStructuralElementInstance(childInstance);
            elementConfig.setName(childItem.getText());

            // Add the new ElementConfiguration as a child of the parent
            parentConfig.add(elementConfig);

            // Recursively process children of this child item
            addChildren(elementConfig, childItem);
        }
    }

    /**
     * Helper method to create a StructuralElementInstance using a factory.
     */
    private StructuralElementInstance createStructuralElementInstance(String name) {
        // Use the StructuralFactory to create an instance
        StructuralElementInstance sei = StructuralFactory.eINSTANCE.createStructuralElementInstance();

        if (sei == null) {
            throw new IllegalStateException("Failed to create StructuralElementInstance.");
        }
        sei.setName(name);

        // Create or find the corresponding StructuralElement to set the type
        StructuralElement structuralElement = findOrCreateStructuralElement(name);
        if (structuralElement == null) {
            throw new IllegalStateException("Failed to find or create a StructuralElement for " + name);
        }

        sei.setType(structuralElement);

        return sei;
    }

    private StructuralElement findOrCreateStructuralElement(String name) {
        // Find an existing StructuralElement by name or create a new one
        StructuralElement structuralElement = StructuralFactory.eINSTANCE.createStructuralElement();
        structuralElement.setName(name);

        return structuralElement;
    }
}

