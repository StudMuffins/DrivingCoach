package com.studmuffins.application;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Jono and Zoe on 1/05/2015.
 */
public class ProgressTracking extends BaseActivity {
    private String[] navMenuTitles;
    private HashMap <String, String> scoreMap = new HashMap<String, String>();

    private String gear = "gear";
    private String fuel = "fuel";
    private String brake = "brake";
    private String fuelScore, gearScore, brakeScore;
    private TextView text_gear;
    private TextView text_fuel;
    private TextView text_brake;
    private LinearLayout fuelLayout, gearLayout, brakeLayout;
    MySQLiteHelperTrip db;

    // constructor
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_tracking);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        set(navMenuTitles);

        // Set String values
        setFuelScore();
        setBrakeScore();
        setGearScore();

        // Set background colours
        setBrakeColour();
        setFuelColour();
        setGearColour();

        // Set text in text fields
        text_gear = (TextView) findViewById(R.id.gearScore_text);
        text_gear.setText(getGearScore());
        text_fuel = (TextView) findViewById(R.id.fuelScore_text);
        text_fuel.setText(getFuelScore());
        text_brake = (TextView) findViewById(R.id.brakeScore_text);
        text_brake.setText(getBrakeScore());
    }

    // Get and Set the fuel score
    public String getFuelScore() {
        return scoreMap.get(fuel);
    }
    public void setFuelScore() {
        fuelScore = Integer.toString((30 + (int)(Math.random()*71)));
        scoreMap.put(fuel, fuelScore);
    }

    // Get and Set the gear changing score
    public String getGearScore(){
        return scoreMap.get(gear);
    }
    public void setGearScore() {
        gearScore = Integer.toString((30 + (int)(Math.random()*71)));
        scoreMap.put(gear, gearScore);
    }

    // Get and Set the braking score
    public String getBrakeScore(){
        return scoreMap.get(brake);
    }
    public void setBrakeScore() {
        brakeScore = Integer.toString((30 + (int)(Math.random()*71)));
        scoreMap.put(brake, brakeScore);
    }

    public void setGearColour() {
        // Connect to layouts through id reference
        gearLayout = (LinearLayout) findViewById(R.id.gear_layout);
        int i = Integer.parseInt(getGearScore());
        if(i < 50)
            gearLayout.setBackgroundColor(Color.parseColor("#FF0A0A"));
        else if(i >= 50 && i < 65)
            gearLayout.setBackgroundColor(Color.parseColor("#FF6C0A"));
        else if(i >= 65 && i < 80)
            gearLayout.setBackgroundColor(Color.parseColor("#F7F445"));
        else
            gearLayout.setBackgroundColor(Color.parseColor("#23FF0A"));
    }

    public void setBrakeColour() {
        brakeLayout = (LinearLayout) findViewById(R.id.brake_layout);
        int i = Integer.parseInt(getBrakeScore());
        if(i < 50)
            brakeLayout.setBackgroundColor(Color.parseColor("#FF0A0A"));
        else if(i >= 50 && i < 65)
            brakeLayout.setBackgroundColor(Color.parseColor("#FF6C0A"));
        else if(i >= 65 && i < 80)
            brakeLayout.setBackgroundColor(Color.parseColor("#F7F445"));
        else
            brakeLayout.setBackgroundColor(Color.parseColor("#23FF0A"));
    }

    public void setFuelColour() {
        fuelLayout = (LinearLayout) findViewById(R.id.fuel_layout);
        int i = Integer.parseInt(getFuelScore());
        if(i < 50)
            fuelLayout.setBackgroundColor(Color.parseColor("#FF0A0A"));
        else if(i >= 50 && i < 65)
            fuelLayout.setBackgroundColor(Color.parseColor("#FF6C0A"));
        else if(i >= 65 && i < 80)
            fuelLayout.setBackgroundColor(Color.parseColor("#F7F445"));
        else
            fuelLayout.setBackgroundColor(Color.parseColor("#23FF0A"));
    }

}
