package com.example.admin.myapplication.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.myapplication.Holy.DataHelper;
import com.example.admin.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

import me.majiajie.swipeback.SwipeBackActivity;


public class EventActivity extends SwipeBackActivity {



    ImageButton add;
    TextView type_rating, name, dates_place, price, place, address;
    SimpleDraweeView image, imagePlace;
    Button buy, UserRating, map;
    double longitude, latitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
       map = (Button) findViewById(R.id.map);

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


        type_rating = (TextView) findViewById(R.id.type_rating);
        name = (TextView) findViewById(R.id.name);
        dates_place = (TextView) findViewById(R.id.dates_place);
        price = (TextView) findViewById(R.id.price);
        place = (TextView) findViewById(R.id.place);
        address = (TextView) findViewById(R.id.address);
        image = (SimpleDraweeView) findViewById(R.id.image);
        imagePlace = (SimpleDraweeView) findViewById(R.id.imagePlace);
        buy = (Button) findViewById(R.id.buy);
        UserRating = (Button) findViewById(R.id.UserRating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add = (ImageButton) findViewById(R.id.add_to_fav);
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
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("buy")));
//                Log.e("Link",getIntent().getStringExtra("buy"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

                Intent intent = new Intent(EventActivity.this,MoreAndBuyActivity.class);
                intent.putExtra("link",getIntent().getStringExtra("buy"));
                startActivity(intent);
            }
        });


        type_rating.setText(getIntent().getStringExtra("types") +" "+ getIntent().getStringExtra("rating"));




    }



}
