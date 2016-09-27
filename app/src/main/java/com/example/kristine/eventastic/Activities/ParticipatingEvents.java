package com.example.kristine.eventastic.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;
import java.util.Collections;

//this activity presents all events the user wants to participate
public class ParticipatingEvents extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Event> arrayList = new ArrayList<>();
    private InternDatabase db;
    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participating_events);
        initDB();
        initUI();
        updateList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        arrayList.clear();
        arrayList.addAll(db.getAllEvents());
        listView.setAdapter(adapter);
        sortData();
    }

    private void sortData() {
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
    }


    private void initUI() {
        initListView();
        initListAdapter();
    }

    private void initListAdapter() {
        adapter = new CityAdapter(this, arrayList);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listView_of_participating_events);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParticipatingEvents.this, AllInformationsOfAParticipatingEvent.class);
                intent.putExtra(getResources().getString(R.string.event_in_intent),arrayList.get(position));

                startActivity(intent);
            }
        });
    }

    private void initDB() {
        db = new InternDatabase(this);
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
            //choosing this the user gets to all possible events
            case R.id.participating_events_to_all_events:
                Intent intent = new Intent(ParticipatingEvents.this, AllEvents.class);
                startActivity(intent);
                return true;

            //choosing this the user gets to CalendarActivity where is shown his next upcomming event
            case R.id.participating_events_to_calendar:
                Intent intent3 = new Intent(ParticipatingEvents.this,CalendarActivity.class);
                startActivity(intent3);
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
