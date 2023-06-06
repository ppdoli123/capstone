package com.example.myapplication11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter3 extends RecyclerView.Adapter<ViewHolder3> {
    private ArrayList<String> arrayList;

    public Adapter3() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_showreview, parent, false);

        ViewHolder3 viewHolder3 = new ViewHolder3(context, view);
        return viewHolder3;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder3 holder, int position) {
        String text = arrayList.get(position);
        holder.review_totalreview.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(String strData) {
        arrayList.add(strData);
    }
}
