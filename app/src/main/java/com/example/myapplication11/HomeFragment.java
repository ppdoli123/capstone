package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication11.databinding.FragmentHomeBinding;

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
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        chart2.addBar(new BarModel("12", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("13", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("14", 20f, 0xFF56B7F1));
        chart2.addBar(new BarModel("15", 20f, 0xFF56B7F1));
        chart2.addBar(new BarModel("16", 10f, 0xFF56B7F1));
        chart2.addBar(new BarModel("17", 10f, 0xFF56B7F1));

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

        adapter= new Adapter_main();
        for (int i =0; i<10;i++){
            String str = i+"번째 아이템";
            adapter.setArrayList(str);
        }
        recyclerView.setAdapter(adapter);

        // 바차트 생성
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
}