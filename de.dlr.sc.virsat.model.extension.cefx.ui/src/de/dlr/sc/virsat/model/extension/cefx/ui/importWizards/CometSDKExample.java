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

import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.ElementUsage;
import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4dal.exceptions.TransactionException;
import cdp4servicesdal.CdpServicesDal;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class demonstrates the use of the COMET SDK to interact with engineering model data and display it in a GUI.
 */
public class CometSDKExample {
    private final Session session;
    private final String baseUrl;

    // Constants for window size
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;

    // Constants for authentication details
    private static final String BASE_URL = "http://sc-010266l.intra.dlr.de:5000/";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pass";

    /**
     * Constructs a CometSDKExample instance and initializes a session.
     *
     * @param baseUrl  the base URL of the COMET server
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public CometSDKExample(String baseUrl, String username, String password) throws Exception {
        this.baseUrl = baseUrl;
        CdpServicesDal dal = new CdpServicesDal();
        Credentials credentials = new Credentials(username, password, new URI(baseUrl), null);
        session = new SessionImpl(dal, credentials);
    }

    /**
     * Fetches data from the COMET server and populates the provided tree with the retrieved data.
     *
     * @param tree the SWT tree to be populated with data
     */
    public void fetchData(Tree tree) {
        try {
            session.open().join();
            System.out.println("Session opened.");

            SiteDirectory siteDirectory = session.getAssembler().retrieveSiteDirectory();
            if (siteDirectory == null) {
                System.out.println("Failed to retrieve site directory.");
                return;
            }

            System.out.println("Retrieved site directory. Models available: " + siteDirectory.getModel().size());

            // Retrieve the first engineering model setup
            var engineeringModelSetup = siteDirectory.getModel().get(0);
            System.out.println("EngineeringModelSetup ID: " + engineeringModelSetup.getIid());

            // Get the engineering model ID from the setup
            var engineeringModelIid = engineeringModelSetup.getEngineeringModelIid();
            System.out.println("EngineeringModel ID: " + engineeringModelIid);

            // Create an EngineeringModel instance and read its data
            EngineeringModel engineeringModel = new EngineeringModel(engineeringModelIid, session.getAssembler().getCache(), new URI(baseUrl));
            session.read(engineeringModel).join();

            System.out.println("EngineeringModel data read.");
            System.out.println("EngineeringModel ID: " + engineeringModel.getIid());

            // Debugging the engineering model contents
            System.out.println("EngineeringModel Contents: " + engineeringModel.toString());

            // Ensure EngineeringModelSetup contains iterations
            var iterationSetups = engineeringModelSetup.getIterationSetup();
            System.out.println("Iteration Setups: " + iterationSetups.size());
            for (var iterationSetup : iterationSetups) {
                System.out.println("Iteration Setup ID: " + iterationSetup.getIid());
                System.out.println("Linked Iteration ID: " + iterationSetup.getIterationIid());
            }

            // Check for iterations
            List<Iteration> iterations = engineeringModel.getIteration();
            System.out.println("Number of iterations: " + iterations.size());
            for (Iteration iteration : iterations) {
                System.out.println("Iteration: " + iteration.toString());
                session.read(iteration).join();
                System.out.println("Iteration ID: " + iteration.getIid());
                System.out.println("Iteration size: " + iteration.getElement().size());

                if (iteration.getElement().size() > 0) {
                    for (ElementDefinition elementDefinition : iteration.getElement()) {
                        System.out.println("Reading element definition: " + elementDefinition.getIid());
                        session.read(elementDefinition).join();
                        TreeItem rootItem = new TreeItem(tree, SWT.NONE);
                        rootItem.setText(elementDefinition.getName());
                        addChildren(rootItem, elementDefinition);
                    }
                } else {
                    System.out.println("No top element found in the iteration.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close().join();
                System.out.println("Session closed.");
            } catch (Exception e) {
                System.out.println("Failed to close session: " + e.getMessage());
            }
        }
    }

    /**
     * Recursively adds children elements to the provided parent tree item.
     *
     * @param parentItem       the parent tree item
     * @param elementDefinition the element definition whose children are to be added
     */
    private void addChildren(TreeItem parentItem, ElementDefinition elementDefinition) throws TransactionException, InterruptedException, ExecutionException {
    	// Check if the element definition has contained elements
        if (elementDefinition.getContainedElement() != null) {
        	
        	// Get the contained elements
            List<ElementUsage> containedElements = elementDefinition.getContainedElement(); 
            
            // Iterate over each contained element usage
            System.out.println("Contained elements for " + elementDefinition.getName() + ": " + containedElements.size());
            
            for (ElementUsage usage : containedElements) {
                System.out.println("Reading element usage: " + usage.getIid());
                
             // Read the element usage data from the session
                session.read(usage).join(); 
                
             // Create a new tree item for the element usage
                TreeItem usageItem = new TreeItem(parentItem, SWT.NONE); 
                
             // Set the text of the tree item to the name of the usage
                usageItem.setText(usage.getName()); 
                
             // Check if the element usage has a linked element definition
                if (usage.getElementDefinition() != null) { 
                    System.out.println("Reading contained element definition: " + usage.getElementDefinition().getIid());
                    
                 // Read the linked element definition data
                    session.read(usage.getElementDefinition()).join(); 
                    
                 // Recursively add children elements to the current tree item
                    addChildren(usageItem, usage.getElementDefinition()); 
                }
            }
        }
    }

    /**
     * The main method to launch the COMET Data Viewer application.
     */
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("COMET Data Viewer");
        shell.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set shell size using constants
        shell.setLayout(new org.eclipse.swt.layout.FillLayout());

        Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

        try {
            CometSDKExample example = new CometSDKExample(BASE_URL, USERNAME, PASSWORD); // Use constants for URL, username, and password
            example.fetchData(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}