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
 * Wizard page for configuring Comet server details and fetching data.
 */
public class CometImportWizardPage extends WizardPage {

    private static final int GRID_COLUMNS = 2;
    private static final int TREE_HEIGHT_HINT = 400;
    private static final int TREE_WIDTH_HINT = 600;

    private Text serverUrlText;
    private Text loginText;
    private Text passwordText;
    private Tree targetTree;

    /**
     * Constructor to create the wizard page.
     *
     * @param pageName the name of the page
     */
    protected CometImportWizardPage(String pageName) {
        super(pageName);
        setTitle("Comet Server Configuration");
        setDescription("Enter details and fetch data.");
    }

    /**
     * Creates the UI controls for the wizard page.
     *
     * @param parent the parent composite
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
                    fetchAndDisplayData();
                    //fetchButton.setEnabled(false);
                }
            }
        });
        fetchButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, GRID_COLUMNS, 1));

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
        GridData treeGridData = new GridData(SWT.FILL, SWT.FILL, true, true, GRID_COLUMNS, 1);
        treeGridData.heightHint = TREE_HEIGHT_HINT;
        treeGridData.widthHint = TREE_WIDTH_HINT;   
        targetTree.setLayoutData(treeGridData);

        setControl(container);
    }

    /**
     * Creates a label and a text field for input.
     *
     * @param parent    the parent composite
     * @param labelText the text for the label
     * @param style     the style for the text field
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
     * Validates the input fields.
     *
     * @return true if the input is valid, false otherwise
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
     * Fetches data from the server and displays it in the tree.
     */
    private void fetchAndDisplayData() {
        String url = serverUrlText.getText();
        String login = loginText.getText();
        String password = passwordText.getText();
        CometDataFetcher fetcher = new CometDataFetcher(url, login, password);
        fetcher.fetchData(targetTree);
    }

    /**
     * Retrieves the checked items from the tree.
     *
     * @return a list of checked items
     */
    public List<TreeItem> getCheckedItems() {
        List<TreeItem> checkedItems = new ArrayList<>();
        for (TreeItem item : targetTree.getItems()) {
            getCheckedItemsRecursively(item, checkedItems);
        }
        return checkedItems;
    }
    /**
     * Recursively retrieves the checked items from the tree.
     *
     * @param item         the current tree item
     * @param checkedItems the list to add the checked items to
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
     * Checks or unchecks all children of the given item.
     *
     * @param item    the parent tree item
     * @param checked the checked state to set
     */
    private void checkChildren(TreeItem item, boolean checked) {
        for (TreeItem child : item.getItems()) {
            child.setChecked(checked);
            checkChildren(child, checked);
        }
    }

    /**
     * Performs any final actions when the wizard finishes.
     *
     * @return true to indicate that the finish button was pressed
     */
    public boolean performFinish() {
        List<TreeItem> checkedItems = getCheckedItems();
        return true;
    }
}
