/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.util;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.provider.PropertydefinitionsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.provider.PropertyinstancesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.provider.DVLMCategoriesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.provider.ConceptsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.general.provider.GeneralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.provider.DVLMItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.resource.provider.DVLMResourceItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.structural.provider.DVLMStructuralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystem;
import de.dlr.sc.virsat.model.extension.cef.model.System;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;

/**
 * test Cases for the CEF Mode Helper
 * @author fisc_ph
 *
 */
public class CefModeHelperTest {

	protected Concept concept;
	protected CefModeHelper cefModeHelper;
	protected EditingDomain ed;
	
	@Before
	public void setUp() throws Exception {
		String conceptXmiPluginPath = "de.dlr.sc.virsat.model.extension.cef/concept/concept.xmi";
		concept = de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader.loadConceptFromPlugin(conceptXmiPluginPath);
		cefModeHelper = new CefModeHelper();
		
		// Create an Editing Domain
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new DVLMResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMStructuralItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new GeneralItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConceptsItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new DVLMCategoriesItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new PropertydefinitionsItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new PropertyinstancesItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		ed = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack());
	}

	@After
	public void tearDown() throws Exception {
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
		
		System system = new System(concept);
		SystemMode systemMode1 = new SystemMode(concept);
		SystemMode systemMode2 = new SystemMode(concept);
		system.add(systemMode1);
		system.add(systemMode2);
		
		SubSystem subSystem = new SubSystem(concept);
		system.add(subSystem);
		Parameter param = new Parameter(concept);
		subSystem.add(param);
		
		List<SystemMode> systemModes = cefModeHelper.getAllModes(param);
		assertThat("Found the system modes on System level", systemModes, hasItems(systemMode1, systemMode2));
	}

	@Test
	public void testGetAllActiveModes() {
		Parameter param = new Parameter(concept);
		
		Value value1 = new Value(concept);
		Value value2 = new Value(concept);
		
		SystemMode systemMode1 = new SystemMode(concept);
		SystemMode systemMode2 = new SystemMode(concept);
		
		value1.setMode(systemMode1);
		value2.setMode(systemMode2);
		
		param.getModeValues().add(value1);
		param.getModeValues().add(value2);
		
		List<SystemMode> activeModes = cefModeHelper.getAllActiveModes(param);
		assertThat("Found the system modes on System level", activeModes, hasItems(systemMode1, systemMode2));
	}

	@Test
	public void testAddModeValue() {
		Parameter param = new Parameter(concept);
		SystemMode systemMode = new SystemMode(concept);
		
		Command command = cefModeHelper.addModeValue(ed, param, systemMode);
		assertTrue("Command can be executed", command.canExecute());
		
		command.execute();
		
		List<SystemMode> activeModes = cefModeHelper.getAllActiveModes(param);
		assertThat("Mode Value has been created and attached for System Mode", activeModes, hasItem(systemMode));
	}

	@Test
	public void testRemoveModeValue() {
		Parameter param = new Parameter(concept);
		SystemMode systemMode = new SystemMode(concept);
		Value value = new Value(concept);
		
		value.setMode(systemMode);
		param.getModeValues().add(value);
		
		assertThat("Mode Value is attached for System Mode", param.getModeValues(), hasItem(value));
		
		Command command = cefModeHelper.removeModeValue(ed, param, systemMode);
		assertTrue("Command can be executed", command.canExecute());
		
		command.execute();
		
		assertTrue("Mode Value has removed for System Mode", param.getModeValues().isEmpty());
	}
	
	@Test
	public void testGetModeValue() {
		Parameter param = new Parameter(concept);
	
		Value value1 = new Value(concept);
		Value value2 = new Value(concept);
		
		SystemMode systemMode1 = new SystemMode(concept);
		SystemMode systemMode2 = new SystemMode(concept);
		
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
		Parameter param = new Parameter(concept);
	
		Value value1 = new Value(concept);
		Value value2 = new Value(concept);
		
		SystemMode systemMode1 = new SystemMode(concept);
		SystemMode systemMode2 = new SystemMode(concept);
		
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

}
