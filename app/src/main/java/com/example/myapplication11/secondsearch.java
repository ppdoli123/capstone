package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication11.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
    String[] wordsArray = {
            "촉촉", "글로시", "글로스", "광택", "윤광", "광채", "보습", "수분",
            "환절기", "겨울철", "여름철", "여름", "날씨", "계절", "간절", "건조",
            "민감성", "민감", "예민", "아토피", "화농성", "피부염", "자극", "고생",
            "유분", "기름기", "기름", "번들", "개기름", "피지", "번들거리",
            "화사", "환해", "안색", "얼굴빛", "복숭앗빛", "청순", "혈색",
            "지속", "착색력", "밀착력", "유지", "오래", "종일",
            "세정", "세정력", "거품", "클렌징", "세안", "개운",
            "지성", "지성인", "기름", "기름지", "여름", "지성용",
            "예쁘다", "예쁘", "이쁘", "예쁜", "이쁜",
            "브라이트", "갈웜", "피치", "코랄", "웜톤",
            "복합성", "복합", "수부", "수부지", "속건성",
            "건성", "건조", "속건정", "겨울철",
            "흡수력", "흡수", "스며들", "스며드",
            "데일리", "베이직", "무난", "평범",
            "탄력", "앰플", "영양",
            "여물", "쿨", "쿨톤",
            "은은한", "골드", "분홍빛",
            "산뜻", "가볍", "가벼운",
            "진정", "재생", "장벽",
            "보송", "매트",
            "밀착", "밀착력",
            "쫀쫀", "쫀득",
            "되직", "무겁",
            "부드러운", "부드럽",
            "촉촉함"
    };
    private FragmentHomeBinding binding;

    private void settingList(){
        list = Arrays.asList(wordsArray);
    }
    List<String> List1 = Arrays.asList("촉촉", "글로시", "글로스", "광택", "윤광", "광채", "보습", "수분");
    List<String> List2 = Arrays.asList("환절기", "겨울철", "여름철", "여름", "날씨", "계절", "간절", "건조");
    List<String> List3 = Arrays.asList("민감성", "민감", "예민", "아토피", "화농성", "피부염", "자극", "고생");
    List<String> List4 = Arrays.asList("유분", "기름기", "기름", "번들", "개기름", "피지", "번들거리");
    List<String> List5 = Arrays.asList("화사", "환해", "안색", "얼굴빛", "복숭앗빛", "청순", "혈색");
    List<String> List6 = Arrays.asList("지속", "착색력", "밀착력", "유지", "오래", "종일");
    List<String> List7 = Arrays.asList("세정", "세정력", "거품", "클렌징", "세안", "개운");
    List<String> List8 = Arrays.asList("지성", "지성인", "기름", "기름지", "여름", "지성용");
    List<String> List9 = Arrays.asList("예쁘다", "예쁘", "이쁘", "예쁜", "이쁜");
    List<String> List10 = Arrays.asList("브라이트", "갈웜", "피치", "코랄", "웜톤");
    List<String> List11 = Arrays.asList("복합성", "복합", "수부", "수부지", "속건성");
    List<String> List12 = Arrays.asList("건성", "건조", "속건정", "겨울철");
    List<String> List13 = Arrays.asList("흡수력", "흡수", "스며들", "스며드");
    List<String> List14 = Arrays.asList("데일리", "베이직", "무난", "평범");
    List<String> List15 = Arrays.asList("탄력", "앰플", "영양");
    List<String> List16 = Arrays.asList("여물", "쿨", "쿨톤");
    List<String> List17 = Arrays.asList("은은한", "골드", "분홍빛");
    List<String> List18 = Arrays.asList("산뜻", "가볍", "가벼운");
    List<String> List19 = Arrays.asList("진정", "재생", "장벽");
    List<String> List20 = Arrays.asList("보송", "매트");
    List<String> List21 = Arrays.asList("밀착", "밀착력");
    List<String> List22 = Arrays.asList("쫀쫀", "쫀득");
    List<String> List23 = Arrays.asList("되직", "무겁");
    List<String> List24 = Arrays.asList("부드러운", "부드럽");
    List<String> list4 = Arrays.asList("촉촉함","수분감");
    List<List<String>> listOfLists = Arrays.asList(List1,List2,List3,List4,List5,List6,List7,List8,List9,List10,
            List11,List12,List13,List14,List15,List16,List17,List18,List19,List20,List21,List22,List23,List24,list4);

        // 검색 버튼




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
        String searchKeyword = intent.getStringExtra("searchKeyword");
        String big_category = intent.getStringExtra("searchType");
        String mid_category = intent.getStringExtra("second_searchType");
        String userDocumentName = intent.getStringExtra("userDocumentName");
        // 검색 버튼
        ImageButton button_secondsearch = findViewById(R.id.button_secondsearch);
        button_secondsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), secondsearch.class);
                intent.putExtra("searchKeyword",autoCompleteTextView.getText().toString());
                intent.putExtra("searchType",big_category);
                intent.putExtra("second_searchType",mid_category);
                intent.putExtra("userDocumentName", userDocumentName); // secondsearch로 이름 전달합니다.
                startActivity(intent);
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.recycle_secondsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashSet<String> addedProducts = new HashSet<>(); // 중복 상품 추적을 위한 HashSet
        for (List<String> Listoflist : listOfLists) {
            if (Listoflist.contains(searchKeyword)) {
                System.out.println(searchKeyword);
                Collections.swap(Listoflist, 0, Listoflist.indexOf(searchKeyword));
                for (String keyword : Listoflist) {
                    // Firestore 데이터 가져오기
                    System.out.println(keyword);
                    db.collection("search_product")
                            .orderBy("keyword") // 키워드로 정렬
                            .whereEqualTo("big_category", big_category)
                            .whereEqualTo("mid_category", mid_category)
                            .whereEqualTo("keyword", keyword)
                            .limit(10)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("success");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String keyword = document.getString("keyword");
                                            String name = document.getString("name");
                                            String type = document.getString("big_category");
                                            String user = userDocumentName;
                                            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                                            db1.collection("Search")
                                                .whereEqualTo("name", name)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String image = document.getString("image");
                                                                String productKey = keyword + name + image + type + user;
                                                                if (!addedProducts.contains(productKey)) {
                                                                    searchitem data = new searchitem(name, image, keyword, type, user);
                                                                    datalist.add(data);
                                                                    addedProducts.add(productKey);
                                                                    System.out.println(keyword + name + image + type + user);
                                                                }
                                                            }

                                                            adapterSecondsearch.notifyDataSetChanged();
                                                        }
                                                    }});
                                        }


                                    } else {
                                        Log.d(TAG, "Error getting documents: " + task.getException());
                                    }
                                }
                            });
                }

            }
        }
        adapterSecondsearch = new Adapter_secondsearch(datalist);
        recyclerView.setAdapter(adapterSecondsearch);

    }


}

