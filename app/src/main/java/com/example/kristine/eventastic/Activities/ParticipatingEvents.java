package com.example.kristine.eventastic.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kristine.eventastic.Adapter.EventAdapter;
import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.AlertReceiver;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

//this activity presents all events the user wants to participate
public class ParticipatingEvents extends AppCompatActivity {

    private static final int ONE_HOUR_IN_MILLISEC = 3600 * 1000;
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

        for (int i=0;i<arrayList.size();i++){
            //for every event will be a notifications compiled
            scheduleNotification(i);
        }
    }

    private void sortData() {
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void scheduleNotification(int i) {
        //get Event-timeStamp in millisec
        SimpleDateFormat dateFormatter = new SimpleDateFormat(getResources().getString(R.string.simple_date_format_1));
        String stringDateEvent = ChangeDateFormat.changeIntoString(arrayList.get(i).getDate());
        String stringTimeEvent = arrayList.get(i).getTime();
        String stringEventBegin = stringDateEvent+" "+stringTimeEvent;
        long longBeginEvent;
        try {
            Date d = dateFormatter.parse(stringEventBegin);
            longBeginEvent = d.getTime();
        } catch (ParseException e){
            return;
        }
        //1 hour before eventBegin: set notification
        long notificationInMillisec = longBeginEvent-ONE_HOUR_IN_MILLISEC;


        Intent intent = new Intent(getApplicationContext(),AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,notificationInMillisec,pendingIntent);
        }
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
            //choosing this the user will get to all possible events
            case R.id.participating_events_to_all_events:
                Intent intent = new Intent(ParticipatingEvents.this, AllEvents.class);
                startActivity(intent);
                return true;

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
