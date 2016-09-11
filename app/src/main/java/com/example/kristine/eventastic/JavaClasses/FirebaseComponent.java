package com.example.kristine.eventastic.JavaClasses;

import com.firebase.client.Firebase;

//this class is important to get a connection to the firebase database
public class FirebaseComponent extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
