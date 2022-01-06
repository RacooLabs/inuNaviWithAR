package com.maru.inunavi.ui.map;

public class Place {



    private String title;
    private int num;
    private double distance;

    public Place(String title, int num, double distance) {
        this.title = title;
        this.num = num;
        this.distance = distance;
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


}
