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

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertEnabled;
import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertNotEnabled;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.extension.cef.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.swtbot.util.SwtBotDebugHelper;

public class CefHierarchyLevelTest extends ACefSwtBotTestCase {
	
	SWTBotTreeItem repositoryNavigatorItem;

	SWTBotTreeItem configurationTreeSystem;
	SWTBotTreeItem elementConfigurationSubSystem;
	SWTBotTreeItem elementConfigurationEquipment;
	
	@Before
	public void before() throws Exception {
		super.before();
		// create the necessary items for the test
		SwtBotDebugHelper.logCodeLine();
		repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");

		SwtBotDebugHelper.logCodeLine();
		configurationTreeSystem = addElement(ConfigurationTree.class, conceptPs, repositoryNavigatorItem);
		SwtBotDebugHelper.logCodeLine();
		elementConfigurationSubSystem = addElement(ElementConfiguration.class, conceptPs, configurationTreeSystem);
		elementConfigurationEquipment = addElement(ElementConfiguration.class, conceptPs, elementConfigurationSubSystem);

	}
	
	@Test
	public void testAddSystemParametersViaCheckBox() {
		
		openEditor(configurationTreeSystem);
		bot.checkBox("System Parameters").click();
		waitForEditingDomainAndUiThread();
		
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS = 3; // Documents folder, system parameters, child SEI
		assertTrue(bot.checkBox("System Parameters").isChecked());
		assertThat("There are documents, system parametes and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		bot.checkBox("System Parameters").click();
		waitForEditingDomainAndUiThread();
		assertFalse(bot.checkBox("System Parameters").isChecked());
		assertThat("System parameters should have been removed", configurationTreeSystem.getItems(), arrayWithSize(2));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		
		bot.checkBox("System Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertTrue(bot.checkBox("System Mass Parameters").isChecked());
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS = 4; // Documents folder, system parameters, system mass parameters, child SEI
		assertThat("There are documents, system parametes, system mass parameters and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
		bot.checkBox("System Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertFalse(bot.checkBox("System Mass Parameters").isChecked());
		assertThat("System parameters should not have been removed, mass paremeters yes", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
	}
	
	@Test
	public void testAddSubSystemParametersViaCheckBox() {
		
		openEditor(elementConfigurationSubSystem);
		bot.checkBox("Sub System Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertTrue(bot.checkBox("Sub System Mass Parameters").isChecked());
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS = 3; // Documents folder, system mass parameters, child SEI
		assertThat("There are documents, subsystem mass parameters and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		
		bot.checkBox("Sub System Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertFalse(bot.checkBox("Sub System Mass Parameters").isChecked());
		assertThat("SubSystem parameters should not have been removed, mass paremeters yes", configurationTreeSystem.getItems(), arrayWithSize(2));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		
	}
	
	@Test
	public void testAddEquipmentParametersViaCheckBox() {
		
		openEditor(elementConfigurationEquipment);
		bot.checkBox("Equipment Parameters").click();
		waitForEditingDomainAndUiThread();
		
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS = 2; // Documents folder, system parameters
		assertTrue(bot.checkBox("Equipment Parameters").isChecked());
		assertThat("There are documents, equipment parametes and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		bot.checkBox("Equipment Parameters").click();
		waitForEditingDomainAndUiThread();
		assertFalse(bot.checkBox("Equipment Parameters").isChecked());
		assertThat("Equipment parameters should have been removed", configurationTreeSystem.getItems(), arrayWithSize(2));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		
		bot.checkBox("Equipment Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertTrue(bot.checkBox("System Mass Parameters").isChecked());
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS = 3; // Documents folder, equipment parameters, equipment mass parameters
		assertThat("There are documents, equipment parametes and equipment mass parametersnow", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
		bot.checkBox("Equipment Mass Parameters").click();
		waitForEditingDomainAndUiThread();
		assertFalse(bot.checkBox("Equipment Mass Parameters").isChecked());
		assertThat("Equipment parameters should not have been removed, mass paremeters yes", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
	}
	
	@Test
	public void testInitialEnabledLevels() {
		
		// Tests how CEF levels can be assigned before any level is assigned 
		
		// On configuration root level, the only the system CAs should be enabled
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));
		
		// On the second level System and Subsystem CAs should be enabled 
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));
		
		// On the third level all CA levels should be enabled
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));

	}
	
	@Test
	public void testEnabledLevelsWithBasicStructure() {
		
		addElement(SystemParameters.class, conceptCefX, configurationTreeSystem);
		addElement(SubSystemMassParameters.class, conceptCefX, elementConfigurationSubSystem);
		addElement(EquipmentMassParameters.class, conceptCefX, elementConfigurationEquipment);
		
		// Now only the system CAs should be enabled
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class))); // Cannot be assigned twice
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));
		
		// Now only subsystem CAs should be enabled on second level as system cannot be contained in a system
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class))); // Cannot be assigned twice
		assertEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertNotEnabled(elementConfigurationSubSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));
		
		// On the third level all CA levels should be enabled
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemPowerParameters.class)));
		
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemPowerParameters.class)));
		
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentMassParameters.class))); // Cannot be assigned twice
		assertNotEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentParameters.class))); // Automatically enabled with mass params
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentPowerParameters.class)));
		assertEnabled(elementConfigurationEquipment.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(EquipmentTemperatureParameters.class)));

	}
	

}
