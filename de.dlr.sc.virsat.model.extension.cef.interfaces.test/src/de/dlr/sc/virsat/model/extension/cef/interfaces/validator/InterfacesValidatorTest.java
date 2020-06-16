/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.interfaces.validator;

// *****************************************************************
// * Import Statements
// *****************************************************************



// *****************************************************************
// * Class Declaration
// *****************************************************************

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * Concept for Functional Interface Architecture in CEF
 * 
 */
public class InterfacesValidatorTest extends AInterfacesValidatorTest {

	@Test
	public void testInterfacesValidator() {
		InterfacesValidator validator = new InterfacesValidator();
		assertTrue(validator.validate(testSei));
	}
}