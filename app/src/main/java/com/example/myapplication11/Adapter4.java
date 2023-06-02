package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter4 extends RecyclerView.Adapter<ViewHolder4> {
    private ArrayList<String> arrayList;

    public Adapter4() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list2, parent, false);

        ViewHolder4 viewHolder4 = new ViewHolder4(context, view);
        return viewHolder4;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder4 holder, int position) {
        String text = arrayList.get(position);
        holder.itemtitle_thirdsearch.setText(text);
        holder.itemimage_thirdsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();
                Intent thirdsearch = new Intent(context, thirdsearch.class);
                ((thirdsearch)context).startActivity(thirdsearch);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(String strData) {
        arrayList.add(strData);
    }
}
