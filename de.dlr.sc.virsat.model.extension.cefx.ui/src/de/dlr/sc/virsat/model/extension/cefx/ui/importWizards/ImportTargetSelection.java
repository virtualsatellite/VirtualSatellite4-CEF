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

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.uiengine.ui.wizard.AImportExportPage;

public class ImportTargetSelection extends AImportExportPage {

	protected ImportTargetSelection(String pageName) {
		super(pageName);

	}
	protected ImportTargetSelection(IContainer iContainer) {
		super("Select import target's element", SWT.OPEN);
		setModel(iContainer);
		setDescription("Please select a tree to import to and a file to import.");
	}

	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		createTreeViewer();

		Label label = new Label((Composite) getControl(), SWT.NONE);
		label.setText("All Geometry files will be imported into the documents folder of the containing model element.");
	}

	/**
	 * Create a tree viewer with filters to show only relevant tree elements for CAD
	 * import /export
	 */
	protected void createTreeViewer() {
		TreeViewer treeViewer = createTreeUI();
		VirSatFilteredWrappedTreeContentProvider filteredCp = (VirSatFilteredWrappedTreeContentProvider) treeViewer
				.getContentProvider();
		filteredCp.addStructuralElementIdFilter(ConfigurationTree.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		filteredCp.addStructuralElementIdFilter(ElementConfiguration.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
	}

	@Override
	public boolean isSelectionValid() {
		Object selection = getSelection();
		return selection instanceof StructuralElementInstance;
	}

	/**
	 * Checks if the page has been sufficiently filled with user data
	 * 
	 * @return true iff the page is complete
	 */
	public boolean isComplete() {
		return isSelectionValid();
	}
}
