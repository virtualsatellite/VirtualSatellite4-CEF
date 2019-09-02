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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptTestCase;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

/**
 * Test class for CefxHierarchyLevelChecker
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
	public void testSingleElementNoWarnings() {
		ElementConfiguration ec = new ElementConfiguration(conceptPS);
		assertFalse(checker.beanHasAmbiguousLevel(ec));
		assertFalse(checker.beanHasInapplicableLevel(ec));
	}
	
	@Test
	public void testElementsWithoutLevels() {
		ElementConfiguration ec1 = new ElementConfiguration(conceptPS);
		ElementConfiguration ec2 = new ElementConfiguration(conceptPS);
		ElementConfiguration ec3 = new ElementConfiguration(conceptPS);
		ec1.add(ec2);
		ec2.add(ec3);
		
		// root can only be system
		assertCanAddSystemCAs(ec1, true);
		assertCanAddSubSystemCAs(ec1, false);
		assertCanAddEquipmentCAs(ec1, false);

		// second can be system or subsystem
		assertCanAddSystemCAs(ec2, true);
		assertCanAddSubSystemCAs(ec2, true);
		assertCanAddEquipmentCAs(ec2, false);
		
		// third can be any level
		assertCanAddSystemCAs(ec3, true);
		assertCanAddSubSystemCAs(ec3, true);
		assertCanAddEquipmentCAs(ec3, true);
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
	
	@Test
	public void testGetLevelDefiningCAs() {
		ConfigurationTree ct = new ConfigurationTree(conceptPS);

		assertTrue(checker.getLevelDefiningCategoryAssignments(ct).isEmpty());

		Parameter irrelevantCA = new Parameter(conceptCEFX);
		ct.add(irrelevantCA);

		assertTrue(checker.getLevelDefiningCategoryAssignments(ct).isEmpty());
		
		SystemParameters sp = new SystemParameters(conceptCEFX);
		ct.add(sp);

		List<IBeanCategoryAssignment> levelDefiningCAs = checker.getLevelDefiningCategoryAssignments(ct);
		assertEquals(1, levelDefiningCAs.size());
		assertTrue(levelDefiningCAs.contains(sp));
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
