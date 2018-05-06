package com.example.admin.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapters.EventsAdapter;
import com.example.admin.myapplication.Adapters.NewsAdapter;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.Parsers.JParser;
import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PickActivity extends BaseSpiceActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    JParser jp = new JParser();

    RecyclerView rv;
    ProgressBar pb;
    private SwipeRefreshLayout mSwipeRefresh;
    TextView filters,error;
    ImageButton filt;
    ArrayList<Event> e = new ArrayList<>();
    Bundle bundle = new Bundle();

    //String url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
     String url;
    boolean used = false;
//String url = "http://www.justmedia.ru/news/default/getAjaxPreviousItems/?previousItemsPosition=0&previousItemsStep=15&rubric=11&tag=0&delimiterDate=2018-04-19";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.rv_news);
        filt = findViewById(R.id.filters);
        filters = findViewById(R.id.filters_text);
        error = findViewById(R.id.error_text);
        pb = findViewById(R.id.progressBar);


        pb.setVisibility(View.VISIBLE);
        error.setVisibility(View.INVISIBLE);

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

        navigationView.getMenu().getItem(1).setChecked(true);

        filt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickActivity.this,FiltersActivity.class);
                startActivityForResult(intent,200);
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        url = data.getStringExtra("url");
        count = 0;
        String first_date = data.getStringExtra("first_date");
        String second_date = data.getStringExtra("second_date");
        String name = data.getStringExtra("name");
        filters.setText(name+", "+date(first_date)+" - "+date(second_date));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        int diff = 1;
        try {
            Date date2 = dateFormat.parse(second_date);
            Date date1 = dateFormat.parse(first_date);
             diff += (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));

        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        String s;
        if (!first_date.equals("Нажмите, чтобы выбрать дату") && !second_date.equals("Нажмите, чтобы выбрать дату")) {
            url += "&date=" + first_date.substring(6, 10) + "-" + first_date.substring(3, 5) + "-" + first_date.substring(0, 2) + "&period=" + diff;
        }
        else if (!first_date.equals("Нажмите, чтобы выбрать дату")){
            url += "&date=" + first_date.substring(6, 10) + "-" + first_date.substring(3, 5) + "-" + first_date.substring(0, 2);
        }
        else if (!second_date.equals("Нажмите, чтобы выбрать дату")){
            url += "&date=" + second_date.substring(6, 10) + "-" + second_date.substring(3, 5) + "-" + second_date.substring(0, 2);
        }

            s = url +"&offset="+ count;


        MyHttpRequest txtRequest = new MyHttpRequest(s);


        e.clear();
        Log.e("Ref",s);
        getSpiceManager().execute(txtRequest, s, DurationInMillis.ONE_MINUTE,
                new TextRequestListener());


    }

String date(String input){
        switch (input.substring(3,5)){
            case "01":
                return input.substring(0,2) + " января";

            case "02":
                return input.substring(0,2) + " февраля";

            case "03":
                return input.substring(0,2) + " марта";

            case "04":
                return input.substring(0,2) + " апреля";

            case "05":
                return input.substring(0,2) + " мая";

            case "06":
                return input.substring(0,2) + " июня";

            case "07":
                return input.substring(0,2) + " июля";

            case "08":
                return input.substring(0,2) + " августа";

            case "09":
                return input.substring(0,2) + " сентября";

            case "10":
                return input.substring(0,2) + " октября";

            case "11":
                return input.substring(0,2) + " ноября";

            case "12":
                return input.substring(0,2) + " декабря";

            default:
                return "";

        }

}


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_news) {

            Intent intent = new Intent(PickActivity.this, NewsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(PickActivity.this, PickActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(PickActivity.this, FavouritesActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


int count = 0;
    @Override
    protected void onStart() {
        super.onStart();
        if (!used) {
            count = 0;
            url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&hasMixed=0";

            e.clear();
            String s = url +"&offset="+ count;
            MyHttpRequest txtRequest = new MyHttpRequest(s);

            pb.setVisibility(View.VISIBLE);

            Log.e("Start", s);
            getSpiceManager().execute(txtRequest, s, DurationInMillis.ONE_MINUTE,
                    new TextRequestListener());
            used = true;
        }


}


    String date(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }

    @Override
    public void onRefresh() {
        count = 0;

        bundle = new Bundle();
        pb.setVisibility(View.VISIBLE);

        error.setVisibility(View.INVISIBLE);

        e.clear();
        String s = url +"&offset="+ count;
        MyHttpRequest txtRequest = new MyHttpRequest(s);


        Log.e("Start", s);
        getSpiceManager().execute(txtRequest, s, DurationInMillis.ONE_MINUTE,
                new TextRequestListener());

    }


    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            error.setText(url+"&offset="+count);
            error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url+"&offset="+count));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            error.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);

            mSwipeRefresh.setRefreshing(false);
        }

        @Override
        public void onRequestSuccess(final String result) {

            e.addAll(jp.parse(result));

            mSwipeRefresh.setRefreshing(false);
            if (count != 0) {
                bundle.putParcelable("SAVED_LAYOUT_MANAGER", rv.getLayoutManager().onSaveInstanceState());
            }
            rv.setLayoutManager(new LinearLayoutManager(PickActivity.this));
            EventsAdapter adapter = new EventsAdapter(e,PickActivity.this);

            adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                @Override
                public void onBottomReached(int position) {

                    count += 12;
                    String s = url+"&offset="+count;
                    MyHttpRequest txtRequest = new MyHttpRequest(s);

                    getSpiceManager().execute(txtRequest, s, DurationInMillis.ONE_MINUTE,
                            new TextRequestListener());

                    pb.setVisibility(View.VISIBLE);

                    error.setVisibility(View.INVISIBLE);

                    Log.e("More",s);




                }
            });
            rv.setAdapter(adapter);

            rv.getLayoutManager().onRestoreInstanceState(( bundle).getParcelable("SAVED_LAYOUT_MANAGER"));
            pb.setVisibility(View.INVISIBLE);

        }
    }
}