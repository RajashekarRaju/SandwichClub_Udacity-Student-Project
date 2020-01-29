package com.developersbreach.sandwichclub.utils;

import com.developersbreach.sandwichclub.list.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Constants containing object, array and string values.
    private static final String OBJECT_SANDWICH_NAME = "name";
    private static final String STRING_SANDWICH_MAIN_NAME = "mainName";
    private static final String ARRAY_SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String STRING_SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String STRING_SANDWICH_DESCRIPTION = "description";
    private static final String STRING_SANDWICH_IMAGE_URL = "image";
    private static final String ARRAY_SANDWICH_INGREDIENTS = "ingredients";

    /**
     * Return a list of {@link Sandwich} objects that has been built up from parsing a JSON response.
     */
    public static Sandwich fetchSandwichJsonData(String json) {

        // Create an empty sandwich object to add values frm response
        Sandwich sandwich = null;

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONObject associated with the key called "name",
            JSONObject nameJsonObject = baseJsonResponse.getJSONObject(OBJECT_SANDWICH_NAME);

            // Extract value for the key "mainName"
            String sandwichName = null;
            if (nameJsonObject.has(STRING_SANDWICH_MAIN_NAME)) {
                sandwichName = nameJsonObject.getString(STRING_SANDWICH_MAIN_NAME);
            }

            // Extract value for the key called "alsoKnownAs"'
            List<String> alsoKnownAs = null;
            if (nameJsonObject.has(ARRAY_SANDWICH_ALSO_KNOWN_AS)) {
                alsoKnownAs = convertJSONArrayToList(nameJsonObject.getJSONArray(ARRAY_SANDWICH_ALSO_KNOWN_AS));
            }

            // Extract value for the key called "placeOfOrigin"
            String placeOfOrigin = null;
            if (baseJsonResponse.has(STRING_SANDWICH_PLACE_OF_ORIGIN)) {
                placeOfOrigin = baseJsonResponse.getString(STRING_SANDWICH_PLACE_OF_ORIGIN);
            }

            // Extract value for the key called "description"
            String description = null;
            if (baseJsonResponse.has(STRING_SANDWICH_DESCRIPTION)) {
                description = baseJsonResponse.getString(STRING_SANDWICH_DESCRIPTION);
            }

            // Extract value for the key called "image"
            String image = null;
            if (baseJsonResponse.has(STRING_SANDWICH_IMAGE_URL)) {
                image = baseJsonResponse.getString(STRING_SANDWICH_IMAGE_URL);
            }

            // Extract value for the key called "ingredients"
            List<String> ingredients = null;
            if (baseJsonResponse.has(ARRAY_SANDWICH_INGREDIENTS)) {
                ingredients = convertJSONArrayToList(baseJsonResponse.getJSONArray(ARRAY_SANDWICH_INGREDIENTS));
            }

            sandwich = new Sandwich(sandwichName, image, alsoKnownAs, placeOfOrigin, description, ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        // Return the objects
        return sandwich;
    }

    /**
     * Convert the given values from JSONArray to list of strings from JSON
     *
     * @return myList
     */
    private static List<String> convertJSONArrayToList(JSONArray jsonArray) {
        // Create a new empty array list to add values from JSONArray
        List<String> stringList = new ArrayList<>();

        // Loop through each element to add to empty list.
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }
}
