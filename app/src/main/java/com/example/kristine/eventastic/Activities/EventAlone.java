package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristine.eventastic.R;

import org.w3c.dom.Text;

public class EventAlone extends AppCompatActivity {


    private TextView city;
    private TextView titel;
    private TextView definition;
    private TextView type;
    private TextView date;
    private TextView time;

    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_alone);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        initUI();
        setUI(extras);
        initOnClickListener();
    }

    private void initOnClickListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
