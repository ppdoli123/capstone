package com.example.myapplication11;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder_community extends RecyclerView.ViewHolder {

    public LinearLayout touch_community;
    public TextView title_community, text_community ;

    ViewHolder_community(Context context, View itemView){
        super(itemView);

        touch_community = itemView.findViewById(R.id.touch_community);
        title_community= itemView.findViewById(R.id.title_community);
        text_community= itemView.findViewById(R.id.text_community);

    }
}