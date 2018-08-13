package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.tbx.gc.greatcash.rewardPOJO.Data;
import com.tbx.gc.greatcash.rewardPOJO.rBean;
import com.tbx.gc.greatcash.rewardPOJO.rewardean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AdVideos extends AppCompatActivity implements RewardedVideoAdListener {

    RelativeLayout back;
    ProgressBar progress;

    LinearLayout admob, adcolony;

    private RewardedVideoAd mRewardedVideoAd;
    AdColonyInterstitial ad;

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_videos);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/5224354917");

        AdColony.configure(this, "app1d33adaa4da141189b", "vz779e4eb8ede841a0b6");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(AdVideos.this);


        admob = findViewById(R.id.admob);
        adcolony = findViewById(R.id.adcolony);
        back = findViewById(R.id.back);
        progress = findViewById(R.id.progress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        admob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                        new AdRequest.Builder().build());
                progress.setVisibility(View.VISIBLE);
            }
        });

        adcolony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.setVisibility(View.VISIBLE);

                AdColonyInterstitialListener listener = new AdColonyInterstitialListener() {
                    @Override
                    public void onRequestFilled(AdColonyInterstitial ad) {
                        /** Store and use this ad object to show your ad when appropriate */
                        ad.show();

                        progress.setVisibility(View.GONE);
                    }

                };
                AdColonyRewardListener listener2 = new AdColonyRewardListener() {
                    @Override
                    public void onReward(AdColonyReward reward) {
                        /** Query the reward object for information here */


                        progress.setVisibility(View.VISIBLE);


                        bean b = (bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        ApiInterface cr = retrofit.create(ApiInterface.class);

                        rewardean body = new rewardean();

                        body.setAction("advideo_earning");

                        Data data = new Data();

                        data.setAdId("2");
                        data.setUserId(pref.getString("id", ""));

                        body.setData(data);

                        Call<rBean> call = cr.rewardAd(body);

                        call.enqueue(new Callback<rBean>() {
                            @Override
                            public void onResponse(Call<rBean> call, Response<rBean> response) {

                                Toast.makeText(AdVideos.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<rBean> call, Throwable t) {

                                progress.setVisibility(View.GONE);
                            }
                        });


                    }
                };

/** Set reward listener for your app to be alerted of reward events */
                AdColony.setRewardListener(listener2);

                AdColony.requestInterstitial("vz779e4eb8ede841a0b6", listener);


            }
        });

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("videoad", "loadded");
        progress.setVisibility(View.GONE);
        mRewardedVideoAd.show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d("videoad", "opened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d("videoad", "started");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d("videoad", "closed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        rewardean body = new rewardean();

        body.setAction("advideo_earning");

        Data data = new Data();

        data.setAdId("1");
        data.setUserId(pref.getString("id", ""));

        body.setData(data);

        Call<rBean> call = cr.rewardAd(body);

        call.enqueue(new Callback<rBean>() {
            @Override
            public void onResponse(Call<rBean> call, Response<rBean> response) {

                Toast.makeText(AdVideos.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<rBean> call, Throwable t) {

                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d("videoad", "left");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d("videoad", String.valueOf(i));
        progress.setVisibility(View.GONE);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
