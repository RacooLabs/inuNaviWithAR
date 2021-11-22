package com.maru.inunavi.ui.timetable.search;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maru.inunavi.R;

public class Schedule {

    public Lecture lectureSchedule[] = new Lecture[328];

    public void addSchedule(Lecture lecture){

        int startTime;
        int endTime;

        String classTime = lecture.getClasstime();
        String [] tokens=classTime.split(",");

        for(int i=0;i<tokens.length; i++){

            String startEndTime[] = tokens[i].split("-");
            startTime = Integer.parseInt(startEndTime[0]);
            endTime = Integer.parseInt(startEndTime[1]);

            for(int j=startTime; j<endTime;j++){
                lectureSchedule[j] = lecture;
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


    public void setting(TextView[] schedule_textView, Context context, RelativeLayout container){

        int positionX = 0;
        int positionY = 0;

        int sameCount = 1;

        for(int i=0;i<schedule_textView.length;i++){

            if(this.lectureSchedule[i] != null){

            /*schedule_textView[i].setText(lectureSchedule[i].getLecturename());
            schedule_textView[i].setBackgroundColor(Color.BLACK);*/

                if (sameCount == 1) {
                    positionX = (int) schedule_textView[i].getX();
                    positionY = (int) schedule_textView[i].getY();
                }

                if (this.lectureSchedule[i+1] == null){

                    TextView view1 = new TextView(context);
                    view1.setText(lectureSchedule[i].getLecturename());
                    view1.setTextColor(Color.WHITE);
                    view1.setBackgroundColor(Color.BLACK);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(schedule_textView[i].getWidth(),
                            schedule_textView[i].getHeight() * sameCount);
                    lp.gravity = Gravity.CENTER;

                    view1.setX(positionX);
                    view1.setY(positionY);
                    view1.setLayoutParams(lp);
                    container.addView(view1);

                    sameCount = 1;

                }else if (!this.lectureSchedule[i].getNumber().equals(this.lectureSchedule[i+1].getNumber())){

                    TextView view1 = new TextView(context);
                    view1.setText(lectureSchedule[i].getLecturename());
                    view1.setTextColor(Color.WHITE);
                    view1.setBackgroundColor(Color.BLACK);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(schedule_textView[i].getWidth(),
                            schedule_textView[i].getHeight() * 4);
                    lp.gravity = Gravity.CENTER;

                    view1.setX(positionX);
                    view1.setY(positionY);
                    view1.setLayoutParams(lp);
                    container.addView(view1);

                    sameCount = 1;

                }else{

                    sameCount++;
                    Log.d("@@@ schedule 92", " error");

                }

            }



        }

    }

}
