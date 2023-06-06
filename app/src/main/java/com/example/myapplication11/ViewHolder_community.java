package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder_community extends RecyclerView.ViewHolder {

    public LinearLayout content_community;
    public TextView title_community, text_community, time_community ;

    ViewHolder_community(Context context, View itemView){
        super(itemView);

        content_community = itemView.findViewById(R.id.content_community);
        title_community= itemView.findViewById(R.id.title_community);
        text_community= itemView.findViewById(R.id.text_community);
        time_community= itemView.findViewById(R.id.time_community);

    }
}