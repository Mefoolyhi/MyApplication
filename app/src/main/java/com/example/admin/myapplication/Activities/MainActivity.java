package com.example.admin.myapplication.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.myapplication.JParser;
import com.example.admin.myapplication.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainActivity extends BaseSpiceActivity {

    private MyHttpRequest txtRequest;
    JParser jp = new JParser();
    //String url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
    String url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
//String url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition=0&previousItemsStep=15&rubric=11&tag=0&delimiterDate=2018-04-19";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtRequest = new MyHttpRequest(url);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
                new TextRequestListener());
    }

    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(final String result) {
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();

            Log.e("PArs",result);
            //jp.parse(result);

        }
    }
}