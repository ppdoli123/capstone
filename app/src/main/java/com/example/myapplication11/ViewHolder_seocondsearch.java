package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder_seocondsearch extends RecyclerView.ViewHolder {
    public LinearLayout item_secondsearch;
    public TextView itemtitle_secondsearch , itemtag_secondsearch , score_secondsearch;
    public ImageView itemimage_secondsearch , star_secondsearch;

    ViewHolder_seocondsearch(Context context, View itemView){
        super(itemView);

        item_secondsearch= itemView.findViewById(R.id.item_secondsearch);
        itemimage_secondsearch= itemView.findViewById(R.id.itemimage_secondsearch);
        itemtitle_secondsearch= itemView.findViewById(R.id.itemtitle_secondsearch);
        itemtag_secondsearch= itemView.findViewById(R.id.itemtag_secondsearch);
        score_secondsearch= itemView.findViewById(R.id.score_secondsearch);
        star_secondsearch= itemView.findViewById(R.id.star_secondsearch);

    }
}