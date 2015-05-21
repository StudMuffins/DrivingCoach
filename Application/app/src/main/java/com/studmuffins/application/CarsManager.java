package com.studmuffins.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


/**
 * Created by lenavartanian on 20/05/15.
 */
public class CarsManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Table name
    private static final String DATABASE_NAME = "Cars",

    //Table and column names
    TABLE_CARS = "Cars",
    KEY_USERNAME = "Username",
    KEY_VEHICLE = "Vehicle";

    //Constructor. Opens or builds db

    public CarsManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CARS + "(" + KEY_USERNAME + "STRING PRIMARY " + KEY_VEHICLE + "STRING");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF TABLE EXISTS " + TABLE_CARS);

            onCreate(db);

        }

    public void createCarsDB(CarsDB carsDB) {
        SQLiteDatabase db = getWritableDatabase();

        //insert new values
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, carsDB.get_username());
        values.put(KEY_VEHICLE, carsDB.get_vehicle());

        db.insert(TABLE_CARS, null, values);
        db.close();

    }

    public CarsManager getUsername(String _username){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARS, new String[] {KEY_USERNAME, KEY_VEHICLE});

        if(cursor != null)
            cursor.moveToFirst();

      CarsDB carsDB = new CarsDB(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return CarsDB;

    }

    }

