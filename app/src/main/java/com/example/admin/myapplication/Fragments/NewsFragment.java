package com.example.admin.myapplication.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.PostValue;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    View view;
    ArrayList<PostValue> news;
    RecyclerView rv;
    ProgressBar pb;
    TextView eror;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        rv = view.findViewById(R.id.rv_news);

        pb = view.findViewById(R.id.progressBar);


        eror = view.findViewById(R.id.eror_text);
        pb.setVisibility(View.INVISIBLE);
        eror.setVisibility(View.INVISIBLE);

        return view;
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }





    }


