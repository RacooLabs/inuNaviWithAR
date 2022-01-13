package com.maru.inunavi.ui.map;

import com.google.android.gms.maps.model.LatLng;

public class Place {


    private int placeID;
    private String title;
    private String sort;
    private double distance;
    private LatLng location;

    // 이미지
    // 운영시간
    // 전화번호

    public Place(int placeID, String title, String sort, double distance, LatLng location) {
        this.placeID = placeID;
        this.title = title;
        this.sort = sort;
        this.distance = distance;
        this.location = location;
    }

    public int getplaceID() {return placeID;}

    public void setplaceID() {this.placeID = placeID;}

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


}
