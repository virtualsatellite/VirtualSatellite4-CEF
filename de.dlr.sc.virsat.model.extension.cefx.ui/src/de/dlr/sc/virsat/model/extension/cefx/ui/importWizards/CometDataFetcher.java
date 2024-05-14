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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import cdp4common.dto.Thing;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4dal.dal.ProxySettings;
import cdp4servicesdal.CdpServicesDal;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Responsible for fetching data from a Comet server using provided credentials and populating an SWT Tree with the data.
 */
public class CometDataFetcher {
    private static final String ROOT_NODE_TEXT = "Comet Data";

    private URI serverUri;
    private Credentials credentials;
    private Session session;
    private Image nodeImage;  // Image used for non-leaf tree nodes

    /**
     * Constructs a new CometDataFetcher.
     *
     * @param url Server URL.
     * @param username User's login name.
     * @param password User's password.
     * @param proxySettings proxy settings
     */
    public CometDataFetcher(String url, String username, String password, ProxySettings proxySettings) {
        this.serverUri = URI.create(url);
        this.credentials = new Credentials(username, password, serverUri, proxySettings);
        this.session = new SessionImpl(new CdpServicesDal(), this.credentials);
        initImages();
    }

    /**
     * Initializes images for the tree nodes from Eclipse's shared image repository.
     */
    private void initImages() {
        nodeImage = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
    }

    /**
     * Fetches data from the Comet server and populates the provided SWT Tree.
     *
     * @param tree The tree to populate with data.
     */
    public void fetchData(Tree tree) {
        try {
            List<Thing> things = session.getDal().open(credentials, new AtomicBoolean()).get();

            Display.getDefault().asyncExec(() -> populateTree(tree, things));
        } catch (InterruptedException e) {
            Display.getDefault().asyncExec(() -> showMessage("Data fetch interrupted.", SWT.ICON_WARNING));
        } catch (ExecutionException e) {
            Display.getDefault().asyncExec(() -> showMessage("Failed to fetch data from the server. Check server availability and credentials.", SWT.ICON_ERROR));
        }
    }

    /**
     * Displays a message box with a specified message and icon.
     *
     * @param message The message to display.
     * @param iconType The type of icon to display 
     */
    private void showMessage(String message, int iconType) {
        MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), iconType | SWT.OK);
        messageBox.setMessage(message);
        messageBox.open();
    }

    /**
     * Populates the tree with data items, each representing a Thing object.
     *
     * @param tree The tree to be populated.
     * @param things The list of Thing objects.
     */
    private void populateTree(Tree tree, List<Thing> things) {
        TreeItem rootItem = new TreeItem(tree, SWT.NONE);
        rootItem.setText(ROOT_NODE_TEXT);
        // Set the image for the root node
        rootItem.setImage(nodeImage); 
        things.forEach(thing -> addTreeItem(rootItem, thing));
        expandAll(tree.getItems());
    }

    /**
     * Adds a tree item to the tree for each Thing object.
     *
     * @param parentItem The parent item under which the new item will be added.
     * @param thing The Thing object to represent.
     */
    private void addTreeItem(TreeItem parentItem, Thing thing) {
        TreeItem item = new TreeItem(parentItem, SWT.NONE);
        item.setText(thing.toString());
     // Set image only for non-leaf nodes
        item.setImage(isLeaf(thing) ? null : nodeImage);  
    }

    /**
     * Determines if this Thing is a leaf node in the hierarchy.
     * A leaf node is defined as not having any other Thing in its container properties.
     *
     * @return true if this Thing is a leaf, false otherwise.
     */
    private boolean isLeaf(Thing thing) {
        // Check if all container lists are empty, which would indicate no children
        for (List<?> containerList : thing.getContainerLists()) {
            if (!containerList.isEmpty()) {
                return false; // Found a non-empty container list, hence it's not a leaf
            }
        }
        return true; // No children found, it's a leaf
    }


    /**
     * Recursively expands all nodes in the tree.
     *
     * @param items Array of TreeItem objects to expand.
     */
    private void expandAll(TreeItem[] items) {
        for (TreeItem item : items) {
            item.setExpanded(true);
            if (item.getItemCount() > 0) {
                expandAll(item.getItems());
            }
        }
    }
}
