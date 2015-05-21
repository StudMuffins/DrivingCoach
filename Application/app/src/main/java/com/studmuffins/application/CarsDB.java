package com.studmuffins.application;

/**
 * Created by lenavartanian on 20/05/15.
 */

//Class used for database interaction

public class CarsDB {

    private String _username, _vehicle;

    //Properties and types of data
    public CarsDB (String username, String vehicle){
        _username = username;
        _vehicle = vehicle;
    }

    public String get_username() {return _username;}
    public String get_vehicle() {return _vehicle;}

}
