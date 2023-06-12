package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_community extends RecyclerView.Adapter<ViewHolder_community> {
    private List<communityitem> dataList;
    Intent intent;


    public Adapter_community(List<communityitem> dataList) {
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ViewHolder_community onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list4, parent, false);

        ViewHolder_community viewHoldercommunity = new ViewHolder_community(context, view);
        return viewHoldercommunity;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_community holder, int position) {
        String text = dataList.get(position).getText();
        String title = dataList.get(position).getTitle();
        holder.text_community.setText(text);
        holder.title_community.setText(title);
        holder.touch_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent=new Intent(context,CommunityActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("text",text);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
