package com.tbx.gc.greatcash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdVideos extends AppCompatActivity implements RewardedVideoAdListener {

    RelativeLayout back;
    ProgressBar progress;

    ImageView admob , adcolony;

    private RewardedVideoAd mRewardedVideoAd;
    AdColonyInterstitial ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_videos);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/5224354917");

        AdColony.configure(this , "appff5f6196e8c64c63aa", "vz560c17c1b60147b8bd");
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

                AdColony.requestInterstitial("vz560c17c1b60147b8bd", listener);



            }
        });

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("videoad" , "loadded");
        progress.setVisibility(View.GONE);
        mRewardedVideoAd.show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d("videoad" , "opened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d("videoad" , "started");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d("videoad" , "closed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.d("videoad" , String.valueOf(rewardItem.getAmount()));
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d("videoad" , "left");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d("videoad" , String.valueOf(i));
        progress.setVisibility(View.GONE);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
