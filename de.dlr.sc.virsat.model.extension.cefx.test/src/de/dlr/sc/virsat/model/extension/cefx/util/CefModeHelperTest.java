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
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptTestCase;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.provider.PropertydefinitionsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.provider.PropertyinstancesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.categories.provider.DVLMCategoriesItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.provider.ConceptsItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.provider.GeneralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.provider.DVLMItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.resource.provider.DVLMResourceItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.dvlm.structural.provider.DVLMStructuralItemProviderAdapterFactory;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

/**
 * test Cases for the CEF Mode Helper
 * @author fisc_ph
 *
 */
public class CefModeHelperTest extends AConceptTestCase {

	private static final String CONCEPT_ID_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	private static final String CONCEPT_ID_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	
	protected Concept conceptCEFX;
	protected Concept conceptPS;
	
	protected CefModeHelper cefModeHelper;
	protected EditingDomain ed;
	
	@Before
	public void setUp() throws Exception {
		conceptCEFX = loadConceptFromPlugin(CONCEPT_ID_CEFX);
		conceptPS = loadConceptFromPlugin(CONCEPT_ID_PS);
		
		cefModeHelper = new CefModeHelper();
		
		ActiveConceptHelper.getCategory(conceptCEFX, SystemMode.class.getSimpleName()).setIsApplicableForAll(true);
		
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
		
		ConfigurationTree system = new ConfigurationTree(conceptPS);
		SystemMode systemMode1 = new SystemMode(conceptCEFX);
		SystemMode systemMode2 = new SystemMode(conceptCEFX);
		system.add(systemMode1);
		system.add(systemMode2);
		
		ElementConfiguration subSystem = new ElementConfiguration(conceptPS);
		system.add(subSystem);
		Parameter param = new Parameter(conceptCEFX);
		subSystem.add(param);
		
		List<SystemMode> systemModes = cefModeHelper.getAllModes(param);
		assertThat("Found the system modes on System level", systemModes, hasItems(systemMode1, systemMode2));
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
		
		Command command = cefModeHelper.addModeValue(ed, param, systemMode);
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
		
		Command command = cefModeHelper.removeModeValue(ed, param, systemMode);
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
