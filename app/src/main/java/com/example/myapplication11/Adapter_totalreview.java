package com.example.myapplication11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_totalreview extends RecyclerView.Adapter<ViewHolder_totalreview> {
    private ArrayList<String> arrayList;

    public Adapter_totalreview() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder_totalreview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list3, parent, false);

        ViewHolder_totalreview viewHolderTotalreview = new ViewHolder_totalreview(context, view);
        return viewHolderTotalreview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_totalreview holder, int position) {
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
