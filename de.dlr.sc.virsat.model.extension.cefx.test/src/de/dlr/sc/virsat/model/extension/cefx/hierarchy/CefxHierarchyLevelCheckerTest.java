/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.hierarchy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptTestCase;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

/**
 */
public class CefxHierarchyLevelCheckerTest extends AConceptTestCase {

	private static final String CONCEPT_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	private static final String CONCEPT_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;

	private Concept conceptPS;
	private Concept conceptCEFX;
	
	private CefxHierarchyLevelChecker checker;

	@Before
	public void setUp() {
		conceptPS = loadConceptFromPlugin(CONCEPT_PS);
		conceptCEFX = loadConceptFromPlugin(CONCEPT_CEFX);

		ActiveConceptHelper.getCategory(conceptCEFX, SystemParameters.class.getSimpleName()).setIsApplicableForAll(true);
		ActiveConceptHelper.getCategory(conceptCEFX, EquipmentParameters.class.getSimpleName()).setIsApplicableForAll(true);
		
		checker = new CefxHierarchyLevelChecker();
	}
	
	@Test
	public void testSingleElement() {
		ElementConfiguration ec = new ElementConfiguration(conceptPS);
		
		assertTrue(checker.canAdd(ec, SystemParameters.class));
		assertTrue(checker.canAdd(ec, SystemMassParameters.class));
		assertTrue(checker.canAdd(ec, SystemPowerParameters.class));

		assertTrue(checker.canAdd(ec, SubSystemMassParameters.class));
		assertTrue(checker.canAdd(ec, SubSystemPowerParameters.class));

		assertTrue(checker.canAdd(ec, EquipmentParameters.class));
		assertTrue(checker.canAdd(ec, EquipmentMassParameters.class));
		assertTrue(checker.canAdd(ec, EquipmentPowerParameters.class));
		assertTrue(checker.canAdd(ec, EquipmentTemperatureParameters.class));
		
		assertFalse(checker.hasMultipleLevels(ec));
		assertFalse(checker.hasWrongLevel(ec));
	}

	@Test
	public void testHierarchy() {
		ConfigurationTree ctSystem = new ConfigurationTree(conceptPS);
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ctSystem.add(sp);

		ElementConfiguration ecPotentialSubSystem = new ElementConfiguration(conceptPS);
		ctSystem.add(ecPotentialSubSystem);

		ElementConfiguration ecEquipment = new ElementConfiguration(conceptPS);
		ecPotentialSubSystem.add(ecEquipment);
		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ecEquipment.add(ep);

		ElementConfiguration ecPotentialEquipment = new ElementConfiguration(conceptPS);
		ecEquipment.add(ecPotentialEquipment);
		

		assertTrue(checker.canAdd(ctSystem, SystemParameters.class));
		assertTrue(checker.canAdd(ctSystem, SystemMassParameters.class));
		assertTrue(checker.canAdd(ctSystem, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ctSystem, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ctSystem, SubSystemPowerParameters.class));

		assertFalse(checker.canAdd(ctSystem, EquipmentParameters.class));
		assertFalse(checker.canAdd(ctSystem, EquipmentMassParameters.class));
		assertFalse(checker.canAdd(ctSystem, EquipmentPowerParameters.class));
		assertFalse(checker.canAdd(ctSystem, EquipmentTemperatureParameters.class));
		
		assertFalse(checker.hasMultipleLevels(ctSystem));
		assertFalse(checker.hasWrongLevel(ctSystem));


		assertFalse(checker.canAdd(ecPotentialSubSystem, SystemParameters.class));
		assertFalse(checker.canAdd(ecPotentialSubSystem, SystemMassParameters.class));
		assertFalse(checker.canAdd(ecPotentialSubSystem, SystemPowerParameters.class));

		assertTrue(checker.canAdd(ecPotentialSubSystem, SubSystemMassParameters.class));
		assertTrue(checker.canAdd(ecPotentialSubSystem, SubSystemPowerParameters.class));

		assertFalse(checker.canAdd(ecPotentialSubSystem, EquipmentParameters.class));
		assertFalse(checker.canAdd(ecPotentialSubSystem, EquipmentMassParameters.class));
		assertFalse(checker.canAdd(ecPotentialSubSystem, EquipmentPowerParameters.class));
		assertFalse(checker.canAdd(ecPotentialSubSystem, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ecPotentialSubSystem));
		assertFalse(checker.hasWrongLevel(ecPotentialSubSystem));
		

		assertFalse(checker.canAdd(ecEquipment, SystemParameters.class));
		assertFalse(checker.canAdd(ecEquipment, SystemMassParameters.class));
		assertFalse(checker.canAdd(ecEquipment, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ecEquipment, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ecEquipment, SubSystemPowerParameters.class));

		assertTrue(checker.canAdd(ecEquipment, EquipmentParameters.class));
		assertTrue(checker.canAdd(ecEquipment, EquipmentMassParameters.class));
		assertTrue(checker.canAdd(ecEquipment, EquipmentPowerParameters.class));
		assertTrue(checker.canAdd(ecEquipment, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ecEquipment));
		assertFalse(checker.hasWrongLevel(ecEquipment));


		assertFalse(checker.canAdd(ecPotentialEquipment, SystemParameters.class));
		assertFalse(checker.canAdd(ecPotentialEquipment, SystemMassParameters.class));
		assertFalse(checker.canAdd(ecPotentialEquipment, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ecPotentialEquipment, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ecPotentialEquipment, SubSystemPowerParameters.class));

		assertTrue(checker.canAdd(ecPotentialEquipment, EquipmentParameters.class));
		assertTrue(checker.canAdd(ecPotentialEquipment, EquipmentMassParameters.class));
		assertTrue(checker.canAdd(ecPotentialEquipment, EquipmentPowerParameters.class));
		assertTrue(checker.canAdd(ecPotentialEquipment, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ecPotentialEquipment));
		assertFalse(checker.hasWrongLevel(ecPotentialEquipment));
	}
	
	@Test
	public void testMultipleLevels() {
		ElementConfiguration ecBothSystemAndEquipmentParams = new ElementConfiguration(conceptPS);
		
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ecBothSystemAndEquipmentParams.add(sp);

		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ecBothSystemAndEquipmentParams.add(ep);

		assertTrue(checker.hasMultipleLevels(ecBothSystemAndEquipmentParams));
	}

	@Test
	public void testWrongLevel() {
		ConfigurationTree ct = new ConfigurationTree(conceptPS);
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ct.add(sp);

		//Equipment cannot be directly under a system because subsystem level is missing
		ElementConfiguration ec = new ElementConfiguration(conceptPS);
		ct.add(ec);
		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ec.add(ep);

		assertTrue(checker.hasWrongLevel(ct));
		assertTrue(checker.hasWrongLevel(ec));
	}
}
