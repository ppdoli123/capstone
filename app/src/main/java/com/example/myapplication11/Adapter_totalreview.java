package com.example.myapplication11;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_totalreview extends RecyclerView.Adapter<ViewHolder_totalreview> {
    private ArrayList<String> arrayList;
    private List<searchreview> dataList;

    public Adapter_totalreview(List<searchreview> dataList) {
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public ViewHolder_totalreview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_showreview, parent, false);

        ViewHolder_totalreview viewHolderTotalreview = new ViewHolder_totalreview(context, view);
        return viewHolderTotalreview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_totalreview holder, int position) {
        String review = dataList.get(position).getReview();
        String sentence = dataList.get(position).getSentence();
        String positive = dataList.get(position).getPositiveSentence();
        String negative = dataList.get(position).getNegativeSentence();
        SpannableString spannableString = new SpannableString(review);

        // Positive keyword color change
        int positiveStartIndex = review.indexOf(positive);
        if (positiveStartIndex != -1) {
            int endIndex = positiveStartIndex + positive.length();
            ForegroundColorSpan positiveForegroundSpan = new ForegroundColorSpan(Color.BLUE);
            spannableString.setSpan(positiveForegroundSpan, positiveStartIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Negative keyword color change
        int negativeStartIndex = review.indexOf(negative); // negative 추가
        if (negativeStartIndex != -1) { // negative 추가
            int endIndex = negativeStartIndex + negative.length(); // negative 추가
            ForegroundColorSpan negativeForegroundSpan = new ForegroundColorSpan(Color.RED); // negative 추가
            spannableString.setSpan(negativeForegroundSpan, negativeStartIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // negative 추가
        }


        holder.review_totalreview.setText(spannableString);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
