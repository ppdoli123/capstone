package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

// 제품 태그검색시 나오는 페이지 adapter과 viewholder 사용
public class Storage_Like extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    RecyclerView recyclerView;
    Adapter_secondsearch adapterSecondsearch;
    String user;
    LinearLayout visibility;


    // 데이터 리스트
    private List<searchitem> datalist;
    private List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        datalist = new ArrayList<searchitem>();
        // 연관 검색창
        list = new ArrayList<String>();
        intent=getIntent();
        String userDocumentName = intent.getStringExtra("userDocumentName");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (userDocumentName == null) {
            return;
        }
        // 사용자 정보 가져오기
        db.collection( "users")
                .document(userDocumentName) // 로그인 사용자의 ID를 지정해 주세요
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        List<String> existingArray = (List<String>) document.get("Like");

                        //HomeFragment 로부터 searchItem , searchType 받아오기



        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        // Firestore 데이터 가져오기
        for (String name : existingArray) {
            db.collection("Search")
                    .whereEqualTo("name", name)
                    .limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    String image = document.getString("image");
                                    String keyword = document.getString("keyword");
                                    String type = document.getString("type");
                                    String user = userDocumentName;
                                    searchitem data = new searchitem(name, image, keyword, type, user);
                                    datalist.add(data);
                                }
                                adapterSecondsearch.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Error getting documents: " + task.getException());
                            }
                        }
                    });
        }


        adapterSecondsearch = new Adapter_secondsearch(datalist);
        recyclerView.setAdapter(adapterSecondsearch);
            }});
    }



}


