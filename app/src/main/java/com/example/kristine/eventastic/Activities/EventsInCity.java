package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.AllEventsInACity;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;


public class EventsInCity extends AppCompatActivity {

    private ListView listView;
    private ExternDatabase helper;

    private CityAdapter adapter;
    private ArrayList<Event> arraylist=new ArrayList<>(), allCities=new ArrayList<>();
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_in_city);
        AllEventsInACity.clearEvent();
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        selectedCity = extras.getString("selected_city");
        AllEventsInACity.clearEvent();
        allCities.addAll(AllEventsPuffer.getAllEvents());
        searching();
        initDB();
        initUI();
        updateList();

    }

    private void searching() {
        for(int i=0; i<allCities.size(); i++){
            if(selectedCity.equals(allCities.get(i).getCity())){
                AllEventsInACity.enterEvent(allCities.get(i));
            }
        }
    }


    private void updateList() {
        arraylist.clear();
        arraylist.addAll(AllEventsInACity.getSpecificCities());
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
        city.setText(getString(R.string.all_events_title)+ " " + selectedCity);
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
