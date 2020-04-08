/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package de.dlr.sc.virsat.cef.swtbot.test;

import org.eclipse.core.runtime.Status;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.swtbot.test.ASwtBotTestCase;
import de.dlr.sc.virsat.swtbot.test.Activator;


/**
 * This class is a stub for SWTBot Ui testing.
 * @author piet_ci
 *
 */
public class SWTBotTestCaseStub extends ASwtBotTestCase {
	
	@Override
	protected void openCorePerspective() {
		bot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Other...").click();
		waitForEditingDomainAndUiThread();
		bot.table().select("VirSat - Core");
		bot.button("Open").click();
		waitForEditingDomainAndUiThread(); 
	}
	
	@Override
	protected void addAllConcepts(String projectName) {
		waitForEditingDomainAndUiThread();
		bot.viewById("de.dlr.sc.virsat.project.ui.navigator.view").setFocus();
		waitForEditingDomainAndUiThread();
		SWTBotTreeItem projectItem = bot.tree().expandNode(projectName);
		waitForEditingDomainAndUiThread();
		projectItem.getNode(Repository.class.getSimpleName()).doubleClick();
		waitForEditingDomainAndUiThread();
		bot.button("Add from Registry").click();
		bot.button("Select All").click();
		bot.button("OK").click();
		waitForEditingDomainAndUiThread();
	}
	
	@Override
	protected void closeWelcomeScreen() {
		Activator.getDefault().getLog().log(new Status(Status.INFO, Activator.getPluginId(), "ASwtBotTestCase: Close welcome screen if it exists"));
		for (SWTBotView view : bot.views()) {
			if (view.getTitle().equals("Welcome")) {
				view.close();
			}
		}
	}
	
	@Before
	public void before() throws Exception {
		super.before();
	}
	
	@Test
	public void stubSWTBot() {
		Assert.assertNull(null);
	}
}
