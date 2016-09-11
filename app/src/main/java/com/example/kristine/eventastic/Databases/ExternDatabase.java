package com.example.kristine.eventastic.Databases;

import android.content.Context;

import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;
import com.firebase.client.Firebase;

import com.google.firebase.database.DatabaseException;




//firebase has been choosed for the online database
public class ExternDatabase {

    public Firebase db;
    private Boolean saved;

    public ExternDatabase(Context context){
        Context c=context;
        Firebase mRef=new Firebase(c.getResources().getString(R.string.firebase_database_url));
        db=mRef.child(c.getResources().getString(R.string.firebase_child));
    }

    //this enters an event in firebase database
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
