package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_thirdsearch extends RecyclerView.Adapter<ViewHolder_thirdsearch> {
    private ArrayList<String> arrayList;

    public Adapter_thirdsearch() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder_thirdsearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list2, parent, false);

        ViewHolder_thirdsearch viewHolderThirdsearch = new ViewHolder_thirdsearch(context, view);
        return viewHolderThirdsearch;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_thirdsearch holder, int position) {
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
