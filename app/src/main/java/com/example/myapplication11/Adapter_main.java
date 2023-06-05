package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 메인화면 리뷰 토픽 리사이클러뷰 어답터

public class Adapter_main extends RecyclerView.Adapter<ViewHolder_main> {
    private ArrayList<String> arrayList;

    public Adapter_main() {
        arrayList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder_main onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // thirdsearch에서 비슷한 상품 추천시 사용되는 리사이클러뷰와 동일해서 item_List2 사용
        View view = inflater.inflate(R.layout.item_list2, parent, false);

        ViewHolder_main viewHolderMain = new ViewHolder_main(context, view);
        return viewHolderMain;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_main holder, int position) {
        String text = arrayList.get(position);
        holder.itemtitle_thirdsearch.setText(text);
        holder.itemimage_thirdsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();
                Intent thirdsearch = new Intent(context, thirdsearch.class);
                (context).startActivity(thirdsearch);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(String strData) {
        arrayList.add(strData);
    }
}
