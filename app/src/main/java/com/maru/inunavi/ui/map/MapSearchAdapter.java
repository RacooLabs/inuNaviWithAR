package com.maru.inunavi.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.ViewHolder> {


    private String userID = "";
    private ArrayList<String> placeList;

    private OnItemDirectionClickListener dListener = null;
    private OnItemClickListener mListener = null ;
    private ArrayList<Place> mData = null;

    public interface OnItemDirectionClickListener {
        void onItemClick(View v, int position) ;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemDirectionClickListener(OnItemDirectionClickListener dListener) {

        this.dListener = dListener ;

    }


    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener ;

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout map_search_item_wrapper;
        ConstraintLayout map_search_item_directionButton;
        TextView map_search_item_title;
        TextView map_search_item_num;
        TextView map_search_item_distance;


        ViewHolder(Context context, View itemView) {

            super(itemView);

            map_search_item_wrapper = itemView.findViewById(R.id.map_search_item_wrapper);
            map_search_item_directionButton = itemView.findViewById(R.id.map_search_item_directionButton);
            map_search_item_title = itemView.findViewById(R.id.map_search_item_title);
            map_search_item_num = itemView.findViewById(R.id.map_search_item_num);
            map_search_item_distance = itemView.findViewById(R.id.map_search_item_distance);



            map_search_item_wrapper.setOnClickListener(new View.OnClickListener() {

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

            map_search_item_directionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition() ;

                    if (pos != RecyclerView.NO_POSITION) {
                        if(dListener != null){
                            dListener.onItemClick(v,pos);
                        }
                    }
                }
            });

        }

    }

    public MapSearchAdapter(ArrayList<Place> list) {

        mData = list;

    }

    @NonNull
    @Override
    public MapSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_fragment_search_item, parent, false);
        MapSearchAdapter.ViewHolder viewHolder = new MapSearchAdapter.ViewHolder(context, view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MapSearchAdapter.ViewHolder holder, int position) {

        holder.map_search_item_title.setText(mData.get(position).getTitle());
        holder.map_search_item_num.setText(mData.get(position).getSort());
        holder.map_search_item_distance.setText((int)(mData.get(position).getDistance()) + "m");

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }



}
