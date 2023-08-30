package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter_main extends RecyclerView.Adapter<Adapter_main.ViewHolder> {
    private List<ProductItem> items = new ArrayList<>();
    private Context context;

    public String user;

    public void setArrayList(ProductItem item) {
        items.add(item);
    }
    public void addItem(ProductItem item,String user, String documentName) {
        item.setDocumentName(documentName);
        items.add(item);
        this.user = user;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem item = items.get(position);
        Glide.with(context).load(item.getImageUrl()).into(holder.image);
        holder.name.setText(item.getName());

        // 이미지 클릭 리스너 추가
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent thirdsearchIntent = new Intent(context, thirdsearch.class);
                thirdsearchIntent.putExtra("documentName", item.getDocumentName());
                // add imageUrl and name to the intent
                thirdsearchIntent.putExtra("imageUrl", item.getImageUrl());
                thirdsearchIntent.putExtra("name", item.getName());
                thirdsearchIntent.putExtra("user", user);
                context.startActivity(thirdsearchIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemimage_thirdsearch);
            name = itemView.findViewById(R.id.itemtitle_thirdsearch);
        }
    }
}
