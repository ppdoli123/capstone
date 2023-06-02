package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    Intent intent;

    HomeFragment homeFragment;
    SettingFragment settingFragment;
    ProfileFragment profileFragment;

    // Firebase 초기화
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        homeFragment = new HomeFragment();
        settingFragment = new SettingFragment();
        profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,homeFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottomNav);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,homeFragment).commit();
                        return true;
                    case R.id.profileFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,profileFragment).commit();
                        return true;
                    case R.id.settingFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
    public void logout(View target){
        Bundle bundle = new Bundle();
        bundle.putInt("num", 0);

        intent = new Intent(this,LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        // 창준

    }}