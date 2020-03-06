/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.decorator;

import java.util.stream.Collectors;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import de.dlr.sc.virsat.model.dvlm.inheritance.IInheritsFrom;

/**
 * 
 * Decorator for showing the inheritance of SEIs in a SysML Stereo-Type fashion
 *
 */
public class InheritanceDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IInheritsFrom) {
			IInheritsFrom iif = (IInheritsFrom) element;
			
			if (!iif.getSuperSeis().isEmpty()) {
				String decorationText = " <<" + iif.getSuperSeis().stream().map(sei -> sei.getName()).collect(Collectors.joining(", ")) + ">>";
				decoration.addSuffix(decorationText);
			} else {
				decoration.addSuffix("");
			}
		}
	}
}
