/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.registry.ActiveConceptConfigurationElement;

/**
 * The plugin root class which is the entry point for the OSGi bundle activation
 * @author fisc_ph
 *
 */
public class Activator extends Plugin {

	public static final String PLUGIN_ID = "de.dlr.sc.virsat.model.extension.cef";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	private Concept concept = null;
	
	/**
	 * method to give simplified access to the model extensions concept.
	 * calling the method once caches the concept
	 * @param forceReload set to true to force a reload from the plugins resource
	 * @return the concept of the model extension
	 */
	public Concept getExtensionConcept(boolean forceReload) {
		if ((concept ==  null) || forceReload) {
			concept = ActiveConceptConfigurationElement.loadConceptFromPlugin(PLUGIN_ID + "/concept/concept.xmi");
		}
		return concept;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
