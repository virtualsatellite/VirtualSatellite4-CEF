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
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Class to fetch comet data from a server and populate a SWT Tree.
 */
public class CometDataFetcher {

    private static final int HTTP_OK = 200;
    private static final String CONTAINED_ELEMENT_PATH = "/containedElement";
    private final String baseUrl;
    private final String username;
    private final String password;

    /**
     * Constructor to initialize the data fetcher with server details.
     *
     * @param url      the base URL of the server
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public CometDataFetcher(String url, String username, String password) {
        this.baseUrl = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Fetches data from the server and populates the given tree.
     *
     * @param tree the tree to populate with data
     */
    public void fetchData(Tree tree) {
        new Thread(() -> {
            try {
                String urlString = baseUrl + CONTAINED_ELEMENT_PATH;
                System.out.println("Fetching URL: " + urlString);
                String response = fetch(urlString);
                if (response != null) {
                    JsonArray jsonArray = (JsonArray) Jsoner.deserialize(response, new JsonArray());
                    Display.getDefault().asyncExec(() -> TreePopulator.populateTree(tree, jsonArray, baseUrl, username, password));
                } else {
                    showErrorMessage(tree, "Failed to fetch data from server.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorMessage(tree, "An error occurred: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Fetches data from the given URL.
     *
     * @param urlString the URL to fetch data from
     * @return the response data as a string, or null if an error occurred
     */
    private String fetch(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return content.toString();
            } else {
                System.out.println("Failed to fetch data: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorContent = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    errorContent.append(inputLine);
                }
                in.close();
                System.out.println("Response Body: " + errorContent.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Shows an error message in a message box.
     *
     * @param tree    the tree to use for getting the display
     * @param message the error message to show
     */
    private void showErrorMessage(Tree tree, String message) {
        Display.getDefault().asyncExec(() -> {
            MessageBox messageBox = new MessageBox(tree.getShell(), SWT.ICON_ERROR);
            messageBox.setMessage(message);
            messageBox.open();
        });
    }
}

