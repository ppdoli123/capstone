package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

// 제품 태그검색시 나오는 페이지 adapter과 viewholder 사용
public class secondsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;
    RecyclerView recyclerView;
    Adapter_secondsearch adapterSecondsearch;



    // 연관 검색 바 설정
    private List<String> list;
    private void settingList(){
        list.add("촉촉한");
        list.add("수분감");
        list.add("보송한");
        list.add("시원한");
        list.add("상쾌한");
        list.add("waterful");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsearch);

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

        autoCompleteTextView.setText(searchType);


        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        adapterSecondsearch = new Adapter_secondsearch();
        for (int i =0; i<10;i++){
            String str = searchItem+" "+searchType+" "+i+"번째 아이템";
            adapterSecondsearch.setArrayList(str);
        }
        recyclerView.setAdapter(adapterSecondsearch);
    }
}


