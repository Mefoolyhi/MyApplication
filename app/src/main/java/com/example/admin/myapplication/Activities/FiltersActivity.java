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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Holy.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FiltersActivity extends AppCompatActivity {


    Spinner type;
    Button go;

    String url = "", tip;
    ImageButton date;
    String date1 = "",date2 = "";
    CheckBox today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = findViewById(R.id.spinner);
        go = findViewById(R.id.ready);
        today = findViewById(R.id.today);
        today.setChecked(false);
        date = findViewById(R.id.date);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(FiltersActivity.this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setSelection(0);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] choose = getResources().getStringArray(R.array.types);

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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(
                        new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                            @Override
                            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                       int yearStart, int monthStart,
                                                       int dayStart, int yearEnd,
                                                       int monthEnd, int dayEnd) {
                                monthEnd++;
                                monthStart++;
                                String d,m;
                                if (dayStart <= 9){
                                    d = '0'+ String.valueOf(dayStart);
                                }
                                else
                                    d = String.valueOf(dayStart);
                                if (monthStart <= 9){
                                    m = '0' + String.valueOf(monthStart);
                                }
                                else
                                    m = String.valueOf(monthStart);
                                date1 = d+"."+m+"."+yearStart;

                                if (dayEnd <= 9){
                                    d = '0'+ String.valueOf(dayEnd);
                                }
                                else
                                    d = String.valueOf(dayEnd);
                                if (monthEnd <= 9){
                                    m = '0' + String.valueOf(monthEnd);
                                }
                                else
                                    m = String.valueOf(monthEnd);
                                date2 = d+"."+m+"."+yearEnd;
                            }
                        });
                smoothDateRangePickerFragment.setThemeDark(true);

                today.setChecked(false);
                //setAccentColor(int color)

                smoothDateRangePickerFragment.show(getFragmentManager(), "smoothDateRangePicker");
            }
        });


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("url",url);
                if (today.isChecked()){
                    date1 = date();
                    date2 = date();
                }
                intent.putExtra("first_date",date1);
                intent.putExtra("second_date",date2);
                intent.putExtra("name",tip);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    String date(){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(Calendar.getInstance().getTime());
    }





}
