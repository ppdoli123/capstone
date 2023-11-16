package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    EditText userid, userpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.login);
        mFirestore = FirebaseFirestore.getInstance();
        Button button2 = findViewById(R.id.newid);
        mAuth = FirebaseAuth.getInstance();

        // 지문인식 메소드 실행
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "인증에러!", Toast.LENGTH_SHORT).show();
            }
            //지문인식 성공할시 메인엑티비티로 화면 전환
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "인증성공!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "인증실패!", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("지문 인증")
                .setNegativeButtonText("취소")
                .setDeviceCredentialAllowed(false)
                .build();


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        userid = findViewById(R.id.editid);
        userpw = findViewById(R.id.editpassword);
        // 로그인 함수
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = userid.getText().toString();
                String password = userpw.getText().toString();

                // Firestore에서 이메일과 비밀번호 확인
                mFirestore.collection("users")
                        .whereEqualTo("username", email)
                        .whereEqualTo("password", password)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    // 로그인 성공
                                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    String userDocumentName = task.getResult().getDocuments().get(0).getId(); // 문서 이름을 가져옵니다.
                                    intent.putExtra("userDocumentName", userDocumentName); // MainActivity로 전달합니다.
                                    startActivity(intent);
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // 지문 인증 버튼 클릭 이벤트
    public void fingerprint(View v) {
        biometricPrompt.authenticate(promptInfo);
    }



    }

