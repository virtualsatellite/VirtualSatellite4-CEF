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
import org.eclipse.swt.widgets.List;

public class SelectedElementsPage extends WizardPage {

    private List selectedElementsList;

    protected SelectedElementsPage(String pageName) {
        super(pageName);
        setTitle("Selected Elements");
        setDescription("Review the selected elements before finalizing the import.");
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));

        Label label = new Label(container, SWT.NONE);
        label.setText("Selected Elements:");

        selectedElementsList = new List(container, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        selectedElementsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        setControl(container);
    }

    /**
     * Method to populate the list with the selected elements.
     */
    public void setSelectedElements(java.util.List<String> elements) {
        selectedElementsList.removeAll();
        for (String element : elements) {
            selectedElementsList.add(element);
        }
    }
}
