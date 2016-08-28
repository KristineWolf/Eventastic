package com.example.kristine.eventastic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kristine.eventastic.JavaClasses.Event;

import java.util.ArrayList;


public class OwnEventDatabase {

    //Konstanten für Datenbankname/-version
    private static final String DATABASE_NAME ="ownevents";
    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_TABLE_ONLINE ="events";

    //Konstanten für Spaltenbezeichner
    public static final String KEY_CITY ="city";
    public static final String KEY_DAY="day";
    public static final String KEY_MONTH="month";
    public static final String KEY_YEAR="year";
    public static final String KEY_HOUR="hour";
    public static final String KEY_MINUTES="minutes";
    public static final String KEY_TITEL="titel";
    public static final String KEY_DEFINITION="definition";
    public static final String KEY_TYPE="type";

    //Instanzen
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    //Konstruktor
    public OwnEventDatabase(Context context){
        helper=new DBOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Aufbau der Verbindung
    private void open() throws SQLException{
        try {
            db=helper.getWritableDatabase();
        }catch (SQLException e){
            db=helper.getReadableDatabase();
        }
    }

    //Trennen der Verbindung
    private void close(){
        db.close();
    }

    //folgendes gilt nur für die online DB --> hier nur als notlösung wird später noch entfernt


    //SQL-Operationen
    public void enterEventInOnlineDB(String city, int day, int month, int year, int hour, int min,
                                     String titel, String definition, String type){
        open();
        ContentValues values=new ContentValues();
        values.put(KEY_CITY, city);
        values.put(KEY_DAY, day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_HOUR, hour);
        values.put(KEY_MINUTES, min);
        values.put(KEY_TITEL, titel);
        values.put(KEY_DEFINITION, definition);
        values.put(KEY_TYPE, type);
        db.insert(DATABASE_TABLE_ONLINE, null, values);
        close();
    }

    public ArrayList<Event> getAllEvents(){
        open();
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor=db.query(DATABASE_TABLE_ONLINE, new String[]{
                        KEY_CITY, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_HOUR, KEY_MINUTES, KEY_TITEL, KEY_DEFINITION, KEY_TYPE},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String city = cursor.getString(0);
                int day=cursor.getInt(1);
                int month=cursor.getInt(2);
                int year=cursor.getInt(3);
                int hour=cursor.getInt(4);
                int minutes=cursor.getInt(5);
                String titel =cursor.getString(6);
                String definition=cursor.getString(7);
                String type =cursor.getString(8);

                events.add(new Event(city,day,month,year,hour,minutes,titel,definition,type));
            }while (cursor.moveToNext());

        }
        close();

        return events;
    }

    /**
    // zunächst überprüfen ob bereits vorhanden
    public boolean insertEvent(Event event){
        if(eventIsNotInDB(event)) {
            ContentValues values = new ContentValues();
            values.put(KEY_CITY, event.getCity());
            values.put(KEY_DAY, event.getDay());
            values.put(KEY_MONTH, event.getMonth());
            values.put(KEY_YEAR, event.getYear());
            values.put(KEY_HOUR, event.getYear());
            values.put(KEY_MINUTES, event.getMinutes());
            values.put(KEY_TITEL, event.getTitel());
            values.put(KEY_DEFINITION, event.getDefintion());
            values.put(KEY_TYPE, event.getType());
            db.insert(DATABASE_TABLE, null, values);
            return true;
        }else {
            return true;
        }
    }

    //nochmal neu und besser
    private boolean eventIsNotInDB(Event event){
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor=db.query(DATABASE_TABLE, new String[]{
                        KEY_CITY, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_HOUR, KEY_MINUTES, KEY_TITEL, KEY_DEFINITION, KEY_TYPE},
                null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String city = cursor.getString(0);
                int day = cursor.getInt(1);
                int month = cursor.getInt(2);
                int year = cursor.getInt(3);
                int hour = cursor.getInt(4);
                int minutes = cursor.getInt(5);
                String titel = cursor.getString(6);
                String definition = cursor.getString(7);
                String type = cursor.getString(8);

                events.add(new Event(city, day, month, year, hour, minutes, titel, definition, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (Event e:events
             ) {
            if(e.equals(event)){return true;}
        }
        return false;
    }

    private ArrayList<Event> removeOldEvent(ArrayList<Event> events, int currentDay, int currentMonth, int currentYear){
        for (Event event:events
             ) {
            if(currentDay==event.getYear()){
                if(currentMonth==event.getMonth()){
                    if(event.getDay()<currentDay){
                        events.remove(event);
                    }

                }else if(currentMonth<event.getMonth()){
                    events.remove(event);
                }

            }else if(currentDay<event.getYear()){
                events.remove(event);
            }

        }
        return events;
    }

    public ArrayList<Event> getAllEvents(int currentDay, int currentMonth, int currentYear){
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor=db.query(DATABASE_TABLE, new String[]{
                KEY_CITY, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_HOUR, KEY_MINUTES, KEY_TITEL, KEY_DEFINITION, KEY_TYPE},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String city = cursor.getString(0);
                int day=cursor.getInt(1);
                int month=cursor.getInt(2);
                int year=cursor.getInt(3);
                int hour=cursor.getInt(4);
                int minutes=cursor.getInt(5);
                String titel =cursor.getString(6);
                String definition=cursor.getString(7);
                String type =cursor.getString(8);

                events.add(new Event(city,day,month,year,hour,minutes,titel,definition,type));
            }while (cursor.moveToNext());

        }
        cursor.close();
        events=removeOldEvent(events, currentDay,currentMonth, currentYear);
        return events;
    }

     **/


    //innere Hilfsklasse
    private class DBOpenHelper extends SQLiteOpenHelper{
        private static final String DATABASE_CREATE_ONLINE ="create table "+DATABASE_TABLE_ONLINE+
                " ("+KEY_CITY+" string, "
                +KEY_DAY+" integer, "
                +KEY_MONTH+" integer, "
                +KEY_YEAR+" integer, "
                +KEY_HOUR+" integer, "
                +KEY_MINUTES+" integer, "
                +KEY_TITEL+" string, "
                +KEY_DEFINITION+ " string, "
                +KEY_TYPE+" string);";

        public DBOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){
            super(context,dbName,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DATABASE_CREATE_ONLINE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
