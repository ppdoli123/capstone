package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder3 extends RecyclerView.ViewHolder {
    public TextView review_totalreview;
    public ImageView image_totalreview;

    ViewHolder3(Context context, View itemView){
        super(itemView);

        image_totalreview= itemView.findViewById(R.id.image_totalreview);
        review_totalreview= itemView.findViewById(R.id.review_totalreview);

    }
}