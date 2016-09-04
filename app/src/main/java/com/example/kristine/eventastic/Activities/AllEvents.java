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
import com.example.kristine.eventastic.Adapter.EventAdapter;
import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.ContemporaryDate;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;

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
        getSettings();
        initDB();
        initUI();
        updateList();
    }

    private void getSettings() {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        location=sharedPreferences.getBoolean("Location",false);
        if(location==false){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Access to your location.");
            builder.setMessage("Change the settings to get events in your neighbourhood.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i= new Intent(AllEvents.this,SettingsActivity.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    private void updateList() {
        events.clear();
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
        toAllPossibleCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllEvents.this,AllPossibleCities.class);
                startActivity(intent);
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
                //intent.putExtra("event",arraylist.get(position));
                intent.putExtra("city",events.get(position).getCity());
                intent.putExtra("titel",events.get(position).getTitel());
                intent.putExtra("time",events.get(position).getTime());
                intent.putExtra("type",events.get(position).getType());
                intent.putExtra("date", ChangeDateFormat.changeIntoString(events.get(position).getDate()));
                intent.putExtra("definition",events.get(position).getDefintion());

                startActivity(intent);
            }
        });
    }

    private void initDB() {
        db=new ExternDatabase();
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
                Intent intent=new Intent(AllEvents.this,AddEvent.class);
                startActivity(intent);
                break;

            //hier wird eine Einstellungsactivity geöffnet
            case R.id.all_possible_cities_settings:
                Intent intent2= new Intent(AllEvents.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            //dadurch kommt der Nutzer zu seinen Veranstaltungen, an denen er teilnehmen will
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
