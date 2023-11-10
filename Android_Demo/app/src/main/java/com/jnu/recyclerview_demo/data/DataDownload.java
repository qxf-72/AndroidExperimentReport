package com.jnu.recyclerview_demo.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataDownload {

    public String download(String urls) {
        StringBuilder stringBuilder = new StringBuilder();
        URL url = null;
        try {
            url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                inputStreamReader.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public ArrayList<MyLocation> parseJsonObjects(String content) {
        ArrayList<MyLocation> locations = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(content);
            JSONArray shopsArray = jsonObject.getJSONArray("shops");

            for (int i = 0; i < shopsArray.length(); i++) {
                JSONObject shop = shopsArray.getJSONObject(i);
                String name = shop.getString("name");
                double latitude = shop.getDouble("latitude");
                double longitude = shop.getDouble("longitude");
                String memo = shop.getString("memo");

                MyLocation location = new MyLocation(name, latitude, longitude, memo);
                locations.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locations;
    }


}
