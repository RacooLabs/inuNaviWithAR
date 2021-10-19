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

                    Log.d("@@@majoradpater : 48", "error");

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;

public class MajorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private OnItemClickListener mListener = null ;

    public void setOnItemClickListener(MajorAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;

    public MajorAdapter(List<Item> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.timetable_major_item_list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);

                return header;
            case CHILD:
                LayoutInflater inflater_child = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater_child.inflate(R.layout.timetable_major_item_list_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);

                return child;



        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);

                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }

                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:

                final ListChildViewHolder childView = (ListChildViewHolder) holder;
                TextView itemTextView = (TextView) childView.child_title;
                itemTextView.setText(data.get(position).text);

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

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);


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
