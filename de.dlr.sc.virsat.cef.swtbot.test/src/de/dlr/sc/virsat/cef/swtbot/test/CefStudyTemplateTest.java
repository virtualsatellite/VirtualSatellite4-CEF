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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Test;

public class CefStudyTemplateTest extends ACefSwtBotTestCase {

	SWTBotTreeItem repositoryNavigatorItem;
	SWTBotTreeItem systemItem;
	SWTBotTreeItem subSystemItem;
	SWTBotTreeItem equipmentItem;
	
	@Before
	public void before() throws Exception {
		super.before();
		// create the necessary items for the test
		repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");
		
		
	}
	
	@Test
	public void testUseStudyTemplate() {
		
		repositoryNavigatorItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF Study").click();
		
		final int STUDY_REPOSITORY_ITEM_COUNT = 2; // apps + system
		assertEquals(STUDY_REPOSITORY_ITEM_COUNT, repositoryNavigatorItem.getItems().length);
		
		systemItem = repositoryNavigatorItem.getNode("Sys: System");
		assertNotNull(systemItem);
		
		
		systemItem.expand();
		final int STUDY_SYSTEM_ITEM_COUNT = 5; // documents + subsystem + system parameters + system mass parameters + system power parameters
		assertEquals(STUDY_SYSTEM_ITEM_COUNT, systemItem.getItems().length);
		
		subSystemItem = systemItem.getNode("SubSys: subSystem");
		assertNotNull(subSystemItem);
		
		subSystemItem.expand();
		final int STUDY_SUB_SYSTEM_ITEM_COUNT = 4; // documents + equipment + subsystem mass parameters + subsystem power parameters
		assertEquals(STUDY_SUB_SYSTEM_ITEM_COUNT, subSystemItem.getItems().length);
		
		equipmentItem = subSystemItem.getNode("Eqp: equipment ( x 1)");
		assertNotNull(equipmentItem);
		
		equipmentItem.expand();
		final int STUDY_EQUIPMENT_ITEM_COUNT = 5; // documents + equipment parameters + equipment mass parameters + equipment power parameters + equipment temperature parameters
		assertEquals(STUDY_EQUIPMENT_ITEM_COUNT, equipmentItem.getItems().length);
	}
	
	@Test
	public void testUseSubSystemTemplate() {
		
		//Prepare project
		repositoryNavigatorItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF Study").click();
		systemItem = repositoryNavigatorItem.getNode("Sys: System");
		systemItem.expand();
		subSystemItem = systemItem.getNode("SubSys: subSystem");
		rename(subSystemItem, "InitialSubsystem");
		
		final int PREVIOUS_SYTEM_ITEM_COUNT = systemItem.getItems().length;
		
		systemItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF SubSystem").click();
		
		assertTrue("One more element in tree", systemItem.getItems().length > PREVIOUS_SYTEM_ITEM_COUNT);
		SWTBotTreeItem newSubSystem = systemItem.getNode("SubSys: subSystem");
		assertNotNull(newSubSystem);
		assertNotEquals("New subsystem is not the initially exisitng one", subSystemItem, newSubSystem);
		
	}
	
	@Test
	public void testUseEquipmentTemplate() {
		
		//Prepare project
		repositoryNavigatorItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF Study").click();
		systemItem = repositoryNavigatorItem.getNode("Sys: System");
		systemItem.expand();
		subSystemItem = systemItem.getNode("SubSys: subSystem");
		subSystemItem.expand();
		equipmentItem = subSystemItem.getNode("Eqp: equipment ( x 1)");
		rename(equipmentItem, "InitialEquipment");
		
		final int PREVIOUS_SUB_SYSTEM_ITEM_COUNT = subSystemItem.getItems().length;
		
		subSystemItem.click();
		bot.menu("Templates").menu("Create New DLR/CEF Equipment").click();
		
		assertTrue("One more element in tree", subSystemItem.getItems().length > PREVIOUS_SUB_SYSTEM_ITEM_COUNT);
		SWTBotTreeItem newEquipment = subSystemItem.getNode("Eqp: equipment ( x 1)");
		assertNotNull(newEquipment);
		assertNotEquals("New subsystem is not the initially exisitng one", equipmentItem, newEquipment);
		
	}
}
