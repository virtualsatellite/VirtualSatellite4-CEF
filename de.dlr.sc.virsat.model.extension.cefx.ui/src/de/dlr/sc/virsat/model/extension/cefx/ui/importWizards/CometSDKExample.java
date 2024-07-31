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
import cdp4common.sitedirectorydata.DomainOfExpertise;
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

public class CometSDKExample {
    private final Session session;
    private final String baseUrl;

    public CometSDKExample(String baseUrl, String username, String password) throws Exception {
        this.baseUrl = baseUrl;
        CdpServicesDal dal = new CdpServicesDal();
        Credentials credentials = new Credentials(username, password, new URI(baseUrl), null);
        session = new SessionImpl(dal, credentials);
    }

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

            var engineeringModelIid = siteDirectory.getModel().get(0).getEngineeringModelIid();
            var iterationIid = siteDirectory.getModel().get(0).getIterationSetup().get(0).getIterationIid();
            var domainOfExpertiseIid = siteDirectory.getModel().get(0).getActiveDomain().get(0).getIid();

            var model = new EngineeringModel(engineeringModelIid, this.session.getAssembler().getCache(),new URI(baseUrl));
            var iteration = new Iteration(iterationIid, this.session.getAssembler().getCache() ,new URI(baseUrl));
            iteration.setContainer(model);
            
            var domainOfExpertise = new DomainOfExpertise(domainOfExpertiseIid, this.session.getAssembler().getCache(),
            		new URI(baseUrl));
            //var modelSetup = siteDirectory.getModel().get(0);
           // System.out.println("Model setup ID: " + modelSetup.getIid());

            //var iterationSetupList = modelSetup.getIterationSetup();
          /*  if (iterationSetupList == null || iterationSetupList.isEmpty()) {
                System.out.println("No iteration setup found in model setup.");
                return;
            }*/

            //var iterationSetup = iterationSetupList.get(0);
           // var iterationIid = iterationSetup.getIterationIid();
           // System.out.println("Iteration setup ID: " + iterationSetup.getIid());
           // System.out.println("modelSetup.getIterationSetup(): " + modelSetup.getIterationSetup());

            //Opening the session again
            this.session.read(iteration, domainOfExpertise).join();
            // Fetching the first open iteration
            var iterationOptional = session.getOpenIterations().keySet().stream().findFirst();
          System.out.println("iterationOptional " + iterationOptional);
            if (!iterationOptional.isPresent()) {
                System.out.println("No open iterations found.");
                return;
            }

            Iteration openIteration = iterationOptional.get();
            System.out.println(openIteration);
          //  var domainOfExpertise = session.getOpenIterations().get(iteration).getLeft();

            // Reading the iteration along with its domain of expertise
            session.read(iteration, domainOfExpertise).join();

            System.out.println("Iteration data read.");
            System.out.println("Iteration ID: " + iteration.getIid());
            System.out.println("Iteration size: " + iteration.getElement().size());

            if (iteration.getElement().size() > 0) {
                for (ElementDefinition elementDefinition : iteration.getElement()) {
                    System.out.println("Reading element definition: " + elementDefinition.getIid());
                    try {
                        session.read(elementDefinition).join();
                    } catch (Exception e) {
                        System.out.println("Error during reading element definition: " + e.getMessage());
                        e.printStackTrace();
                    }
                    TreeItem rootItem = new TreeItem(tree, SWT.NONE);
                    rootItem.setText(elementDefinition.getName());
                    addChildren(rootItem, elementDefinition);
                }
            } else {
                System.out.println("No top element found in the iteration.");
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

    private void addChildren(TreeItem parentItem, ElementDefinition elementDefinition) throws TransactionException, InterruptedException, ExecutionException {
        if (elementDefinition.getContainedElement() != null) {
            List<ElementUsage> containedElements = elementDefinition.getContainedElement();
            System.out.println("Contained elements for " + elementDefinition.getName() + ": " + containedElements.size());
            for (ElementUsage usage : containedElements) {
                System.out.println("Reading element usage: " + usage.getIid());
                try {
                    session.read(usage).join();
                } catch (Exception e) {
                    System.out.println("Error during reading element usage: " + e.getMessage());
                    e.printStackTrace();
                }
                TreeItem usageItem = new TreeItem(parentItem, SWT.NONE);
                usageItem.setText(usage.getName());
                if (usage.getElementDefinition() != null) {
                    System.out.println("Reading contained element definition: " + usage.getElementDefinition().getIid());
                    try {
                        session.read(usage.getElementDefinition()).join();
                    } catch (Exception e) {
                        System.out.println("Error during reading contained element definition: " + e.getMessage());
                        e.printStackTrace();
                    }
                    addChildren(usageItem, usage.getElementDefinition());
                }
            }
        }
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("COMET Data Viewer");
        shell.setSize(400, 400);
        shell.setLayout(new org.eclipse.swt.layout.FillLayout());

        Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

        try {
            String url = "http://sc-010266l.intra.dlr.de:5000/";
            String username = "admin";
            String password = "pass";

            CometSDKExample example = new CometSDKExample(url, username, password);
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


