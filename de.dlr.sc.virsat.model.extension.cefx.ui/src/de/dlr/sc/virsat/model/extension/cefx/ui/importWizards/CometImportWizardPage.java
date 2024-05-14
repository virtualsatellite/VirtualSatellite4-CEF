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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import cdp4dal.dal.ProxySettings;

/**
 * A wizard page that provides UI for entering Comet server details,
 * fetching data, and displaying it in a tree.
 */
public class CometImportWizardPage extends WizardPage {
    
    // Constants for layout configuration
    private static final int COLUMNS = 2;
    private static final boolean SHOULD_NOT_FILL = false;
    private static final boolean GRAB_EXCESS_SPACE = true;
    
    // UI Components
    private Text serverUrlText;
    private Text loginText;
    private Text passwordText;
    private Tree targetTree;

    /**
     * Constructor for the wizard page.
     * @param pageName the name of the page
     */
    protected CometImportWizardPage(String pageName) {
        super(pageName);
        setTitle("Comet Server Configuration");
        setDescription("Enter details and fetch data.");
    }

    /**
     * Creates the control elements on the wizard page.
     * @param parent the parent composite
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(COLUMNS, SHOULD_NOT_FILL);
        container.setLayout(layout);

        // Creating server URL field
        createLabelAndTextField(container, "Server URL:", SWT.BORDER);

        // Creating login field
        createLabelAndTextField(container, "Login:", SWT.BORDER);

        // Creating password field
        createLabelAndTextField(container, "Password:", SWT.BORDER | SWT.PASSWORD);

        // Creating fetch data button
        createFetchButton(container);

        // Creating tree to display fetched data
        targetTree = new Tree(container, SWT.MULTI | SWT.BORDER);
        targetTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, GRAB_EXCESS_SPACE, GRAB_EXCESS_SPACE, COLUMNS, 1));

        setControl(container);
    }

    /**
     * Helper method to create a label and text field.
     * @param parent the parent composite
     * @param labelText the text to set on the label
     * @param style the style of the text field
     */
    private void createLabelAndTextField(Composite parent, String labelText, int style) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(labelText);
        Text text = new Text(parent, style);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, GRAB_EXCESS_SPACE, SHOULD_NOT_FILL));
        if (labelText.equals("Server URL:")) {
            serverUrlText = text;
        } else if (labelText.equals("Login:")) {
            loginText = text;
        } else if (labelText.equals("Password:")) {
            passwordText = text;
        }
    }

    /**
     * Helper method to create the fetch button.
     * @param parent the parent composite
     */
    private void createFetchButton(Composite parent) {
        Button fetchButton = new Button(parent, SWT.PUSH);
        fetchButton.setText("Fetch Data");
        fetchButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (validateInput()) {
                    fetchAndDisplayData();
                    fetchButton.setEnabled(false); // Disable after fetching
                }
            }
        });
        fetchButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, SHOULD_NOT_FILL, SHOULD_NOT_FILL));
    }

    /**
     * Validates the input fields for proper format and completeness.
     * @return true if the input is valid, otherwise false
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
     * Fetches and displays data from the Comet server using the input provided by the user.
     */
    private void fetchAndDisplayData() {
        String url = serverUrlText.getText();
        String login = loginText.getText();
        String password = passwordText.getText();
        ProxySettings proxySettings = null;
        CometDataFetcher fetcher = new CometDataFetcher(url, login, password, proxySettings);
        fetcher.fetchData(targetTree);
    }

    /**
     * I will implement this class later.
     */
    
	public boolean performFinish() {
		
		return false;
	}
}