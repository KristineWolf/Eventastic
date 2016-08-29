package com.example.kristine.eventastic.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
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
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
/**
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
 **/
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editCity;
    private Spinner editType;
    private EditText editTitle;
    private EditText editDate;
    private EditText editTime;
    private EditText editDefinition;

    private TextView typeEvent;

    private Button enter;

    private ExternDatabase db;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initDB();
        initUI();
        initDateField();
        initTimeField();
        initClickListener();
        /**
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
         **/
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        db = new ExternDatabase(databaseReference);
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
                String type = typeEvent.getText().toString();


                if (city.equals("") || date.equals("") || time.equals("") || titel.equals("") || definition.equals("") || type.equals("")) {
                    return;
                } else {
                    editCity.setText("");
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

        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);
        String month;
        String day;
        if (cal.get(Calendar.MONTH) + 1 < 10) {
            month = "" + 0 + (cal.get(Calendar.MONTH) + 1);
        } else {
            month = "" + (cal.get(Calendar.MONTH) + 1);
        }
        if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "" + 0 + cal.get(Calendar.DAY_OF_MONTH);
        } else {
            day = "" + cal.get(Calendar.DAY_OF_MONTH);
        }
        int realDate = Integer.parseInt("" + cal.get(Calendar.YEAR) + month + day);
        Event event = new Event(city, realDate, time, titel, definition, type);
        boolean saved = db.insertItem(event);

        Toast.makeText(this,"You added the Event '"+titel+"'.",Toast.LENGTH_LONG).show();
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
        initTypeSpinner();

        // Get a reference to the AutoCompleteTextView in the layout
        editCity = (EditText) findViewById(R.id.editCity);
        // Get the string array
        /*String[] cities = getResources().getStringArray(R.array.cities_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, cities);
        editCity.setAdapter(adapterCities);
        */


        editTitle = (EditText) findViewById(R.id.editTitel);
        editDate = (EditText) findViewById(R.id.editDate);
        editTime = (EditText) findViewById(R.id.editTime);
        editDefinition = (EditText) findViewById(R.id.editDefinition);

        enter = (Button) findViewById(R.id.button_add);
    }

    private void initTypeSpinner() {
        editType = (Spinner) findViewById(R.id.editType);
        ArrayAdapter adapterType = ArrayAdapter.createFromResource(this, R.array.eventtype_array, R.layout.support_simple_spinner_dropdown_item);
        editType.setAdapter(adapterType);
        editType.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeEvent = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    /**
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kristine.eventastic.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kristine.eventastic.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    */

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
