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

import org.junit.Before;

import de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.swtbot.test.ASwtBotTestCase;

public class ACefSwtBotTestCase extends ASwtBotTestCase {
	
	protected Concept conceptCef;
	protected Concept conceptCefX;
	
	@Before
	public void before() throws Exception {
		super.before();
		
		conceptCef = ConceptXmiLoader.loadConceptFromPlugin(de.dlr.sc.virsat.model.extension.cef.Activator.PLUGIN_ID + "/concept/concept.xmi");
		conceptCefX = ConceptXmiLoader.loadConceptFromPlugin(de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID + "/concept/concept.xmi");
		
	}
	
	@Override
	protected void openCorePerspective() {
		bot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Other...").click();
		waitForEditingDomainAndUiThread();
		bot.table().select("VirSat - Core");
		bot.button("Open").click();
		waitForEditingDomainAndUiThread(); 
	}
	
	protected void openCefPerspective() {
		bot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Other...").click();
		waitForEditingDomainAndUiThread();
		bot.table().select("CEF - Classic");
		bot.button("Open").click();
		waitForEditingDomainAndUiThread(); 
	}
	
	protected void openCefXPerspective() {
		bot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Other...").click();
		waitForEditingDomainAndUiThread();
		bot.table().select("CEF - X");
		bot.button("Open").click();
		waitForEditingDomainAndUiThread(); 
	}

}
