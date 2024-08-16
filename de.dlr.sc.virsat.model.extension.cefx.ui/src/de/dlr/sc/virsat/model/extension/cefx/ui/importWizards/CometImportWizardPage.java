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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * The CometImportWizardPage class represents a wizard page used to configure 
 * and fetch data from a COMET server. It provides a UI for entering server details, 
 * initiating data fetch, and displaying the fetched data in a tree structure.
 */
public class CometImportWizardPage extends WizardPage {

    private static final int GRID_COLUMNS = 2;
    private static final int VERTICALSPAN = 1;
    private static final int TREE_HEIGHT_HINT = 400;
    private static final int TREE_WIDTH_HINT = 600;

    private Text serverUrlText;
    private Text loginText;
    private Text passwordText;
    private Tree targetTree;
    private CometDataFetcher fetcher;

    /**
     * Constructs a new CometImportWizardPage with the specified name.
     * 
     * @param pageName The name of the wizard page.
     */
    protected CometImportWizardPage(String pageName) {
        super(pageName);
        setTitle("Comet Server Configuration");
        setDescription("Enter details and fetch data.");
    }

    /**
     * Creates the controls (UI elements) for this wizard page.
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(GRID_COLUMNS, false);
        container.setLayout(layout);

        createLabelAndTextField(container, "Server URL:", SWT.BORDER);
        createLabelAndTextField(container, "Login:", SWT.BORDER);
        createLabelAndTextField(container, "Password:", SWT.BORDER | SWT.PASSWORD);

        Button fetchButton = new Button(container, SWT.PUSH);
        fetchButton.setText("Fetch Data");
        fetchButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (validateInput()) {
                    fetcher = new CometDataFetcher(serverUrlText.getText(), loginText.getText(), passwordText.getText());
                    fetchAndDisplayData();
                }
            }
        });
        fetchButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, GRID_COLUMNS, VERTICALSPAN));

        targetTree = new Tree(container, SWT.CHECK | SWT.MULTI | SWT.BORDER);
        targetTree.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.detail == SWT.CHECK) {
                    TreeItem item = (TreeItem) e.item;
                    checkChildren(item, item.getChecked());
                }
            }
        });
        GridData treeGridData = new GridData(SWT.FILL, SWT.FILL, true, true, GRID_COLUMNS, VERTICALSPAN);
        treeGridData.heightHint = TREE_HEIGHT_HINT;
        treeGridData.widthHint = TREE_WIDTH_HINT;
        targetTree.setLayoutData(treeGridData);

        setControl(container);
    }

    /**
     * Creates a label and a text field with the specified label text and style.
     */
    private void createLabelAndTextField(Composite parent, String labelText, int style) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(labelText);
        Text text = new Text(parent, style);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        if (labelText.equals("Server URL:")) {
            serverUrlText = text;
        } else if (labelText.equals("Login:")) {
            loginText = text;
        } else if (labelText.equals("Password:")) {
            passwordText = text;
        }
    }

    /**
     * Validates the user input in the text fields. Ensures that all fields are filled.
     */
    private boolean validateInput() {
        if (serverUrlText.getText().isEmpty() || loginText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            setMessage("All fields are required.", SWT.ICON_WARNING);
            return false;
        }
        if (!serverUrlText.getText().startsWith("http://") && !serverUrlText.getText().startsWith("https://")) {
            setMessage("Server URL must start with http:// or https://", SWT.ICON_WARNING);
            return false;
        }
        return true;
    }

    /**
     * Initiates the process of fetching data from the COMET server and displaying it 
     * in the  Tree widget.
     */
    private void fetchAndDisplayData() {
        fetcher.fetchData(targetTree);
    }

    /**
     * Retrieves a list of all TreeItems that are checked in the Tree.
     */
    public List<TreeItem> getCheckedItems() {
        List<TreeItem> checkedItems = new ArrayList<>();
        for (TreeItem item : targetTree.getItems()) {
            getCheckedItemsRecursively(item, checkedItems);
        }
        return checkedItems;
    }

    /**
     * Recursively collects checked TreeItems and adds them to the provided list.
     */
    private void getCheckedItemsRecursively(TreeItem item, List<TreeItem> checkedItems) {
        if (item.getChecked()) {
            checkedItems.add(item);
        }
        for (TreeItem child : item.getItems()) {
            getCheckedItemsRecursively(child, checkedItems);
        }
    }

    /**
     * Recursively checks or unchecks all child {@link TreeItem}s of the specified TreeItem.
     */
    private void checkChildren(TreeItem item, boolean checked) {
        for (TreeItem child : item.getItems()) {
            child.setChecked(checked);
            checkChildren(child, checked);
        }
    }

    /**
     * Performs the finish operation when the wizard is completed. 
     */
    public boolean performFinish() {
        return true;
    }
}
