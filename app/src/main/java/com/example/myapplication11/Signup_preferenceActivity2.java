package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signup_preferenceActivity2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 데이터 생성
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        String documentId = getIntent().getStringExtra("documentId");
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        RadioButton radioButton3 = findViewById(R.id.radioButton3);
        RadioButton radioButton4 = findViewById(R.id.radioButton4);
        RadioButton radioButton5 = findViewById(R.id.radioButton5);
        RadioButton radioButton6 = findViewById(R.id.radioButton6);
        Button button6=findViewById(R.id.button6);
        Button button7=findViewById(R.id.button7);


        button6.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),Signup_preferenceActivity.class);
        intent.putExtra("documentId", documentId);
        startActivity(intent);
        }});
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Signup_preferenceActivity2.this, "선택 사항을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> user = new HashMap<>();
                    if (selectedId == radioButton1.getId()) {
                        user.put("user_preference_2", "지성");
                    } else if (selectedId == radioButton2.getId()) {
                        user.put("user_preference_2", "건성");
                    } else if (selectedId == radioButton3.getId()) {
                        user.put("user_preference_2", "복합성");
                    }else if (selectedId == radioButton4.getId()) {
                        user.put("user_preference_2", "민감성");
                    }else if (selectedId == radioButton5.getId()) {
                        user.put("user_preference_2", "중성");
                    }else if (selectedId == radioButton6.getId()) {
                        user.put("user_preference_2", "");
                    }
                    db.collection("users").document(documentId).update(user);
                    // Like 배열 필드 생성 코드
                    List<String> newArray = new ArrayList<>();
                    db.collection("users").document(documentId).update("Like",newArray);
                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("documentId", documentId);
                    startActivity(intent);
                }
    }});
}
}


