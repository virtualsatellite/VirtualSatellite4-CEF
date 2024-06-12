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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * This class demonstrates fetching and displaying data from a RESTful service using SWT for the GUI.
 */
public class CometExample {
    private final String baseUrl;
    private final String username;
    private final String password;

    // Constants for authentication details
    private static final String BASE_URL = "http://sc-010266l.intra.dlr.de:5000/EngineeringModel/33edade4-698c-4a91-8413-b9d192214c21/iteration/54bd5c12-3f03-420a-8ac1-07cad2de5bf4/element/c172341f-33b6-4880-bb82-fc6f1b8115c5";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pass";

    /**
     * Constructs a CometExample instance with the given base URL, username, and password.
     *
     * @param baseUrl  the base URL of the service
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public CometExample(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Fetches data from the service and populates the provided tree with the retrieved data.
     *
     * @param tree the SWT tree to be populated with data
     */
    public void fetchData(Tree tree) {
        try {
            String response = fetch(baseUrl);
            if (response != null) {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject system = jsonArray.getJSONObject(0);
                TreeItem rootItem = new TreeItem(tree, 0);
                rootItem.setText(system.getString("name"));
                addChildren(rootItem, system);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursively adds children elements to the provided parent tree item based on the JSON data.
     *
     * @param parentItem the parent tree item
     * @param parentJson the parent JSON object containing child elements
     */
    private void addChildren(TreeItem parentItem, JSONObject parentJson) {
        try {
            // Process contained elements
            if (parentJson.has("containedElement")) { // Check if the JSON object has contained elements
                JSONArray children = parentJson.getJSONArray("containedElement"); // Get the contained elements array
                for (int i = 0; i < children.length(); i++) { // Iterate over each contained element
                    String childId = children.getString(i); // Get the child ID
                    String childUrl = baseUrl + "/containedElement/" + childId; // Construct the child URL
                    System.out.println("Fetching child URL: " + childUrl);
                    String childResponse = fetch(childUrl); // Fetch the child data
                    if (childResponse != null) { // If the child data is fetched successfully
                        System.out.println("Child Response: " + childResponse);
                        if (childResponse.startsWith("[")) { // Check if the response is an array
                            JSONArray childArray = new JSONArray(childResponse); // Parse the child response as JSON array
                            for (int j = 0; j < childArray.length(); j++) { // Iterate over each element in the child array
                                JSONObject childJson = childArray.getJSONObject(j); // Get the child JSON object
                                TreeItem childItem = new TreeItem(parentItem, 0); // Create a new tree item for the child
                                childItem.setText(childJson.getString("name")); // Set the text of the tree item to the name of the child
                                addChildren(childItem, childJson); // Recursively add children elements to the current tree item
                            }
                        } else { // If the response is a single JSON object
                            JSONObject childJson = new JSONObject(childResponse); // Parse the child response as JSON object
                            TreeItem childItem = new TreeItem(parentItem, 0); // Create a new tree item for the child
                            childItem.setText(childJson.getString("name")); // Set the text of the tree item to the name of the child
                            addChildren(childItem, childJson); // Recursively add children elements to the current tree item
                        }
                    } else {
                        System.out.println("Failed to fetch child data for: " + childId);
                    }
                }
            }

            // Process element definitions if present
            if (parentJson.has("elementDefinition")) { // Check if the JSON object has an element definition
                String elementDefinitionId = parentJson.getString("elementDefinition"); // Get the element definition ID
                String elementDefinitionUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/element/")) + "/element/" + elementDefinitionId + "/containedElement"; // Construct the element definition URL
                System.out.println("Fetching element definition URL: " + elementDefinitionUrl);
                String elementDefinitionResponse = fetch(elementDefinitionUrl); // Fetch the element definition data
                if (elementDefinitionResponse != null) { // If the element definition data is fetched successfully
                    JSONArray elementDefinitionArray = new JSONArray(elementDefinitionResponse); // Parse the element definition response as JSON array
                    for (int i = 0; i < elementDefinitionArray.length(); i++) { // Iterate over each element in the array
                        JSONObject elementDefinitionJson = elementDefinitionArray.getJSONObject(i); // Get the element definition JSON object
                        TreeItem elementDefinitionItem = new TreeItem(parentItem, 0); // Create a new tree item for the element definition
                        elementDefinitionItem.setText(elementDefinitionJson.getString("name")); // Set the text of the tree item to the name of the element definition
                        addChildren(elementDefinitionItem, elementDefinitionJson); // Recursively add children elements to the current tree item
                    }
                } else {
                    System.out.println("Failed to fetch element definition data for: " + elementDefinitionId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches data from the given URL using HTTP GET request with basic authentication.
     *
     * @param urlString the URL to fetch data from
     * @return the response data as a string, or null if an error occurs
     */
    private String fetch(String urlString) {
        try {
            URL url = new URL(urlString); // Create a URL object from the string
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Open an HTTP connection
            connection.setRequestMethod("GET"); // Set the request method to GET
            String auth = username + ":" + password; // Create the authentication string
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes()); // Encode the authentication string
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth); // Set the Authorization header

            int responseCode = connection.getResponseCode(); // Get the response code
            if (responseCode == 200) { // If the response code is OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Create a BufferedReader to read the response
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) { // Read the response line by line
                    content.append(inputLine); // Append each line to the content
                }
                in.close(); // Close the BufferedReader
                return content.toString(); // Return the response content as a string
            } else {
                System.out.println("Failed to fetch data: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream())); // Create a BufferedReader to read the error response
                String inputLine;
                StringBuilder errorContent = new StringBuilder();
                while ((inputLine = in.readLine()) != null) { // Read the error response line by line
                    errorContent.append(inputLine); // Append each line to the error content
                }
                in.close(); // Close the BufferedReader
                System.out.println("Response Body: " + errorContent.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The main method to launch the COMET Data Viewer application.
     */
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("COMET Data Viewer");
        shell.setSize(400, 300); // Set shell size
        shell.setLayout(new org.eclipse.swt.layout.FillLayout());

        Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

        CometExample example = new CometExample(BASE_URL, USERNAME, PASSWORD); // Use constants for URL, username, and password
        example.fetchData(tree);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}