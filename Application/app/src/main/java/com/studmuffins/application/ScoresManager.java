package com.studmuffins.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenavartanian on 20/05/15.
 */
public class ScoresManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Table name
    private static final String DATABASE_NAME = "Scores",

    //initialize table and column names
    TABLE_SCORES = "Scores",
    KEY_VEHICLE = "Vehicle",
    KEY_TRIPDATE = "Tripdate",
    KEY_BRAKESCORE = "Brakescore",
    KEY_GEARSCORE = "Gearscore",
    KEY_FUELSCORE = "Fuelscore";

    //Constructor. Opens or builds db

    public ScoresManager (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SCORES + "(" + KEY_VEHICLE + "STRING PRIMARY KEY, " + KEY_TRIPDATE + " NUMBERS, " + KEY_BRAKESCORE + " NUMBERS, " + KEY_GEARSCORE + "NUMBERS, " + KEY_FUELSCORE + "NUMBERS, ");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES );

            onCreate(db);
        }
    public void createScoresDB(ScoresDB scoresDB){
        SQLiteDatabase db = getWritableDatabase();

        //insert new values
        ContentValues values = new ContentValues();
        values.put(KEY_VEHICLE, scoresDB.getVehicle());
        values.put(KEY_TRIPDATE, scoresDB.getTripdate());
        values.put(KEY_BRAKESCORE, scoresDB.getBrakeScore());
        values.put(KEY_GEARSCORE, scoresDB.getGearScore());
        values.put(KEY_FUELSCORE, scoresDB.getFuelScore());

        db.insert(TABLE_SCORES, null, values);
        db.close();

    }
        public ScoresManager getVehicle(String _vehicle){
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(TABLE_SCORES, new String[], {KEY_VEHICLE, KEY_TRIPDATE, KEY_BRAKESCORE, KEY_GEARSCORE, KEY_FUELSCORE});
                   if (cursor != null)
                       cursor.moveToFirst();

            ScoresDB scoresDB = new ScoresDB(Integer.parseInt(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            db.close();
            cursor.close();

            return ScoresManager;
        }


    }
