package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kristine.eventastic.R;

/**
 * Created by Teresa on 29.08.2016.
 */
public class Calendar extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.calender_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){

            //hier wird die Einstellungsactivity geöffnet
            case R.id.event_in_city_settings:
                Intent intent2= new Intent(Calendar.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return true;
    }

}
