package com.example.kristine.eventastic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kristine.eventastic.Activities.AllInformationsOfAnEvent;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;

/**
 * Created by Kristine on 22.08.2016.
 */
public class CityAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> arrayList;
    private Context context;

    public CityAdapter (Context context, ArrayList<Event> arrayList){
        super(context, R.layout.event, arrayList);
        this.arrayList=arrayList;
        this.context=context;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View v;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.event,null);
        }
        else {
            v=convertView;
        }
        if(arrayList.get(position)!=null){
            TextView titel =(TextView) v.findViewById(R.id.event_titel);
            TextView date =(TextView)v.findViewById(R.id.event_date);
            TextView time =(TextView)v.findViewById(R.id.event_time);
            TextView type =(TextView)v.findViewById(R.id.event_type);

            titel.setText(arrayList.get(position).getTitel());
            String currentlDate= ChangeDateFormat.changeIntoString(arrayList.get(position).getDate());
            date.setText(currentlDate);

            time.setText(arrayList.get(position).getTime());

            type.setText(arrayList.get(position).getType());
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]details=new String[6];

                details[0]=arrayList.get(position).getCity();
                details[1]=arrayList.get(position).getTitel();
                details[2]=arrayList.get(position).getTime();
                details[3]=arrayList.get(position).getType();
                details[4]=ChangeDateFormat.changeIntoString(arrayList.get(position).getDate());
                details[5]=arrayList.get(position).getDefintion();
                seeAllDetailsOfEvent(details);
            }
        });

        return v;
    }

    private void seeAllDetailsOfEvent(String...allInformations){
        Intent intent= new Intent(context,AllInformationsOfAnEvent.class);
        intent.putExtra("city",allInformations[0]);
        intent.putExtra("titel",allInformations[1]);
        intent.putExtra("time",allInformations[2]);
        intent.putExtra("type",allInformations[3]);
        intent.putExtra("date",allInformations[4]);
        intent.putExtra("definition",allInformations[5]);
        context.startActivity(intent);
    }


}
