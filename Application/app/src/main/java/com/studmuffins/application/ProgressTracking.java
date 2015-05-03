package com.studmuffins.application;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Jono and Zoe on 1/05/2015.
 */
public class ProgressTracking extends Fragment {
    private HashMap <String, Integer> fuelMap = new HashMap<String, Integer>();
    private HashMap <String, Integer> gearMap = new HashMap<String, Integer>();
    private HashMap <String, Integer> brakeMap = new HashMap<String, Integer>();

    private  String dateKey;
    private String score_gear = "11";
    private TextView text_gear;
    private String score_fuel = "50";
    private TextView text_fuel;

    //constructor
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_tracking, container, false);

        //setScoreStrings();
        text_gear = (TextView) view.findViewById(R.id.gearScore_text);
        text_gear.setText(score_gear);
        text_fuel = (TextView) view.findViewById(R.id.fuelScore_text);
        text_fuel.setText(score_fuel);

        return view;
    }

    // Get and Set the fuel score
    public int getFuelValue() {
        //fuelMap.put(String, //Value);
        return fuelMap.get(dateKey);
    }
    public void setFuelValue(int fuelScore) {
        fuelMap.put(dateKey, fuelScore);
    }

    // Get and Set the gear changing score
    public int getGearValue(){
        //gearMap.put(String, //Value);
        return gearMap.get(dateKey);
    }
    public void setGearValue(int gearScore) {
        gearMap.put(dateKey, gearScore);
    }

    // Get and Set the braking score
    public int getBrakeValue(){
        //brakeMap.put(String, //Value);
        return brakeMap.get(dateKey);
    }
    public void setBrakeValue(int brakeScore) {
        brakeMap.put(dateKey, brakeScore);
    }

    public void setScoreStrings() {
        score_gear = Integer.toString(getGearValue());
        score_fuel = Integer.toString(getFuelValue());
    }
}
