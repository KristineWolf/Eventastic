package com.example.kristine.eventastic.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

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

//this service sets an alarm if an event will take place.
public class NotificationService extends Service {

    private static final int ONE_HOUR_IN_MILLISEC = 3600 * 1000;
    private InternDatabase db;
    private ArrayList<Event> events=new ArrayList<>();

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDB();
        getEvents();
        for (int i=0;i<events.size();i++){
            //for every event on the user's list will be a notification compiled
            scheduleNotification(i);
        }
        return START_STICKY;
    }

        private void scheduleNotification(int i) {
            //get Event-timeStamp in millisec
            SimpleDateFormat dateFormatter = new SimpleDateFormat(getResources().getString(R.string.simple_date_format_1));
            String stringDateEvent = ChangeDateFormat.changeIntoString(events.get(i).getDate());
            String stringTimeEvent = events.get(i).getTime();
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
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,notificationInMillisec,pendingIntent);
            }
        }


    private void getEvents() {
        events.clear();
        events.addAll(db.getAllEvents());
        Collections.sort(events);
    }

    private void initDB() {
        db=new InternDatabase(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
