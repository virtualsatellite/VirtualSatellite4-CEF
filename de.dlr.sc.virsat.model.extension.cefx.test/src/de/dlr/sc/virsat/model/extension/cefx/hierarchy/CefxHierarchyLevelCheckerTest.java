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
		ConfigurationTree ct = new ConfigurationTree(conceptPS);
		ElementConfiguration ec1 = new ElementConfiguration(conceptPS);
		ElementConfiguration ec2 = new ElementConfiguration(conceptPS);
		ElementConfiguration ec3 = new ElementConfiguration(conceptPS);
		
		ct.add(ec1);
		ec1.add(ec2);
		ec2.add(ec3);

		SystemParameters sp = new SystemParameters(conceptCEFX);
		ct.add(sp);

		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ec2.add(ep);
		
		// CT can only be system
		assertTrue(checker.canAdd(ct, SystemParameters.class));
		assertTrue(checker.canAdd(ct, SystemMassParameters.class));
		assertTrue(checker.canAdd(ct, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ct, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ct, SubSystemPowerParameters.class));

		assertFalse(checker.canAdd(ct, EquipmentParameters.class));
		assertFalse(checker.canAdd(ct, EquipmentMassParameters.class));
		assertFalse(checker.canAdd(ct, EquipmentPowerParameters.class));
		assertFalse(checker.canAdd(ct, EquipmentTemperatureParameters.class));
		
		assertFalse(checker.hasMultipleLevels(ct));
		assertFalse(checker.hasWrongLevel(ct));

		// EC1 can only be subsystem
		assertFalse(checker.canAdd(ec1, SystemParameters.class));
		assertFalse(checker.canAdd(ec1, SystemMassParameters.class));
		assertFalse(checker.canAdd(ec1, SystemPowerParameters.class));

		assertTrue(checker.canAdd(ec1, SubSystemMassParameters.class));
		assertTrue(checker.canAdd(ec1, SubSystemPowerParameters.class));

		assertFalse(checker.canAdd(ec1, EquipmentParameters.class));
		assertFalse(checker.canAdd(ec1, EquipmentMassParameters.class));
		assertFalse(checker.canAdd(ec1, EquipmentPowerParameters.class));
		assertFalse(checker.canAdd(ec1, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ec1));
		assertFalse(checker.hasWrongLevel(ec1));
		
		//EC2 and 3 can be equipment
		assertFalse(checker.canAdd(ec2, SystemParameters.class));
		assertFalse(checker.canAdd(ec2, SystemMassParameters.class));
		assertFalse(checker.canAdd(ec2, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ec2, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ec2, SubSystemPowerParameters.class));

		assertTrue(checker.canAdd(ec2, EquipmentParameters.class));
		assertTrue(checker.canAdd(ec2, EquipmentMassParameters.class));
		assertTrue(checker.canAdd(ec2, EquipmentPowerParameters.class));
		assertTrue(checker.canAdd(ec2, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ec2));
		assertFalse(checker.hasWrongLevel(ec2));

		assertFalse(checker.canAdd(ec3, SystemParameters.class));
		assertFalse(checker.canAdd(ec3, SystemMassParameters.class));
		assertFalse(checker.canAdd(ec3, SystemPowerParameters.class));

		assertFalse(checker.canAdd(ec3, SubSystemMassParameters.class));
		assertFalse(checker.canAdd(ec3, SubSystemPowerParameters.class));

		assertTrue(checker.canAdd(ec3, EquipmentParameters.class));
		assertTrue(checker.canAdd(ec3, EquipmentMassParameters.class));
		assertTrue(checker.canAdd(ec3, EquipmentPowerParameters.class));
		assertTrue(checker.canAdd(ec3, EquipmentTemperatureParameters.class));

		assertFalse(checker.hasMultipleLevels(ec3));
		assertFalse(checker.hasWrongLevel(ec3));
	}
	
	@Test
	public void testMultipleLevels() {
		ElementConfiguration ec = new ElementConfiguration(conceptPS);
		
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ec.add(sp);

		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ec.add(ep);

		assertTrue(checker.hasMultipleLevels(ec));
	}

	@Test
	public void testWrongLevel() {
		ConfigurationTree ct = new ConfigurationTree(conceptPS);
		ElementConfiguration ec = new ElementConfiguration(conceptPS);
		ct.add(ec);
		
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ct.add(sp);

		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ec.add(ep);

		assertTrue(checker.hasWrongLevel(ct));
		assertTrue(checker.hasWrongLevel(ec));
	}
}
