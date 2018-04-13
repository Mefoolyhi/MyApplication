package com.example.admin.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JParser {

    String LOG_TAG = "Parser";
    ArrayList<Event> data = new ArrayList<>();
    String nextUrl;


    public ArrayList<Event> getData() {
        return data;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    JParser(){

        data.clear();
    }





    public void parse(String jstring) {

        try

        {


        JSONObject dataJsonObj = new JSONObject(jstring);
        JSONArray friends = dataJsonObj.getJSONArray("results");
        nextUrl = dataJsonObj.getString("next"); //следующая страница для парсинга
            Log.e(LOG_TAG, String.valueOf(friends.length()));
        for (int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);


            String id = friend.getString("id");
            String title = friend.getString("title");
            Event e = new Event(title,Long.parseLong(id));
            data.add(e);
            Log.d(LOG_TAG, "id: " + id);
            Log.d(LOG_TAG, "title: " + title);
        }

    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
}
}
