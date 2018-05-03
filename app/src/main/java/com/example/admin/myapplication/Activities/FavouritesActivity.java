package com.example.admin.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.myapplication.Adapters.FavouritesAdapter;
import com.example.admin.myapplication.Holy.DataHelper;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rv;
    ProgressBar pb;
    TextView error;
    ArrayList<Event> e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        error = findViewById(R.id.error_text);
        pb = findViewById(R.id.progressBar);


        pb.setVisibility(View.VISIBLE);
        error.setVisibility(View.INVISIBLE);

        try {
            rv = findViewById(R.id.rv);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setLayoutManager(new LinearLayoutManager(this));

            DataHelper dt = new DataHelper(this);
            e = dt.getFavourites();
            rv.setAdapter(new FavouritesAdapter(e, this));

            pb.setVisibility(View.INVISIBLE);
        } catch (Exception e){
            Log.e("Fav",e.getMessage());
            pb.setVisibility(View.INVISIBLE);
            error.setText("Что-то пошло не так, попробуйте чуть позже");
            error.setVisibility(View.VISIBLE);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {

            Intent intent = new Intent(FavouritesActivity.this, NewsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(FavouritesActivity.this, PickActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(FavouritesActivity.this, FavouritesActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
