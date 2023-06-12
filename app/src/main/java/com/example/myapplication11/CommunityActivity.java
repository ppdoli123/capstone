package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;


public class CommunityActivity extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;


    TextView text_community , title_community;
    Adapter_community adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        intent = getIntent();
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        text_community=(TextView) findViewById(R.id.text_community);
        title_community=(TextView) findViewById(R.id.title_community);

        text_community.setText(text);
        title_community.setText(title);
      /*  adapter= new Adapter_community();
        recyclerView.setAdapter(adapter);*/
    }
}