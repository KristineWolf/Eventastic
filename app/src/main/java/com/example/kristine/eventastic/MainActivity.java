package com.example.kristine.eventastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kristine.eventastic.Activities.AllEvents;
import com.example.kristine.eventastic.Activities.CalendarActivity;
import com.example.kristine.eventastic.Activities.AboutTheApp;

import com.example.kristine.eventastic.Activities.ParticipatingEvents;
import com.example.kristine.eventastic.Activities.SettingsActivity;


public class MainActivity extends AppCompatActivity {

    private Button eventsInSpecifiedCity, eventsTheUserWantsToVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initClickListener();
    }

    //with those Buttons the user will get to the two most important sections of our app.
    private void initClickListener() {
        eventsInSpecifiedCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllEvents.class);
                startActivity(intent);
            }
        });

        eventsTheUserWantsToVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, ParticipatingEvents.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        eventsInSpecifiedCity=(Button)findViewById(R.id.toAllEventsInACity);
        eventsTheUserWantsToVisit=(Button)findViewById(R.id.EventsYouWantToParticipate);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){

            case R.id.main_activity_explanation:
                Intent intent= new Intent(MainActivity.this, AboutTheApp.class);
                startActivity(intent);
                return true;


            case R.id.main_activity_calendar:
                Intent intent3 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent3);
                return true;

            case R.id.main_activity_settings:
                Intent intent2= new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                return true;

        }

        return false;
    }



}
