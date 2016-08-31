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

    private TextView city;
    private TextView titel;
    private TextView definition;
    private TextView type;
    private TextView date;
    private TextView time;

    private Button delete;

    private InternDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_informations_of_aparticipating_event);
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEvent(city.getText().toString(),time.getText().toString(),titel.getText().toString(),type.getText().toString(),definition.getText().toString());
                Intent intent = new Intent(AllInformationsOfAParticipatingEvent.this, ParticipatingEvents.class);
                startActivity(intent);
                delete.setText(R.string.button_deleted);
                String messageDeleted = getResources().getString(R.string.toast_deleted);
                Toast toast = Toast.makeText(AllInformationsOfAParticipatingEvent.this,messageDeleted, Toast.LENGTH_LONG);
                toast.show();
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
        city=(TextView)findViewById(R.id.information_of_participating_event_city);
        titel=(TextView)findViewById(R.id.information_of_participating_event_title);
        definition=(TextView)findViewById(R.id.information_of_participating_event_definition);
        type=(TextView)findViewById(R.id.information_of_participating_event_type);
        date=(TextView)findViewById(R.id.information_of_participating_event_date);
        time=(TextView)findViewById(R.id.information_of_participating_event_time);

        delete=(Button)findViewById(R.id.delete_event_button);
    }
}
