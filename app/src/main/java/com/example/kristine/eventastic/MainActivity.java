package com.example.kristine.eventastic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kristine.eventastic.Activities.EventsInCity;
import com.example.kristine.eventastic.Activities.Explanation;
import com.example.kristine.eventastic.Activities.ParticipatingEvents;

public class MainActivity extends AppCompatActivity {

    private Button eventsInSpecifiedCity;
    private Button eventsTheUserWantsToVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initClickListener();
    }

    private void initClickListener() {
        eventsInSpecifiedCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsInCity.class);
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
            //diese Activity soll die App erklären, wozu sie dient was man alles machen kann...
            case R.id.main_activity_explanation:
                Intent intent= new Intent(MainActivity.this, Explanation.class);
                startActivity(intent);
                break;
            case R.id.main_activity_settings:
                //hier wird eine Einstellungsactivity geöffnet
                //bin mir aber nicht sicher ob des auch eine Activity ist
                //sollte eig etwas anderes sein --> SharedPreferences -->VL 04 bei Einstellungen
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }
}
