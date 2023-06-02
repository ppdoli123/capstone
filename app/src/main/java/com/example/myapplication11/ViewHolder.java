package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView itemtitle_secondsearch , itemtag_secondsearch , score_secondsearch;
    public ImageView itemimage_secondsearch , star_secondsearch;

    ViewHolder(Context context, View itemView){
        super(itemView);

        itemimage_secondsearch= itemView.findViewById(R.id.itemimage_secondsearch);
        itemtitle_secondsearch= itemView.findViewById(R.id.itemtitle_secondsearch);
        itemtag_secondsearch= itemView.findViewById(R.id.itemtag_secondsearch);
        score_secondsearch= itemView.findViewById(R.id.score_secondsearch);
        star_secondsearch= itemView.findViewById(R.id.star_secondsearch);

    }
}