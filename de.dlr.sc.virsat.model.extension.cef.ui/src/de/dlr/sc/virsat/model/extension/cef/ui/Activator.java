/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Activator class of this Plugin
 * @author fisc_ph
 *
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "de.dlr.sc.virsat.model.extension.cef.ui";

	/**
	 * Constructor of the Activator
	 */
	public Activator() {
		super();
	}

	private static Plugin plugin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		plugin = this;
	}
	
	/**
	 * get Default plugin for access to Logger etc
	 * @return the default plugin of thei activator
	 */
	public static Plugin getDefault() {
		return plugin;
	}
}
