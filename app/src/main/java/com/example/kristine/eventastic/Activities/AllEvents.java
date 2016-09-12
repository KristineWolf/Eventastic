package com.example.kristine.eventastic.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.ContemporaryDate;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;

//this activity presents all events
public class AllEvents extends AppCompatActivity {

    private ExternDatabase db;
    private ListView listview;
    private CityAdapter adapter;
    private Button toAllPossibleCities;
    private ArrayList<Event> events=new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private boolean location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        AllEventsPuffer.clearArrayList();
        initDB();
        initUI();

    }


    //every time the activity has to check the settings and therefore will react to it.
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        location=sharedPreferences.getBoolean(getResources().getString(R.string.location),false);
        if(location==false){
            toAllPossibleCities.setText(getResources().getString(R.string.button_to_all_cities));
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(getResources().getString(R.string.dialog_message));
            builder.setPositiveButton(getResources().getString(R.string.dialog_pos_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                                        Intent i= new Intent(AllEvents.this,SettingsActivity.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.dialog_neg_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            builder.setCancelable(false);
            AlertDialog dialog=builder.create();
            dialog.show();
        }else {
            toAllPossibleCities.setText(getResources().getString(R.string.button_to_events_in_neighborhood));
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        updateList();
    }

    private void updateList() {
        events.clear();
        //with this Listener the activity gets events from the firebase database
        //the activity will get only events which will still take place.
        //all events are going to be saved in a static class
        db.db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>= ContemporaryDate.getContemporaryDate()) {
                    AllEventsPuffer.enterEvent(event);
                    events.add(event);
                    Collections.sort(events);
                    adapter.notifyDataSetChanged();
                    toAllPossibleCities.setEnabled(true);
                    toAllPossibleCities.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>= ContemporaryDate.getContemporaryDate()) {
                    AllEventsPuffer.enterEvent(event);
                    events.add(event);
                    Collections.sort(events);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event=dataSnapshot.getValue(Event.class);
                if(event.getDate()>= ContemporaryDate.getContemporaryDate()) {
                    AllEventsPuffer.enterEvent(event);
                    events.add(event);
                    Collections.sort(events);
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

        listview.setAdapter(adapter);
    }



    private void initUI() {
        initButton();
        initListView();
        initAdapter();
    }

    private void initButton() {
        toAllPossibleCities=(Button)findViewById(R.id.to_all_possible_cities);
        //According to whether the user permits the app to get the location, he gets two different Activities.
        toAllPossibleCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location){
                    Intent i =new Intent(AllEvents.this, EventNearLocation.class);
                    startActivity(i);
                }else {
                    Intent intent = new Intent(AllEvents.this, AllPossibleCities.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initAdapter() {
        adapter=new CityAdapter(this,events);
    }

    private void initListView() {
        listview = (ListView)findViewById(R.id.all_events_listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllEvents.this, AllInformationsOfAnEvent.class);
                intent.putExtra(getResources().getString(R.string.event_in_intent),events.get(position));
                startActivity(intent);
            }
        });
    }

    private void initDB() {
        db=new ExternDatabase(this);
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


            case R.id.all_possible_cities_add_event:
                Intent intent=new Intent(AllEvents.this,AddEvent.class);
                startActivity(intent);
                break;

            case R.id.all_possible_cities_settings:
                Intent intent2= new Intent(AllEvents.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            //Choosing this icon the user gets to the activity which contains all participating events.
            case R.id.all_possible_cities_to_my_events:
                Intent intent3 = new Intent(AllEvents.this, ParticipatingEvents.class);
                startActivity(intent3);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }

        return true;
    }
}
