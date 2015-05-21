package com.studmuffins.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenavartanian on 21/05/15.
 */
public class MySQLiteHelperTrip extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "TripDB";

    public MySQLiteHelperTrip(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Create trip db
        String CREATE_TRIP_TABLE = "CREATE TABLE trip ( " + "name TEXT PRIMARY KEY, " + "date DATETIME, " + "brakescore NUMBER, " + "fuelscore NUMBER, " + "gearscore NUMBER )";
        //Create trip table
        db.execSQL(CREATE_TRIP_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       //if exists, drop old table
        db.execSQL("DROP TABLE IF EXISTS trip");

        this.onCreate(db);
    }
    //Table and column names
    private static final String TABLE_TRIP = "trip";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_BRAKESCORE = "brakescore";
    private static final String KEY_FUELSCORE = "fuelscore";
    private static final String KEY_GEARSCORE = "gearscore";

    private static final String[] COLUMNS = {KEY_NAME, KEY_DATE, KEY_BRAKESCORE, KEY_FUELSCORE, KEY_GEARSCORE};

    public void addTrip(Trip trip){
        Log.d("add trip", trip.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, trip.getName());
        values.put(KEY_DATE, trip.getDate());
        values.put(KEY_BRAKESCORE, trip.getBrakescore());
        values.put(KEY_FUELSCORE, trip.getFuelscore());
        values.put(KEY_GEARSCORE, trip.getGearscore());

        db.insert(TABLE_TRIP, null, values);

        db.close();
    }

    public Trip getTrip(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRIP, COLUMNS, "name = ?", new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Trip trip = new Trip();
        trip.setName(cursor.getString(0));
        trip.setDate(cursor.getString(1));
        trip.setBrakescore(cursor.getString(2));
        trip.setFuelscore(cursor.getString(3));
        trip.setGearscore(cursor.getString(4));

        Log.d("getTrip(" + name + ")", trip.toString());

        return trip;

    }

    public List<Trip> getAllTrips(){
        List<Trip> trips = new LinkedList<Trip>();

        String query = "SELECT  * FROM " + TABLE_TRIP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Trip trip = null;
        if (cursor.moveToFirst()){
            do{
                trip = new Trip();
                trip.setName(cursor.getString(0));
                trip.setDate(cursor.getString(1));
                trip.setBrakescore(cursor.getString(2));
                trip.setFuelscore(cursor.getString(3));
                trip.setGearscore(cursor.getString(4));

                trips.add(trip);
            } while (cursor.moveToNext());

        }
        Log.d("getAllTrips()", trips.toString());

        return trips;

    }

    public int updateTrip(Trip trip) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date", trip.getDate());
        values.put("brakescore", trip.getBrakescore());
        values.put("fuelscore", trip.getFuelscore());
        values.put("gearscore", trip.getGearscore());

        int i = db.update(TABLE_TRIP, values, KEY_NAME + " = ? ", new String[]{String.valueOf(trip.getName())});

        db.close();
        return i;

    }

    public void deleteTrip(Trip trip){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRIP, KEY_NAME + " =? ", new String[]{String.valueOf(trip.getName())});
        db.close();



        Log.d("delete", trip.toString());

    }
}


