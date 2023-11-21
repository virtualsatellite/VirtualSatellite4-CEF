/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.templates;

import org.eclipse.emf.common.command.Command;

/**
 * Creates data to a System SEI with to enable a quick study.
 *
 */

public class CreateDLRCEFXEquipmentHandler extends CreateDLRCEFXTemplateHandler {
	@Override
	protected Command getCommand() {
		return CreateDLRCEFXEquipmentCommand.create(firstSelectedEObject, activeConcept, ed);
	}
}
