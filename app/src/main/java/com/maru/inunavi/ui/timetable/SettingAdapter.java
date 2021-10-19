package com.maru.inunavi.ui.timetable;

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

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }



    private ArrayList<String> mData = null;

    public TextView textView_setting_list;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_setting_list;

        ViewHolder(Context context, View itemView) {

            super(itemView);

            textView_setting_list = itemView.findViewById(R.id.textView_setting_list);

            textView_setting_list.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.d("@@@settingadapter : 44", "error");

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


    public SettingAdapter(ArrayList<String> list) {
        mData = list;
    }

    @NonNull
    @Override
    public SettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timetable_setting_item_list, parent, false);
        SettingAdapter.ViewHolder viewholder = new SettingAdapter.ViewHolder(context, view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(SettingAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.textView_setting_list.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}
