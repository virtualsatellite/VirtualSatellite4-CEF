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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptTestCase;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
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
		
		assertCanAddSystemCAs(ec, true);
		assertCanAddSubSystemCAs(ec, true);
		assertCanAddEquipmentCAs(ec, true);
		
		assertFalse(checker.beanHasAmbiguousLevel(ec));
		assertFalse(checker.beanHasInapplicableLevel(ec));
		assertFalse(checker.isSystem(ec));
		assertFalse(checker.isSubSystem(ec));
		assertFalse(checker.isEquipment(ec));
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
		

		assertCanAddSystemCAs(ctSystem, true);
		assertCanAddSubSystemCAs(ctSystem, false);
		assertCanAddEquipmentCAs(ctSystem, false);

		assertTrue(checker.isSystem(ctSystem));
		assertFalse(checker.beanHasAmbiguousLevel(ctSystem));
		assertFalse(checker.beanHasInapplicableLevel(ctSystem));

		
		assertCanAddSystemCAs(ecPotentialSubSystem, false);
		assertCanAddSubSystemCAs(ecPotentialSubSystem, true);
		assertCanAddEquipmentCAs(ecPotentialSubSystem, false);

		assertFalse(checker.beanHasAmbiguousLevel(ecPotentialSubSystem));
		assertFalse(checker.beanHasInapplicableLevel(ecPotentialSubSystem));
		

		assertCanAddSystemCAs(ecEquipment, false);
		assertCanAddSubSystemCAs(ecEquipment, false);
		assertCanAddEquipmentCAs(ecEquipment, true);

		assertTrue(checker.isEquipment(ecEquipment));
		assertFalse(checker.beanHasAmbiguousLevel(ecEquipment));
		assertFalse(checker.beanHasInapplicableLevel(ecEquipment));


		assertCanAddSystemCAs(ecPotentialEquipment, false);
		assertCanAddSubSystemCAs(ecPotentialEquipment, false);
		assertCanAddEquipmentCAs(ecPotentialEquipment, true);

		assertFalse(checker.beanHasAmbiguousLevel(ecPotentialEquipment));
		assertFalse(checker.beanHasInapplicableLevel(ecPotentialEquipment));
	}

	@Test
	public void testMultipleLevels() {
		ElementConfiguration ecBothSystemAndEquipmentParams = new ElementConfiguration(conceptPS);
		
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ecBothSystemAndEquipmentParams.add(sp);

		EquipmentParameters ep = new EquipmentParameters(conceptCEFX);
		ecBothSystemAndEquipmentParams.add(ep);

		assertTrue(checker.beanHasAmbiguousLevel(ecBothSystemAndEquipmentParams));
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

		assertTrue(checker.beanHasInapplicableLevel(ct));
		assertTrue(checker.beanHasInapplicableLevel(ec));
	}
	
	/**
	 * Asserts that system categories can/cannot be added to this bean
	 * @param bean 
	 * @param expectedCanAdd true if we expect that system categories can be added to the bean, false otherwise
	 */
	private void assertCanAddSystemCAs(IBeanStructuralElementInstance bean, boolean expectedCanAdd) {
		assertEquals(expectedCanAdd, checker.canAdd(bean, SystemParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, SystemMassParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, SystemPowerParameters.class));
		assertEquals(expectedCanAdd, checker.canAddSystemCategory(bean));
	}

	/**
	 * Asserts that subsystem categories can/cannot be added to this bean
	 * @param bean 
	 * @param expectedCanAdd true if we expect that subsystem categories can be added to the bean, false otherwise
	 */
	private void assertCanAddSubSystemCAs(IBeanStructuralElementInstance bean, boolean expectedCanAdd) {
		assertEquals(expectedCanAdd, checker.canAdd(bean, SubSystemMassParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, SubSystemPowerParameters.class));
		assertEquals(expectedCanAdd, checker.canAddSubSystemCategory(bean));
	}

	/**
	 * Asserts that equipment categories can/cannot be added to this bean
	 * @param bean 
	 * @param expectedCanAdd true if we expect that equipment categories can be added to the bean, false otherwise
	 */
	private void assertCanAddEquipmentCAs(IBeanStructuralElementInstance bean, boolean expectedCanAdd) {
		assertEquals(expectedCanAdd, checker.canAdd(bean, EquipmentParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, EquipmentMassParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, EquipmentPowerParameters.class));
		assertEquals(expectedCanAdd, checker.canAdd(bean, EquipmentTemperatureParameters.class));
		assertEquals(expectedCanAdd, checker.canAddEquipmentCategory(bean));
	}
}
