package com.example.kristine.eventastic.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CalendarActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_calendar2);
        CalendarView probe =(CalendarView)findViewById(R.id.calendar_probe);

        probe.setSelectedWeekBackgroundColor(getResources().getColor(R.color.light_red));

        probe.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        probe.setSelectedDateVerticalBar(R.color.red);
        probe.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),dayOfMonth+"."+month+"."+year,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
