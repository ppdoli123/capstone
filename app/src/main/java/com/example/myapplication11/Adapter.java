package com.example.myapplication11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<String> arrayList;

    public Adapter() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = arrayList.get(position);
        holder.itemtitle_secondsearch.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(String strData) {
        arrayList.add(strData);
    }
}
