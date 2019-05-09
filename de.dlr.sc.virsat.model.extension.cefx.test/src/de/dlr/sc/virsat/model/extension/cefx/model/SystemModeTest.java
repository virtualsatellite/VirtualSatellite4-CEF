/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import de.dlr.sc.virsat.model.extension.cefx.model.ASystemModeTest;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;

// *****************************************************************
// * Import Statements
// *****************************************************************



// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Abstract Generator Gap Class
 * 
 * Don't Manually modify this class
 * 
 * 
 * 
 */	
public class SystemModeTest extends ASystemModeTest {

	@Test
	public void testCompare() {
		SystemMode smA = new SystemMode(concept);
		SystemMode smB = new SystemMode(concept);
		SystemMode smB2 = new SystemMode(concept);
		
		smA.setName("Mode A");
		smB.setName("Mode B");
		smB2.setName("Mode B");
		
		assertEquals("Compare works as expected", -1, smA.compareTo(smB));
		assertEquals("Compare works as expected", 1, smB.compareTo(smA));
		assertEquals("Compare works as expected", 0, smB2.compareTo(smB));
		assertEquals("Compare works as expected", 0, smB.compareTo(smB2));
	}
	
}
