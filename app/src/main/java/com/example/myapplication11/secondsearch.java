package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

// 제품 태그검색시 나오는 페이지 adapter과 viewholder 사용
public class secondsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    RecyclerView recyclerView;
    Adapter_secondsearch adapterSecondsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsearch);

        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        adapterSecondsearch = new Adapter_secondsearch();
        for (int i =0; i<10;i++){
            String str = i+"번째 아이템";
            adapterSecondsearch.setArrayList(str);
        }
        recyclerView.setAdapter(adapterSecondsearch);
    }
}


