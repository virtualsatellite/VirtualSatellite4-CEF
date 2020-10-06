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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
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
		SwtBotDebugHelper.logCodeLine();
		elementConfigurationEquipment = addElement(ElementConfiguration.class, conceptPs, elementConfigurationSubSystem);
		SwtBotDebugHelper.logCodeLine();

	}
	
	@Test
	public void testAddSystemParametersViaCheckBox() {
		
		openEditor(configurationTreeSystem);
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("System Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS = 3; // Documents folder, system parameters, child SEI
		assertTrue(bot.checkBox("System Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		elementConfigurationEquipment.expand();
		assertThat("There are documents, system parametes and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("System Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		SwtBotDebugHelper.logCodeLine();
		assertFalse(bot.checkBox("System Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		assertThat("System parameters should have been removed", configurationTreeSystem.getItems(), arrayWithSize(2));
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		
		
		//Adding system mass parameters automatically also adds system parameters
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("System Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		SwtBotDebugHelper.logCodeLine();
		assertTrue(bot.checkBox("System Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS = 4; // Documents folder, system parameters, system mass parameters, child SEI
		assertThat("There are documents, system parametes, system mass parameters and child items now", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("System Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		SwtBotDebugHelper.logCodeLine();
		assertFalse(bot.checkBox("System Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		assertThat("System parameters should not have been removed, mass paremeters yes", configurationTreeSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemParameters.class)));
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SystemMassParameters.class)));
		
	}
	
	@Test
	public void testAddRemoveSubSystemParametersViaCheckBox() {
		
		SwtBotDebugHelper.logCodeLine();
		openEditor(elementConfigurationSubSystem);
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Sub System Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		bot.button("Save").click();
		SwtBotDebugHelper.logCodeLine();
		openEditor(elementConfigurationSubSystem);
		assertTrue(bot.checkBox("Sub System Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		elementConfigurationEquipment.expand();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SUB_SYSTEM_MATH_PARAMETERS = 3; // Documents folder, system mass parameters, child SEI
		assertThat("There are documents, subsystem mass parameters and child items now", elementConfigurationSubSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SUB_SYSTEM_MATH_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(configurationTreeSystem.contextMenu(conceptCefX.getDisplayName()).menu(getAddCommandName(SubSystemMassParameters.class)));
		
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Sub System Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		bot.button("Save").click();
		openEditor(elementConfigurationSubSystem);
		SwtBotDebugHelper.logCodeLine();
		assertFalse(bot.checkBox("Sub System Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SUB_SYSTEM = 2; // Documents folder, child SEI
		assertThat("SubSystem parameters should not have been removed, mass paremeters yes", elementConfigurationSubSystem.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SUB_SYSTEM));
		SwtBotDebugHelper.logCodeLine();
		
	}
	
	@Test
	public void testAddRemoveEquipmentParametersViaCheckBox() {
		
		SwtBotDebugHelper.logCodeLine();
		openEditor(elementConfigurationEquipment);
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Equipment Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		bot.button("Save").click();
		
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_EQUIPMENT_PARAMETERS = 2; // Documents folder, system parameters
		openEditor(elementConfigurationEquipment);
		assertTrue(bot.checkBox("Equipment Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		elementConfigurationEquipment.expand();
		assertThat("There are documents, equipment parametes and child items now", elementConfigurationEquipment.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_EQUIPMENT_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Equipment Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		bot.button("Save").click();
		openEditor(elementConfigurationEquipment);
		SwtBotDebugHelper.logCodeLine();
		assertFalse(bot.checkBox("Equipment Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_EQUIPMENT = 1; // Documents folder
		assertThat("Equipment parameters should have been removed", elementConfigurationEquipment.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_EQUIPMENT));
		
	}
	
	@Test
	public void testAddRemoveEquipmentParametersMassParametersViaCheckBox() {
		
		openEditor(elementConfigurationEquipment);
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Equipment Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		bot.button("Save").click();
		openEditor(elementConfigurationEquipment);
		
		SwtBotDebugHelper.logCodeLine();
		assertTrue(bot.checkBox("Equipment Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS = 3; // Documents folder, equipment parameters, equipment mass parameters
		elementConfigurationEquipment.expand();
		assertThat("There are documents, equipment parametes and equipment mass parameters now", elementConfigurationEquipment.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_MATH_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
		
		SwtBotDebugHelper.logCodeLine();
		bot.checkBox("Equipment Mass Parameters").click();
		SwtBotDebugHelper.logCodeLine();
		waitForEditingDomainAndUiThread();
		// Does not ask for save because equipment parameters are still there... So no hierarchy change happening
		openEditor(elementConfigurationEquipment);
		SwtBotDebugHelper.logCodeLine();
		assertFalse(bot.checkBox("Equipment Mass Parameters").isChecked());
		SwtBotDebugHelper.logCodeLine();
		final int EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS = 2; // Documents folder, system parameters
		assertThat("Equipment parameters should not have been removed, mass paremeters yes", elementConfigurationEquipment.getItems(), arrayWithSize(EXPECTED_NUMBER_TREE_CHILDREN_SYSTEM_PARAMETERS));
		SwtBotDebugHelper.logCodeLine();
	}
	
	@Test
	public void testInitialEnabledLevels() {
		
		// Tests how CEF levels can be assigned before any level is assigned 
		
		// On configuration root level, the only the system CAs should be enabled
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemParameters.class));
		assertEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemMassParameters.class));
		assertEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SubSystemPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SubSystemMassParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentTemperatureParameters.class));
		
		// On the second level System and Subsystem CAs should be enabled 
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemMassParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SubSystemPowerParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SubSystemMassParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentTemperatureParameters.class));
		
		
		// On the third level all CA levels should be enabled
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemMassParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SubSystemPowerParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SubSystemMassParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentMassParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentPowerParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentTemperatureParameters.class));
		SwtBotDebugHelper.logCodeLine();

	}
	
	@Test
	public void testEnabledLevelsWithBasicStructure() {
		SwtBotDebugHelper.logCodeLine();
		addElement(SystemParameters.class, conceptCefX, configurationTreeSystem);
		SwtBotDebugHelper.logCodeLine();
		addElement(SubSystemMassParameters.class, conceptCefX, elementConfigurationSubSystem);
		SwtBotDebugHelper.logCodeLine();
		addElement(EquipmentMassParameters.class, conceptCefX, elementConfigurationEquipment);
		SwtBotDebugHelper.logCodeLine();
		
		// Now only the system CAs should be enabled
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemParameters.class)); // Already applied
		assertEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemMassParameters.class));
		assertEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SubSystemPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, SubSystemMassParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(configurationTreeSystem, conceptCefX, EquipmentTemperatureParameters.class));
		SwtBotDebugHelper.logCodeLine();
		
		// Now only subsystem CAs should be enabled on second level as system cannot be contained in a system
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SubSystemPowerParameters.class)); 
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, SubSystemMassParameters.class)); // Cannot be assigned twice
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationSubSystem, conceptCefX, EquipmentTemperatureParameters.class));
		SwtBotDebugHelper.logCodeLine();
		
		// On the third level all CA levels should be enabled
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemMassParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SystemPowerParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SubSystemPowerParameters.class));
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, SubSystemMassParameters.class));
		
		SwtBotDebugHelper.logCodeLine();
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentParameters.class)); // Cannot be assigned twice
		assertNotEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentMassParameters.class)); // Automatically enabled with mass params
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentPowerParameters.class));
		assertEnabled(contextMenuAddCommand(elementConfigurationEquipment, conceptCefX, EquipmentTemperatureParameters.class));
		SwtBotDebugHelper.logCodeLine();
	}
	
	protected SWTBotMenu contextMenuAddCommand(SWTBotTreeItem treeItem, Concept concept, Class<?> beanClass) {
		return treeItem.contextMenu(concept.getDisplayName()).menu(getAddCommandName(beanClass));
	}
	
	private String getAddCommandName(Class<?> beanClass) {
		return "Add " +  beanClass.getSimpleName();
	}
	

}
