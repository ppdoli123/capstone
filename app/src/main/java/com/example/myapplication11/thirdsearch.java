package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thirdsearch extends AppCompatActivity {
    private ListView ListView1;
    private FirebaseFirestore db;
    private List<Pair<String, Float>> chartValues; // 전역 변수로 선언

    Intent intent;
    TextView productName;
    TextView productprice;
    Button productpage;
    ImageView productImage;
    PieChart chart1;
    BarChart chart2;
    String imageUrl;
    String documentName;
    String name;
    String type;

    public void initView(View v) {
        chart1 = (PieChart) v.findViewById(R.id.tab1_chart_1);
        chart2 = (BarChart) v.findViewById(R.id.tab1_chart_2);
    }

    // 파이 차트 설정
    private void setPieChart(String documentId) {
        chart1.clearChart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productRef = db.collection("product");
        int[] colors = {Color.parseColor("#CDA67F"), Color.parseColor("#00BFFF"), Color.parseColor("#007F7F"), Color.parseColor("#C8A2C8")};

        productRef.document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // 필드 값이 숫자인 필드명과 값을 저장할 맵 생성
                        Map<String, Long> valuesMap = new HashMap<>();
                        Map<String, Object> data = document.getData();
                        // 숫자 타입의 필드를 찾아 valuesMap에 추가
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            if (entry.getValue() instanceof Number) {
                                long value = ((Number) entry.getValue()).longValue();
                                valuesMap.put(entry.getKey(), value);
                            }
                        }
                        productprice = findViewById(R.id.price);
                        productpage = findViewById(R.id.product_page);
                        String price = document.getString("price");
                        String pagelink = document.getString("link");
                        productprice.setText("가격: "+price+"원");
                        productpage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent openWebPageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pagelink));
                                startActivity(openWebPageIntent);
                            }
                        });

                        // 값을 기준으로 내림차순으로 정렬된 엔트리 리스트 생성
                        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(valuesMap.entrySet());
                        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                        // 가장 큰 값부터 차트에 추가 (최대 4개)
                        for (int i = 0; i < sortedEntries.size() && i < 4; i++) {
                            Map.Entry<String, Long> entry = sortedEntries.get(i);
                            String type = entry.getKey();
                            long value = entry.getValue();
                            int color = colors[i];
                            chart1.addPieSlice(new PieModel(type, value, color));

                            // 해당 필드 이름을 TextView에 설정
                            if (i == 0) {
                                TextView textView1 = findViewById(R.id.reviewer1);
                                textView1.setText(type);
                            } else if (i == 1) {
                                TextView textView2 = findViewById(R.id.reviewer2);
                                textView2.setText(type);
                            } else if (i == 2) {
                                TextView textView3 = findViewById(R.id.reviewer3);
                                textView3.setText(type);
                            } else if (i == 3) {
                                TextView textView4 = findViewById(R.id.reviewer4);
                                textView4.setText(type);
                            }
                        }

                        chart1.startAnimation();
                    } else {
                        Log.d(TAG, "No documents found in 'product' collection.");
                    }
                } else {
                    Log.d(TAG, "Failed to fetch documents: " + task.getException());
                }
            }
        });
    }

    // 막대 차트 설정
    private CollectionReference keywordCollection;

    private void processData(final List<DocumentSnapshot> keywordDocs) {
        List<Pair<String, Float>> valueList = new ArrayList<>();

        // 해당하는 데이터 찾기
        for (DocumentSnapshot keywordDoc : keywordDocs) {
            for (String field : keywordDoc.getData().keySet()) {
                if (!field.equals("recommCnt") && !field.equals("rescore") && !field.equals("name") && !field.equals("img_url")) {
                    Float fieldValue = keywordDoc.getDouble(field).floatValue();
                    // 필드 이름과 그 값을 저장
                    valueList.add(new Pair<>(field, fieldValue));
                }
            }
        }

        // 가장 큰 값 3개와 가장 작은 값 3개를 찾고 정렬
        Collections.sort(valueList, new Comparator<Pair<String, Float>>() {

            @Override
            public int compare(Pair<String, Float> o1, Pair<String, Float> o2) {
                return o2.second.compareTo(o1.second);
            }
        });

        List<Pair<String, Float>> chartValues = new ArrayList<>();

        if (valueList.size() >= 6) {
            chartValues.addAll(valueList.subList(0, 3)); // 상위 3 값을 추가
            for (int i = valueList.size() - 3; i < valueList.size(); i++) {
                Pair<String, Float> pair = valueList.get(i);
                chartValues.add(new Pair<>(pair.first, pair.second * -1)); // -1을 곱하여 하위 3 값을 추가
            }
        } else {
            chartValues.addAll(valueList);
        }
        setBarChart(valueList);
        // 가장 큰 값 10개를 찾고 정렬
        Collections.sort(chartValues, (o1, o2) -> o2.second.compareTo(o1.second));

// 상위 3개 아이템을 가져오기 위한 리스트
        List<Pair<String, Float>> topThreeValues = chartValues.subList(0, Math.min(3, chartValues.size()));

// 상위 3개의 차트 값에서 필드 이름(String)만 뽑아내어 리스트로 저장
        List<String> topThreeFieldNames = new ArrayList<>();
        for (Pair<String, Float> pair : topThreeValues) {
            topThreeFieldNames.add(pair.first);
        }

// 필드 이름 리스트를 사용하여 상세 정보 가져오기
        fetchTopTenProductDetails(topThreeFieldNames);
    }
    private void fetchTopTenProductDetails(List<String> fieldNames) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference keywordCollection = db.collection("keyword");

        keywordCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Map<String, Integer> fieldNameToCount = new HashMap<>();

                // 각 문서의 필드 값을 확인하며 각 필드의 개수 합산
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    for (String fieldName : fieldNames) {
                        Integer prevCount = fieldNameToCount.getOrDefault(fieldName, 0);

                        String nameFieldValue = doc.getString("name");
                        if (nameFieldValue != null) {
                            long fieldValue = (long) doc.get(fieldName);
                            int newValue = prevCount + (int) fieldValue;
                            fieldNameToCount.put(nameFieldValue, newValue);
                        }

                }
                }

                // 개수가 높은 순서로 상위 10개 필드를 선택하여 결과값으로 설정
                List<Map.Entry<String, Integer>> entryList = new ArrayList<>(fieldNameToCount.entrySet());
                entryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                int itemCount = Math.min(entryList.size(), 10);
                for (int i = 0; i < itemCount; i++) {
                     // 로그 추가 부분
                    Map.Entry<String, Integer> entry = entryList.get(i);
                    String fieldName = entry.getKey();
                    int count = entry.getValue();

                    keywordCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                String fieldValue = doc.getString("name");
                                if (fieldValue != null && fieldValue.equals(fieldName)) {
                                    String productId = doc.getId();
                                    String itemimage_thirdsearch = doc.getString("img_url");
                                    String itemtitle_thirdsearch = fieldValue;

                                    // RecyclerView 아이템 추가
                                    adapter.addItem(itemimage_thirdsearch, itemtitle_thirdsearch, productId);
                                    Log.d("MyTag", "Adapter item 추가됨"); // 로그 추가 부분

                                    if (itemCount == adapter.getItemCount()) {
                                        adapter.notifyDataSetChanged();
                                        Log.d("MyTag", "Adapter 갱신됨"); // 로그 추가 부분
                                    }
                                    break; // 원하는 값을 찾았으므로 반복문 종료
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("MyTag", "Query 실행 중 오류 발생: " + e.getMessage()); // 예외 발생 시 오류 메시지 출력
                        }
                    });

                }
            }
        });
    }




    private void setBarChart(List<Pair<String, Float>> chartValues) {
        chart2.clearChart();

        int count = 0;

        for (Pair<String, Float> value : chartValues) {
            if (count < 3) {
                chart2.addBar(new BarModel(value.first, value.second, 0xFF56B7F1));
            } else {
                chart2.addBar(new BarModel(value.first, value.second, Color.RED));
            }
            count++;
        }

        chart2.startAnimation();
    }

    private void fetchKeyword(String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        keywordCollection = db.collection("keyword");

        keywordCollection.whereEqualTo("name", name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> keywordDocs = queryDocumentSnapshots.getDocuments();
                processData(keywordDocs);
            }
        });
    }

    RecyclerView recyclerView;
    Adapter_thirdsearch adapter;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdsearch);

        productName = findViewById(R.id.product_name);
        productImage = findViewById(R.id.product_image);
        chart1 = findViewById(R.id.tab1_chart_1);
        chart2 = findViewById(R.id.tab1_chart_2);

        Intent intent = getIntent();
        if (intent != null) {
            String documentName = intent.getStringExtra("documentName");
            String imageUrl = intent.getStringExtra("imageUrl");
            String name = intent.getStringExtra("name");

            productName.setText(name); // 상품명 설정
            Glide.with(thirdsearch.this)
                    .load(imageUrl)
                    .into(productImage); // 이미지 로드

            // 파이 차트 설정
            setPieChart(documentName);

            // 리사이클러뷰 설정
            RecyclerView recyclerView = findViewById(R.id.recycle_thirdsearch);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            adapter = new Adapter_thirdsearch(this);
            recyclerView.setAdapter(adapter);

            fetchKeyword(name);

            // '전체 리뷰 보기' 버튼 클릭 이벤트 처리
            Button button = findViewById(R.id.allreview);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), TotalReviewActivity.class);
                    startActivity(intent);
                }
            });
        }
    }



}