package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class TotalReviewActivity extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;

    private List<searchreview> datalist;
    TextView itemtitle_totalreview;
    RecyclerView recyclerView;
    Adapter_totalreview adapterTotalreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalreview);

        datalist = new ArrayList<searchreview>();

        itemtitle_totalreview = (TextView)findViewById(R.id. itemtitle_totalreview);
        recyclerView=(RecyclerView) findViewById(R.id.recycle_totalreview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        //HomeFragment 로부터 searchItem , searchType 받아오기
        intent=getIntent();
        String name = intent.getStringExtra("name");
        itemtitle_totalreview.setText(name);

        // Firestore 데이터 가져오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("search_product")
                .orderBy("name") // 이름에 따라 정렬
                .whereEqualTo("name", name) // 이름이 "John"인 데이터만 필터링
                .limit(50)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String review = document.getString("preprocessing_review");
                                String positive = document.getString("positive_keyword");
                                String negative = document.getString("negative_keyword");
                                searchreview data = new searchreview(name,review,positive, negative);
                                datalist.add(data);
                                System.out.println(data.getReview());
                            }
                            adapterTotalreview.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
        adapterTotalreview= new Adapter_totalreview(datalist);
        recyclerView.setAdapter(adapterTotalreview);
    }


}