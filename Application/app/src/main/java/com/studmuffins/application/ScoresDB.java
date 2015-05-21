package com.studmuffins.application;

/**
 * Created by lenavartanian on 20/05/15.
 */
public class ScoresDB {

    //Class used for database interaction
    private String _vehicle, _tripdate, _brakescore, _gearscore, _fuelscore;

    //Properites and data types
    public ScoresDB (String vehicle, String tripdate, String brakescore, String gearscore, String fuelscore) {
        _vehicle = vehicle;
        _tripdate = tripdate;
        _brakescore = brakescore;
        _gearscore = gearscore;
        _fuelscore = fuelscore;
    }

    public String getVehicle() {return _vehicle;}
    public String getTripdate() {return _tripdate;}
    public String getBrakeScore() {return _brakescore;}
    public String getGearScore() {return _gearscore;}
    public String getFuelScore() {return _fuelscore;}
}
