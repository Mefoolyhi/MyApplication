package com.example.admin.myapplication.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Theatre;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class MapActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {


    private GoogleMap mMap;
    List<Theatre> data;
    String url = "http://5.189.85.227:8124/";
    //"http://5.189.85.227:8124/concerts/"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView name = (TextView) toolbar.findViewById(R.id.name);
        name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        name.setSingleLine(true);
        name.setMarqueeRepeatLimit(-1); // '-1' for infinite
        name.setSelected(true);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(3).setChecked(true);

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

    private interface Server{
        @GET("theatres")
        Call<String> getTheatres();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

        }
        Server service = retrofit.create(Server.class);
        final HashMap<String, Integer> theatres = new HashMap<>();
        Call<String> call = service.getTheatres();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                Log.e("ALARM",response.body());
                String s = response.body().replace("\\","");
                s = s.substring(1,s.length()-1);
                Log.e("TEST", s);

                data = new Gson().fromJson(s, new TypeToken<List<Theatre>>(){}.getType());
                for (int i = 0; i < data.size(); i++){
                    Theatre t = data.get(i);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLatitude(),t.getLongintude())).title(t.getTitle()).snippet("Нажмите, чтобы увидеть больше"));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(t.getLatitude(),t.getLongintude())));
                    theatres.put(t.getTitle(), i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });


        mMap.setBuildingsEnabled(true);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapActivity2.this, TheatreActivity.class);
                Theatre t = data.get(theatres.get(marker.getTitle()));
                intent.putExtra("title",t.getTitle());
                intent.putExtra("link",t.getLink());
                intent.putExtra("number",t.getNumber());
                intent.putExtra("image", t.getImage());
                intent.putExtra("id", t.getId());
                intent.putExtra("address",t.getAddress());
                startActivityForResult(intent,2);

            }
        });

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_news) {

            Intent intent = new Intent(MapActivity2.this, NewsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(MapActivity2.this, PickActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(MapActivity2.this, FavouritesActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(MapActivity2.this, MapActivity2.class);
            startActivity(intent);
            finish();

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
