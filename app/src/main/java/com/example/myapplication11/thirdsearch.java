package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class thirdsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    TextView productName;
    ImageView productImage;
    PieChart chart1;
    BarChart chart2;
    String imageUrl;
    String documentName;
    String name;
    String type;
    public void initView(View v){

        chart1 = (PieChart) v.findViewById(R.id.tab1_chart_1);
        chart2 = (BarChart)v.findViewById(R.id.tab1_chart_2);

    }

    // 파이 차트 설정
    private void setPieChart(String documentId) {
        // Firestore 인스턴스 가져오기
        chart1.clearChart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productRef = db.collection("product");
        int[] colors = {Color.parseColor("#CDA67F"), Color.parseColor("#00BFFF"), Color.parseColor("#007F7F"), Color.parseColor("#C8A2C8")};

// 다른 항목과 색상을 추가로 지정하려면 위와 같이 계속해서 put 메소드를 사용하여 추가하세요.


        // 'product' 컬렉션의 모든 문서 가져오기
        productRef.document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {

                        // 필드 값이 숫자인 필드명과 값을 저장할 맵 생성
                        Map<String, Long> valuesMap = new HashMap<>();
                        Map<String, Object> data = document.getData();

                        // 숫자 타입의 필드를 찾아 valuesMap에 추가
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            if (entry.getValue() instanceof Number) {
                                long value = ((Number) entry.getValue()).longValue();
                                valuesMap.put(entry.getKey(), value);
                            }
                        }

                        // 값을 기준으로 내림차순으로 정렬된 엔트리 리스트 생성
                        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(valuesMap.entrySet());
                        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                        // 가장 큰 값부터 차트에 추가 (최대 4개)
                        for (int i = 0; i < sortedEntries.size() && i < 4; i++) {
                            Map.Entry<String, Long> entry = sortedEntries.get(i);
                            String type = entry.getKey();
                            long value = entry.getValue();
                            int color = colors[i];
                            chart1.addPieSlice(new PieModel(type, value, color));

                            // 해당 필드 이름을 TextView에 설정
                            if (i == 0) {
                                TextView textView1 = findViewById(R.id.reviewer1);
                                textView1.setText(type);
                            } else if (i == 1) {
                                TextView textView2 = findViewById(R.id.reviewer2);
                                textView2.setText(type);
                            } else if (i == 2) {
                                TextView textView3 = findViewById(R.id.reviewer3);
                                textView3.setText(type);
                            } else if (i == 3) {
                                TextView textView4 = findViewById(R.id.reviewer4);
                                textView4.setText(type);
                            }
                        }

                        chart1.startAnimation();
                    } else {
                        Log.d(TAG, "No documents found in 'product' collection.");
                    }
                } else {
                    Log.d(TAG, "Failed to fetch documents: " + task.getException());
                }
                }

    });}

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
        productName = findViewById(R.id.product_name);
        productImage = findViewById(R.id.product_image);
        setBarChart();

        Intent intent = getIntent();
        if (intent != null) {
            documentName = intent.getStringExtra("documentName");
            imageUrl = intent.getStringExtra("imageUrl"); // get imageUrl
            name = intent.getStringExtra("name"); // get name
        }

        recyclerView=(RecyclerView) findViewById(R.id.recycle_thirdsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));


        // productName과 productImage 업데이트
        productName.setText(name); // Set name in TextView
        Glide.with(thirdsearch.this)
                .load(imageUrl)
                .into(productImage); // Set imageUrl in ImageView
        Log.d(TAG, "Received documentName: " + documentName);
        setPieChart(documentName);

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