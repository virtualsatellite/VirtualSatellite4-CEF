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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ISharedImages;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Utility class to populate a SWT Tree widget with data fetched from a server.
 */
public class TreePopulator {

    private static final String ROOT_ITEM_TEXT = "System";
    private static final int HTTP_OK = 200;
    private static Image nodeImage;
    private static String baseUrl;
    private static String username;
    private static String password;

    /**
     * Private constructor to prevent instantiation.
     */
    private TreePopulator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Initializes the images used for the tree items.
     */
    private static void initImages() {
        nodeImage = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
    }

    /**
     * Populates the given tree with data from the specified JSON array.
     *
     * @param tree     the tree to populate
     * @param jsonArray the JSON array containing the data
     * @param url      the base URL for fetching additional data
     * @param user     the username for authentication
     * @param pass     the password for authentication
     */
    public static void populateTree(Tree tree, JsonArray jsonArray, String url, String user, String pass) {
        baseUrl = url;
        username = user;
        password = pass;
        initImages();
        tree.removeAll();

        // Create root item
        TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
        rootItem.setText(ROOT_ITEM_TEXT);
        rootItem.setImage(nodeImage);

        // Create tree items from JSON array
        for (Object obj : jsonArray) {
            JsonObject jsonObject = (JsonObject) obj;
            TreeItem item = createTreeItem(rootItem, jsonObject);
            if (item != null) {
                addChildren(item, jsonObject);
            }
        }

        // Expand all items
        expandAll(tree.getItems());
    }

    /**
     * Creates a tree item from a JSON object and adds it to the parent item.
     *
     * @param parentItem the parent tree item
     * @param jsonObject the JSON object containing the data
     * @return the created tree item, or null if the JSON object is invalid
     */
    private static TreeItem createTreeItem(TreeItem parentItem, JsonObject jsonObject) {
        String name = (String) jsonObject.get("name");
        if (name == null) {
            System.err.println("Warning: 'name' key is missing or null in JSON object: " + jsonObject.toJson());
            return null; // Skip this item
        }
        TreeItem item = new TreeItem(parentItem, SWT.CHECK);
        item.setText(name);
        item.setImage(nodeImage);
        return item;
    }

    /**
     * Adds children to the parent item based on the JSON object.
     *
     * @param parentItem the parent tree item
     * @param parentJson the JSON object containing the data
     */
    private static void addChildren(TreeItem parentItem, JsonObject parentJson) {
        try {
            if (parentJson.containsKey("containedElement")) {
                JsonArray children = (JsonArray) parentJson.get("containedElement");
                for (Object obj : children) {
                    String childId = (String) obj;
                    String childUrl = baseUrl + "/containedElement/" + childId;
                    System.out.println("Fetching child URL: " + childUrl);
                    JsonObject childJson = fetchData(childUrl);
                    if (childJson != null && !childJson.isEmpty()) {
                        TreeItem childItem = createTreeItem(parentItem, childJson);
                        if (childItem != null) {
                            addChildren(childItem, childJson);
                        }
                    } else {
                        System.out.println("Fetched data is empty for child URL: " + childUrl);
                    }
                }
            } else {
                System.out.println("No 'containedElement' key found in JSON object: " + parentJson.toJson());
            }

            if (parentJson.containsKey("elementDefinition")) {
                String elementDefinitionId = (String) parentJson.get("elementDefinition");
                String elementDefinitionUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/element/")) + "/element/" + elementDefinitionId + "/containedElement";
                System.out.println("Fetching element definition URL: " + elementDefinitionUrl);
                JsonArray elementDefinitionArray = fetchDataArray(elementDefinitionUrl);
                if (elementDefinitionArray != null && !elementDefinitionArray.isEmpty()) {
                    for (Object obj : elementDefinitionArray) {
                        JsonObject elementDefinitionJson = (JsonObject) obj;
                        TreeItem elementDefinitionItem = createTreeItem(parentItem, elementDefinitionJson);
                        if (elementDefinitionItem != null) {
                            addChildren(elementDefinitionItem, elementDefinitionJson);
                        }
                    }
                } else {
                    System.out.println("Failed to fetch element definition data for: " + elementDefinitionId);
                }
            } else {
                System.out.println("No 'elementDefinition' key found in JSON object: " + parentJson.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches data from the specified URL and returns it as a JSON object.
     *
     * @param urlString the URL to fetch data from
     * @return the fetched JSON object, or null if an error occurred
     */
    private static JsonObject fetchData(String urlString) {
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
                JsonObject jsonResponse = (JsonObject) Jsoner.deserialize(content.toString(), new JsonObject());
                System.out.println("Fetched data: " + jsonResponse.toJson());
                return jsonResponse;
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
     * Fetches data from the specified URL and returns it as a JSON array.
     *
     * @param urlString the URL to fetch data from
     * @return the fetched JSON array, or null if an error occurred
     */
    private static JsonArray fetchDataArray(String urlString) {
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
                JsonArray jsonResponse = (JsonArray) Jsoner.deserialize(content.toString(), new JsonArray());
                System.out.println("Fetched data array: " + jsonResponse.toJson());
                return jsonResponse;
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
     * Expands all tree items recursively.
     *
     * @param items the array of tree items to expand
     */
    private static void expandAll(TreeItem[] items) {
        for (TreeItem item : items) {
            item.setExpanded(true);
            if (item.getItemCount() > 0) {
                expandAll(item.getItems());
            }
        }
    }

    /**
     * Disposes of resources held by this class.
     */
    public static void dispose() {
        if (nodeImage != null) {
            nodeImage.dispose();
        }
    }
}
