package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.R;

public class AllInformationsOfAnEvent extends AppCompatActivity {

    private String city, title, definition, type, date, time;
    private Button add;
    private InternDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_informations_of_an_event);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        informationInBundle(extras);
        initUI();
        initDB();
        initOnClickListener();
    }

    private void informationInBundle(Bundle extras) {
        city=extras.getString("city");
        time=extras.getString("time");
        title=extras.getString("title");
        date=extras.getString("date");
        definition=extras.getString("definition");
        type=extras.getString("type");
    }


    private void initDB() {
        db=new InternDatabase(this);
    }

    private void initOnClickListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    db.deleteEvent(city, time,title,type, definition);
                    db.insertEventItem(city, date, time, definition, type, title);
                    add.setText(R.string.button_added);


            }
        });
    }


    private void initUI() {
        TextView tCity=(TextView)findViewById(R.id.clicked_event_city);
        TextView tTitle=(TextView)findViewById(R.id.clicked_event_title);
        TextView tDefinition=(TextView)findViewById(R.id.clicked_event_definition);
        TextView tType=(TextView)findViewById(R.id.clicked_event_type);
        TextView tDate=(TextView)findViewById(R.id.clicked_event_date);
        TextView tTime=(TextView)findViewById(R.id.clicked_event_time);

        add=(Button)findViewById(R.id.add_event_button);

        tCity.setText(getResources().getString(R.string.city_in)+" "+ city);
        tTitle.setText(title);
        tDefinition.setText(definition);
        tType.setText(type);
        tDate.setText(date);
        tTime.setText(time);
    }
}
