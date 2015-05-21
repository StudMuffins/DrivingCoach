package com.studmuffins.application;

/**
 * Created by lenavartanian on 21/05/15.
 */
public class Trip {
    private String name;
    private String date;
    private String brakescore;
    private String fuelscore;
    private String gearscore;

    public Trip(){}

    public Trip(String name, String date, String brakescore, String fuelscore, String gearscore){
        super();
        this.name = name;
        this.date = date;
        this.brakescore = brakescore;
        this.fuelscore = fuelscore;
        this.gearscore = gearscore;

    }

    @Override
    public String toString(){
        return "Car [name= " + name + ", date=" + date + ", brakescore= " + brakescore + ", fuelscore= " + fuelscore + ", gearscore= " + gearscore + "]";
    }

    public String getName(){return name;}
    public String getDate(){return date;}
    public String getBrakescore() {return brakescore;}
    public String getFuelscore() {return fuelscore;}
    public String getGearscore() {return gearscore;}

    public void setName(String name){this.name = name;}
    public void setDate(String date){this.date = date;}
    public void setBrakescore(String brakescore){this.brakescore = brakescore;}
    public void setFuelscore(String fuelscore){this.fuelscore = fuelscore;}
    public void setGearscore(String gearscore){this.gearscore = gearscore;}


    }

