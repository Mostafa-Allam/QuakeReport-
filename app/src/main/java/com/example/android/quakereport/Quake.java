package com.example.android.quakereport;

public class Quake {

    private   double mag;
    private  String place;
    private   long time;
    private  String url;


    public Quake(){

    }
    public Quake(double mag, long time, String place, String url) {
        this.mag = mag;
        this.place = place;
        this.time = time;
        this.url = url;
    }

    public double getMag() {
        return mag;
    }
    public long getTime() {
        return time;
    }
    public String getPlace() {
        return place;
    }
    public String getUrl() {
        return url;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
