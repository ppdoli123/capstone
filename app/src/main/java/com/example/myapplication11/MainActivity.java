package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;





public class MainActivity extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;

// Firebase 초기화
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        // 데이터베이스 오프라인 기능 활성화 (선택 사항)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //db.collection("user")
          //      .get()
            //    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              //      @Override
                //    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                  //      int count = queryDocumentSnapshots.size();
                    //    Log.d("Firestore", "컬렉션 개수: " + count);
                   // }
                //});
    }
    public void logout(View target){
        Bundle bundle = new Bundle();
        bundle.putInt("num", 0);

        intent = new Intent(this,LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    // 창준

    }}