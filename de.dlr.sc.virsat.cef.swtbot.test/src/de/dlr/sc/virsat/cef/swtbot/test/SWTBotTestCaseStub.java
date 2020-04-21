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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.swtbot.test.ASwtBotTestCase;


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
	
	@Before
	public void before() throws Exception {
		super.before();
	}
	
	@Test
	public void stubSWTBot() {
		Assert.assertNull(null);
	}
}
