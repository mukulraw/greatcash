package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.redeemPOJO.redeemBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.tbx.gc.greatcash.MainActivity.str_totalAmount;

public class Wallat extends Fragment {


    Button redeem;
    ProgressBar progress;
    TextView points;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.walletfragment, container, false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        points = view.findViewById(R.id.points);
        progress = view.findViewById(R.id.progress);
        redeem = view.findViewById(R.id.redeem);

        redeem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getActivity(),Redeem.class);
                intent.putExtra("amount" , str_totalAmount);
                startActivity(intent);

            }
        });

        points.setText("$"+str_totalAmount);


//        redeem.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//                progress.setVisibility(View.VISIBLE);
//
//
//                bean b = (bean) getContext().getApplicationContext();
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(b.BASE_URL)
//                        .addConverterFactory(ScalarsConverterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//
//                ApiInterface cr = retrofit.create(ApiInterface.class);
//
//                challengeRequestBean body = new challengeRequestBean();
//
//                com.tbx.gc.greatcash.challengeRequestPOJO.Data data = new com.tbx.gc.greatcash.challengeRequestPOJO.Data();
//
//                body.setAction("redeem");
//
//                data.setUserId(pref.getString("id", ""));
//
//                Log.d("iidd" , pref.getString("id" , ""));
//
//                body.setData(data);
//
//                Call<redeemBean> call = cr.redeem(body);
//
//                call.enqueue(new Callback<redeemBean>() {
//                    @Override
//                    public void onResponse(Call<redeemBean> call, Response<redeemBean> response) {
//
//                        if (response.body().getStatus().equals("1"))
//                        {
//
//                            Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
//
//                        }
//                        else
//                        {
//                            Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
//                        }
//
//                        progress.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onFailure(Call<redeemBean> call, Throwable t) {
//progress.setVisibility(View.GONE);
//                    }
//                });
//
//
//            }
//        });


        return view;
    }



}
