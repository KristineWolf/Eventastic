package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.sql.Array;
import java.util.ArrayList;

public class AllInformationsOfAnEvent extends AppCompatActivity {

    private TextView city;
    private TextView titel;
    private TextView definition;
    private TextView type;
    private TextView date;
    private TextView time;

    private Button add;

    private InternDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_informations_of_an_event);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        initUI();
        setUI(extras);
        initDB();
        initOnClickListener();
    }




    private void initDB() {
        db=new InternDatabase(this);
    }

    private void initOnClickListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    db.deleteEvent(city.getText().toString(), time.getText().toString(),titel.getText().toString(),type.getText().toString(), definition.getText().toString());
                    db.insertEventItem(city.getText().toString(), date.getText().toString(), time.getText().toString(), definition.getText().toString(), type.getText().toString(), titel.getText().toString());
                    add.setText(R.string.button_added);


            }
        });
    }



    private void setUI(Bundle extras) {
        city.setText(extras.getString("city"));
        titel.setText(extras.getString("titel"));
        definition.setText(extras.getString("definition"));
        type.setText(extras.getString("type"));
        date.setText(extras.getString("date"));
        time.setText(extras.getString("time"));


    }



    private void initUI() {
        city=(TextView)findViewById(R.id.clicked_event_city);
        titel=(TextView)findViewById(R.id.clicked_event_titel);
        definition=(TextView)findViewById(R.id.clicked_event_definition);
        type=(TextView)findViewById(R.id.clicked_event_type);
        date=(TextView)findViewById(R.id.clicked_event_date);
        time=(TextView)findViewById(R.id.clicked_event_time);

        add=(Button)findViewById(R.id.add_event_button);
    }
}
