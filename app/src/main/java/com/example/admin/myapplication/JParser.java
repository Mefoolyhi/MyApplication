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



    public ArrayList<Event> getData() {
        return data;
    }



    JParser(){

        data.clear();
    }





    public void parse(String jstring) {

        try

        {


        JSONObject dataJsonObj = new JSONObject(jstring);
        JSONArray friends = dataJsonObj.getJSONArray("data");
            Log.e(LOG_TAG, String.valueOf(friends.length()));
        for (int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);


            String id = friend.getJSONObject("event").getString("id");
            String title = friend.getJSONObject("event").getString("title");
            String url = friend.getJSONObject("event").getString("url");
            String rating = friend.getJSONObject("event").getString("contentRating");
            String imageurl = friend.getJSONObject("event").getJSONObject("image").getJSONObject("eventCover").getString("url");
            JSONArray prices = friend.getJSONObject("event").getJSONArray("ticket");
            JSONObject price = prices.getJSONObject(0).getJSONObject("price");
            String pr = price.getString("min").substring(0,price.getString("min").length() - 2) + "-" + price.getString("max").substring(0,price.getString("max").length() - 2);
            price = friend.getJSONObject("event").getJSONObject("scheduleInfo");
            String place = price.getString("placePreview");
            String dates = price.getJSONObject("preview").getString("dates");

            Event e = new Event(title,id,rating,pr,imageurl,dates,place,url);
            data.add(e);
            Log.d(LOG_TAG, id + " " + title + " " + rating +
                    " "+ pr+" "+imageurl+" " + dates+" "+place+ " " + url);
        }

    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
}
}
