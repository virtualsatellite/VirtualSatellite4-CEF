/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.cef.calculation.modes;

import org.junit.Before;

import de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;

/**
 * Abstract test base class
 * @author muel_s8
 *
 */

public abstract class ATestCase {
	
	protected static final double TEST_EPSILON =  0.0001;
	private static final String CONCEPT_ID = "de.dlr.sc.virsat.model.extension.cef";
	
	protected Concept concept;
	
	@Before
	public void setUp() throws Exception {
		String conceptXmiPluginPath = CONCEPT_ID + "/concept/concept.xmi";
		concept = ConceptXmiLoader.loadConceptFromPlugin(conceptXmiPluginPath);
	}
	
}
