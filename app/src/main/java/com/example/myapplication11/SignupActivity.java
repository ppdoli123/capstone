package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    public String documentId;

    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //이름,id,password 주소값 가져오기.
        EditText name = findViewById(R.id.signup_name);
        EditText pw = findViewById(R.id.signup_pw);
        EditText pw_check = findViewById(R.id.signup_pw_check);
        //button6의 주소값 가져오고, button6 실행시 로그인 화면으로 돌아감.
        Button button6 = findViewById(R.id.button6);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button validateButton = findViewById(R.id.validateButton);
        Button button7 = findViewById(R.id.button7);
        button7.setEnabled(false);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();

                // 이름과 이메일이 비어있는지 체크
                if (username.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher);
                    dialog.setTitle("알림");
                    dialog.setMessage("아이디를 입력하세요.");
                    dialog.setNegativeButton("확인", null);
                    dialog.show();
                } else {
                    // users 컬렉션에서 동일한 이메일을 가진 사용자가 있는지 확인
                    db.collection("users")
                            .whereEqualTo("username", username)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        boolean isEmailExists = false;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            isEmailExists = true;
                                        }

                                        if (isEmailExists) {
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                                            dialog.setIcon(R.mipmap.ic_launcher);
                                            dialog.setTitle("알림");
                                            dialog.setMessage("이미 사용 중인 아이디입니다.");
                                            dialog.setNegativeButton("확인", null);
                                            dialog.show();
                                        } else {
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                                            dialog.setIcon(R.mipmap.ic_launcher);
                                            dialog.setTitle("알림");
                                            dialog.setMessage("사용 가능한 아이디입니다.");
                                            dialog.setNegativeButton("확인", null);
                                            dialog.show();

                                            // validateButton 이후에만 button7 클릭 가능하도록 설정
                                            button7.setEnabled(true);
                                        }
                                    } else {
                                        // 오류 처리
                                        Toast.makeText(SignupActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String userpw = pw.getText().toString();
                String userpw_check = pw_check.getText().toString();
                //이름,id,password의 입력칸이 하나라도 비었을시 알림이 뜸.
                if (username.equals("") ||  userpw.equals("") || userpw_check.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher);
                    dialog.setTitle("알림");
                    dialog.setMessage("모두 입력하시오.");
                    dialog.setNegativeButton("확인", null);
                    dialog.show();
                }
                //모두 입력했을시 데이터를 담고 회원가입 성향파악 화면으로 돌아감
                else {
                    if (userpw.equals(userpw_check)) {
                        System.out.println("넘어가야됨");
                        Map<String, Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("password", userpw);
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    documentId = documentReference.getId();
                                    Intent intent = new Intent(getApplicationContext(), Signup_preferenceActivity.class);
                                    intent.putExtra("documentId", documentId);
                                    startActivity(intent);});
                    } else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                        dialog.setIcon(R.mipmap.ic_launcher);
                        dialog.setTitle("알림");
                        dialog.setMessage("비밀번호가 일치하지않습니다");
                        dialog.setNegativeButton("확인", null);
                        dialog.show();
                    }
    }
}}
);
        }
}