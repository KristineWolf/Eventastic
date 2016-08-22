package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Database.OwnEventDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;


public class EventsInCity extends AppCompatActivity {

    private ListView listView;

    private OwnEventDatabase db;

    private CityAdapter adapter;
    private ArrayList<Event> arraylist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_in_city);
        setActionBarInActivity();
        initDB();
        initUI();
        updateList();

    }

    private void updateList() {
        arraylist.clear();
        arraylist.addAll(db.getAllEvents());
        listView.setAdapter(adapter);

    }

    private void initDB() {
        db=new OwnEventDatabase(this);
    }

    private void initUI() {
        initListView();
        initListAdapter();
    }

    private void initListAdapter() {
        adapter=new CityAdapter(this, arraylist);
    }

    private void initListView() {
        listView=(ListView) findViewById(R.id.allEventsInACity);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Intent intent=new Intent(EventsInCity.this, EventAlone.class);
                Event event = arraylist.get(position);
                intent.putExtra(event.getCity(),"city");
                intent.putExtra(event.getDefintion(),"definition");
                intent.putExtra(event.getTitel(),"titel");
                intent.putExtra(event.getType(),"type");
                intent.putExtra(""+event.getDay()+"."+event.getMonth()+"."+event.getYear(),"date");
                intent.putExtra(""+event.getHour()+":"+event.getMinutes(),"time");
                startActivity(intent);
                return false;
            }
        });
    }

    private void setActionBarInActivity() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
            //dadurch kann ein Nutzer eine Veranstaltung hinzufügen
            case R.id.event_in_city_add_event:
                Intent intent=new Intent(EventsInCity.this,AddEvent.class);
                startActivity(intent);
                break;
            case R.id.main_activity_settings:
                //hier wird eine Einstellungsactivity geöffnet
                //bin mir aber nicht sicher ob des auch eine Activity ist
                //sollte eig etwas anderes sein --> SharedPreferences -->VL 04 bei Einstellungen
                break;
            //dadurch kommt der Nutzer zu seinen Veranstaltungen, an denen er teilnehmen will
            case R.id.event_in_city_your_events:
                Intent i=new Intent(EventsInCity.this,ParticipatingEvents.class);
                startActivity(i);
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
