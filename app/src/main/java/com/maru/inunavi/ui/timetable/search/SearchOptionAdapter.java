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

    private com.maru.inunavi.ui.timetable.SettingAdapter.OnItemClickListener mListener = null ;

    public void setOnItemClickListener(com.maru.inunavi.ui.timetable.SettingAdapter.OnItemClickListener listener) {
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

package com.maru.inunavi.ui.timetable.search;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;

public class SearchOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private OnItemClickListener mListener = null ;



    public void setOnItemClickListener(SearchOptionAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int DEFAULT_HEADER = 2;
    public static final int DEFAULT_CHILD = 3;
    public static final int MULTI_CHILD = 4;

    public int ALL_CHECKED = 2;

    private List<Item> data;

    private ArrayList<String> selectionList;

    public SearchOptionAdapter(List<Item> data) {
        this.data = data;
    }

    public SearchOptionAdapter(List<Item> data, ArrayList<String> selectionList) {
        this.data = data;
        this.selectionList = selectionList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();


        switch (type) {

            case DEFAULT_CHILD:
                LayoutInflater inflater_default_child = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater_default_child.inflate(R.layout.timetable_single_item_list_child, parent, false);
                ListDefaultChildViewHolder default_child = new ListDefaultChildViewHolder(view);

                return default_child;

            case MULTI_CHILD:
                LayoutInflater inflater_multi_child = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater_multi_child.inflate(R.layout.timetable_multi_item_list_child, parent, false);
                ListMultiChildViewHolder multi_child = new ListMultiChildViewHolder(view);

                return multi_child;

        }
        return null;
    }

    public void doAllCheck(){
        ALL_CHECKED = 1;
        notifyDataSetChanged();
    }

    public void doAllCancel(){
        ALL_CHECKED = 0;
        notifyDataSetChanged();
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {

            case DEFAULT_CHILD:

                final ListDefaultChildViewHolder defaultChildView = (ListDefaultChildViewHolder) holder;
                TextView itemTextView_defaultChild = (TextView) defaultChildView.child_title;
                itemTextView_defaultChild.setText(data.get(position).text);
                break;

            case MULTI_CHILD:

                final ListMultiChildViewHolder multiChildView = (ListMultiChildViewHolder) holder;
                TextView itemTextView_multiChild = (TextView) multiChildView.child_title;
                itemTextView_multiChild.setText(data.get(position).text);

                CheckBox checkBox = (CheckBox) multiChildView.checkBox;
                String child_text = data.get(position).text;

                // 체크 박스 리스너
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        if(isChecked){
                            if(!selectionList.contains(child_text))
                                selectionList.add(child_text);
                        }else{
                            if(selectionList.contains(child_text))
                                selectionList.remove(child_text);
                        }

                    }

                });


                if(selectionList.contains(child_text)){

                    multiChildView.checkBox.setChecked(true);

                }else{

                    multiChildView.checkBox.setChecked(false);

                }


                switch (ALL_CHECKED){
                    case 0 :
                        multiChildView.checkBox.setChecked(false);
                        break;
                    case 1:
                        multiChildView.checkBox.setChecked(true);
                        break;

                    default:
                }

                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;
        public LinearLayout header_title_layout;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
            header_title_layout = (LinearLayout) itemView.findViewById(R.id.header_title_layout);


        }
    }

    private class ListChildViewHolder extends RecyclerView.ViewHolder {

        public TextView child_title;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            child_title = (TextView) itemView.findViewById(R.id.child_title);

            child_title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }

                }
            });

        }
    }

    private class ListDefaultHeaderViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout default_header_title_layout;
        public TextView default_header_title;

        public ListDefaultHeaderViewHolder(View itemView) {
            super(itemView);

            default_header_title_layout = (LinearLayout) itemView.findViewById(R.id.default_header_title_layout);
            default_header_title = (TextView) itemView.findViewById(R.id.default_header_title);
            default_header_title_layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(default_header_title,pos);
                        }
                    }

                }
            });

        }
    }

    private class ListDefaultChildViewHolder extends RecyclerView.ViewHolder {

        public TextView child_title;

        public ListDefaultChildViewHolder(View itemView) {
            super(itemView);
            child_title = (TextView) itemView.findViewById(R.id.child_title);

            child_title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }

                }
            });
        }
    }

    private class ListMultiChildViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout multi_child_layout;
        public CheckBox checkBox;
        public TextView child_title;
        public String child_text;

        public ListMultiChildViewHolder(View itemView) {
            super(itemView);
            multi_child_layout = itemView.findViewById(R.id.timetable_multi_item_list_child_layout);
            checkBox = (CheckBox) itemView.findViewById(R.id.tita_multi_item_list_child_checkBox);
            child_title = (TextView) itemView.findViewById(R.id.child_title);
            child_text = child_title.getText().toString();


            multi_child_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked())
                        checkBox.setChecked(false);
                    else
                        checkBox.setChecked(true);
                }
            });


        }
    }



    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }

}
