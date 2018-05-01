package com.example.admin.myapplication.Parsers;

import android.util.Log;

import com.example.admin.myapplication.Utils.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JParser {

    String LOG_TAG = "Parser";





    public ArrayList<Event> parse(String jstring) {

        ArrayList<Event> data = new ArrayList<>();
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
            JSONArray prices = friend.getJSONObject("event").getJSONArray("tickets");
            JSONObject price = prices.getJSONObject(0).getJSONObject("price");
            String pr = price.getString("min").substring(0,price.getString("min").length() - 2) + "-" + price.getString("max").substring(0,price.getString("max").length() - 2);
            price = friend.getJSONObject("scheduleInfo");
            String place = price.getString("placePreview");
            String dates = price.getJSONObject("preview").getString("text");

            Event e = new Event(title,id,rating,pr,imageurl,dates,place,url);
            data.add(e);
        }

    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
    return data;
}
}
