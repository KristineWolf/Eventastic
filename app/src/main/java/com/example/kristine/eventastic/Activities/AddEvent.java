package com.example.kristine.eventastic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kristine.eventastic.Database.OwnEventDatabase;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

public class AddEvent extends AppCompatActivity {

    private EditText editCity;
    private EditText editTitle;
    private EditText editDate;
    private EditText editTime;
    private EditText editDefinition;
    private EditText editType;

    private Button enter;

    private OwnEventDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initDB();
        initUI();
        initClickListener();
    }

    private void initDB() {
        db=new OwnEventDatabase(this);
    }

    //hier muss noch überprüft werden, ob auch alle Daten eingegeben wurden..
    private void initClickListener() {
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city =editCity.getText().toString();
                String date = editDate.getText().toString();
                String time= editTime.getText().toString();
                String titel=editTitle.getText().toString();
                String definition =editDefinition.getText().toString();
                String type=editType.getText().toString();

                //dies müss ja noch anders umgesetzt werden und zwar mit einem Spinner sodass des nutzer Tag und Datum auswählen
                //können
                int day=Integer.parseInt(""+date.charAt(0)+date.charAt(1));
                int month=Integer.parseInt(""+date.charAt(3)+date.charAt(4));
                int year=Integer.parseInt(""+date.charAt(5)+date.charAt(6)+date.charAt(7)+date.charAt(8));

                int hour= Integer.parseInt(""+time.charAt(0)+time.charAt(1));
                int min= Integer.parseInt(""+time.charAt(3)+time.charAt(4));

                Event event= new Event(city,day,month,year,hour,min,titel,definition,type);
                db.enterEventInOnlineDB(event);
                Toast.makeText(AddEvent.this, "Event was added.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initUI() {
        editCity=(EditText)findViewById(R.id.editCity);
        editDate=(EditText)findViewById(R.id.editDate);
        editTitle=(EditText)findViewById(R.id.editTitel);
        editDefinition=(EditText)findViewById(R.id.editDefinition);
        editTime=(EditText)findViewById(R.id.editTime);
        editType=(EditText)findViewById(R.id.editType);

        enter=(Button)findViewById(R.id.button);
    }
}
