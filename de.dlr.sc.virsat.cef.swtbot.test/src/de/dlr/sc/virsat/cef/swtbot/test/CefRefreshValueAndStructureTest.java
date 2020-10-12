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

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.System;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.SystemParameters;

public class CefRefreshValueAndStructureTest extends ACefSwtBotTestCase {

	SWTBotTreeItem repositoryNavigatorItem;
	SWTBotTreeItem systemItem;
	SWTBotTreeItem systemParameterItem;
	SWTBotTreeItem modeDurationItem;
	SWTBotTreeItem systemMassParametersItem;

	static final int VALUE_COLUMN_INDEX = 1;
	static final int UNIT_COLUMN_INDEX = 2; 
	static final int NAME_COLUMN_INDEX = 0;
	static final int SYSTEM_MARGIN_ROW_INDEX = 1;
	static final int MODE_DURATION_ROW_INDEX = 0;
	
	@Override
	public void before() throws Exception {
		super.before();
		// create the necessary items for the test
		repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");
		repositoryNavigatorItem.click();
		systemItem = addElement(System.class, conceptCef, repositoryNavigatorItem);
		openEditor(systemItem);
	}

	@Test
	public void refreshTreeTablePropertyValueTest() {
		systemParameterItem = addElement(SystemParameters.class, conceptCef, systemItem);
		openEditor(systemParameterItem);

		final String EXP_SYSTEM_PARAMETERS_NAME = "TestSystemParameters";
		final String EXP_SYSTEM_PARAMETERS_MARGIN = "20";
		final String EXP_SYSTEM_PARAMETERS_UNIT = "Megabyte: MB";

		// set system parameter values
		rename(systemParameterItem, EXP_SYSTEM_PARAMETERS_NAME);
		renameField(SystemParameters.PROPERTY_SYSTEMMARGIN, EXP_SYSTEM_PARAMETERS_MARGIN);
		bot.comboBox().setSelection(EXP_SYSTEM_PARAMETERS_UNIT);
		save();

		openEditor(systemItem);
		
		final int SYSTEM_ITEM_LEVEL = 1;
		SWTBotTree systemParametersTree = new SWTBotTree((Tree) bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), 
				SYSTEM_ITEM_LEVEL));
		String systemMarginValue = systemParametersTree.
				getAllItems()[SYSTEM_MARGIN_ROW_INDEX].cell(VALUE_COLUMN_INDEX).toString();
		String systemMarginUnit = systemParametersTree.
				getAllItems()[SYSTEM_MARGIN_ROW_INDEX].cell(UNIT_COLUMN_INDEX).toString();

		assertEquals("SP: " + EXP_SYSTEM_PARAMETERS_NAME, systemParameterItem.getText());
		assertEquals(EXP_SYSTEM_PARAMETERS_MARGIN, systemMarginValue);
		assertEquals(EXP_SYSTEM_PARAMETERS_UNIT, systemMarginUnit);

		// set mode duration values
		systemParameterItem.expand();
		modeDurationItem = systemParameterItem.getNode(0).expand().getNode(0); // get mode duration editor

		final String EXP_MODE_DURATION_NAME = "TestModeDuration";
		final Double EXP_MODE_DURATION_DEFAULT_VALUE = 50.0;
		final String EXP_MODE_DURATION_UNIT = "Minute: min";
		final String EXP_MODE_DURATION_NOTE = "test note";

		openEditor(modeDurationItem);
		rename(modeDurationItem, EXP_MODE_DURATION_NAME);
		renameField(Parameter.PROPERTY_DEFAULTVALUE, EXP_MODE_DURATION_DEFAULT_VALUE.toString()); // set default value in mode duration
		bot.comboBox().setSelection(EXP_MODE_DURATION_UNIT);
		renameField(Parameter.PROPERTY_NOTE, EXP_MODE_DURATION_NOTE);
		
		String modeDurationNote = getPropertyValue(Parameter.PROPERTY_NOTE);
		assertEquals(EXP_MODE_DURATION_NOTE, modeDurationNote);
		
		openEditor(systemItem);
		
		String modeDurationName = systemParametersTree.
				getAllItems()[MODE_DURATION_ROW_INDEX].cell(NAME_COLUMN_INDEX);
		String modeDurationDefaultValue = systemParametersTree.
				getAllItems()[MODE_DURATION_ROW_INDEX].cell(VALUE_COLUMN_INDEX);
		String modeDurationUnit = systemParametersTree.
				getAllItems()[MODE_DURATION_ROW_INDEX].cell(UNIT_COLUMN_INDEX);
		
		final Double DELTA_DOUBLE = 0.00001;
		
		assertEquals(EXP_MODE_DURATION_NAME, modeDurationName);
		assertEquals(EXP_MODE_DURATION_DEFAULT_VALUE, Double.parseDouble(modeDurationDefaultValue), DELTA_DOUBLE);
		assertEquals(EXP_MODE_DURATION_UNIT, modeDurationUnit);
	}
	
	@Test
	public void refreshTreeTablePropertyStructureTest() {
		systemParameterItem = addElement(SystemParameters.class, conceptCef, systemItem);
		SWTBotTree systemParametersTree = getSWTBotTree(systemItem, "Section for: SystemParameters");
		systemParametersTree.getAllItems()[MODE_DURATION_ROW_INDEX].expand();

		assertEquals("mode duration does not have system mode specific value", 0, 
				systemParametersTree.getAllItems()[MODE_DURATION_ROW_INDEX].getItems().length);

		SWTBotTreeItem systemModeItem = addElement(SystemMode.class, conceptCef, systemItem);
		final String EXP_SYSTEM_MODE_NAME = "Operational";
		rename(systemModeItem, EXP_SYSTEM_MODE_NAME);
		
		openEditor(systemItem);
		systemParametersTree.getAllItems()[MODE_DURATION_ROW_INDEX].expand();
		
		assertEquals("mode duration has system mode specific value", 1, 
				systemParametersTree.getAllItems()[MODE_DURATION_ROW_INDEX].getItems().length);
		SWTBotTreeItem operationalModeDuration = systemParametersTree.getAllItems()[MODE_DURATION_ROW_INDEX].getItems()[0];
		assertEquals(EXP_SYSTEM_MODE_NAME, operationalModeDuration.cell(NAME_COLUMN_INDEX));
	}
}
