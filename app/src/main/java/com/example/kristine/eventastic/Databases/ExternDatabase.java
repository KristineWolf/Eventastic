package com.example.kristine.eventastic.Databases;

import com.example.kristine.eventastic.JavaClasses.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Kristine on 28.08.2016.
 */

//als online Datenbank wurde firebase ausgewählt
public class ExternDatabase {
    private DatabaseReference db;
    private ArrayList<Event> events = new ArrayList<>();
    private Boolean saved;

    public ExternDatabase(DatabaseReference db){
        this.db=db;
    }



    public Boolean insertItem(Event event){
        try {
            db.child("events").push().setValue(event);
            saved=true;
        }catch (DatabaseException e){
            e.printStackTrace();
            saved=false;
        }
        return saved;
    }


    public ArrayList<Event> getAllEvents(final int date){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getData(dataSnapshot,date);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                getData(dataSnapshot,date);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return events;
    }

    private void getData(DataSnapshot dataSnapshot,int date) {
        events.clear();
        for(DataSnapshot data:dataSnapshot.getChildren()){
            Event event = data.getValue(Event.class);
            if(data.getValue(Event.class).getDate()>=date){


                events.add(event);
            }
        }
    }
}
