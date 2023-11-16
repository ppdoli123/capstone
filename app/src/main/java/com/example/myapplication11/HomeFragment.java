package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication11.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String userDocumentName;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String userDocumentName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("userDocumentName", userDocumentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userDocumentName = getArguments().getString("userDocumentName");

        }
    }
    // 그래프 연동
    BarChart chart2;
    ImageButton button_secondsearch;
    Button like_button;
    public void initView(View v){

        chart2 = (BarChart)v.findViewById(R.id.tab1_chart_2);

    }


    // 막대 차트 설정
    private void setBarChart() {

        chart2.clearChart();
        // value에 값을 넣으면 됌
        chart2.addBar(new BarModel("피부", 8637f, 0xFF56B7F1));
        chart2.addBar(new BarModel("색상", 6974f, 0xFF56B7F1));
        chart2.addBar(new BarModel("바르다", 6139f, 0xFF56B7F1));
        chart2.addBar(new BarModel("컬러", 4827f, 0xFF56B7F1));
        chart2.addBar(new BarModel("그리다", 4589f, 0xFF56B7F1));
        chart2.addBar(new BarModel("번짐", 4391f, 0xFF56B7F1));

        chart2.startAnimation();

    }

    // 연관 검색 바 설정
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
    private void settingList(){
        list = Arrays.asList(wordsArray);
    }

    RecyclerView recyclerView;
    Adapter_main adapter;
    String second_Spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fetchDataAndPopulateRecyclerView();

        // 연관 검색창
        list = new ArrayList<String>();

        // 리스트에 검색될 데이터(단어)를 추가한다.
        settingList();

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) root.findViewById(R.id.autoCompleteTextView);

        // AutoCompleteTextView 에 아답터를 연결한다.
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line,  list ));

        // 리사이클러뷰
        recyclerView=(RecyclerView) root.findViewById(R.id.recycle_mainsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        // 강조 시각. 이 부분을 수정합니다.
        if (getArguments() != null) {
            userDocumentName = getArguments().getString("userDocumentName");
            Log.d("HomeFragment", "userDocumentName: " + userDocumentName); // 디버그용 로그 출력
        } else {
            Log.d("HomeFragment", "userDocumentName is null"); // 디버그용 로그 출력
        }
        adapter= new Adapter_main();
        recyclerView.setAdapter(adapter); // 어댑터 연결
        initView(root);
        setBarChart();

        // Spinner
        Spinner itemSpinner = (Spinner)root.findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(spinnerAdapter);

        // second_Spinner
        Spinner second_itemSpinner = (Spinner)root.findViewById(R.id.spinner1);
        // spinner listner1
        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 스피너에서 아이템이 선택되었을 때 수행할 작업을 여기에 추가합니다.
                    String selectedValue = parentView.getItemAtPosition(position).toString();
                    System.out.println(selectedValue);
                    ArrayAdapter<CharSequence> second_spinnerAdapter;
                    if(selectedValue.equals("베이스메이크업")){
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner1, android.R.layout.simple_spinner_item);
                    } else if (selectedValue.equals("립메이크업")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner2, android.R.layout.simple_spinner_item);
                    }else if (selectedValue.equals("남성 스킨케어")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner3, android.R.layout.simple_spinner_item);
                    }else if (selectedValue.equals("남성 메이크업")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner4, android.R.layout.simple_spinner_item);
                    }else if (selectedValue.equals("클렌징")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner5, android.R.layout.simple_spinner_item);
                    }else if (selectedValue.equals("스킨케어")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner6, android.R.layout.simple_spinner_item);
                    }else if (selectedValue.equals("선케어")) {
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner7, android.R.layout.simple_spinner_item);
                    } else{
                        second_spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.spinner8, android.R.layout.simple_spinner_item);
                    }
                    second_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    second_itemSpinner.setAdapter(second_spinnerAdapter);
                }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무 것도 선택되지 않았을 때 수행할 작업을 여기에 추가합니다.
            }
        });

        //spinner listener2
        second_itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 스피너에서 아이템이 선택되었을 때 수행할 작업을 여기에 추가합니다.
                second_Spinner=second_itemSpinner.getSelectedItem().toString();
                System.out.println(second_Spinner);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무 것도 선택되지 않았을 때 수행할 작업을 여기에 추가합니다.
            }
        });

        // 검색 버튼
        button_secondsearch = root.findViewById(R.id.button_secondsearch);
        button_secondsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),secondsearch.class);
                intent.putExtra("searchKeyword",autoCompleteTextView.getText().toString());
                intent.putExtra("searchType",itemSpinner.getSelectedItem().toString());
                intent.putExtra("second_searchType",second_Spinner);
                intent.putExtra("userDocumentName", userDocumentName); // secondsearch로 이름 전달합니다.
                startActivity(intent);
            }
        });


        return root;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void fetchDataAndPopulateRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (userDocumentName == null) {
            return;
        }
        // 사용자 정보 가져오기
        db.collection( "users")
                .document(userDocumentName) // 로그인 사용자의 ID를 지정해 주세요
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String user_preference1 = document.getString("user_preference_1");
                        String user_preference2 = document.getString("user_preference_2");

                        // Get the TextView and set its text
                        TextView preferenceTextView = binding.preference;
                        TextView preferenceTextView_1 = binding.preference1;
                        preferenceTextView.setText(user_preference1 + " " + user_preference2);

                        if (user_preference1 == null && user_preference2 == null) {
                            // 두 사용자 환경설정이 모두 NULL인 경우 처리
                            preferenceTextView.setText("사용자 환경설정이 없습니다.");
                            preferenceTextView_1.setText("");

                        } else {
                            Task<QuerySnapshot> mainTask = null; // 여기에 null로 초기화를 추가합니다.

                            if (user_preference1 != null) {
                                mainTask = db.collection("product")
                                        .orderBy(FieldPath.of(user_preference1), Query.Direction.DESCENDING)
                                        .limit(10)
                                        .get();
                            } else if(user_preference2 != null) {
                                mainTask = db.collection("product")
                                        .orderBy(FieldPath.of(user_preference2), Query.Direction.DESCENDING)
                                        .limit(10)
                                        .get();
                            }

// mainTask가 정상적으로 초기화되었다면 처리를 진행합니다.
                            if (mainTask != null) {
                                mainTask.addOnSuccessListener(querySnapshot -> {
                                    processQuerySnapshot(querySnapshot);
                                });

                                mainTask.addOnFailureListener(e -> {
                                    if (user_preference2 != null) {
                                        // 실패한 경우 두 번째 환경설정 사용 (만약 user_preference1이 사용된 경우)
                                        Task<QuerySnapshot> secondTask = db.collection("product")
                                                .orderBy(FieldPath.of(user_preference2), Query.Direction.DESCENDING)
                                                .limit(10)
                                                .get();

                                        secondTask.addOnSuccessListener(querySnapshot -> {
                                            processQuerySnapshot(querySnapshot);
                                        });
                                    }
                                    else if (user_preference1 != null) {
                                        // 실패한 경우 두 번째 환경설정 사용 (만약 user_preference1이 사용된 경우)
                                        Task<QuerySnapshot> secondTask = db.collection("product")
                                                .orderBy(FieldPath.of(user_preference1), Query.Direction.DESCENDING)
                                                .limit(10)
                                                .get();

                                        secondTask.addOnSuccessListener(querySnapshot -> {
                                            processQuerySnapshot(querySnapshot);
                                        });
                                    }
                                });
                            }
                        }
                    } else {
                        Log.d("HomeFragment", "No such document");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HomeFragment", "Error adding document", e);
                    }
                });
    }

// 나머지 코드는 그대로 유지 ...


    private void processQuerySnapshot(QuerySnapshot querySnapshot) {
        Set<String> addedProducts = new HashSet<>();
        if (!querySnapshot.isEmpty()) {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                String productId = doc.getId();
                if (!addedProducts.contains(productId)) {
                    addedProducts.add(productId);

                    String itemimage_thirdsearch = doc.getString("img_url");
                    String itemtitle_thirdsearch = doc.getString("name");
                    // RecyclerView 아이템 추가

                        adapter.addItem(new ProductItem(itemimage_thirdsearch, itemtitle_thirdsearch), userDocumentName ,productId);

                }
            }
            // 어댑터를 업데이트한다.
            adapter.notifyDataSetChanged();
        }
    }
                        }


