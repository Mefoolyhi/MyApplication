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


            String title = friend.getJSONObject("event").getString("title");
            String id = friend.getJSONObject("event").getString("id");
            String rating = friend.getJSONObject("event").getString("contentRating");
            Double userRating = friend.getJSONObject("event").getJSONObject("userRating").getJSONObject("overall").getDouble("value");

            JSONArray types = friend.getJSONObject("event").getJSONArray("tags");
            String tags = "";
            for (int j = 0; j < types.length(); j++) {
                JSONObject type = types.getJSONObject(j);
                tags+= type.getString("name") + " ";
            }

            String toBuy = "https://afisha.yandex.ru" +  friend.getJSONObject("event").getString("url") + "?schedule-filter-tickets=true";
            String imageurl = friend.getJSONObject("event").getJSONObject("image").getJSONObject("headingPrimaryS").getString("url");
            JSONArray prices = friend.getJSONObject("event").getJSONArray("tickets");
            String pr;
            if (prices.length() == 0){
                pr = "Билетов нет";
            }
            else {
                JSONObject price = prices.getJSONObject(0).getJSONObject("price");
                pr = price.getString("min").substring(0, price.getString("min").length() - 2) + "-" + price.getString("max").substring(0, price.getString("max").length() - 2) + " рублей";
            }

                String OP = friend.getJSONObject("scheduleInfo").getString("onlyPlace");
                if (!OP.equals("null")) {

                    String place = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getString("title");

                    String address = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getString("address");
                    Double longitude = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getJSONObject("coordinates").getDouble("longitude");
                    Double latitude = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getJSONObject("coordinates").getDouble("latitude");


                        String placeImgUrl = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getString("logo");

                        if (placeImgUrl.equals("null")) {
                            placeImgUrl = "http://www.liceoitaliano.net/wp-content/uploads/2014/07/teatro-150x150.png";

                        }
                        else {
                            placeImgUrl = friend.getJSONObject("scheduleInfo").getJSONObject("onlyPlace").getJSONObject("logo").getJSONObject("touchPlaceCover").getString("url");
                        }
                        String dates = friend.getJSONObject("scheduleInfo").getJSONObject("preview").getString("text");

                        Event e = new Event(longitude, latitude, userRating, title, rating, pr, imageurl, dates, place, toBuy, address, placeImgUrl, tags,id);
                        data.add(e);


                }

            }


    } catch(
    Exception e)

    {
       Log.e("Parse",e.getMessage());
       e.printStackTrace();


    }
    Log.e("Pars", String.valueOf(data.size()));
    return data;
}
}
