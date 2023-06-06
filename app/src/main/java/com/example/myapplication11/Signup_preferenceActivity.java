package com.example.myapplication11;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup_preferenceActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 데이터 생성
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        String documentId = getIntent().getStringExtra("documentId");
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        RadioButton radioButton3 = findViewById(R.id.radioButton3);

        Button button7=findViewById(R.id.button7);
        Button button6=findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                intent.putExtra("documentId", documentId);
                startActivity(intent);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Signup_preferenceActivity.this, "선택 사항을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> user = new HashMap<>();
                    if (selectedId == radioButton1.getId()) {
                        user.put("user_preference_1", "웜톤");
                    } else if (selectedId == radioButton2.getId()) {
                        user.put("user_preference_1", "쿨톤");
                    } else if (selectedId == radioButton3.getId()) {
                        user.put("user_preference_1", "");
                    }
                    db.collection("users").document(documentId).update(user);
                    Intent intent=new Intent(getApplicationContext(),Signup_preferenceActivity2.class);
                    intent.putExtra("documentId", documentId);
                    startActivity(intent);
                }
            }
        });
    }
}
