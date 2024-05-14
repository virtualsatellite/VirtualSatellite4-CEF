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

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This wizard handles the import of data from a Comet server.
 * It guides the user through server configuration and initiates the data import process.
 */
public class CometImportWizard extends Wizard implements IImportWizard {
    
    private CometImportWizardPage mainPage;  // The main page of the wizard

    /**
     * Constructor for CometImportWizard.
     * Sets the window title and specifies that a progress monitor is needed.
     */
    public CometImportWizard() {
        super();
        setWindowTitle("Comet Import Wizard");
        setNeedsProgressMonitor(true);
    }

    /**
     * Initializes the wizard with the workbench and current selection.
     * This method is called when the wizard is initialized and is responsible
     * for adding the pages to the wizard.
     *
     * @param workbench The current workbench.
     * @param selection The current object selection.
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        mainPage = new CometImportWizardPage("Configure Comet Server");
        addPage(mainPage);
    }

    /**
     * Called when the user clicks the Finish button on the wizard.
     * This method should contain the logic to process the data collected through the wizard pages.
     *
     * @return true if the data processing is successful and the wizard can be closed; false otherwise.
     */
    @Override
    public boolean performFinish() {
        // This method delegates the finish operation to the mainPage
        return mainPage.performFinish();
    }
}

