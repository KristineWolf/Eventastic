package com.example.kristine.eventastic.JavaClasses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.kristine.eventastic.Activities.CalendarActivity;
import com.example.kristine.eventastic.Activities.ParticipatingEvents;
import com.example.kristine.eventastic.R;


public class AlertReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context,CalendarActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri sound = Uri.parse("android.resource://" +"com.example.kristine.eventastic.JavaClasses"+ "/" + R.raw.xylophone);

        // setup actions
        Intent calendarIntent = new Intent(context, CalendarActivity.class);
        PendingIntent piCalendar = PendingIntent.getActivity(context,1,calendarIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action1 = new NotificationCompat.Action.Builder(R.drawable.calendar, context.getResources().getString(R.string.calendar), piCalendar).build();

        Intent eventsIntent = new Intent(context, ParticipatingEvents.class);
        PendingIntent piEvents = PendingIntent.getActivity(context,1,eventsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.drawable.to_particpating_events, context.getResources().getString(R.string.participating_events), piEvents).build();


        // setup notification
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_fox)
                        .setContentTitle(context.getResources().getString(R.string.notification_title))
                        .setContentText(context.getResources().getString(R.string.notification_text))
                        .setSound(sound)
                        .setLights(Color.RED, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getResources().getString(R.string.notification_text_large)))
                        .setAutoCancel(true)
                        .addAction(action1)
                        .addAction(action2);

        Notification notification = mBuilder.build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_VIBRATE;

        // gets an instance of the NotificationManager service and builds notification for upcomming event
        NotificationManager myNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(1,notification);

    }
}
