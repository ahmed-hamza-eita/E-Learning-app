package com.hamza.e_learningapp;

import android.app.Application;


import com.hamza.e_learningapp.data.MySharedPrefrance;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPrefrance.init(this);
    }
}
