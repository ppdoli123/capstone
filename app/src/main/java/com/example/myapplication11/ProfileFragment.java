package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication11.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String userDocumentName;
    TextView profileName;
    TextView profileType;
    TextView profileLike;
    ImageView profileImage;
    Intent intent;
    RecyclerView recyclerView;
    Adapter_secondsearch adapterSecondsearch;
    String user;
    LinearLayout visibility;


    // 데이터 리스트
    private List<searchitem> datalist;
    private List<String> list;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String userDocumentName) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find views by ID
        profileName = view.findViewById(R.id.profile_name);
        profileType = view.findViewById(R.id.profile_type);
        profileLike = view.findViewById(R.id.like_num);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore에서 userDocumentName을 이용해 DocumentReference 생성
        DocumentReference userDocRef = db.collection("users").document(userDocumentName);

        // DocumentReference로부터 데이터를 읽어오는 작업 실행
        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // 데이터 불러오기 성공, 이제 UI를 업데이트합니다.
                        String username = document.getString("username");
                        String userpreference1 = document.getString("user_preference_1");
                        String userpreference2 = document.getString("user_preference_2");
                        profileName.setText(username);
                        profileType.setText(userpreference1 + " " + userpreference2);
                        Object likeObj = document.get("Like");
                        if (likeObj != null) {
                            List<String> like_num = (List<String>) likeObj;
                            profileLike.setText(String.valueOf(like_num.size()));
                        } else {
                            // handle the case when "Like" is null
                            profileLike.setText("0");
                        }

                        // Storage_Like 클래스의 코드를 직접 수행
                        datalist = new ArrayList<searchitem>();
                        List<String> existingArray = (List<String>) document.get("Like");

                        //HomeFragment 로부터 searchItem , searchType 받아오기

                        recyclerView = view.findViewById(R.id.recycle_secondsearch);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                        // Firestore 데이터 가져오기
                        try {
                            for (String name : existingArray) {

                                db.collection("Search")
                                        .whereEqualTo("name", name)
                                        .limit(1)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String name = document.getString("name");
                                                        String image = document.getString("image");
                                                        String keyword = document.getString("keyword");
                                                        String type = document.getString("type");
                                                        String user = userDocumentName;
                                                        searchitem data = new searchitem(name, image, keyword, type, user);
                                                        datalist.add(data);
                                                    }
                                                    adapterSecondsearch.notifyDataSetChanged();
                                                } else {
                                                    Log.d(TAG, "Error getting documents: " + task.getException());
                                                }
                                            }
                                        });
                            }
                        }catch (NullPointerException e) {
                                // Null 예외 처리
                            profileType.setText("");
                            Log.e(TAG, "existingArray is null", e);
                            }


                        adapterSecondsearch = new Adapter_secondsearch(datalist);
                        recyclerView.setAdapter(adapterSecondsearch);
                    } else {
                        Log.d(TAG, "No such document");
                    }

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}