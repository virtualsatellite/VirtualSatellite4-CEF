/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.util;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptTestCase;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyComposed;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

/**
 * test Cases for the CEF Mode Helper
 *
 */
public class CefModeHelperTest extends AConceptTestCase {

	private static final String CONCEPT_ID_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	private static final String CONCEPT_ID_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	
	protected Concept conceptCEFX;
	protected Concept conceptPS;
	
	protected CefModeHelper cefModeHelper;
	
	@Before
	public void setUp() throws Exception {
		conceptCEFX = loadConceptFromPlugin(CONCEPT_ID_CEFX);
		conceptPS = loadConceptFromPlugin(CONCEPT_ID_PS);
		
		cefModeHelper = new CefModeHelper();
		
		prepareEditingDomain();
	}

	@Test
	public void testGetAllModes() {
		
		/**
		 * Structure:
		 * System 
		 * - SystemMode1
		 * - SystemMode2
		 * - SubSystem
		 * -- Parameter
		 */
		
		ActiveConceptHelper.getCategory(conceptCEFX, SystemMode.class.getSimpleName()).setIsApplicableForAll(true);
		
		ConfigurationTree system = new ConfigurationTree(conceptPS);
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		system.add(systemMode1);
		system.add(systemMode2);
		
		ElementConfiguration subSystem = new ElementConfiguration(conceptPS);
		system.add(subSystem);
		Parameter param = new Parameter(conceptCEFX);
		subSystem.add(param);
		
		List<SystemMode> systemModesSei = cefModeHelper.getAllModes(subSystem);
		assertThat("Found the system modes on System level", systemModesSei, hasItems(systemMode1, systemMode2));
		
		List<SystemMode> systemModesParam = cefModeHelper.getAllModes(param);
		assertEquals("Found the same system modes on System level", systemModesSei, systemModesParam);
	}
	
	@Test
	public void testGetModeByName() {
		ActiveConceptHelper.getCategory(conceptCEFX, SystemMode.class.getSimpleName()).setIsApplicableForAll(true);
		
		ConfigurationTree system = new ConfigurationTree(conceptPS);
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		system.add(systemMode1);
		system.add(systemMode2);
		
		systemMode1.setName("MODE_1");
		systemMode2.setName("MODE_2");
		
		assertNull("Did not find non-existent system mode", cefModeHelper.getModeByName(system, "DOESNT_EXIST"));
		assertEquals("Found the system modes on System level", cefModeHelper.getModeByName(system, "MODE_1"), systemMode1);
	}

	@Test
	public void testGetAllActiveModes() {
		Parameter param = new Parameter(conceptCEFX);
		
		Value value1 = new Value(conceptCEFX);
		Value value2 = new Value(conceptCEFX);
		
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		
		value1.setMode(systemMode1);
		value2.setMode(systemMode2);
		
		param.getModeValues().add(value1);
		param.getModeValues().add(value2);
		
		List<SystemMode> activeModes = cefModeHelper.getAllActiveModes(param);
		assertThat("Found the system modes on System level", activeModes, hasItems(systemMode1, systemMode2));
	}

	@Test
	public void testAddModeValue() {
		Parameter param = new Parameter(conceptCEFX);
		SystemMode systemMode = new SystemMode(conceptCEFX);
		
		Command command = cefModeHelper.addModeValue(editingDomain, param, systemMode);
		assertTrue("Command can be executed", command.canExecute());
		
		command.execute();
		
		List<SystemMode> activeModes = cefModeHelper.getAllActiveModes(param);
		assertThat("Mode Value has been created and attached for System Mode", activeModes, hasItem(systemMode));
	}

	@Test
	public void testRemoveModeValue() {
		Parameter param = new Parameter(conceptCEFX);
		SystemMode systemMode = new SystemMode(conceptCEFX);
		Value value = new Value(conceptCEFX);
		
		value.setMode(systemMode);
		param.getModeValues().add(value);
		
		assertThat("Mode Value is attached for System Mode", param.getModeValues(), hasItem(value));
		
		Command command = cefModeHelper.removeModeValue(editingDomain, param, systemMode);
		assertTrue("Command can be executed", command.canExecute());
		
		command.execute();
		
		assertTrue("Mode Value has removed for System Mode", param.getModeValues().isEmpty());
	}
	
	@Test
	public void testGetModeValue() {
		Parameter param = new Parameter(conceptCEFX);
	
		Value value1 = new Value(conceptCEFX);
		Value value2 = new Value(conceptCEFX);
		
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		systemMode1.setName("MODE_1");
		systemMode2.setName("MODE_2");
		
		value1.setMode(systemMode1);
		value2.setMode(systemMode2);
		
		
		param.getModeValues().add(value1);
		param.getModeValues().add(value2);
		
		Value modeValue1 = cefModeHelper.getModeValue(param, systemMode1);
		Value modeValue2 = cefModeHelper.getModeValue(param, systemMode2);
		assertEquals("Found the correct mode value", modeValue1, value1);
		assertEquals("Found the correct mode value", modeValue2, value2);
	}
	
	@Test
	public void testGetModeValueOrDefault() {
		Parameter param = new Parameter(conceptCEFX);
	
		Value value1 = new Value(conceptCEFX);
		Value value2 = new Value(conceptCEFX);
		
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		systemMode1.setName("MODE_1");
		systemMode2.setName("MODE_2");
		
		value1.setMode(systemMode1);
		value2.setMode(systemMode2);
		
		// CHECKSTYLE:OFF
		param.setDefaultValue(5);
		value1.setValue(10);
		value2.setValue(20);
		// CHECKSYTLE:ON
		
		param.getModeValues().add(value1);
		param.getModeValues().add(value2);
		
		final double EPS = 0.00001;
		double modeValueDefault = cefModeHelper.getModeValueOrDefault(param, null);
		double modeValue1 = cefModeHelper.getModeValueOrDefault(param, systemMode1);
		double modeValue2 = cefModeHelper.getModeValueOrDefault(param, systemMode2);
		assertEquals("Found the correct mode value", param.getDefaultValue(), modeValueDefault, EPS);
		assertEquals("Found the correct mode value", value1.getValue(), modeValue1, EPS);
		assertEquals("Found the correct mode value", value2.getValue(), modeValue2, EPS);
	}
	
	@Test
	public void testIsValueCalculated() {
		CefModeHelper helper = new CefModeHelper();
		
		ElementConfiguration subSystem = new ElementConfiguration(conceptPS);
		EquipmentMassParameters paramsMass = new EquipmentMassParameters(conceptCEFX);
		EquipmentParameters params = new EquipmentParameters(conceptCEFX);
		subSystem.add(params);
		subSystem.add(paramsMass);
		BeanPropertyComposed<Parameter> beanNotComputed = paramsMass.getMassBean();
		BeanPropertyComposed<Parameter> beanComputed = paramsMass.getMassTotalBean();
		
		Value valueNotComputed = new Value(conceptCEFX);
		Value valueComputed = new Value(conceptCEFX);
		
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		
		valueNotComputed.setMode(systemMode1);
		valueComputed.setMode(systemMode2);
		
		beanNotComputed.getValue().getModeValues().add(valueNotComputed);
		beanComputed.getValue().getModeValues().add(valueComputed);
		
		assertTrue(helper.isValueCalculated((CategoryAssignment) valueComputed.getTypeInstance()));
		assertFalse(helper.isValueCalculated((CategoryAssignment) valueNotComputed.getTypeInstance()));
	}

}
