package com.example.kristine.eventastic.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
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
import java.util.Date;

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
        if (arrayList.size() != 0){
            setNextEvent();
            checkNotification();
        }
    }

    private void checkNotification() {
        //heute Datum und nächstes Event Datum
        Date dateTo = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateFormatterDate = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday = dateFormatterDate.format(dateTo);
        String dateEvent = ChangeDateFormat.changeIntoString(arrayList.get(0).getDate());

        // aktuelle Uhrzeit EventUhrzeit
        SimpleDateFormat dateFormatterTime = new SimpleDateFormat("HH:mm");
        String timeNow = dateFormatterTime.format(new Date());
        String timeEvent = arrayList.get(0).getTime();

        //falls Datum und Zeit übereinstimmt mit nächstem Event, Notification senden.
        //Bin noch am überlegen, wie man das am besten löst, dass sie eine Stunde eher abgeschickt wird
        if (dateEvent.equals(dateToday)){
            if (timeEvent.equals(timeNow)){
                sendNotification();
            }
        }
    }

    private void sendNotification() {
        //habe im raw orner verschiedene Töne hinzugefügt. Kannst du gerne auch ändern bzw Töne für Tasten hinzufügen
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.snare_drum_roll);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.to_all_events)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_text_1)+" "+ arrayList.get(0).getTime() + " "+getString(R.string.notification_text_2) + arrayList.get(0).getTitel()+getString(R.string.notification_text_3))
                        .setSound(sound);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void setNextEvent() {
        title = (TextView)findViewById(R.id.information_of_next_event_title);
        title.setText(arrayList.get(0).getTitel());
        city  = (TextView)findViewById(R.id.information_of_next_event_city);
        city.setText(arrayList.get(0).getCity());

        date = (TextView)findViewById(R.id.information_of_next_event_date);
        String dateEvent = ChangeDateFormat.changeIntoString(arrayList.get(0).getDate());
        date.setText(dateEvent);

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