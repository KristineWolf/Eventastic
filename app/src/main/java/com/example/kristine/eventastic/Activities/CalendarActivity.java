package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class CalendarActivity extends AppCompatActivity {

    private ArrayList<Event> arrayList = new ArrayList<>();
    private InternDatabase db;
    private EventAdapter adapter;
    private int i;
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
            checkIfNextEventOver(0);
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
                Intent intent=new Intent(CalendarActivity.this,AllEvents.class);
                startActivity(intent);
            }
        });
     }

    private void checkIfNextEventOver(int numbEvents) {
       //current TimeStamp millisec
        long longDateToday = System.currentTimeMillis();

        //TimeStamp next event millisec
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String stringDateEvent = ChangeDateFormat.changeIntoString(arrayList.get(numbEvents).getDate());
        String stringTimeEvent = arrayList.get(numbEvents).getTime();
        String stringEventBegin = stringDateEvent+" "+stringTimeEvent;

        long longEventBegin;
        try {
           Date d = dateFormatter.parse(stringEventBegin);
           longEventBegin = d.getTime();
        } catch (ParseException e){
           return;
        }

        //falls Event schon vorbei, nächsten Eintrag aus der Liste überprüfen.
        if (longEventBegin < longDateToday){
            if (arrayList.size()>numbEvents+1){
                checkIfNextEventOver(numbEvents+1);
            } else noEventOnMyList();
        } else
            setNextEvent(numbEvents);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.calendar_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){

            //back to my events
            case R.id.calendar_to_all_my_events:
                Intent intent = new Intent(CalendarActivity.this, ParticipatingEvents.class);
                startActivity(intent);
                break;

            case android.R.id.home:
                finish();
                break;

        }

        return true;
    }
}