package com.example.kristine.eventastic.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kristine.eventastic.Databases.ExternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AutoCompleteTextView editCities;
    private EditText editTitle, editDate, editTime, editDefinition;
    private TextView typeEvent;
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

        db = new ExternDatabase();
    }


    private void initClickListener() {
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = editCities.getText().toString();
                String date = editDate.getText().toString();
                String time = editTime.getText().toString();
                String titel = editTitle.getText().toString();
                String definition = editDefinition.getText().toString();
                String type = typeEvent.getText().toString();


                if (city.equals("") || date.equals("") || time.equals("") || titel.equals("") || definition.equals("") || type.equals("")) {
                    return;
                } else {
                    editCities.setText("");
                    editTime.setText("");
                    editTitle.setText("");
                    editDate.setText("");
                    editDefinition.setText("");
                    addEvent(city, date, time, titel, definition, type);
                }
            }
        });
    }

    private void addEvent(String city, String date, String time, String titel, String definition, String type) {
        Event event = new Event(city, ChangeDateFormat.changeFirstIntoDateFormatAfterwardsIntoInteger(date), time, titel, definition, type);
        boolean saved = db.insertItem(event);

        Toast.makeText(this,"You added the Event '"+titel+"'.",Toast.LENGTH_LONG).show();
    }


    private void initUI() {
        initTypeSpinner();
        initCityAutocomplete();
        editTitle = (EditText) findViewById(R.id.editTitel);
        editDate = (EditText) findViewById(R.id.editDate);
        editTime = (EditText) findViewById(R.id.editTime);
        editDefinition = (EditText) findViewById(R.id.editDefinition);
        enter = (Button) findViewById(R.id.button_add);
    }

    private void initTypeSpinner() {
        Spinner editType = (Spinner) findViewById(R.id.editType);
        ArrayAdapter adapterType = ArrayAdapter.createFromResource(this, R.array.eventtype_array, R.layout.support_simple_spinner_dropdown_item);
        editType.setAdapter(adapterType);
        editType.setOnItemSelectedListener(this);
    }

    private void initCityAutocomplete() {
        editCities = (AutoCompleteTextView)findViewById(R.id.editCity);
        String[] citiesToSelect = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citiesToSelect);
        editCities.setAdapter(adapterCities);
        editCities.setThreshold(1);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeEvent = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }




    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView t = (TextView) getActivity().findViewById(R.id.editTime);
            String hour;
            String min;
            if (hourOfDay < 10) {
                hour = "0" + hourOfDay;
            } else {
                hour = "" + hourOfDay;
            }
            if (minute < 10) {
                min = "0" + minute;
            } else {
                min = "" + minute;
            }
            t.setText(hour + ":" + min);
        }


    }

}
