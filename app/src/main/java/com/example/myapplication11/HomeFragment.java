package com.example.myapplication11;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication11.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Tasks;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.List;
import android.widget.AutoCompleteTextView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// 필요한 import 문 추가
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.widget.TextView;
import android.widget.Toast;


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
    private void settingList(){
        list.add("촉촉한");
        list.add("수분감");
        list.add("보송한");
        list.add("시원한");
        list.add("상쾌한");
        list.add("waterful");
    }

    RecyclerView recyclerView;
    Adapter_main adapter;


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

        // 검색 버튼
        button_secondsearch = root.findViewById(R.id.button_secondsearch);
        button_secondsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),secondsearch.class);
                intent.putExtra("searchType",autoCompleteTextView.getText().toString());
                intent.putExtra("searchItem",itemSpinner.getSelectedItem().toString());
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
        db.collection("users")
                .document(userDocumentName) // 로그인 사용자의 ID를 지정해 주세요
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String user_preference1 = document.getString("user_preference_1");
                        String user_preference2 = document.getString("user_preference_2");

                        // Get the TextView and set its text
                        TextView preferenceTextView = binding.preference;
                        preferenceTextView.setText(user_preference1 + "," + user_preference2);

                        if (user_preference1 == null && user_preference2 == null) {
                            // 두 사용자 환경설정이 모두 NULL인 경우 처리
                            Toast.makeText(getContext(), "사용자 환경설정이 없습니다. 기본 설정을 사용합니다.", Toast.LENGTH_SHORT).show();

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

                        adapter.addItem(new ProductItem(itemimage_thirdsearch, itemtitle_thirdsearch),productId);

                }
            }
            // 어댑터를 업데이트한다.
            adapter.notifyDataSetChanged();
        }
    }
                        }


