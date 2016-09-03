package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Teresa on 29.08.2016.
 */
public class Calendar extends AppCompatActivity {

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
            checkIfFirstEventOver();
        } else {
            noEventOnMyList();
        }
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

     private void noEventOnMyList() {
        title.setVisibility(View.INVISIBLE);
        city.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        TextView nextEvent = (TextView) findViewById(R.id.title_next_event);
        calendarView.setDate(System.currentTimeMillis(),false,true);

        //TODO: noch anders gestalten
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

     private void checkIfFirstEventOver() {
        //date today and date next event
       SimpleDateFormat dateFormatterDate = new SimpleDateFormat("dd.MM.yyyy");
       Date longDateToday = java.util.Calendar.getInstance().getTime();
       String stringDateToday = dateFormatterDate.format(longDateToday);
       String stringDateEvent = ChangeDateFormat.changeIntoString(arrayList.get(0).getDate());

       //event-begin in Millisec
       SimpleDateFormat formattedTime = new SimpleDateFormat("HH:mm");
       String stringTimeEvent = arrayList.get(0).getTime();
       long longEventBeginTime;
       try {
           Date d = formattedTime.parse(stringTimeEvent);
           longEventBeginTime = d.getTime();
       } catch (ParseException e){
           return;
       }

        //falls Event heute, aber schon vorbei, zweiten (1) Eintrag aus der Liste wählen. Sonst der ersten (0)
        if (stringDateEvent.equals(stringDateToday)){
            if (longEventBeginTime < System.currentTimeMillis())
                setNextEvent(1);
        } else
            setNextEvent(0);
     }

    private void setNextEvent(int event) {
        title.setText(arrayList.get(event).getTitel());
        city.setText(arrayList.get(event).getCity());
        String dateEvent = ChangeDateFormat.changeIntoString(arrayList.get(event).getDate());
        time.setText(arrayList.get(event).getTime());
        date.setText(dateEvent);

        //Setzen des CalenderViews auf das nächste Datum
        String stringDate = dateEvent;
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(stringDate);
            long milliseconds = d.getTime();
            calendarView.setDate(milliseconds);
        } catch (ParseException et){
            return;
        }
    }
}