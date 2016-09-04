package com.example.kristine.eventastic.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
            //Für jedes Event am heutigen Tag wird eine Notification erzeugt.
            scheduleNotification(i);
        }
    }

    private void sortData() {
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void scheduleNotification(int i) {
        Intent intent = new Intent(getApplicationContext(),AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        String stringDateEvent = ChangeDateFormat.changeIntoString(arrayList.get(i).getDate());
        String stringTimeEvent = arrayList.get(i).getTime();
        String stringBeginEvent = stringDateEvent+" " +stringTimeEvent;

        //Event-Datum und Uhrzeit in Millisec
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        long longBeginEvent;
        try {
            Date d = formattedDate.parse(stringBeginEvent);
            longBeginEvent = d.getTime();
        } catch (ParseException e){
            return;
        }
        //1 Stunde vor EventBeginn soll Notification ausgelöst werden
        long notificationInMillisec = longBeginEvent-ONE_HOUR_IN_MILLISEC;
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,notificationInMillisec,pendingIntent);
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
                intent.putExtra(getResources().getString(R.string.key_city),arrayList.get(position).getCity());
                intent.putExtra(getResources().getString(R.string.key_title),arrayList.get(position).getTitel());
                intent.putExtra(getResources().getString(R.string.key_time),arrayList.get(position).getTime());
                intent.putExtra(getResources().getString(R.string.key_type),arrayList.get(position).getType());
                intent.putExtra(getResources().getString(R.string.key_date), ChangeDateFormat.changeIntoString(arrayList.get(position).getDate()));
                intent.putExtra(getResources().getString(R.string.key_definition),arrayList.get(position).getDefintion());

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
            //hier kommt der Nutzer zu allen Events
            case R.id.participating_events_to_all_events:
                Intent intent = new Intent(ParticipatingEvents.this, AllPossibleCities.class);
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
