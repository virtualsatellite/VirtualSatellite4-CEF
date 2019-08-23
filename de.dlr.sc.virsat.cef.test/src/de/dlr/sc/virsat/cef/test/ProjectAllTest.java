/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.cef.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author fisc_ph
 *
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
				de.dlr.sc.virsat.test.ProjectAllTest.class,
				de.dlr.sc.virsat.cef.calculation.test.AllTests.class,
				de.dlr.sc.virsat.model.extension.cef.test.AllTests.class,
				de.dlr.sc.virsat.model.extension.cef.test.AllTestsGen.class,
				de.dlr.sc.virsat.model.extension.cefx.test.AllTests.class,
				de.dlr.sc.virsat.model.extension.cefx.test.AllTestsGen.class,
				de.dlr.sc.virsat.model.extension.cef.interfaces.test.AllTests.class,
				de.dlr.sc.virsat.model.extension.cef.interfaces.test.AllTestsGen.class
				})

/**
 * Test Class
 */
public class ProjectAllTest {   
}