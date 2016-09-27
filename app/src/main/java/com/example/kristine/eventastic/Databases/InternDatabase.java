package com.example.kristine.eventastic.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.ContemporaryDate;
import com.example.kristine.eventastic.JavaClasses.Event;

import java.util.ArrayList;

//this in the SQlite database which saves all participating events
public class InternDatabase {
    private static final String DATABASE_NAME="participatingevents";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_TABLE ="events";

    public static final String KEY_CITY ="city";
    public static final String KEY_DATE="date";
    public static final String KEY_TIME="time";
    public static final String KEY_TITEL="titel";
    public static final String KEY_DEFINITION="definition";
    public static final String KEY_TYPE="type";

    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public InternDatabase(Context context){
        helper=new DBOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private void open() throws SQLException {
        try {
            db=helper.getWritableDatabase();
        }catch (SQLException e){
            db=helper.getReadableDatabase();
        }
    }

    private void close(){db.close();}

    public void insertEventItem(String city, String date, String time, String defintion, String type, String title){
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_CITY, city);
        values.put(KEY_DATE, ChangeDateFormat.changeFirstIntoDateFormatAfterwardsIntoInteger(date));
        values.put(KEY_TIME, time);
        values.put(KEY_TITEL, title);
        values.put(KEY_DEFINITION, defintion);
        values.put(KEY_TYPE, type);
        db.insert(DATABASE_TABLE, null, values);
        close();
    }

    public ArrayList<Event> getAllEvents(){
        open();
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor=db.query(DATABASE_TABLE, new String[]{
                        KEY_CITY, KEY_DATE, KEY_DEFINITION, KEY_TIME, KEY_TITEL, KEY_TYPE},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String city = cursor.getString(0);
                int date =cursor.getInt(1);
                String definition=cursor.getString(2);
                String time=cursor.getString(3);
                String titel=cursor.getString(4);
                String type =cursor.getString(5);

                //events are still visible and in the database till their day is over
                if(date< ContemporaryDate.getContemporaryDate()){
                    deleteEvent(city,time,titel,type,definition);
                }else {
                    events.add(new Event(city, date, time, titel, definition, type));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return events;
    }



    public void deleteEvent(String city, String time, String titel,String type, String definition){
        String whereClause= KEY_CITY+" =? AND "+KEY_TIME+" =? AND "+KEY_TITEL+ " =? AND "+
                KEY_TYPE+" =? AND "+KEY_DEFINITION+" =?";
        open();
        db.delete(DATABASE_TABLE,whereClause,new String[]{city,time,titel,type,definition});
        close();
    }



    private class DBOpenHelper extends SQLiteOpenHelper {

        private static final String CREATE_DATABASE="create table "+DATABASE_TABLE+
                " ("+KEY_CITY+" string, "
                +KEY_DATE +" integer, "
                +KEY_DEFINITION+" string, "
                +KEY_TIME+" string, "
                +KEY_TITEL+" string, "
                +KEY_TYPE+" string);";

        public DBOpenHelper(Context context,String dbName, SQLiteDatabase.CursorFactory factory, int version){
            super(context,dbName,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
