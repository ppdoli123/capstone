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

public class Adapter_thirdsearch extends RecyclerView.Adapter<Adapter_thirdsearch.ViewHolder> {

    private Context context;
    private List<String> imageUrls;
    private List<String> names;
    private List<String> documentNames;

    public Adapter_thirdsearch(Context context) {
        this.context = context;
        imageUrls = new ArrayList<>();
        names = new ArrayList<>();
        documentNames = new ArrayList<>();
    }

    public void addItem(String imageUrl, String name, String documentName) {
        imageUrls.add(imageUrl);
        names.add(name);
        documentNames.add(documentName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        String name = names.get(position);
        String documentName = documentNames.get(position);

        Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.image);
        holder.name.setText(name);

        // 이미지 클릭 리스너 추가
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent thirdsearchIntent = new Intent(context, thirdsearch.class);
                thirdsearchIntent.putExtra("documentName", documentName);
                thirdsearchIntent.putExtra("imageUrl", imageUrl);
                thirdsearchIntent.putExtra("name", name);
                context.startActivity(thirdsearchIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
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
