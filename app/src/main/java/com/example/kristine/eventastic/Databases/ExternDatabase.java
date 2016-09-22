package com.example.kristine.eventastic.Databases;

import android.content.Context;

import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;


import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




//firebase has been choosed for the online database
public class ExternDatabase {

    public FirebaseDatabase db;
    private Boolean saved;
    public DatabaseReference mRef;

    public ExternDatabase(Context context){
        Context c=context;
        db=FirebaseDatabase.getInstance();
         mRef=db.getReference(c.getResources().getString(R.string.firebase_child));

    }

    //this enters an event in firebase database
    public Boolean insertItem(Event event){
        try {
            mRef.push().setValue(event);
            saved=true;
        }catch (DatabaseException e){
            e.printStackTrace();
            saved=false;
        }
        return saved;
    }

}
