package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristine.eventastic.Databases.InternDatabase;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

//this activity presents all the informations of a participating event and allows the user to eliminate the event from his list.
public class AllInformationsOfAParticipatingEvent extends AppCompatActivity {

    private String city, titel, definition, type, date, time;
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

    // puts all the information of an event into a bundle
    private void informationInBundle(Bundle extras) {
        Event event = (Event)extras.get(getResources().getString(R.string.event_in_intent));
        city=event.getCity();
        time=event.getTime();
        titel=event.getTitel();
        date=ChangeDateFormat.changeIntoString(event.getDate());
        definition=event.getDefintion();
        type=event.getType();
    }

    private void initDB() {
        db=new InternDatabase(this);
    }

    //cklicking the button will eliminate the event from the list.
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