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

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String originalName; // Holds the original name
    private String cleanedName;  // Holds the cleaned name
    private List<TreeNode> children;

    public TreeNode(String originalName) {
        this.originalName = originalName;
        this.cleanedName = cleanName(originalName); // Automatically clean the name during initialization
        this.children = new ArrayList<>();
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getCleanedName() {
        return cleanedName;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Adds a child to this TreeNode. Prevents duplicates based on the cleaned name.
     */
    public void addChild(TreeNode child) {
        for (TreeNode existingChild : children) {
            if (existingChild.getCleanedName().equals(child.getCleanedName())) {
                System.out.println("Duplicate child ignored: " + child.getCleanedName());
                return;
            }
        }
        children.add(child);
        System.out.println("Added child: " + child.getCleanedName());
    }

    /**
     * Cleans a name by removing special characters and spaces, leaving only alphanumeric characters.
     */
    private String cleanName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.replaceAll("[^a-zA-Z0-9]", ""); // Removes all non-alphanumeric characters
    }
}
