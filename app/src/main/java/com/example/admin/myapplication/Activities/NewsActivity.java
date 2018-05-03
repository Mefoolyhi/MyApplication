package com.example.admin.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.myapplication.Adapters.NewsAdapter;
import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.Parsers.NewsParcer;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.PostValue;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewsActivity extends BaseSpiceActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<PostValue> news = new ArrayList<>();
    RecyclerView rv;
    NewsParcer np = new NewsParcer();
    ProgressBar pb;
    TextView eror;


    int count = 0;

    String url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition="+ count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();



    String date(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());

        return date;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.rv_news);

        pb = findViewById(R.id.progressBar);
        eror = findViewById(R.id.eror_text);
        pb.setVisibility(View.VISIBLE);
        eror.setVisibility(View.INVISIBLE);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_news) {

            Intent intent = new Intent(NewsActivity.this, NewsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(NewsActivity.this, PickActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(NewsActivity.this, FavouritesActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        count = 0;
        url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition="+ count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();

        MyHttpRequest txtRequest = new MyHttpRequest(url);


        getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
                new TextRequestListener());

    }

    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            eror.setText("Проблемы с подключением к интернету");
            eror.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
        }


        @Override
        public void onRequestSuccess(final String result) {



            news.addAll(np.parse(result));
            rv.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
            NewsAdapter adapter = new NewsAdapter(news, NewsActivity.this);

            adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                @Override
                public void onBottomReached(int position) {
                    count += 15;
                    url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition="+ count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();
                    MyHttpRequest txtRequest = new MyHttpRequest(url);

                    getSpiceManager().execute(txtRequest, "txt" + count, DurationInMillis.ONE_MINUTE,
                            new TextRequestListener());





                }
            });
            rv.setAdapter(adapter);
            pb.setVisibility(View.INVISIBLE);

        }
    }

}
