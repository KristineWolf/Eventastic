package com.example.kristine.eventastic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    //checking network connection and point the user out if there is no connection
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
        }else {
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.network_title));
            builder.setMessage(getResources().getString(R.string.network_message));
            builder.setPositiveButton(getResources().getString(R.string.network_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            AlertDialog dialog=builder.create();
            dialog.show();

        }
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
