/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package de.dlr.sc.virsat.model.extension.cefx.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import de.dlr.sc.virsat.model.calculation.ui.views.DomainRoundView;
import de.dlr.sc.virsat.model.extension.cefx.ui.masssummary.MassSummaryView;
import de.dlr.sc.virsat.model.extension.cefx.ui.modesview.ModeGraphsView;
import de.dlr.sc.virsat.model.extension.cefx.ui.modesview.ModeTableView;

/**
 * This is the Perspective Factory for the initial Phase A View.
 * @author fisc_ph
 *
 */
public class CefXPerspective implements IPerspectiveFactory {

	public static final String ID_PRODUCT_FOLDER = "de.dlr.sc.virsat.perspective.cefx.product";
	public static final String ID_CONFIGURATION_FOLDER = "de.dlr.sc.virsat.perspective.cefx.configuration";
	public static final String ID_BOTTOM_LEFT_FOLDER = "de.dlr.sc.virsat.perspective.cefx.BOTTOMLEFT";
	public static final String ID_BOTTOM_RIGHT_FOLDER = "de.dlr.sc.virsat.perspective.cefx.BOTTOMRIGHT";
	public static final String ID_LEFT_MID_FOLDER = "de.dlr.sc.virsat.perspective.cefx.LEFT_MID";
	
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		final float QUARTER = 0.25f;
		final float TWOTHIRDS = 0.66f;
		final float HALF = 0.5f;
		
		// Create a folder to bottom right and Add the problem and CEF views to it	
		IFolderLayout bottomrightFolder = layout.createFolder(ID_BOTTOM_RIGHT_FOLDER, IPageLayout.BOTTOM, TWOTHIRDS, editorArea);
		bottomrightFolder.addView(MassSummaryView.VIEW_ID);
		bottomrightFolder.addView(ModeTableView.VIEW_ID);
		bottomrightFolder.addView(DomainRoundView.VIEW_ID);
		bottomrightFolder.addView("org.eclipse.ui.views.PropertySheet");
		bottomrightFolder.addView("org.eclipse.ui.views.ProblemView");

		// Create a folder to the bottom left and add the outline view to it
		IFolderLayout bottomleftFolder = layout.createFolder(ID_BOTTOM_LEFT_FOLDER, IPageLayout.LEFT, HALF, ID_BOTTOM_RIGHT_FOLDER);
		bottomleftFolder.addView(ModeGraphsView.VIEW_ID);
		bottomleftFolder.addView("org.eclipse.ui.views.ContentOutline");
		
		// Create a folder to left and Add the navigator to the left side
		IFolderLayout productViewFolder = layout.createFolder(ID_PRODUCT_FOLDER, IPageLayout.LEFT, QUARTER, editorArea);
		productViewFolder.addView("de.dlr.sc.virsat.model.extension.cefx.ui.ProductTreeView");
		productViewFolder.addView("de.dlr.sc.virsat.project.ui.navigator.view");
		
		IFolderLayout configurationViewFolder = layout.createFolder(ID_CONFIGURATION_FOLDER, IPageLayout.RIGHT, HALF, ID_PRODUCT_FOLDER);
		configurationViewFolder.addView("de.dlr.sc.virsat.model.extension.cefx.ui.ConfigurationTreeView");
		configurationViewFolder.addView("org.eclipse.ui.navigator.ProjectExplorer");
	}
}
