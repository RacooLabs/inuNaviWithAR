package com.maru.inunavi.ui.satisfied;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.maru.inunavi.ui.timetable.search.Lecture;

import java.util.ArrayList;
import java.util.List;

public class OverviewInfo {

    private String startLectureName = "";
    private String endLectureName = "";
    private String endLectureTime = "";

    private int totalTime = 0;
    private int distance = 0;

    private String directionString = "";
    private List<LatLng> directionList = new ArrayList<>();

    public OverviewInfo(String startLectureName, String endLectureName,  String endLectureTime, int totalTime, int distance, String directionString) {
        this.startLectureName = startLectureName;
        this.endLectureName = endLectureName;
        this.endLectureTime = endLectureTime;
        this.totalTime = totalTime;
        this.distance = distance;
        this.directionString = directionString;


        directionString = directionString.replaceAll(" ", "");
        directionString = directionString.trim();
        String[] directionStringSplit = directionString.split(",");

        for (int i=0;i<directionStringSplit.length;i+=2){

            Log.d("@@@overviewinfo.class : 33", "\""+ directionStringSplit[i+1] + "\"");

            directionList.add(new LatLng(Double.parseDouble(directionStringSplit[i]),
                    Double.parseDouble(directionStringSplit[i+1])));

        }

    }


    public String getStartLectureName() {
        return startLectureName;
    }

    public void setStartLectureName(String startLectureName) {
        this.startLectureName = startLectureName;
    }

    public String getEndLectureName() {
        return endLectureName;
    }

    public void setEndLectureName(String endLectureName) {
        this.endLectureName = endLectureName;
    }

    public String getDirectionString() {
        return directionString;
    }

    public void setDirectionString(String directionString) {
        this.directionString = directionString;
    }

    public List<LatLng> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(ArrayList<LatLng> directionList) {
        this.directionList = directionList;
    }

    public String getEndLectureTime() {
        return endLectureTime;
    }

    public void setEndLectureTime(String endLectureTime) {
        this.endLectureTime = endLectureTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }




}
