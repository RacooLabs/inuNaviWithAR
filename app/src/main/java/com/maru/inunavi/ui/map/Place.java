package com.maru.inunavi.ui.map;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Place {


    private String placeCode = "NONE";
    private String title = "";
    private String sort = "";
    private double distance = 0.0;
    private LatLng location = null;
    private String time = "-";
    private String callNum = "-";

    // 이미지

    public Place(String placeCode, String title, String sort, double distance, LatLng location, String time, String callNum) {
        this.placeCode = placeCode;
        this.title = title;
        this.sort = sort;
        this.distance = distance;
        this.location = location;
        this.time = time;
        this.callNum = callNum;
    }

    public String getPlaceCode() {return placeCode;}

    public void setPlaceCode() {this.placeCode = placeCode;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(int num) {
        this.sort = sort;
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

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time;  }

    public String getCallNum() { return callNum; }

    public void setCallNum(String callNum) { this.callNum = callNum; }

}
