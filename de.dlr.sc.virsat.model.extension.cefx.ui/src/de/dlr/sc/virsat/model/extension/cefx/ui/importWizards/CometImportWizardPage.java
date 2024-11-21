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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.statushandlers.StatusManager;

import de.dlr.sc.virsat.build.ui.Activator;

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
    private boolean isDataFetched = false;

    /**
     * Constructs a new CometImportWizardPage with the specified name.
     * 
     * @param pageName The name of the wizard page.
     */
    protected CometImportWizardPage(String pageName) {
        super(pageName);
        setTitle("Comet Server Configuration");
        setDescription("");
    }

    /**
     * Creates the controls (UI elements) for this wizard page.
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(GRID_COLUMNS, false);
        container.setLayout(layout);

        // Create red color and bold font
        Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
        FontData[] fontData = getFont().getFontData();
        for (FontData fd : fontData) {
            fd.setStyle(SWT.BOLD); 
        }
        Font boldFont = new Font(Display.getCurrent(), fontData);

        // Create a custom description label with red color and bold font
        Label customDescription = new Label(container, SWT.NONE);
        customDescription.setText("Enter details and fetch data.");
        customDescription.setForeground(red);
        customDescription.setFont(boldFont);   

        GridData descriptionGridData = new GridData(SWT.FILL, SWT.CENTER, true, false, GRID_COLUMNS, VERTICALSPAN);
        customDescription.setLayoutData(descriptionGridData);
        
        container.addDisposeListener(event -> boldFont.dispose());

        // Create blue labels for the text fields
        Color blue = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
        createLabelAndTextField(container, "Server URL:", SWT.BORDER, blue); 
        createLabelAndTextField(container, "Login:", SWT.BORDER, blue);      
        createLabelAndTextField(container, "Password:", SWT.BORDER | SWT.PASSWORD, blue);

        createFetchButton(container);

        targetTree = new Tree(container, SWT.CHECK | SWT.MULTI | SWT.BORDER);
        targetTree.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.detail == SWT.CHECK) {
                    TreeItem item = (TreeItem) e.item;

                    checkChildren(item, item.getChecked());

                    List<TreeNode> updatedNodes = getCheckedTreeNodes();
                    System.out.println("Updated TreeNodes: " + updatedNodes);
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
     * Creates a "Fetch Data" button within the provided container. The button is aligned to the right side of the container.
     */
    private void createFetchButton(Composite container) {
        Button fetchButton = new Button(container, SWT.PUSH);
        fetchButton.setText("Fetch Data");

        // Set the button to be aligned to the right side
        GridData fetchButtonGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        fetchButtonGridData.horizontalSpan = GRID_COLUMNS;
        fetchButton.setLayoutData(fetchButtonGridData);

        fetchButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleFetchData();
            }
        });
    }

    /**
     * Handles the process of fetching data from a server, triggered by the "Fetch Data" button.
     * This method validates the input fields, retrieves user input (server URL, username, and password),
     * and uses a ProgressMonitorDialog to display the progress of connecting to the COMET server 
     * and fetching data. It ensures that the data is fetched only once.
     */
    private void handleFetchData() {
        // Check if data has already been fetched
        if (isDataFetched) {
            setMessage("Data has already been fetched.", SWT.ICON_INFORMATION);
            return;
        }

        // Validate the input fields before proceeding
        if (!validateInput()) {
            return;
        }

        // These variables will hold the input values from the UI
        final String[] serverUrl = new String[1];
        final String[] username = new String[1];
        final String[] password = new String[1];

        // Access UI elements from the UI thread
        Display.getDefault().syncExec(() -> {
            serverUrl[0] = serverUrlText.getText();
            username[0] = loginText.getText();
            password[0] = passwordText.getText();
        });

        Shell shell = getShell();

        // Use a ProgressMonitorDialog to show progress to the user
        ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
        try {
            progressDialog.run(true, true, new IRunnableWithProgress() {
                @Override
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Connecting to COMET server...", IProgressMonitor.UNKNOWN);

                        // Instantiate the CometDataFetcher with the retrieved values
                        fetcher = new CometDataFetcher(serverUrl[0], username[0], password[0]);

                        // Fetch and display data
                        fetcher.fetchData(targetTree);

                        // After successful data fetch, set the flag to true
                        isDataFetched = true;

                    } catch (Exception e) {
                        Status status = new Status(Status.ERROR, Activator.getPluginId(), "Failed to fetch data from COMET server.", e);
                        StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
                    } finally {
                        monitor.done();
                    }
                }
            });
        } catch (Exception e) {
            Status status = new Status(Status.ERROR, Activator.getPluginId(), "Failed to run progress dialog.", e);
            StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
        }
    }

    /**
     * Creates a label and a text field with the specified label text and style.
     */
    private void createLabelAndTextField(Composite parent, String labelText, int style, Color labelColor) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(labelText);
        label.setForeground(labelColor);  // Set label color to blue
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
     * Retrieves a list of all TreeItems that are checked in the Tree.
     */
    public List<TreeNode> getCheckedTreeNodes() {
        List<TreeNode> checkedNodes = new ArrayList<>();
        for (TreeItem item : targetTree.getItems()) {
            TreeNode node = collectCheckedItemsRecursively(item);
            if (node != null) {
                checkedNodes.add(node);
            }
        }
        System.out.println("Checked nodes: " + checkedNodes);
        return checkedNodes;
    }

    /**
     * Recursively collects checked TreeItems and adds them to the provided list.
     */
    private TreeNode collectCheckedItemsRecursively(TreeItem item) {
        System.out.println("Checking item: " + item.getText() + ", isChecked: " + item.getChecked());

        if (!item.getChecked() && item.getItems().length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(cleanName(item.getText()));
        System.out.println("Creating TreeNode: " + node.getName());

        for (TreeItem child : item.getItems()) {
            TreeNode childNode = collectCheckedItemsRecursively(child);
            if (childNode != null) {
                node.addChild(childNode);
                System.out.println("Added child: " + childNode.getName() + " to parent: " + node.getName());
            }
        }

        if (!item.getChecked() && node.getChildren().isEmpty()) {
            return null; 
        }

        return node;
    }

    /**
     * Clean a name.
     * 
     */
    private String cleanName(String name) {
        if (name == null || name.isEmpty()) {
            return name; 
        }
        return name.replaceAll("[^a-zA-Z0-9]", ""); 
    }

    /**
     * Recursively checks or unchecks all child  TreeItems of the specified TreeItem.
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