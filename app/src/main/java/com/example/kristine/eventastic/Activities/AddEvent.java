package com.example.kristine.eventastic.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity {

    private EditText editCity;
    private EditText editTitle;
    private EditText editDate;
    private EditText editTime;
    private EditText editDefinition;
    private EditText editType;

    private Button enter;

    private ExternDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initDB();
        initUI();
        initDateField();
        initTimeField();
        initClickListener();
    }

    private void initTimeField() {
        editTime.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showTimePickerDialog(v);
                                        }
                                    }
        );
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }



    private void initDateField() {
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    private void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    private void initDB() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        db=new ExternDatabase(databaseReference);
    }


    private void initClickListener() {
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editCity.getText().toString();
                String date = editDate.getText().toString();
                String time = editTime.getText().toString();
                String titel = editTitle.getText().toString();
                String definition = editDefinition.getText().toString();
                String type = editType.getText().toString();

                if(city.equals("")||date.equals("")||time.equals("")||titel.equals("")||definition.equals("")||type.equals("")){
                    return;
                }else {
                    editCity.setText("");
                    editTime.setText("");
                    editTitle.setText("");
                    editDate.setText("");
                    editType.setText("");
                    editDefinition.setText("");

                    addEvent(city, date, time, titel, definition, type);
                }
            }
        });
    }

    private void addEvent(String city, String date, String time, String titel, String definition, String type) {
        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);
        String month;
        String day;
        if(cal.get(Calendar.MONTH)+1<10){
            month=""+0+(cal.get(Calendar.MONTH)+1);
        }else {
            month=""+(cal.get(Calendar.MONTH)+1);
        }
        if(cal.get(Calendar.DAY_OF_MONTH)<10){
            day=""+0+cal.get(Calendar.DAY_OF_MONTH);
        }else {
            day=""+cal.get(Calendar.DAY_OF_MONTH);
        }
        int realDate = Integer.parseInt(""+cal.get(Calendar.YEAR)+month+day);
        Event event = new Event(city,realDate,time,titel,definition,type);
        boolean saved= db.insertItem(event);


    }

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {

            return new Date();
        }
    }

    private void initUI() {
        editCity = (EditText) findViewById(R.id.editCity);
        editDate = (EditText) findViewById(R.id.editDate);
        editTitle = (EditText) findViewById(R.id.editTitel);
        editDefinition = (EditText) findViewById(R.id.editDefinition);
        editTime = (EditText) findViewById(R.id.editTime);
        editType = (EditText) findViewById(R.id.editType);

        enter = (Button) findViewById(R.id.button_add);
    }



    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView textView = (TextView) getActivity().findViewById(R.id.editDate);

            GregorianCalendar date = new GregorianCalendar(year, month, day);
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            String dateString = df.format(date.getTime());

            textView.setText(dateString);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView t=(TextView)getActivity().findViewById(R.id.editTime);
            String hour;
            String min;
            if(hourOfDay<10){
                hour="0"+hourOfDay;
            }
            else {
                hour=""+hourOfDay;
            }
            if(minute<10){
                min="0"+minute;
            }
            else {
                min=""+minute;
            }
            t.setText(hour+":"+min);
        }



    }

}
