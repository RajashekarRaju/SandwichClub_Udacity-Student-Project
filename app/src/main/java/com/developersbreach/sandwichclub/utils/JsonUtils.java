package com.developersbreach.sandwichclub.utils;

import com.developersbreach.sandwichclub.list.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String OBJECT_SANDWICH_NAME = "name";
    private static final String STRING_SANDWICH_MAIN_NAME = "mainName";
    private static final String ARRAY_SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String STRING_SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String STRING_SANDWICH_DESCRIPTION = "description";
    private static final String STRING_SANDWICH_IMAGE_URL = "image";
    private static final String ARRAY_SANDWICH_INGREDIENTS = "ingredients";


    public static Sandwich fetchSandwichJsonData(String json) {

        Sandwich sandwich = null;
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONObject associated with the key called "name",
            JSONObject nameJsonObject = baseJsonResponse.getJSONObject(OBJECT_SANDWICH_NAME);

            // Extract the value for the key called "mainName"
            String sandwichName = null;
            if (nameJsonObject.has(STRING_SANDWICH_MAIN_NAME)) {
                sandwichName = nameJsonObject.getString(STRING_SANDWICH_MAIN_NAME);
            }

            // Extract the value for the key called "mainName"'
            List<String> alsoKnownAs = null;
            if (nameJsonObject.has(STRING_SANDWICH_MAIN_NAME)) {
                alsoKnownAs = jsonArrayValuesToList(nameJsonObject.getJSONArray(ARRAY_SANDWICH_ALSO_KNOWN_AS));
            }

            // Extract the value for the key called "original_title"
            String placeOfOrigin = null;
            if (baseJsonResponse.has(STRING_SANDWICH_PLACE_OF_ORIGIN)) {
                placeOfOrigin = baseJsonResponse.getString(STRING_SANDWICH_PLACE_OF_ORIGIN);
            }

            // Extract the value for the key called "original_title"
            String description = null;
            if (baseJsonResponse.has(STRING_SANDWICH_DESCRIPTION)) {
                description = baseJsonResponse.getString(STRING_SANDWICH_DESCRIPTION);
            }

            // Extract the value for the key called "original_title"
            String image = null;
            if (baseJsonResponse.has(STRING_SANDWICH_IMAGE_URL)) {
                image = baseJsonResponse.getString(STRING_SANDWICH_IMAGE_URL);
            }

            // Extract the value for the key called "mainName"
            List<String> ingredients = null;
            if (baseJsonResponse.has(ARRAY_SANDWICH_INGREDIENTS)) {
                ingredients = jsonArrayValuesToList(baseJsonResponse.getJSONArray(ARRAY_SANDWICH_INGREDIENTS));
            }

            sandwich = new Sandwich(sandwichName, image, alsoKnownAs, placeOfOrigin, description, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    /**
     * This method adds the JsonArray Values in to a List<String> and returns that List.
     *
     * @return myList
     */
    private static List<String> jsonArrayValuesToList(JSONArray jsonArray) {
        List<String> myList = new ArrayList<String>();

        // Adding each JSONArrayValues into myList.
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                myList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return myList;
    }
}
