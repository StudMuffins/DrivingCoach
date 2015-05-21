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
 * Created by Zoe on 21/05/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "CarDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Car database
        String CREATE_CAR_TABLE = "CREATE TABLE car ( " + "name TEXT PRIMARY KEY, " + "transmission TEXT, " + "fuel TEXT )";

        // create car table
        db.execSQL(CREATE_CAR_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older car table if exists
        db.execSQL("DROP TABLE IF EXISTS car");

        // create fresh car table
        this.onCreate(db);
    }

    // Car table name
    private static final String TABLE_CAR = "car";
    // Car table column names
    private static final String KEY_NAME = "name";
    private static final String KEY_TRANSMISSION = "transmission";
    private static final String KEY_FUEL = "fuel";

    private static final String[] COLUMNS = {KEY_NAME, KEY_TRANSMISSION, KEY_FUEL};

    public void addCar(Car car) {
        Log.d("addCar", car.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create contentValues to add to key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, car.getName());
        values.put(KEY_TRANSMISSION, car.getTransmission());
        values.put(KEY_FUEL, car.getFuel());
        // 3. insert
        db.insert(TABLE_CAR, null, values);

        // 4. close
        db.close();
    }

    public Car getCar(String name) {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_CAR, COLUMNS, " name = ?", new String[] {String.valueOf(name)}, null, null, null, null);

        // 3. if we get results, get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build new car object
        Car car = new Car();
        car.setName(cursor.getString(0));
        car.setTransmission(cursor.getString(1));
        car.setFuel(cursor.getString(2));

        Log.d("getCar("+name+")", car.toString());

        return car;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new LinkedList<Car>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CAR;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build car and add it to the list
        Car car = null;
        if (cursor.moveToFirst()) {
            do {
                car = new Car();
                car.setName(cursor.getString(0));
                car.setTransmission(cursor.getString(1));
                car.setFuel(cursor.getString(2));

                // add car to cars
                cars.add(car);
            } while (cursor.moveToNext());
        }

        Log.d("getAllCars()", cars.toString());

        return cars;
    }

    public String[] getCarNames() {
        String[] carNames = new String[] {};

        // 1. build the query
        String query = "SELECT name FROM" + TABLE_CAR;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. add each name to the list
        if(cursor.moveToFirst()) {
            int i = 0;
            do {
                carNames[i] = cursor.getString(i);
                i++;
            } while (cursor.moveToNext());
        }

        return carNames;
    }

    public int updateCar(Car car) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("transmission", car.getTransmission());
        values.put("fuel", car.getFuel());

        // 3. updating rows
        int i = db.update(TABLE_CAR, values, KEY_NAME+" = ?", new String[] {String.valueOf(car.getName())});

        // 4. close
        db.close();

        return i;
    }

    public void deleteCar(Car car) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_CAR, KEY_NAME+" = ?", new String[] {String.valueOf(car.getName())});

        // 3. close
        db.close();

        Log.d("deleteCar", car.toString());
    }

}
