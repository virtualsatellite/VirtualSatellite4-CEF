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

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class CefRefreshValueAndStructureTest extends ACefSwtBotTestCase {
	
	SWTBotTreeItem repositoryNavigatorItem;
	SWTBotTreeItem systemItem;
	
	
	@Override
	public void before() {
		// create the necessary items for the test
		repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");
		repositoryNavigatorItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF Study").click();
		systemItem = repositoryNavigatorItem.getNode("Sys: System");
		systemItem.expand();
		
	}

	
}
