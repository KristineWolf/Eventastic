package com.example.kristine.eventastic.Activities;


import android.os.Bundle;

import android.preference.PreferenceActivity;


import com.example.kristine.eventastic.R;

//presents the settings
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }





}