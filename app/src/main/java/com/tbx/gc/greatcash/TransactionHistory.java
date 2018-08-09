package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransactionHistory extends Fragment {
    CardView joining, earnMore, video, shop, survey_card, offer;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hirtoryfragment, container, false);

        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        joining = view.findViewById(R.id.joining);
        earnMore = view.findViewById(R.id.earn_more);
        video = view.findViewById(R.id.video);
        shop = view.findViewById(R.id.shop);
        survey_card = view.findViewById(R.id.survey);
        offer = view.findViewById(R.id.offer);

        joining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "joining");
                startActivity(intent);


            }
        });

        earnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "earn_more");
                startActivity(intent);


            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "video");
                startActivity(intent);


            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "shop");
                startActivity(intent);


            }
        });

        survey_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "survey");
                startActivity(intent);
            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrasnsactionList.class);
                intent.putExtra("userId", pref.getString("id", ""));
                intent.putExtra("type", "offer");
                startActivity(intent);
            }
        });

        return view;
    }
}
