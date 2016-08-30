package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.EventAdapter;
import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;
import java.util.Collections;

public class ParticipatingEvents extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Event> arrayList = new ArrayList<>();
    private InternDatabase db;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participating_events);
        initDB();
        initUI();
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
        adapter = new EventAdapter(this, arrayList);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listView_of_participating_events);
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

            //hier kommt der Nutzer zu allen Events
            case R.id.participating_events_to_all_events:
                Intent intent = new Intent(ParticipatingEvents.this, EventsInCity.class);
                startActivity(intent);
                return true;

            case R.id.participating_events_to_calendar:
                Intent intent3 = new Intent(ParticipatingEvents.this,Calendar.class);
                startActivity(intent3);
                return true;


            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;


        }

        return false;
    }
}
