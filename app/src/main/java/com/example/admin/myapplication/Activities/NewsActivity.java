package com.example.admin.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.admin.myapplication.Holy.DataHelper;
import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.Parsers.NewsParcer;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;
import com.example.admin.myapplication.Utils.PostValue;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.majiajie.swipeback.utils.ActivityStack;

public class NewsActivity extends BaseSpiceActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<PostValue> news = new ArrayList<>();
    RecyclerView rv;
    NewsParcer np = new NewsParcer();
    ProgressBar pb;
    TextView eror;
    Bundle bundle = new Bundle();
    private SwipeRefreshLayout mSwipeRefresh;


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
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rv.scrollToPosition(0);
                    Log.e("TAP","TAPTAPTAP");
                }
                catch (Exception e){


                }
            }
        });
        setSupportActionBar(toolbar);




        rv = findViewById(R.id.rv_news);

        pb = findViewById(R.id.progressBar);
        eror = findViewById(R.id.eror_text);
        pb.setVisibility(View.VISIBLE);
        eror.setVisibility(View.INVISIBLE);

        mSwipeRefresh =  findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        //Настраиваем цветовую тему значка обновления, используя наши цвета:
//        mSwipeRefresh.setColorSchemeResources
//                (R.color.light_blue, R.color.middle_blue,R.color.deep_blue);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);


        SharedPreferences sp = getSharedPreferences("hasVisited",
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            ArrayList<Event> r = new ArrayList<>();

            try {
                DataHelper dh = new DataHelper(this);
                 r = dh.getFavourites();

            } catch (Exception e){
                Log.e("Begin",e.getMessage() + " " + String.valueOf(r.size()));
            }

            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();

        }


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
    Boolean used = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (!used) {
            count = 0;
            news.clear();
            url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition=" + count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();


            MyHttpRequest txtRequest = new MyHttpRequest(url);


            pb.setVisibility(View.VISIBLE);

            getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
                    new TextRequestListener());
            used = true;
        }

    }

    @Override
    public void onRefresh() {
        news.clear();
        count = 0;
        bundle = new Bundle();
        url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition="+ count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();

        MyHttpRequest txtRequest = new MyHttpRequest(url);


        pb.setVisibility(View.VISIBLE);


        eror.setVisibility(View.INVISIBLE);

        getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
                new TextRequestListener());

    }

    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            eror.setText("Проблемы с подключением к интернету");
            eror.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            mSwipeRefresh.setRefreshing(false);
        }


        @Override
        public void onRequestSuccess(final String result) {


            mSwipeRefresh.setRefreshing(false);

            news.addAll(np.parse(result));

            if (count != 0) {
                bundle.putParcelable("SAVED_LAYOUT_MANAGER", rv.getLayoutManager().onSaveInstanceState());
            }
            rv.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
            NewsAdapter adapter = new NewsAdapter(news, NewsActivity.this);

            adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                @Override
                public void onBottomReached(int position) {
                    count += 15;
                    url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition="+ count + "&previousItemsStep=15&rubric=11&tag=0&delimiterDate=" + date();
                    MyHttpRequest txtRequest = new MyHttpRequest(url);




                    eror.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.VISIBLE);
                    getSpiceManager().execute(txtRequest, "txt" + count, DurationInMillis.ONE_MINUTE,
                            new TextRequestListener());





                }
            });
            rv.setAdapter(adapter);
            rv.getLayoutManager().onRestoreInstanceState(( bundle).getParcelable("SAVED_LAYOUT_MANAGER"));
            pb.setVisibility(View.INVISIBLE);

        }
    }

}
