package com.example.admin.myapplication.Holy;

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
           }

        }
    }


