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


public class AlertReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context,CalendarActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri sound = Uri.parse("android.resource://" +"com.example.kristine.eventastic.JavaClasses"+ "/" + R.raw.xylophone);
        long[] vibrate = { 0, 100, 200, 300 };
        PendingIntent intentToCalendar = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.to_all_events)
                        .setContentTitle(context.getResources().getString(R.string.notification_title))
                        .setContentText(context.getResources().getString(R.string.notification_text))
                        .setSound(sound)
                        .setVibrate(vibrate)
                        .setLights(Color.RED, 3000, 3000)
                        .setContentIntent(intentToCalendar);
        android.support.v4.app.NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle();
        String[] events = new String[6];
        inboxStyle.setBigContentTitle("Hope you have a great Time.");
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setAutoCancel(true);
        mBuilder.setStyle(inboxStyle);

        // gets an instance of the NotificationManager service and builds notification for upcomming event
        NotificationManager myNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(1,mBuilder.build());
    }
}
