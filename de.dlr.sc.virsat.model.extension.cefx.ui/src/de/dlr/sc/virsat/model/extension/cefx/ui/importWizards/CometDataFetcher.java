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

import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;

import cdp4common.dto.Thing;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4dal.dal.ProxySettings;
import cdp4servicesdal.CdpServicesDal;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Responsible for fetching data from a Comet server using provided credentials and populating an SWT Tree with the data.
 */
public class CometDataFetcher {


    private URI serverUri;
    private Credentials credentials;
    private Session session;

    /**
     * Constructs a new CometDataFetcher.
     *
     * @param url Server URL.
     * @param username User's login name.
     * @param password User's password.
     * @param proxySettings proxy settings
     */
    public CometDataFetcher(String url, String username, String password, ProxySettings proxySettings) {
        this.serverUri = URI.create(url);
        this.credentials = new Credentials(username, password, serverUri, proxySettings);
        this.session = new SessionImpl(new CdpServicesDal(), this.credentials);
   
    }

  

    /**
     * Fetches data from the Comet server and populates the provided SWT Tree.
     *
     * @param tree The tree to populate with data.
     */
    public void fetchData(Tree tree) {
        try {
            List<Thing> things = session.getDal().open(credentials, new AtomicBoolean()).get();

            Display.getDefault().asyncExec(() -> TreePopulator.populateTree(tree, things));
        } catch (InterruptedException e) {
            Display.getDefault().asyncExec(() -> showMessage("Data fetch interrupted.", SWT.ICON_WARNING));
        } catch (ExecutionException e) {
            Display.getDefault().asyncExec(() -> showMessage("Failed to fetch data from the server. Check server availability and credentials.", SWT.ICON_ERROR));
        }
    }

    /**
     * Displays a message box with a specified message and icon.
     *
     * @param message The message to display.
     * @param iconType The type of icon to display 
     */
    private void showMessage(String message, int iconType) {
        MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), iconType | SWT.OK);
        messageBox.setMessage(message);
        messageBox.open();
    }
}
