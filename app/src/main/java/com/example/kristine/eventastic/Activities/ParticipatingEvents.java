package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kristine.eventastic.R;

public class ParticipatingEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participating_events);
        setActionBarInActivity();
    }

    private void setActionBarInActivity() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.participating_events_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            //hier wird eine Einstellungsactivity geöffnet
            case R.id.main_activity_settings:
                Intent intent= new Intent(ParticipatingEvents.this, SettingsActivity.class);
                startActivity(intent);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }
}
