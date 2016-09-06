package com.example.kristine.eventastic.JavaClasses;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import com.example.kristine.eventastic.Activities.CalendarActivity;
import com.example.kristine.eventastic.R;

/**
 * Created by Teresa on 03.09.2016.
 */
public class AlertReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager myNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context,CalendarActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri sound = Uri.parse("android.resource://" +"com.example.kristine.eventastic.JavaClasses"+ "/" + R.raw.snare_drum_roll);
        long[] vibrate = { 1000, 1000, 1000, 1000, 1000 } ;
        PendingIntent intentToCalendar = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        // Gets an instance of the NotificationManager service
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.to_all_events)
                        .setContentTitle(context.getResources().getString(R.string.notification_title))
                        .setContentText(context.getResources().getString(R.string.notification_text))
                        .setSound(sound)
                        .setVibrate(vibrate)
                        .setLights(Color.RED, 3000, 3000)
                        .setContentIntent(intentToCalendar);

        // Builds the notification and issues it.
        myNotificationManager.notify(1,mBuilder.build());
    }
}
