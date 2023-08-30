package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

// 제품 태그검색시 나오는 페이지 adapter과 viewholder 사용
public class secondsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    RecyclerView recyclerView;
    Adapter_secondsearch adapterSecondsearch;




    // 데이터 리스트
    private List<searchitem> datalist;
    private List<String> list;
    private void settingList(){
        list.add("광택감");
        list.add("수분감");
        list.add("촉촉함");
        list.add("가벼움");
        list.add("보습");
        list.add("쫀쫀함");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsearch);
        datalist = new ArrayList<searchitem>();
        // 연관 검색창
        list = new ArrayList<String>();

        // 리스트에 검색될 데이터(단어)를 추가한다.
        settingList();

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        // AutoCompleteTextView 에 아답터를 연결한다.
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,  list ));

        //HomeFragment 로부터 searchItem , searchType 받아오기
        intent=getIntent();
        String searchItem = intent.getStringExtra("searchItem");
        String searchType = intent.getStringExtra("searchType");
        String userDocumentName = intent.getStringExtra("userDocumentName");
        autoCompleteTextView.setText(searchType);


        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        // Firestore 데이터 가져오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Search")
                .orderBy("type") // 이름에 따라 정렬
                .whereEqualTo("type", searchItem) // 이름이 "John"인 데이터만 필터링
                .whereEqualTo("keyword", searchType) // 이름이 "John"인 데이터만 필터링
                .limit(10)
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
                                searchitem data = new searchitem(name,image,keyword,type,user);
                                datalist.add(data);
                            }
                            adapterSecondsearch.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });


        adapterSecondsearch = new Adapter_secondsearch(datalist);
        recyclerView.setAdapter(adapterSecondsearch);

    }



}


