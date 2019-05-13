/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.provider;

import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTree;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTreeDomain;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.project.ui.navigator.contentProvider.VirSatProjectContentProvider;

/**
 * Simple content provider that filters for just showing the Product Tree
 *
 */
public class ProductTreeContentProvider extends VirSatFilteredWrappedTreeContentProvider {

	/**
	 * Constructor setting up the filter
	 */
	public ProductTreeContentProvider() {
		super(new VirSatProjectContentProvider());
		addStructuralElementIdFilter(ProductTree.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		addStructuralElementIdFilter(ProductTreeDomain.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		addStructuralElementIdFilter(ElementDefinition.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
	}
}
