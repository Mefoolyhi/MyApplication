package com.example.admin.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.myapplication.Adapters.EventsAdapter;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.Holy.Server;
import com.example.admin.myapplication.Parsers.JParser;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PickActivity extends BaseSpiceActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    JParser jp = new JParser();






    RecyclerView rv;
    ProgressBar pb;
    private SwipeRefreshLayout mSwipeRefresh;
    TextView filters,error;
    ImageButton filt;
    ArrayList<Event> e = new ArrayList<>();
    Bundle bundle = new Bundle();


    boolean used = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView name = (TextView) toolbar.findViewById(R.id.name);
        name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        name.setSingleLine(true);
        name.setMarqueeRepeatLimit(-1); // '-1' for infinite
        name.setSelected(true);
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

    int type_of_event = 0;
    String date = "";
    int diff = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        pb.setVisibility(View.VISIBLE);
        type_of_event = data.getIntExtra("type",0);
        count = 0;
        diff = 0;
        date = "";
        error.setVisibility(View.INVISIBLE);
        String first_date = data.getStringExtra("first_date");
        String second_date = data.getStringExtra("second_date");
        String name = data.getStringExtra("name");
        Log.e("dates",first_date + " "+second_date);


        e.clear();

        rv.scrollToPosition(0);
        bundle = new Bundle();

        if (first_date.length()<3){
            filters.setText(name);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://afisha.yandex.ru/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            Server service = retrofit.create(Server.class);
            Call<String> call;
            switch (type_of_event){
                case 0:
                    call = service.getNews(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 1:
                    call = service.getConcerts(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 2:
                    call = service.getKids(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 3:
                    call = service.getMusical(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 4:
                    call = service.getShow(12, count, 0);
                    call.enqueue(callback);
                    break;
            }


        }
        else {
            filters.setText(name + ", " + date(first_date) + " - " + date(second_date));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


            try {
                Date date2 = dateFormat.parse(second_date);
                Date date1 = dateFormat.parse(first_date);
                diff += (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
                date = first_date.substring(6, 10) + "-" + first_date.substring(3, 5) + "-" + first_date.substring(0, 2);

            } catch (ParseException e1) {
                Log.e("DateDif",e1.getMessage());
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://afisha.yandex.ru/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            Server service = retrofit.create(Server.class);
            Call<String> call;
            switch (type_of_event){
                case 0:
                    call = service.getNews(12, count, 0, date,diff);
                    call.enqueue(callback);
                    break;
                case 1:
                    call = service.getConcerts(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 2:
                    call = service.getKids(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 3:
                    call = service.getMusical(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 4:
                    call = service.getShow(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
            }

        }





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
            finish();

        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(PickActivity.this, PickActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(PickActivity.this, FavouritesActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(PickActivity.this, MapActivity2.class);
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
            type_of_event = 0;
            e.clear();
            diff = 1;
            date = "";
            used = true;

            pb.setVisibility(View.VISIBLE);

            Log.e("Start", "LETS GO!");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://afisha.yandex.ru/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            Server service = retrofit.create(Server.class);
            Call<String> call = service.getNews(12, count, 0);
            call.enqueue(callback);

        }


}

    @Override
    public void onRefresh() {
        count = 0;

        bundle = new Bundle();
        pb.setVisibility(View.VISIBLE);

        error.setVisibility(View.INVISIBLE);


        e.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afisha.yandex.ru/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Server service = retrofit.create(Server.class);
        Call<String> call;
        if (date.equals("") || diff == 1){
            switch (type_of_event){
                case 0:
                    call = service.getNews(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 1:
                    call = service.getConcerts(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 2:
                    call = service.getKids(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 3:
                    call = service.getMusical(12, count, 0);
                    call.enqueue(callback);
                    break;
                case 4:
                    call = service.getShow(12, count, 0);
                    call.enqueue(callback);
                    break;
            }

        }
        else{
            switch (type_of_event){
                case 0:
                    call = service.getNews(12, count, 0, date,diff);
                    call.enqueue(callback);
                    break;
                case 1:
                    call = service.getConcerts(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 2:
                    call = service.getKids(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 3:
                    call = service.getMusical(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
                case 4:
                    call = service.getShow(12, count, 0,date,diff);
                    call.enqueue(callback);
                    break;
            }

        }





    }


    Callback<String> callback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                e.addAll(jp.parse(response.body()));
                Log.e("ALARM", String.valueOf(response.raw()));

                mSwipeRefresh.setRefreshing(false);
                if (count != 0) {
                    bundle.putParcelable("SAVED_LAYOUT_MANAGER", rv.getLayoutManager().onSaveInstanceState());
                }
                rv.setLayoutManager(new LinearLayoutManager(PickActivity.this));
                EventsAdapter adapter = new EventsAdapter(e,PickActivity.this);

                adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                    @Override
                    public void onBottomReached(int position) {

                        if (jp.dataSize() > 0) {
                            count += 12;
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://afisha.yandex.ru/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .build();

                            Server service = retrofit.create(Server.class);
                            Call<String> call;
                            Log.e("Point", date + " " + String.valueOf(diff));
                            if (date.equals("")) {
                                switch (type_of_event) {
                                    case 0:
                                        call = service.getNews(12, count, 0);
                                        call.enqueue(callback);
                                        break;
                                    case 1:
                                        call = service.getConcerts(12, count, 0);
                                        call.enqueue(callback);
                                        break;
                                    case 2:
                                        call = service.getKids(12, count, 0);
                                        call.enqueue(callback);
                                        break;
                                    case 3:
                                        call = service.getMusical(12, count, 0);
                                        call.enqueue(callback);
                                        break;
                                    case 4:
                                        call = service.getShow(12, count, 0);
                                        call.enqueue(callback);
                                        break;
                                }

                            } else {
                                switch (type_of_event) {
                                    case 0:
                                        call = service.getNews(12, count, 0, date, diff);
                                        call.enqueue(callback);
                                        break;
                                    case 1:
                                        call = service.getConcerts(12, count, 0, date, diff);
                                        call.enqueue(callback);
                                        break;
                                    case 2:
                                        call = service.getKids(12, count, 0, date, diff);
                                        call.enqueue(callback);
                                        break;
                                    case 3:
                                        call = service.getMusical(12, count, 0, date, diff);
                                        call.enqueue(callback);
                                        break;
                                    case 4:
                                        call = service.getShow(12, count, 0, date, diff);
                                        call.enqueue(callback);
                                        break;
                                }

                            }

                            pb.setVisibility(View.VISIBLE);

                            error.setVisibility(View.INVISIBLE);

                            Log.e("More", String.valueOf(count));


                        }
                    }
                });
                rv.setAdapter(adapter);

                rv.getLayoutManager().onRestoreInstanceState(( bundle).getParcelable("SAVED_LAYOUT_MANAGER"));
                pb.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                Log.e("ALARM", e.getMessage());
                e.printStackTrace();
                error.setText("Произошла ошибка, попробуйте позже");
                error.setVisibility(View.VISIBLE);
                pb.setVisibility(View.INVISIBLE);

                mSwipeRefresh.setRefreshing(false);
            }

        }


        @Override
        public void onFailure(Call<String> call, Throwable t) {

            Log.e("ALARM", t.getMessage());
            t.printStackTrace();
            error.setText("Произошла ошибка, попробуйте позже");
            error.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);

            mSwipeRefresh.setRefreshing(false);
        }
    };





}