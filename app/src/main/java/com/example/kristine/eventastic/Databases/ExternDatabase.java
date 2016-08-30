package com.example.kristine.eventastic.Databases;

import com.example.kristine.eventastic.JavaClasses.Event;
import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kristine on 28.08.2016.
 */


//als online Datenbank wurde firebase ausgewählt
public class ExternDatabase {

    public Firebase db;
    private Boolean saved;

    public ExternDatabase(){
        Firebase mRef=new Firebase("https://androidprojekt16-fd51a.firebaseio.com/");
        db=mRef.child("events");
    }

    public Boolean insertItem(Event event){
        try {
            db.push().setValue(event);
            saved=true;
        }catch (DatabaseException e){
            e.printStackTrace();
            saved=false;
        }
        return saved;
    }

}
