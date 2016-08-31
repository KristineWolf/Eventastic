package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.ContemporaryDate;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;


public class EventsInCity extends AppCompatActivity {

    private ListView listView;
    private SearchView searchForCity;
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
        helper.db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>=ContemporaryDate.getContemporaryDate()) {
                    arraylist.add(event);
                    Collections.sort(arraylist);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>=ContemporaryDate.getContemporaryDate()) {
                    arraylist.add(event);
                    Collections.sort(arraylist);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>=ContemporaryDate.getContemporaryDate()) {
                    arraylist.add(event);
                    Collections.sort(arraylist);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        listView.setAdapter(adapter);
    }


    private void initDB() {
        helper=new ExternDatabase();
    }

    private void initUI() {
        setupSearchView();
        initCityName();
        initListView();
        initListAdapter();
    }

    private void initCityName() {
        TextView city = (TextView)findViewById(R.id.title_events_in_this_city);
        city.setText(getString(R.string.all_events_title)+ " jeweilige Stadt");
    }

    private void setupSearchView() {
    }

    private void initListView() {
        listView=(ListView) findViewById(R.id.allEventsInACity);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventsInCity.this, AllInformationsOfAnEvent.class);
                intent.putExtra("city",arraylist.get(position).getCity());
                intent.putExtra("titel",arraylist.get(position).getTitel());
                intent.putExtra("time",arraylist.get(position).getTime());
                intent.putExtra("type",arraylist.get(position).getType());
                intent.putExtra("date", ChangeDateFormat.changeIntoString(arraylist.get(position).getDate()));
                intent.putExtra("definition",arraylist.get(position).getDefintion());

                startActivity(intent);
            }
        });
    }

    private void initListAdapter() {
       adapter=new CityAdapter(this,arraylist);
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
                break;

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
