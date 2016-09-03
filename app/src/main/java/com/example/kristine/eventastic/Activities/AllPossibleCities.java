package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;
import java.util.Collections;

public class AllPossibleCities extends AppCompatActivity {

    private ListView listview;
    private ArrayList<Event>allEvents=new ArrayList<>();
    private ArrayList<String> arraylist=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_possible_cities);
        getDataFromPuffer();
        searchingThroughData();
        initUI();
        updateList();
    }

    private void updateList() {
        listview.setAdapter(adapter);
    }

    private void searchingThroughData() {
        for(int i=0; i<allEvents.size();i++){
            if (!arraylist.contains(allEvents.get(i).getCity())){
                arraylist.add(allEvents.get(i).getCity());
            }
        }
        Collections.sort(arraylist);
    }

    private void initUI() {
        initListView();
        initAdapter();
    }

    private void initAdapter() {
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arraylist);
    }

    private void initListView() {
        listview=(ListView)findViewById(R.id.all_cities);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllPossibleCities.this,EventsInCity.class);
                intent.putExtra("selected_city",arraylist.get(position));
                startActivity(intent);
            }
        });
    }

    private void getDataFromPuffer(){
        allEvents.addAll(AllEventsPuffer.getAllEvents());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.all_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){

            //hier kann der Nutzer ein Event zur Datenbank hinzufügen
            case R.id.all_possible_cities_add_event:
                Intent intent=new Intent(AllPossibleCities.this,AddEvent.class);
                startActivity(intent);
                break;

            //hier wird eine Einstellungsactivity geöffnet
            case R.id.all_possible_cities_settings:
                Intent intent2= new Intent(AllPossibleCities.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            //dadurch kommt der Nutzer zu seinen Veranstaltungen, an denen er teilnehmen will
            case R.id.all_possible_cities_to_my_events:
                Intent intent3 = new Intent(AllPossibleCities.this, ParticipatingEvents.class);
                startActivity(intent3);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }

        return true;
    }
}
