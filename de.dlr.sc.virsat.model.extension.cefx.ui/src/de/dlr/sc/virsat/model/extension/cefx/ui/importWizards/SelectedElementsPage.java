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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.List;

public class SelectedElementsPage extends WizardPage {

    private Tree selectedElementsTree; 
    private List<TreeItem> selectedTreeItems;
    protected SelectedElementsPage(String pageName) {
        super(pageName);
        setTitle("Selected Elements");
        setDescription("Review the selected elements and tree structure.");
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));

        Label label = new Label(container, SWT.NONE);
        label.setText("Selected Elements:");

        selectedElementsTree = new Tree(container, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        selectedElementsTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        setControl(container);
    }

    /**
     * Populates the Tree with the selected elements.
     * This method will be called when the user clicks "Next" from the previous page.
     */
    public void setSelectedElements(List<TreeItem> selectedItems) {
    	
    	// Clear any existing items
        selectedElementsTree.removeAll(); 

        // Create root TreeItem based on the first selected item
        if (!selectedItems.isEmpty()) {
            TreeItem root = new TreeItem(selectedElementsTree, SWT.NONE);
            root.setText(selectedItems.get(0).getText());
            addChildren(root, selectedItems.get(0));
            root.setExpanded(true);
        }
    }

    /**
     * Recursively adds children of the TreeItems.
     */
    private void addChildren(TreeItem parent, TreeItem original) {
        for (TreeItem item : original.getItems()) {
            TreeItem child = new TreeItem(parent, SWT.NONE);
            child.setText(item.getText());
            addChildren(child, item); 
        }
    }

    public Tree getTree() {
        return selectedElementsTree;
    }
    
    public List<TreeItem> getSelectedItems() {
        return this.selectedTreeItems;
    }
}
