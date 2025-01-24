/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package de.dlr.sc.virsat.model.extension.cefx.ui.importWizards;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;

import de.dlr.sc.virsat.model.dvlm.general.IAssignedDiscipline;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.command.CreateAddStructuralElementInstanceCommand;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.resources.command.CreateSeiResourceAndFileCommand;

public class DLRStructureManager {
	
	
	/**
	 * private Constructor
	 */
	private DLRStructureManager() {
		
	}

	/**
	 * Set child discipline to parent (if any) and create a command to add child
	 * 
	 * @return command that will create file structure for a child and add it to the
	 *         parent
	 */	
    public static CompoundCommand createAddChildSEICommand(EObject parent, StructuralElementInstance child,
            VirSatTransactionalEditingDomain domain) {
        Discipline discipline = null;
        if (parent instanceof IAssignedDiscipline) {
            discipline = ((IAssignedDiscipline) parent).getAssignedDiscipline();
        }
        child.setAssignedDiscipline(discipline);
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(new CreateSeiResourceAndFileCommand(domain.getResourceSet(), child));
        cmd.append(CreateAddStructuralElementInstanceCommand.create(domain, parent, child));
        return cmd;
    }
}
