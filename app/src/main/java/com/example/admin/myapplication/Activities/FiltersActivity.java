package com.example.admin.myapplication.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.admin.myapplication.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.octo.android.robospice.persistence.DurationInMillis;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ArrayAdapter<?> adapter =
//                ArrayAdapter.createFromResource(PickActivity.this, R.array.types, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        type.setSelection(0);
//        type.setAdapter(adapter);
//        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String[] choose = getResources().getStringArray(R.array.types);
//                Toast toast = Toast.makeText(PickActivity.this,
//                        "Ваш выбор: " + choose[i], Toast.LENGTH_SHORT);
//                toast.show();
//                String url = "";
//                switch (choose[i]){
//                    case "Концерты":
//                        url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
//                        txtRequest = new MyHttpRequest(url);
//
//
//                        break;
//                    case "Спектакли":
//                        url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
//                        txtRequest = new MyHttpRequest(url);
//
//
//                        break;
//
//                }
//
//                getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
//                        new PickActivity.TextRequestListener());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

}
