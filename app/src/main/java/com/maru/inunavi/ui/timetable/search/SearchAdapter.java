package com.maru.inunavi.ui.timetable.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    private ArrayList<Lecture> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_search_list_lectureName;
        TextView textView_search_list_professor;
        TextView textView_search_list_info;
        TextView textView_search_list_time;
        TextView tita_search_add;

        ViewHolder(Context context, View itemView) {

            super(itemView);

            textView_search_list_lectureName = itemView.findViewById(R.id.textView_search_list_lectureName);
            textView_search_list_professor = itemView.findViewById(R.id.textView_search_list_professor);
            textView_search_list_info = itemView.findViewById(R.id.textView_search_list_info);
            textView_search_list_time = itemView.findViewById(R.id.textView_search_list_time);
            tita_search_add = itemView.findViewById(R.id.tita_search_add);

        }

    }


    public SearchAdapter(ArrayList<Lecture> list) {
        mData = list;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timetable_search_item_list, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(context, view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {

        holder.textView_search_list_lectureName.setText(mData.get(position).getLectureName());
        holder.textView_search_list_professor.setText(mData.get(position).getProfessor());
        holder.textView_search_list_info.setText(mData.get(position).getGrade() + "학년 " + mData.get(position).getCategory() + " " +
                mData.get(position).getScore() + "학점 " + mData.get(position).getNumber());
        holder.textView_search_list_time.setText(mData.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
