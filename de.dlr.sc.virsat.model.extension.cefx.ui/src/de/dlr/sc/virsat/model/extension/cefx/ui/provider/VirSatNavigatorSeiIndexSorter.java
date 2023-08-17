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

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.Viewer;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.ui.navigator.commonSorter.VirSatNavigatorSeiSorter;

public class VirSatNavigatorSeiIndexSorter extends VirSatNavigatorSeiSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		boolean e1Sei = e1 instanceof StructuralElementInstance;
		boolean e2Sei = e2 instanceof StructuralElementInstance;

		if (e1Sei && e2Sei) {
			int indexE1 = 0;
			int indexE2 = 0;
			if (((StructuralElementInstance) e1).getParent() != null) {
				StructuralElementInstance parent = ((StructuralElementInstance) e1).getParent();
				indexE1 = parent.getChildren().indexOf(e1);
				indexE2 = parent.getChildren().indexOf(e2);
			} else {
				ResourceSet resourceSet = ((StructuralElementInstance) e1).eResource().getResourceSet();
				if (resourceSet instanceof VirSatResourceSet) {
					Repository parent = ((VirSatResourceSet) resourceSet).getRepository();
					indexE1 = parent.getRootEntities().indexOf(e1);
					indexE2 = parent.getRootEntities().indexOf(e2);
				}
			}
			if (indexE1 < indexE2) {
				return -1;
			} else if (indexE1 > indexE2) {
				return 1;
			}
			return 0;
		} else {
			return super.compare(viewer, e1, e2);
		}
	}
}
