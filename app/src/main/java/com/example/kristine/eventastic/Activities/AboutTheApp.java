package com.example.kristine.eventastic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kristine.eventastic.R;

public class AboutTheApp extends AppCompatActivity {

    // this activity shows information about this app. not interactive.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);
    }
}
