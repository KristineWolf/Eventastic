package com.example.kristine.eventastic.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import com.example.kristine.eventastic.R;

/**
 * Created by Teresa on 29.08.2016.
 */
public class Calendar extends AppCompatActivity{

    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initUI();
    }

    private void initUI() {
        calendarView = (CalendarView)findViewById(R.id.calendarView);
    }


}
