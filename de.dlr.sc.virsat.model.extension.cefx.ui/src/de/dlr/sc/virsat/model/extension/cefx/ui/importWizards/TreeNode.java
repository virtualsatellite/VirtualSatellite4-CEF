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
    private String name;
    private List<TreeNode> children;

    public TreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Checks if a child with the given name already exists in this TreeNode.
     */
    public void addChild(TreeNode child) {
        for (TreeNode existingChild : children) {
            if (existingChild.getName().equals(child.getName())) {
                System.out.println("Duplicate child ignored: " + child.getName());
                return; 
            }
        }
        children.add(child);
        System.out.println("Added child: " + child.getName());
    }

}