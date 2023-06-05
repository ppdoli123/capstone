package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder4 extends RecyclerView.ViewHolder {
    public TextView itemtitle_thirdsearch , itemtag_thirdsearch , score_thirdsearch;
    public ImageView itemimage_thirdsearch , star_thirdsearch;

    ViewHolder4(Context context, View itemView){
        super(itemView);

        itemimage_thirdsearch= itemView.findViewById(R.id.itemimage_preference);
        itemtitle_thirdsearch= itemView.findViewById(R.id.itemtitle_preference);
        score_thirdsearch= itemView.findViewById(R.id.score_preference);
        star_thirdsearch= itemView.findViewById(R.id.star_preference);

    }
}