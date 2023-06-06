package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder_main extends RecyclerView.ViewHolder {
    public LinearLayout item_thirdsearch;
    public TextView itemtitle_thirdsearch , itemtag_thirdsearch , score_thirdsearch;
    public ImageView itemimage_thirdsearch , star_thirdsearch;

    ViewHolder_main(Context context, View itemView){
        super(itemView);
        item_thirdsearch= itemView.findViewById(R.id.item_thirdsaerch);
        itemimage_thirdsearch= itemView.findViewById(R.id.itemimage_thirdsearch);
        itemtitle_thirdsearch= itemView.findViewById(R.id.itemtitle_thirdsearch);
        score_thirdsearch= itemView.findViewById(R.id.score_thirdsearch);
        star_thirdsearch= itemView.findViewById(R.id.star_thirdsearch);

    }
}