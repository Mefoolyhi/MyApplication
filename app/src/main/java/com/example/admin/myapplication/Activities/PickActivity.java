package com.example.admin.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Parsers.JParser;
import com.example.admin.myapplication.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class PickActivity extends BaseSpiceActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MyHttpRequest txtRequest;
    JParser jp = new JParser();

    RecyclerView rv;
    ProgressBar pb;
    TextView filters,error;
    ImageButton filt;

    //String url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
    //String url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
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



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(1).setChecked(true);
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

            Intent intent = new Intent(PickActivity.this, NewsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(PickActivity.this, PickActivity.class);
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

}

    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(PickActivity.this, "failure", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(final String result) {
            Toast.makeText(PickActivity.this, "success", Toast.LENGTH_LONG).show();

            Log.e("PArs",result);
            jp.parse(result);

        }
    }
}