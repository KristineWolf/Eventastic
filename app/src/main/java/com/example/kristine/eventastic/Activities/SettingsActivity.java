package com.example.kristine.eventastic.Activities;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.example.kristine.eventastic.R;

/**
 * Created by Teresa on 27.08.2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        return false;
    }
}