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
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterSwitchKind;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4servicesdal.CdpServicesDal;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * The CometDataFetcher class is responsible for fetching and populating 
 * data from a COMET system into a SWT  Tree widget.
 */
public class CometDataFetcher {

    private final Credentials credentials;
    private final Session session;
    private final URI uri;

    /**
     * Constructs a new CometDataFetcher instance.
     * 
     * @param url      The URL of the COMET system.
     * @param username The username for authentication.
     * @param password The password for authentication.
     */
    public CometDataFetcher(String url, String username, String password) {
        this.uri = URI.create(url);
        CdpServicesDal dal = new CdpServicesDal();
        this.credentials = new Credentials(username, password, this.uri, null);
        this.session = new SessionImpl(dal, this.credentials);
    }

    /**
     * Initiates the process to fetch data and populate the provided Tree.
     */
    public void fetchData(Tree tree) {
        new Thread(() -> {
            try {
                openSession();
                Display.getDefault().asyncExec(() -> populateTreeWithIteration(tree));
            } catch (IOException e) {
                // Handle I/O exceptions, such as network issues
                handleException(e, tree);
            } catch (GeneralSecurityException e) {
                // Handle security-related exceptions, such as authentication failures
                handleException(e, tree);
            } catch (RuntimeException e) {
                // Handle any other runtime exceptions that might occur
                handleException(e, tree);
            }
        }).start();
    }

    /**
     * Opens the session for communication with the COMET system.
     */
    public void openSession() throws IOException, GeneralSecurityException {
        session.open().join();
    }

    /**
     * Populates the provided Tree with data from the selected iteration 
     * of the engineering model.
     * 
     * @param tree The SWT Tree widget to populate.
     */
    private void populateTreeWithIteration(Tree tree) {
        var siteDirectory = session.getAssembler().retrieveSiteDirectory();
        var model = siteDirectory.getModel().get(0); 

        var iterationIid = model.getIterationSetup().get(0).getIterationIid();
        var domainOfExpertiseIid = model.getActiveDomain().get(0).getIid();

        EngineeringModel engineeringModel = new EngineeringModel(model.getEngineeringModelIid(), session.getAssembler().getCache(), uri);
        Iteration iteration = new Iteration(iterationIid, session.getAssembler().getCache(), uri);
        iteration.setContainer(engineeringModel);

        DomainOfExpertise domainOfExpertise = new DomainOfExpertise(domainOfExpertiseIid, session.getAssembler().getCache(), uri);

        session.read(iteration, domainOfExpertise).join();

        Optional<Iteration> openIteration = session.getOpenIterations().keySet().stream().findFirst();

        if (openIteration.isPresent()) {
            openIteration.get().getElement().stream()
                .filter(this::isTopLevelElement)  // Filter for the top-level element
                .findFirst()
                .ifPresent(element -> {
                    TreeItem topLevelItem = new TreeItem(tree, SWT.NONE);
                    topLevelItem.setText(element.getName());
                    addChildren(topLevelItem, element);
                    topLevelItem.setExpanded(true);
                });
        } else {
            showErrorMessage(tree, "No open iteration found.");
        }
    }

    /**
     * Custom method to determine if an ElementDefinition is a top-level element.
     * A top-level element is typically not referenced by any other elements.
     */
    private boolean isTopLevelElement(ElementDefinition element) {
        return element.referencingElementUsages().isEmpty();
    }




    /**
     * Recursively adds child elements of a parent  ElementDefinition to a TreeItem.
     */
    private void addChildren(TreeItem parentItem, ElementDefinition parentElement) {
        parentElement.getParameter().stream()
                .filter(parameter -> "mass".equalsIgnoreCase(parameter.getParameterType().getName()) 
                		|| "mass margin".equalsIgnoreCase(parameter.getParameterType().getName()))
                .forEach(parameter -> {
                    String value = fetchParameterValue(parameter);
                    if (value != null) { // Ensure we only add valid manual values
                        TreeItem item = new TreeItem(parentItem, SWT.NONE);
                        item.setText(parameter.getParameterType().getName() + ": " + value + " kg");
                    }
                });

        for (ElementUsage usage : parentElement.getContainedElement()) {
            TreeItem usageItem = new TreeItem(parentItem, SWT.NONE);
            usageItem.setText(usage.getName());
            addChildren(usageItem, usage.getElementDefinition());
        }
    }

    /**
     * Fetches the value of the specified Parameter.
     * 
     */
    private String fetchParameterValue(Parameter parameter) {
    	try {
    		ParameterValueSet valueSet = parameter.getValueSet().get(0);

    		if (valueSet.getValueSwitch() == ParameterSwitchKind.MANUAL) {
    			return valueSet.getManual().get(0); 
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    /**
     * Displays an error message in a MessageBox.
     */
    private void showErrorMessage(Tree tree, String message) {
        Display.getDefault().asyncExec(() -> {
            MessageBox messageBox = new MessageBox(tree.getShell(), SWT.ICON_ERROR);
            messageBox.setMessage(message);
            messageBox.open();
        });
    }

    /**
     * Handles exceptions by printing the stack trace and displaying an error message.
     */
    private void handleException(Exception e, Tree tree) {
        e.printStackTrace();
        showErrorMessage(tree, "An error occurred: " +  e.getMessage());
    }
}