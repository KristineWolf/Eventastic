package com.example.kristine.eventastic.Activities;

import android.content.Intent;
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


//this activity presents all possible cities where events will take place.
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

    //gets only the cities
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

    //clicking on one city the user will get to an activity which will present only the events in this city.
    private void initListView() {
        listview=(ListView)findViewById(R.id.all_cities);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllPossibleCities.this,EventsInCity.class);
                intent.putExtra(getResources().getString(R.string.selected_city),arraylist.get(position));
                startActivity(intent);
            }
        });
    }

    //gets all events from the static class
    private void getDataFromPuffer(){
        allEvents.addAll(AllEventsPuffer.getAllEvents());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.empty_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
