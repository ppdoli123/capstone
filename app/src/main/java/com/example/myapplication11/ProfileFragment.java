package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication11.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


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