package com.example.admin.myapplication.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.myapplication.Adapters.NewsAdapter;
import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import me.majiajie.swipeback.SwipeBackActivity;

public class NewActivity extends SwipeBackActivity {

    SimpleDraweeView image;
    TextView main, title,date;
    ProgressBar sp;

    String url = "http://5.189.85.227:8124/new/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        main = (TextView) findViewById(R.id.main);
        title = (TextView) findViewById(R.id.name);
        image = (SimpleDraweeView) findViewById(R.id.image);
        date = (TextView) findViewById(R.id.date);
        sp = (ProgressBar) findViewById(R.id.spinner);

        sp.setVisibility(View.VISIBLE);

        date.setText(getIntent().getStringExtra("date"));
        title.setText(getIntent().getStringExtra("title"));
        image.setImageURI(getIntent().getStringExtra("imageLink"));
        main.setText(getIntent().getStringExtra("shortText"));
        url += getIntent().getStringExtra("name");
        Log.e("url",url);





        new GetNewTask().execute();



    }
    private class GetNewTask extends AsyncTask<Void,Void,String>{


        @Override
        protected String doInBackground(Void... voids) {
            try{
            RestTemplate template = new RestTemplate();
            return template.getForObject(url,String.class);}
            catch (Exception e){
                Log.e("SERver",e.getMessage());
                e.printStackTrace();
                return "\n";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("Ok",s);
            try {
                JSONObject dataJsonObj = new JSONObject(s);
                main.append(dataJsonObj.getString("text"));
            }catch (Exception e){
                Log.e("Server is broken",e.getMessage());
            }
            sp.setVisibility(View.INVISIBLE);

        }
    }



}
