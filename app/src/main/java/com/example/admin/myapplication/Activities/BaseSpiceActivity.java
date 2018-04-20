package com.example.admin.myapplication.Activities;

import android.app.Activity;

import com.example.admin.myapplication.SampleSpiceService;
import com.octo.android.robospice.SpiceManager;

public abstract class BaseSpiceActivity extends Activity {
    private SpiceManager spiceManager = new SpiceManager(SampleSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

}