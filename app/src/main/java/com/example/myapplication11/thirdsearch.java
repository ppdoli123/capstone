package com.example.myapplication11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;


public class thirdsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;

    PieChart chart1;
    BarChart chart2;

/*    public void initView(View v){

        chart1 = (PieChart) v.findViewById(R.id.tab1_chart_1);
        chart2 = (BarChart)v.findViewById(R.id.tab1_chart_2);

    }*/

    // 파이 차트 설정
    private void setPieChart() {


        chart1.clearChart();

        chart1.addPieSlice(new PieModel("TYPE 1", 60, Color.parseColor("#CDA67F")));
        chart1.addPieSlice(new PieModel("TYPE 2", 40, Color.parseColor("#00BFFF")));

        chart1.startAnimation();

    }

    // 막대 차트 설정
    private void setBarChart() {

        chart2.clearChart();

        chart2.addBar(new BarModel("12", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("13", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("14", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("15", 20f, 0xFF56B7F1));
        chart2.addBar(new BarModel("16", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("17", 10f, 0xFF56B7F1));

        chart2.startAnimation();

    }

    RecyclerView recyclerView;
    Adapter_thirdsearch adapter;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdsearch);
        chart1 = (PieChart) findViewById(R.id.tab1_chart_1);
        chart2 = (BarChart) findViewById(R.id.tab1_chart_2);
        setBarChart();
        setPieChart();

        recyclerView=(RecyclerView) findViewById(R.id.recycle_thirdsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        adapter= new Adapter_thirdsearch();
        for (int i =0; i<10;i++){
            String str = i+"번째 아이템";
            adapter.setArrayList(str);
        }
        recyclerView.setAdapter(adapter);

        button=(Button) findViewById(R.id.allreview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TotalReviewActivity.class);
                startActivity(intent);
            }
        });
    }

}