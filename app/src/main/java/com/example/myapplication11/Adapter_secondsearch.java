package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_secondsearch extends RecyclerView.Adapter<ViewHolder_seocondsearch> {
    private ArrayList<String> arrayList;

    public Adapter_secondsearch() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder_seocondsearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);

        ViewHolder_seocondsearch viewHolderSeocondsearchSearch = new ViewHolder_seocondsearch(context, view);
        return viewHolderSeocondsearchSearch;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_seocondsearch holder, int position) {
        String text = arrayList.get(position);
        holder.itemtitle_secondsearch.setText(text);
        holder.item_secondsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();
                Intent thirdsearch = new Intent(context, thirdsearch.class);
                ((secondsearch)context).startActivity(thirdsearch);
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
