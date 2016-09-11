package com.example.kristine.eventastic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import java.util.ArrayList;


public class CityAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> arrayList;
    private Context context;

    public CityAdapter (Context context, ArrayList<Event> arrayList){
        super(context, R.layout.all_events, arrayList);
        this.arrayList=arrayList;
        this.context=context;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View v;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.all_events,null);
        }
        else {
            v=convertView;
        }
        if(arrayList.get(position)!=null){
            TextView titel =(TextView) v.findViewById(R.id.all_event_titel);
            TextView date =(TextView)v.findViewById(R.id.all_event_date);
            TextView time =(TextView)v.findViewById(R.id.all_event_time);
            TextView type =(TextView)v.findViewById(R.id.all_event_short_description);



            titel.setText(arrayList.get(position).getTitel());
            date.setText(ChangeDateFormat.changeIntoString(arrayList.get(position).getDate()));

            time.setText(arrayList.get(position).getTime());
            type.setText(context.getResources().getString(R.string.a_)+" "+arrayList.get(position).getType()+" "+context.getResources().getString(R.string.in_)+" "+arrayList.get(position).getCity());
        }


        return v;
    }




}
