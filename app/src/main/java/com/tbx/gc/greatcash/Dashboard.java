package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.dashboardPOJO.dashboardBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Dashboard extends Fragment{

    RelativeLayout locked;

    TextView totalAmount;

    TextView level1join ,level1amount;
    TextView level2join ,level2amount;
    TextView level3join ,level3amount;
    TextView level4join ,level4amount;
    TextView level5join ,level5amount;
    TextView level6join ,level6amount;
    TextView level7join ,level7amount;
    TextView textview_28;

    ProgressBar progress;

    SharedPreferences pref;

    TextView marquee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        locked = view.findViewById(R.id.textView5);
        totalAmount = view.findViewById(R.id.textView28);

        marquee = view.findViewById(R.id.textView6);

        marquee.setText("To unlock the dashboard you have to complete the challenges by installing all the apps");
        marquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        marquee.setSingleLine(true);
        marquee.setMarqueeRepeatLimit(5);
        marquee.setSelected(true);

        level1join = view.findViewById(R.id.textView8);
        level2join = view.findViewById(R.id.textView38);
        level3join = view.findViewById(R.id.textView18);
        level4join = view.findViewById(R.id.textView46);
        level5join = view.findViewById(R.id.textView42);
        level6join = view.findViewById(R.id.textView54);
        level7join = view.findViewById(R.id.textView50);

        level1amount = view.findViewById(R.id.textView10);
        level2amount = view.findViewById(R.id.textView40);
        level3amount = view.findViewById(R.id.textView16);
        level4amount = view.findViewById(R.id.textView48);
        level5amount = view.findViewById(R.id.textView44);
        level6amount = view.findViewById(R.id.textView56);
        level7amount = view.findViewById(R.id.textView52);

        textview_28=view.findViewById(R.id.textView28);

        progress = view.findViewById(R.id.progressBar13);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);


        final bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("dashboard");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<dashboardBean> call = cr.dashboard(body);
        call.enqueue(new Callback<dashboardBean>() {
            @Override
            public void onResponse(Call<dashboardBean> call, Response<dashboardBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    if (response.body().getData().getDashboardStatus().equals("1"))
                    {
                        b.locked = true;

                        locked.setVisibility(View.VISIBLE);
                        marquee.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        b.locked = false;

                        locked.setVisibility(View.GONE);
                        marquee.setVisibility(View.GONE);
                        level1join.setText(response.body().getData().getLevel1Joins());
                        level2join.setText(response.body().getData().getLevel2Joins());
                        level3join.setText(response.body().getData().getLevel3Joins());
                        level4join.setText(response.body().getData().getLevel4Joins());
                        level5join.setText(response.body().getData().getLevel5Joins());
                        level6join.setText(response.body().getData().getLevel6Joins());
                        level7join.setText(response.body().getData().getLevel7Joins());

                        level1amount.setText("$ " + response.body().getData().getLevel1Amount());
                        level2amount.setText("$ " + response.body().getData().getLevel2Amount());
                        level3amount.setText("$ " + response.body().getData().getLevel3Amount());
                        level4amount.setText("$ " + response.body().getData().getLevel4Amount());
                        level5amount.setText("$ " + response.body().getData().getLevel5Amount());
                        level6amount.setText("$ " + response.body().getData().getLevel6Amount());
                        level7amount.setText("$ " + response.body().getData().getLevel7Amount());

                        textview_28.setText("$ " + response.body().getData().getTotalAmountEarned());
                    }
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<dashboardBean> call, Throwable t) {
             progress.setVisibility(View.GONE);
            }
        });


    }
}
