package com.example.admin.myapplication.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.R;

import java.util.Calendar;

public class FiltersActivity extends AppCompatActivity {


    Spinner type;
    Button back,go;

    String url = "", tip;
    TextView first,second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        type = findViewById(R.id.spinner);
        back = findViewById(R.id.back);
        go = findViewById(R.id.ready);
        first = findViewById(R.id.first_date);
        second = findViewById(R.id.second_date);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(FiltersActivity.this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setSelection(0);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] choose = getResources().getStringArray(R.array.types);
                Toast toast = Toast.makeText(FiltersActivity.this,
                        "Ваш выбор: " + choose[i], Toast.LENGTH_SHORT);
                toast.show();
                tip = choose[i];
                switch (choose[i]){
                    case "Концерты":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&hasMixed=0";


                        break;
                    case "Спектакли":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?&city=yekaterinburg&limit=12&hasMixed=0";



                        break;
                    case "Детское":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-kids/?city=yekaterinburg&limit=12&hasMixed=0";
                        break;

                    case "Мюзиклы":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-musical/?city=yekaterinburg&limit=12&hasMixed=0";
                        break;
                    case "Шоу":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-show/?city=yekaterinburg&limit=12&hasMixed=0";
                                break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getFragmentManager(), "first");

            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getFragmentManager(), "second");
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("url",url);
                intent.putExtra("first_date",first.getText().toString());
                intent.putExtra("second_date",second.getText().toString());
                intent.putExtra("name",tip);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("ValidFragment")
    class DatePicker extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            Dialog picker = new DatePickerDialog(getActivity(), this,
                    year, month, day);


            return picker;
        }
        int id = 0;

        @Override
        public void show(FragmentManager manager, String tag) {
            super.show(manager, tag);
            if (tag.equals("first")){
                id = 1;
            }
            else{
                id = 2;
            }

        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
            month++;

            String d,m;
            if (day <= 9){
                d = '0'+ String.valueOf(day);
            }
            else
                d = String.valueOf(day);
            if (month <= 9){
                m = '0' + String.valueOf(month);
            }
            else
                m = String.valueOf(month);
            if (id == 1) {
                first.setText(d + "-" + m + "-" + year);
                first.setTextSize(20);
                first.setTextColor(Color.rgb(0,0,0));
            }
            else {
                second.setText(d + "-" + m + "-" + year);
                second.setTextSize(20);
                second.setTextColor(Color.rgb(0,0,0));
            }
        }
    }
}
