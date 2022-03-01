/*
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

public class MajorAdapter extends RecyclerView.Adapter<com.maru.inunavi.ui.timetable.search.MajorAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private com.maru.inunavi.ui.timetable.SignUpMajorAdapter.OnItemClickListener mListener = null ;

    public void setOnItemClickListener(com.maru.inunavi.ui.timetable.SignUpMajorAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    private ArrayList<String> mData = null;


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_setting_list;

        ViewHolder(Context context, View itemView) {

            super(itemView);

            textView_setting_list = itemView.findViewById(R.id.textView_setting_list);

            textView_setting_list.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {



                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }

                }
            });

        }


    }


    public MajorAdapter(ArrayList<String> list) {
        mData = list;
    }

    @NonNull
    @Override
    public MajorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timetable_item_list, parent, false);
        MajorAdapter.ViewHolder viewholder = new MajorAdapter.ViewHolder(context, view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(MajorAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        //holder.textView_setting_list.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}
*/

package com.maru.inunavi.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;

public class SignUpMajorAdapter extends RecyclerView.Adapter<SignUpMajorAdapter.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private SignUpMajorAdapter.OnItemClickListener mListener = null ;

    public void setOnItemClickListener(SignUpMajorAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    private ArrayList<String> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView child_title;

        ViewHolder(Context context, View itemView) {

            super(itemView);

            child_title = itemView.findViewById(R.id.child_title);

            child_title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition() ;

                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }

                }
            });

        }


    }


    public SignUpMajorAdapter(ArrayList<String> list) {
        mData = list;
    }

    @NonNull
    @Override
    public SignUpMajorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timetable_single_item_list_child, parent, false);
        SignUpMajorAdapter.ViewHolder viewHolder = new SignUpMajorAdapter.ViewHolder(context, view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SignUpMajorAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.child_title.setText(text);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

}
