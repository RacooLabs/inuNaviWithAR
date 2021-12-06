package com.maru.inunavi.ui.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.Lecture;


import java.util.List;


public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {

    private List<Lecture> mData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView textView_recommend_lectureName;
        public TextView textView_recommend_professor;
        public TextView textView_recommend_info;
        public TextView textView_recommend_time;
        public TextView textView_recommend_add;

        public TextView textView_subtitle;


        public MyViewHolder(View v,int viewType) {

            super(v);

            switch (viewType){
                case 0:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

                case 1:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

                case 2:

                    textView_recommend_lectureName = (TextView) v.findViewById(R.id.textView_recommend_lectureName);
                    textView_recommend_professor = (TextView) v.findViewById(R.id.textView_recommend_professor);
                    textView_recommend_info = (TextView) v.findViewById(R.id.textView_recommend_info);
                    textView_recommend_time = (TextView) v.findViewById(R.id.textView_recommend_time);

                    break;

                default:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

            }

        }

    }

    public RecommendAdapter(List<Lecture> mData) {

        this.mData = mData;

    }


    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LinearLayout v;

        switch (viewType){
            case 0:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;

            case 1:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;

            case 2:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview, parent, false);

                break;

            default:
                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;
        }

        MyViewHolder vh = new MyViewHolder(v,viewType);
        return vh;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if (mData.get(position).getLecturename().equals("거리 맞춤 추천")){
            holder.textView_subtitle.setText("거리 맞춤 추천");

        } else if (mData.get(position).getLecturename().equals("개인 맞춤 추천")){
            holder.textView_subtitle.setText("개인 맞춤 추천");

        } else {

            holder.textView_recommend_lectureName.setText(mData.get(position).getLecturename());
            holder.textView_recommend_professor.setText(mData.get(position).getProfessor());
            holder.textView_recommend_info.setText(mData.get(position).getGrade() + "학년 " + mData.get(position).getCategory() + " " +
                    mData.get(position).getPoint() + "학점 " + mData.get(position).getNumber());
            holder.textView_recommend_time.setText(mData.get(position).getClasstime_raw());

        }

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public int getItemViewType(int position) {

        if (mData.get(position).getLecturename().equals("거리 맞춤 추천")) {
            return 0;
        } else if (mData.get(position).getLecturename().equals("개인 맞춤 추천")) {
            return 1;
        } else {
            return 2;
        }

    }

}
