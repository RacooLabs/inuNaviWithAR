package com.maru.inunavi.ui.map;

import com.google.android.gms.maps.model.LatLng;

public class Place {



    private String title;
    private int num;
    private double distance;
    private LatLng location;

    public Place(String title, int num, double distance, LatLng location) {
        this.title = title;
        this.num = num;
        this.distance = distance;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


}
