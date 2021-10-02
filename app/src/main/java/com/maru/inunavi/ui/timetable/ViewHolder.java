package com.maru.inunavi.ui.timetable;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textView_setting_list;

    ViewHolder(Context context, View itemView) {
        super(itemView);
        textView_setting_list = itemView.findViewById(R.id.textView_setting_list);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = getAdapterPosition() ;
                if (pos != RecyclerView.NO_POSITION) {
                    if(pos == 0){

                    }
                }

            }
        });

    }

}
