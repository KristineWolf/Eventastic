package com.example.kristine.eventastic.JavaClasses;

import com.firebase.client.Firebase;

/**
 * Created by Kristine on 30.08.2016.
 */
public class FirebaseComponent extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
