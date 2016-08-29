package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class EventsInCity extends AppCompatActivity {

    private ListView listView;

    private ExternDatabase helper;

    private CityAdapter adapter;
    private ArrayList<Event> arraylist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_in_city);
        initDB();
        initUI();
        updateList();

    }



    private void updateList() {
        arraylist.clear();


        listView.setAdapter(adapter);

    }

    private void initDB() {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        helper=new ExternDatabase(db);
    }

    private void initUI() {
        initListView();
        initListAdapter();
    }

    private void initListView() {
        listView=(ListView) findViewById(R.id.allEventsInACity);

    }

    private void initListAdapter() {

        Calendar cal=Calendar.getInstance();
        String month;
        String day;
        if(cal.get(Calendar.MONTH)+1<10){
            month=""+0+(cal.get(Calendar.MONTH)+1);
        }else {
            month=""+(cal.get(Calendar.MONTH)+1);
        }
        if(cal.get(Calendar.DAY_OF_MONTH)<10){
            day=""+0+cal.get(Calendar.DAY_OF_MONTH);
        }else {
            day=""+cal.get(Calendar.DAY_OF_MONTH);
        }
        int realDate = Integer.parseInt(""+cal.get(Calendar.YEAR)+month+day);

        adapter=new CityAdapter(this,helper.getAllEvents(realDate));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.event_in_city_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){

            //hier kann der Nutzer ein Event zur Datenbank hinzufügen
            case R.id.event_in_city_add_event:
                Intent intent=new Intent(EventsInCity.this,AddEvent.class);
                startActivity(intent);

            //hier wird eine Einstellungsactivity geöffnet
            case R.id.event_in_city_settings:
                Intent intent2= new Intent(EventsInCity.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            //dadurch kommt der Nutzer zu seinen Veranstaltungen, an denen er teilnehmen will
            case R.id.event_in_city_to_my_events:
                Intent intent3 = new Intent(EventsInCity.this, ParticipatingEvents.class);
                startActivity(intent3);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }

        return true;
    }
}
