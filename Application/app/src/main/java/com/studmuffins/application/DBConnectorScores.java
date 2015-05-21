package com.studmuffins.application;

import java.sql.*;


/**
 * Created by lenavartanian on 20/05/15.
 */
public class DBConnectorScores{

    Connection conn = null;

    public static Connection ConnectorDB(){

        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\lenavartanian\\ScoresDB");

            return conn;
        }
        catch (Exception e) {
            System.out.println("Error. Try again. ");
            return null;

        }
    }
}