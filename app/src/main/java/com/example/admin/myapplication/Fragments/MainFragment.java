package com.example.admin.myapplication.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.JParser;
import com.example.admin.myapplication.MyHttpRequest;
import com.example.admin.myapplication.R;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainFragment extends Fragment
{


    Spinner type;
    ListView rv;
    ProgressBar pb;
    TextView defaultt;

    JParser jp = new JParser();



    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    private MyHttpRequest txtRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        rv = v.findViewById(R.id.rv);
        type = v.findViewById(R.id.spinner);
        defaultt = v.findViewById(R.id.default_text);
        pb = v.findViewById(R.id.progressBar);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setSelection(0);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] choose = getResources().getStringArray(R.array.types);
                Toast toast = Toast.makeText(getActivity(),
                        "Ваш выбор: " + choose[i], Toast.LENGTH_SHORT);
                toast.show();
                String url = "";
                switch (choose[i]){
                    case "Концерты":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-concert/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
                        break;
                    case "Спектакли":
                        url = "https://afisha.yandex.ru/api/events/selection/all-events-theatre/?city=yekaterinburg&limit=12&offset=0&hasMixed=0";
                        break;

                }
                txtRequest = new MyHttpRequest(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pb.setVisibility(View.INVISIBLE);




        return v;
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        getSpiceManager().execute(txtRequest, "txt", DurationInMillis.ONE_MINUTE,
//                new MainFragment().TextRequestListener());
//    }



    public final class TextRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "failure", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(final String result) {
            Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();

            Log.e("PArs",result);
            jp.parse(result);

        }
    }


    }


