package com.example.kristine.eventastic.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private TextView title, city, date, time;

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
        } else {
            actionsWhenNoEventYet();

        }
    }

    private void actionsWhenNoEventYet() {
        title.setVisibility(View.INVISIBLE);
        city.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        TextView nextEvent = (TextView) findViewById(R.id.title_next_event);

        //TODO: evtl noch anders gestalten
        nextEvent.setText(getString(R.string.nothing_on_list));
        nextEvent.setTextSize(20);
        nextEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Calendar.this,AllPossibleCities.class);
                startActivity(intent);
            }
        });
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
        title = (TextView)findViewById(R.id.information_of_next_event_title);
        city  = (TextView)findViewById(R.id.information_of_next_event_city);
        date = (TextView)findViewById(R.id.information_of_next_event_date);
        time = (TextView)findViewById(R.id.information_of_next_event_time);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setDate(java.util.Calendar.getInstance().getTimeInMillis(),true,true);
        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.light_red));

        calendarView.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        calendarView.setSelectedDateVerticalBar(R.color.red);
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


    private void setNextEvent() {
        title.setText(arrayList.get(0).getTitle());
        city.setText(arrayList.get(0).getCity());
        String dateEvent = ChangeDateFormat.changeIntoString(arrayList.get(0).getDate());
        time.setText(arrayList.get(0).getTime());
        date.setText(dateEvent);
        // TODO: dieses Datum im CalendarView markieren. Event setzen geht leider nicht, da es ein default CalendarView ist.
    }


    private void checkNotification() {
        //date today and date nexte event
        Date dateTo = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateFormatterDate = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday = dateFormatterDate.format(dateTo);
        String dateEvent = ChangeDateFormat.changeIntoString(arrayList.get(0).getDate());

        // aktuelle Uhrzeit, EventUhrzeit
        SimpleDateFormat dateFormatterTime = new SimpleDateFormat("HH:mm");
        String timeNow = dateFormatterTime.format(new Date());
        String timeEvent = arrayList.get(0).getTime();

        //falls Datum und Zeit übereinstimmt mit nächstem Event, Notification senden.
        //TODO: Notification eine Stunde eher abschicken
        if (dateEvent.equals(dateToday)){
            if (timeEvent.equals(timeNow)){
                sendNotification();
            }
        }
    }

    private void sendNotification() {
        //habe im raw orner verschiedene Töne hinzugefügt. Kannst du gerne auch ändern bzw Töne für Tasten hinzufügen
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.snare_drum_roll);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.to_all_events)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_text_1)+" "+ arrayList.get(0).getTime() + " "+getString(R.string.notification_text_2) + arrayList.get(0).getTitle()+getString(R.string.notification_text_3))
                        .setSound(sound)
                        .setTicker("EVENTASTIC:  new message");

        Intent toThisEvent = new Intent(this,Calendar.class);

        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
        tStackBuilder.addParentStack(Calendar.class);
        tStackBuilder.addNextIntent(toThisEvent);

        PendingIntent pendingIntentNotification = tStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntentNotification);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


}