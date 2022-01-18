package com.maru.inunavi.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;

public class MapNaviSearchActivityAdapter extends RecyclerView.Adapter<MapNaviSearchActivityAdapter.MyViewHolder> {

    private List<Place> mData;

    private String userID = "";

    private OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener ;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //public ImageView map_frag_detail_cardView_image;
        public ConstraintLayout map_frag_navi_search_item_wrapper;
        public TextView map_frag_navi_search_item_title;
        public TextView map_frag_navi_search_item_num;
        public TextView map_frag_navi_search_item_distance;

        public MyViewHolder(View v) {

            super(v);

            //map_frag_detail_cardView_image =  v.findViewById(R.id.map_frag_detail_cardView_image);
            map_frag_navi_search_item_wrapper = v.findViewById(R.id.map_frag_navi_search_item_wrapper);
            map_frag_navi_search_item_title =  v.findViewById(R.id.map_frag_navi_search_item_title);
            map_frag_navi_search_item_num = v.findViewById(R.id.map_frag_navi_search_item_num);
            map_frag_navi_search_item_distance = v.findViewById(R.id.map_frag_navi_search_item_distance);

            map_frag_navi_search_item_wrapper.setOnClickListener(new View.OnClickListener() {

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

    public MapNaviSearchActivityAdapter(List<Place> mData) {

        this.mData = mData;

    }


    @Override
    public MapNaviSearchActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.map_fragment_navi_search_item, parent, false);

        MapNaviSearchActivityAdapter.MyViewHolder vh = new MapNaviSearchActivityAdapter.MyViewHolder(view);
        return vh;

    }


    @Override
    public void onBindViewHolder(MapNaviSearchActivityAdapter.MyViewHolder holder, int position) {

        holder.map_frag_navi_search_item_title.setText(mData.get(position).getTitle());
        holder.map_frag_navi_search_item_num.setText(mData.get(position).getSort());
        holder.map_frag_navi_search_item_distance.setText((int)(mData.get(position).getDistance()) + "m");

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


}

