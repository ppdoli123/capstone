package com.example.myapplication11;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Adapter_secondsearch extends RecyclerView.Adapter<ViewHolder_seocondsearch> {
    private List<searchitem> dataList;
    private ImageView imageView;

    public Adapter_secondsearch(List<searchitem> dataList) {
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public ViewHolder_seocondsearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_secondsearch, parent, false);

        ViewHolder_seocondsearch viewHolderSeocondsearchSearch = new ViewHolder_seocondsearch(context, view);
        return viewHolderSeocondsearchSearch;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_seocondsearch holder, int position) {


        String name = dataList.get(position).getName();
        String itemtag = dataList.get(position).getKeyword();
        // 이미지 URL
        String imageUrl = dataList.get(position).getImageUrl();
        String user = dataList.get(position).getUser();

        // 이미지 로드 작업 시작
        new LoadImageTask(holder.itemimage_secondsearch).execute(imageUrl);
        holder.itemtitle_secondsearch.setText(name);
        holder.itemtag_secondsearch.setText(itemtag);
        holder.item_secondsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("product")
                        .whereEqualTo("name", name) // 이름이 "John"인 데이터만 필터링
                        .limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String documentId = document.getId();
                                        String name = document.getString("name");
                                        String image = document.getString("img_url");
                                        Context context = view.getContext();
                                        Intent thirdsearchIntent = new Intent(context, thirdsearch.class);
                                        thirdsearchIntent.putExtra("documentName", documentId);
                                        // add imageUrl and name to the intent
                                        thirdsearchIntent.putExtra("imageUrl", image);
                                        thirdsearchIntent.putExtra("name", name);
                                        thirdsearchIntent.putExtra("user", user);
                                        context.startActivity(thirdsearchIntent);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: " + task.getException());
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

}
