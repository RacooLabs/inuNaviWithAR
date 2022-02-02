package com.maru.inunavi.ui.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

import java.util.ArrayList;

public class SearchKeywordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String userID = "";
    private ArrayList<String> placeList;

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;


    private OnItemClickListener mListener = null ;
    private ArrayList<String> mData = null;
    private ArrayList<Drawable> iconData = null;


    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener ;

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout search_keyword_wrapper;
        ImageView map_frag_search_keyword_cardView_image;
        TextView map_frag_search_keyword_cardView_title;


        ViewHolder(Context context, View itemView) {

            super(itemView);

            search_keyword_wrapper = itemView.findViewById(R.id.search_keyword_wrapper);
            map_frag_search_keyword_cardView_image = itemView.findViewById(R.id.map_frag_search_keyword_cardView_image);
            map_frag_search_keyword_cardView_title = itemView.findViewById(R.id.map_frag_search_keyword_cardView_title);


            search_keyword_wrapper.setOnClickListener(new View.OnClickListener() {

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

    class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        // 헤더 뷰홀더
        HeaderFooterViewHolder(Context context, View headerView) {
            super(headerView);
        }

    }

    public SearchKeywordAdapter(ArrayList<String> list, ArrayList<Drawable> iconList) {

        mData = list;
        iconData = iconList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        RecyclerView.ViewHolder holder;

        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_fragment_search_keyword_cardview_padding, parent, false);
            holder = new HeaderFooterViewHolder(context, view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_fragment_search_keyword_cardview, parent, false);
            holder = new ViewHolder(context, view);
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



        if (holder instanceof HeaderFooterViewHolder) {
            HeaderFooterViewHolder headerViewHolder = (HeaderFooterViewHolder) holder;

        } else {

            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.map_frag_search_keyword_cardView_image.setBackground(iconData.get(position));
            viewHolder.map_frag_search_keyword_cardView_title.setText(mData.get(position));

        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == mData.size()-1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }


}
