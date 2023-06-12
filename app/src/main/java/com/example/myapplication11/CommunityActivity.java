package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;


public class CommunityActivity extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;


    RecyclerView recyclerView;
    Adapter_community adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalreview);

        recyclerView=(RecyclerView) findViewById(R.id.recycle_totalreview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        adapter= new Adapter_community();
        for (int i =0; i<10;i++){
            String str = i+"피부 촉촉해지는 느낌이 좋구요 가성비가 좋은 제품이라 듬뿍 바를수 있어 좋은것 같습니다.";
            adapter.setArrayList(str);
        }
        recyclerView.setAdapter(adapter);
    }
}