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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ISharedImages;
import cdp4common.dto.Thing;
import java.util.List;
/**
 * Utility class to populate an SWT Tree with data represented by Thing objects.
 */
public class TreePopulator {

    // Constants
    private static final String ROOT_ITEM_TEXT = "Comet Data";
    private static Image nodeImage;  // Image used for non-leaf tree nodes

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TreePopulator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Initializes images for the tree nodes from Eclipse's shared image repository.
     */
    private static void initImages() {
        nodeImage = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
    }

    /**
     * Populates the given SWT Tree with data from a list of Thing objects.
     * It creates a root item and attaches child items representing each Thing.
     *
     * @param tree The SWT Tree to populate.
     * @param things The list of Thing objects retrieved from the Comet server.
     */
    public static void populateTree(Tree tree, List<Thing> things) {
        initImages(); // Initialize images before populating the tree
        tree.removeAll(); // Clear previous items if any
        TreeItem rootItem = new TreeItem(tree, SWT.NONE);
        rootItem.setText(ROOT_ITEM_TEXT);
        rootItem.setImage(nodeImage);  // Set the image for the root node
        for (Thing thing : things) {
            TreeItem item = createTreeItem(rootItem, thing);
            // Recursively add child items
            populateTreeItem(item, thing);
        }
        expandAll(tree.getItems());  // Optionally expand all nodes.
    }

    /**
     * Creates a tree item for a given Thing and attaches it to the specified parent item.
     * Each tree item represents a Thing object, displaying a string representation of it.
     *
     * @param parentItem The parent TreeItem under which the new item will be created.
     * @param thing The Thing object to create a tree item for.
     * @return The created TreeItem.
     */
    private static TreeItem createTreeItem(TreeItem parentItem, Thing thing) {
        TreeItem item = new TreeItem(parentItem, SWT.NONE);
        item.setText(thing.toString());
        if (!isLeaf(thing)) {
            item.setImage(nodeImage);  // Set the image for non-leaf nodes
        }
        return item;
    }

    /**
     * Populates a tree item with child items representing the contained Things.
     *
     * @param parentItem The parent TreeItem.
     * @param parentThing The parent Thing object.
     */
    private static void populateTreeItem(TreeItem parentItem, Thing parentThing) {
        for (List<?> containerList : parentThing.getContainerLists()) {
            for (Object obj : containerList) {
                if (obj instanceof Thing) {
                    Thing childThing = (Thing) obj;
                    TreeItem childItem = createTreeItem(parentItem, childThing);
                    // Recursively add child items
                    populateTreeItem(childItem, childThing);
                }
            }
        }
    }

    /**
     * Determines if this Thing is a leaf node in the hierarchy.
     * A leaf node is defined as not having any other Thing in its container properties.
     *
     * @param thing The Thing object to check.
     * @return true if this Thing is a leaf, false otherwise.
     */
    private static boolean isLeaf(Thing thing) {
        // Check if all container lists are empty, which would indicate no children
        for (List<?> containerList : thing.getContainerLists()) {
            if (!containerList.isEmpty()) {
                return false; // Found a non-empty container list, hence it's not a leaf
            }
        }
        return true; // No children found, it's a leaf
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

    /**
     * Disposes of the resources used by the TreePopulator.
     */
    public static void dispose() {
        if (nodeImage != null) {
            nodeImage.dispose();
        }
    }
}
