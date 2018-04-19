package com.example.admin.myapplication;

import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpRequest extends GoogleHttpClientSpiceRequest<String> {

    private String baseUrl;
    String LOG_TAG = "MyHttpRequest";

    public MyHttpRequest(String url) {
        super(String.class);
        this.baseUrl = url;
    }

    @Override
    public String loadDataFromNetwork() throws IOException {

        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory factory = transport.createRequestFactory(new MyHttpRequestInitializer());
        HttpRequest request = factory.buildGetRequest(new GenericUrl(baseUrl));



        return request.execute().parseAsString();
    }


    private class MyHttpRequestInitializer implements HttpRequestInitializer {

        MyHttpRequestInitializer() { }

        public void initialize(HttpRequest request) throws IOException {

//            HttpURLConnection urlConnection;
//            URL url = new URL(baseUrl);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line);
//            }
//
//            String resultJson = buffer.toString();
//
//            try {
//                JSONObject dataJsonObj = new JSONObject(resultJson);
//                JSONArray friends = dataJsonObj.getJSONArray("results");
//                for (int i = 0; i < friends.length(); i++) {
//                    JSONObject friend = friends.getJSONObject(i);
//
//
//                    String id = friend.getString("id");
//                    String title = friend.getString("title");
//                    Log.d(LOG_TAG, "id: " + id);
//                    Log.d(LOG_TAG, "title: " + title);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }

}
