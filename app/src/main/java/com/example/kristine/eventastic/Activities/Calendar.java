package com.example.kristine.eventastic.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristine.eventastic.Adapter.EventAdapter;
import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Teresa on 29.08.2016.
 */
public class Calendar extends AppCompatActivity{

    private ArrayList<Event> arrayList = new ArrayList<>();
    private InternDatabase db;
    private EventAdapter adapter;

    private TextView title;
    private TextView city;
    private TextView date;
    private TextView time;

    private String df;

    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initDB();
        initUI();
        updateList();
        setNextEvent();
        getCurrentDateTime();
    }

    //zur Hilfe ich hab die Zeit als String gespeichert in der Form hh:mm falls des wichtig ist
    private void getCurrentDateTime() {
        //falls übereinstimmt mit nächstem Event, 1 Stunde vor Beginn Notification senden.
        //noch nicht fertig
        String dateStamp = new SimpleDateFormat("ddmmyyyy").format(java.util.Calendar.getInstance().getTime());
        if (dateStamp.equals(arrayList.get(0).getDate())){
            notification();
        }
    }

    private void notification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.to_all_events)
                        .setContentTitle("hello")
                        .setContentText("hello");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void setNextEvent() {
        title = (TextView)findViewById(R.id.information_of_next_event_title);
        title.setText(arrayList.get(0).getTitel());
        city  = (TextView)findViewById(R.id.information_of_next_event_city);
        city.setText(arrayList.get(0).getCity());
        date = (TextView)findViewById(R.id.information_of_next_event_date);
        //date.setText(arrayList.get(0).getDate());
        //--> funktioniert nicht, App stürzt ab. konnte auf die Schnelle keimen Fehler finden
        // weil ich das Datum als int wert in der Form gespeichert hab: yyyymmdd
        //dadurch lässt sich schneller überprüfen ob eine Veranstaltung schon stattgefunden hat
        //deswegen musst du den int wert in einen String umwandeln mit der ChangeDateFormat klasse
        date.setText(ChangeDateFormat.changeIntoString(arrayList.get(0).getDate()));
        time = (TextView)findViewById(R.id.information_of_next_event_time);
        time.setText(arrayList.get(0).getTime());
    }

    private void updateList() {
        arrayList.clear();
        arrayList.addAll(db.getAllEvents());
        sortData();
    }

    private void initDB() {
        db = new InternDatabase(this);
    }

    private void sortData() {
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void initUI() {
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),dayOfMonth+"."+month+"."+year,Toast.LENGTH_SHORT).show();
            }
        });
        initListAdapter();
    }

    private void initListAdapter() {
        adapter = new EventAdapter(this, arrayList);
    }



}