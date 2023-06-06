package com.example.myapplication11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_community extends RecyclerView.Adapter<ViewHolder_community> {
    private ArrayList<String> arrayList;

    public Adapter_community() {
        arrayList = new ArrayList<>();

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
        String text = arrayList.get(position);
        holder.text_community.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(String strData) {
        arrayList.add(strData);
    }
}
