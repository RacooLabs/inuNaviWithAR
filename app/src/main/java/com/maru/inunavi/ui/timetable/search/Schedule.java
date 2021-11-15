package com.maru.inunavi.ui.timetable.search;

public class Schedule {

    public String lectureSchedule[] = new String[328];

    public void addSchedule(String lectureTime, String lectureNumber){

        int startTime;
        int endTime;

        String classTime = lectureTime;
        String [] tokens=classTime.split(",");

        for(int i=0;i<tokens.length; i++){

            String startEndTime[] = tokens[i].split("-");
            startTime = Integer.parseInt(startEndTime[0]);
            endTime = Integer.parseInt(startEndTime[1]);

            for(int j=startTime; j<endTime;j++){
                lectureSchedule[j] = lectureNumber;
            }

        }

    }

    public boolean validate(String lectureTime){

        int startTime;
        int endTime;

        String classTime = lectureTime;

        String [] tokens=classTime.split(",");

        for(int i=0;i<tokens.length; i++){

            String startEndTime[] = tokens[i].split("-");
            startTime = Integer.parseInt(startEndTime[0]);
            endTime = Integer.parseInt(startEndTime[1]);

            for(int j=startTime; j<endTime;j++){
                if(lectureSchedule[j] != null){
                    return false;
                }
            }

        }

        return true;

    }

}
