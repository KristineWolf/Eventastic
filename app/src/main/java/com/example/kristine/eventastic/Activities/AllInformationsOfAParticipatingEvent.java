package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.R;

public class AllInformationsOfAParticipatingEvent extends AppCompatActivity {

    private String city;
    private String titel;
    private String definition;
    private String type;
    private String date;
    private String time;

    private Button delete;

    private InternDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_informations_of_aparticipating_event);
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
        titel=extras.getString("titel");
        date=extras.getString("date");
        definition=extras.getString("definition");
        type=extras.getString("type");
    }


    private void initDB() {
        db=new InternDatabase(this);
    }

    private void initOnClickListener() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEvent(city,time,titel,type,definition);
                Intent intent = new Intent(AllInformationsOfAParticipatingEvent.this, ParticipatingEvents.class);
                startActivity(intent);
                delete.setText(R.string.button_deleted);
                String messageDeleted = getResources().getString(R.string.toast_deleted);
                Toast toast = Toast.makeText(AllInformationsOfAParticipatingEvent.this,messageDeleted, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }







    private void initUI() {
        TextView tCity=(TextView)findViewById(R.id.information_of_participating_event_city);
        TextView tTitel=(TextView)findViewById(R.id.information_of_participating_event_title);
        TextView tDefinition=(TextView)findViewById(R.id.information_of_participating_event_definition);
        TextView tType=(TextView)findViewById(R.id.information_of_participating_event_type);
        TextView tDate=(TextView)findViewById(R.id.information_of_participating_event_date);
        TextView tTime=(TextView)findViewById(R.id.information_of_participating_event_time);

        delete=(Button)findViewById(R.id.delete_event_button);

        tCity.setText("in "+city);
        tTitel.setText(titel);
        tDefinition.setText(definition);
        tType.setText(type);
        tDate.setText(date);
        tTime.setText(time);
    }
}