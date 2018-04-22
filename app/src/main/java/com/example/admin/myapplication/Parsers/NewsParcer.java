package com.example.admin.myapplication.Parsers;

import android.util.Log;

import com.example.admin.myapplication.Utils.Event;
import com.example.admin.myapplication.Utils.PostValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsParcer {
    String LOG_TAG = "Parser";









    public ArrayList<PostValue> parse(String jstring) {

        ArrayList<PostValue> data = new ArrayList<>();

        try

        {


            JSONObject dataJsonObj = new JSONObject(jstring);
            JSONArray friends = dataJsonObj.getJSONArray("items");
            Log.e(LOG_TAG, String.valueOf(friends.length()));
            for (int i = 0; i < friends.length(); i++) {
                JSONObject friend = friends.getJSONObject(i);
                String title = friend.getString("title");
                String id = friend.getString("id");
                String imageurl = friend.getString("image");
                String date = friend.getString("datetime");
                PostValue pv = new PostValue(id,date,title,imageurl);
                data.add(pv);
            }

        } catch(
                Exception e)

        {
            e.printStackTrace();
        }
        return data;
    }
}


