package com.example.admin.myapplication;

import android.app.Application;

import me.majiajie.swipeback.utils.ActivityStack;

public class Apl extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(ActivityStack.getInstance());
    }
}