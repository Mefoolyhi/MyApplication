package com.example.admin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

public class MainActivity extends BaseSpiceActivity {

    private MyHttpRequest txtRequest;
    JParser jp = new JParser();
    String url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
    //https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&offset=0&hasMixed=0


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
            jp.parse(result);

        }
    }
}