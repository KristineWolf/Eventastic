package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

//this activity presents all informations of an event and allows the user to participate by clicking on a button.
public class AllInformationsOfAnEvent extends AppCompatActivity {

    private String city, titel, definition, type, date, time;
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

    // puts all the information of an event into a bundle
    private void informationInBundle(Bundle extras) {
        Event event = (Event)extras.get(getResources().getString(R.string.event_in_intent));
        city=event.getCity();
        time=event.getTime();
        titel=event.getTitel();
        date= ChangeDateFormat.changeIntoString(event.getDate());
        definition=event.getDefintion();
        type=event.getType();
    }

    private void initDB() {
        db=new InternDatabase(this);
    }

    //clicking the button will save the event in the SQlite database
    private void initOnClickListener() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.xylophone2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEvent(city, time,titel,type, definition);
                db.insertEventItem(city, date, time, definition, type, titel);
                add.setText(R.string.button_added);
                add.setClickable(false);
                add.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mediaPlayer.start();
            }
        });
    }


    private void initUI() {
        TextView tCity=(TextView)findViewById(R.id.clicked_event_city);
        TextView tTitel=(TextView)findViewById(R.id.clicked_event_titel);
        TextView tDefinition=(TextView)findViewById(R.id.clicked_event_definition);
        TextView tType=(TextView)findViewById(R.id.clicked_event_type);
        TextView tDate=(TextView)findViewById(R.id.clicked_event_date);
        TextView tTime=(TextView)findViewById(R.id.clicked_event_time);

        add=(Button)findViewById(R.id.add_event_button);

        tCity.setText(getResources().getString(R.string.city_in)+" "+city);
        tTitel.setText(titel);
        tDefinition.setText(definition);
        tType.setText(type);
        tDate.setText(date);
        tTime.setText(time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.empty_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
