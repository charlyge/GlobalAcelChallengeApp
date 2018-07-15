package com.charlyge.android.globalacelchallengeapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by DELL PC on 7/12/2018.
 */


public class networkUtils {
    final static String BASE_URL = "https://intern-challenge.herokuapp.com/persons";


    final static String QUERY = "page";

    public static URL buildUrl(String SearchQuery){
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY,SearchQuery).build();
        URL url=null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHttpUrlConnection(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
