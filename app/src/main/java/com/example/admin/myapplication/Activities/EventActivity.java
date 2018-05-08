package com.example.admin.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.admin.myapplication.Adapters.EventsAdapter;
import com.example.admin.myapplication.Holy.DataHelper;
import com.example.admin.myapplication.R;

import com.example.admin.myapplication.Utils.Event;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class EventActivity extends AppCompatActivity {



    ImageButton add;
    TextView type_rating, name, dates_place, price, place, address;
    SimpleDraweeView image, imagePlace;
    Button buy, UserRating, map;
    double longitude, latitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
       map = findViewById(R.id.map);

       map.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(EventActivity.this, MapsActivity.class);
               intent.putExtra("longitude",longitude);
               intent.putExtra("latitude",latitude);
               intent.putExtra("name",getIntent().getStringExtra("place"));
               startActivity(intent);
           }
       });


        type_rating = findViewById(R.id.type_rating);
        name = findViewById(R.id.name);
        dates_place = findViewById(R.id.dates_place);
        price = findViewById(R.id.price);
        place = findViewById(R.id.place);
        address = findViewById(R.id.address);
        image = findViewById(R.id.image);
        imagePlace = findViewById(R.id.imagePlace);
        buy = findViewById(R.id.buy);
        UserRating = findViewById(R.id.UserRating);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add = findViewById(R.id.add_to_fav);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHelper dh = new DataHelper(EventActivity.this);
                ContentValues cv = new ContentValues();
                cv.put("name",getIntent().getStringExtra("name"));
                cv.put("rating",getIntent().getStringExtra("rating"));
                cv.put("imageLink",getIntent().getStringExtra("image"));
                cv.put("price",getIntent().getStringExtra("price"));
                cv.put("dates",getIntent().getStringExtra("dates"));
                cv.put("place",getIntent().getStringExtra("place"));
                cv.put("buy",getIntent().getStringExtra("buy"));
                cv.put("address",getIntent().getStringExtra("address"));
                cv.put("longitude",getIntent().getDoubleExtra("longitude",0));
                cv.put("latitude",getIntent().getDoubleExtra("latitude",0));
                cv.put("types",getIntent().getStringExtra("types"));
                cv.put("placeImage",getIntent().getStringExtra("imagePlace"));
                cv.put("id",getIntent().getStringExtra("id"));
                cv.put("userRating",getIntent().getDoubleExtra("UserRating", 0));
                dh.insert(cv);
                Snackbar.make(view, "Добавлено в избранное", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        name.setText(getIntent().getStringExtra("name"));
        dates_place.setText(getIntent().getStringExtra("dates"));
        price.setText(getIntent().getStringExtra("price"));
        place.setText(getIntent().getStringExtra("place"));
        address.setText(getIntent().getStringExtra("address"));
        UserRating.setText(String.valueOf(getIntent().getDoubleExtra("UserRating", 0)));

        if (getIntent().getDoubleExtra("UserRating", 0) >= 8.0){
            UserRating.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if (8 > getIntent().getDoubleExtra("UserRating", 0) && getIntent().getDoubleExtra("UserRating", 0) >= 5.0){
            UserRating.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        else
            UserRating.setBackgroundColor(getResources().getColor(R.color.red));

        image.setImageURI(getIntent().getStringExtra("image"));
        longitude = getIntent().getDoubleExtra("longitude",0);
        latitude = getIntent().getDoubleExtra("latitude",0);
        imagePlace.setImageURI(getIntent().getStringExtra("imagePlace"));
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("buy")));
                Log.e("Link",getIntent().getStringExtra("buy"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        type_rating.setText(getIntent().getStringExtra("types") +" "+ getIntent().getStringExtra("rating"));




    }



}
