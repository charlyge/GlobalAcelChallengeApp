package com.charlyge.android.globalacelchallengeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by DELL PC on 7/12/2018.
 */


public class networkUtils {

    public static String makeHttpUrlConnection(String url) throws IOException {

        URL nUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) nUrl.openConnection();
        urlConnection.setReadTimeout(30000 /* milliseconds */);
        urlConnection.setConnectTimeout(30000 /* milliseconds */);
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()){
                return scanner.next();

            }else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }

    }

    public static ArrayList<persons> extractFeaturesFromJson(String data) throws JSONException {
        ArrayList<persons> personsArrayList = new ArrayList<>();
        JSONObject root= new JSONObject(data);
        JSONArray jsonArray = root.getJSONArray("persons");
        for (int i=0;i<jsonArray.length();i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            String description = jsonObject.getString("description");
            String photo_thumb= jsonObject.getString("photo_thumb");
            String photo = jsonObject.getString("photo");
            personsArrayList.add(new persons(id,name,age,photo,photo_thumb,description));
        }

        return personsArrayList;
    }


}
