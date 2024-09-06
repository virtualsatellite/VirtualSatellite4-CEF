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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This wizard handles the import of data from a Comet server.
 * It guides the user through server configuration and initiates the data import process.
 */
public class CometImportWizard extends Wizard implements IImportWizard {
    
    private CometImportWizardPage mainPage; 
    private SelectedElementsPage selectedElementsPage;

    public CometImportWizard() {
        super();
        setWindowTitle("CometImportWizard");
        setNeedsProgressMonitor(true);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        mainPage = new CometImportWizardPage("Configure Comet Server");
        selectedElementsPage = new SelectedElementsPage("Selected Elements");
        addPage(mainPage);
        addPage(selectedElementsPage);
    }

    @Override
    public boolean performFinish() {
        return mainPage.performFinish();
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        if (page == mainPage) {
            java.util.List<TreeItem> checkedItems = mainPage.getCheckedItems();
            java.util.List<String> elementNames = checkedItems.stream()
                    .map(TreeItem::getText)
                    .collect(java.util.stream.Collectors.toList());

            selectedElementsPage.setSelectedElements(elementNames);
            return selectedElementsPage;
        }
        return super.getNextPage(page);
    }
}