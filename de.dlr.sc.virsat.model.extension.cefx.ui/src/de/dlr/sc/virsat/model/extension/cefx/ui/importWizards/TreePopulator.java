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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import cdp4common.dto.Thing;
import java.util.List;

/**
 * Utility class to populate an SWT Tree with data represented by Thing objects.
 */

public class TreePopulator {

    // Constants
    private static final String ROOT_ITEM_TEXT = "Comet Data";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TreePopulator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Populates the given SWT Tree with data from a list of Thing objects.
     * It creates a root item and attaches child items representing each Thing.
     *
     * @param tree The SWT Tree to populate.
     * @param things The list of Thing objects retrieved from the Comet server.
     */
    public static void populateTree(Tree tree, List<Thing> things) {
        TreeItem rootItem = new TreeItem(tree, SWT.NONE);
        rootItem.setText(ROOT_ITEM_TEXT);
        things.forEach(thing -> createTreeItem(rootItem, thing));
        expandAll(tree.getItems());  
    }

    /**
     * Creates a tree item for a given Thing and attaches it to the specified parent item.
     * Each tree item represents a Thing object, displaying a string representation of it.
     *
     * @param parentItem The parent TreeItem under which the new item will be created.
     * @param thing The Thing object to create a tree item for.
     */
    private static void createTreeItem(TreeItem parentItem, Thing thing) {
        TreeItem item = new TreeItem(parentItem, SWT.NONE);
        item.setText(thing.toString()); 
    }

    /**
     * Recursively expands all nodes in the tree to ensure that all items are visible.
     * This method iterates through each TreeItem and if it has children, recursively expands them.
     *
     * @param items An array of TreeItem objects to be expanded.
     */
    private static void expandAll(TreeItem[] items) {
        for (TreeItem item : items) {
            item.setExpanded(true);
            if (item.getItemCount() > 0) {
                expandAll(item.getItems());
            }
        }
    }
}
