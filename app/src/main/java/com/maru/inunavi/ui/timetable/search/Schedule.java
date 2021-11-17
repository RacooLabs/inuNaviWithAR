package com.maru.inunavi.ui.timetable.search;

import android.content.Context;
import android.widget.TextView;

import com.maru.inunavi.R;

public class Schedule {

    public String lectureSchedule[] = new String[328];

    public void addSchedule(String lectureName, String professor, String lectureTime, String lectureNumber){

        int startTime;
        int endTime;

        String classTime = lectureTime;
        String [] tokens=classTime.split(",");

        for(int i=0;i<tokens.length; i++){

            String startEndTime[] = tokens[i].split("-");
            startTime = Integer.parseInt(startEndTime[0]);
            endTime = Integer.parseInt(startEndTime[1]);

            for(int j=startTime; j<endTime;j++){
                lectureSchedule[j] = lectureName + " " +professor;
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

    public void setting(TextView[] schedule_textView, Context context){

        for(int i=0;i<schedule_textView.length;i++){
            if(this.lectureSchedule[i] != null){
                schedule_textView[i].setText(lectureSchedule[i]);
                schedule_textView[i].setBackgroundColor(context.getResources().getColor(R.color.black));
            }
        }

    }

}
