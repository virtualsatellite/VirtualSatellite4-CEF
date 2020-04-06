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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//*import de.dlr.sc.virsat.model.extension.fdir.model.Fault;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.swtbot.test.ASwtBotTestCase;


/**
 * This class is a stub for SWTBot Ui testing.
 * @author piet_ci
 *
 */
public class SWTBotTestCaseStub extends ASwtBotTestCase {
	
	private SWTBotTreeItem repositoryNavigatorItem;	
	private SWTBotTreeItem configurationTree;
	private SWTBotTreeItem elementConfiguration;

	
	@Before
	public void before() throws Exception {
		super.before();
	}
	
	@Test
	public void stubSWTBot() {
		// create the necessary items for the test
		repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");
		configurationTree = addElement(ConfigurationTree.class, conceptPs, repositoryNavigatorItem);
		elementConfiguration = addElement(ElementConfiguration.class, conceptPs, configurationTree);
		Assert.assertNotNull(elementConfiguration);
	}
}
