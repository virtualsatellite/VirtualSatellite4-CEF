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

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class DLRLogger {

    private static final String PLUGIN_ID = "de.dlr.sc.virsat.model.extension.cefx";
    
    /**
     * Constructor
    **/

    private DLRLogger() {
    	
    }
    
    /**
     * Logs an informational message.
     *
     */
    public static void logInfo(String message) {
        ILog log = Platform.getLog(DLRLogger.class);
        log.log(new Status(IStatus.INFO, PLUGIN_ID, message));
    }

    /**
     * Logs an error message with an exception.
     */
    public static void logError(String message, Throwable exception) {
        ILog log = Platform.getLog(DLRLogger.class);
        log.log(new Status(IStatus.ERROR, PLUGIN_ID, message, exception));
    }

    /**
     * Displays an error message in a dialog.
     */
    public static void showErrorDialog(String title, String message) {
        Display.getDefault().asyncExec(() -> MessageDialog.openError(null, title, message));
    }
}