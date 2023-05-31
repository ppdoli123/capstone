package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;


public class secondsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsearch);

        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        adapter= new Adapter();
        for (int i =0; i<10;i++){
            String str = i+"번째 아이템";
            adapter.setArrayList(str);
        }
        recyclerView.setAdapter(adapter);
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView itemtitle_secondsearch , itemtag_secondsearch , score_secondsearch;
    public ImageView itemimage_secondsearch , star_secondsearch;

    ViewHolder(Context context, View itemView){
        super(itemView);

        itemimage_secondsearch= itemView.findViewById(R.id.itemimage_secondsearch);
        itemtitle_secondsearch= itemView.findViewById(R.id.itemtitle_secondsearch);
        itemtag_secondsearch= itemView.findViewById(R.id.itemtag_secondsearch);
        score_secondsearch= itemView.findViewById(R.id.score_secondsearch);
        star_secondsearch= itemView.findViewById(R.id.star_secondsearch);

    }
}

