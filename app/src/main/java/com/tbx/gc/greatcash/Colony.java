package com.tbx.gc.greatcash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyNativeAdView;
import com.adcolony.sdk.AdColonyNativeAdViewListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.InterstitialAd;
import com.heyzap.sdk.ads.NativeAd;
import com.heyzap.sdk.ads.NativeListener;
import com.heyzap.sdk.ads.OfferWall;

public class Colony extends AppCompatActivity {


    private AdView mAdView;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colony);

        linear = findViewById(R.id.linear);

        mAdView = new AdView(this);

        AdSize adSize = new AdSize(300 , 100);

        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));

        linear.addView(mAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        AdView adView1 = new AdView(this);
        adView1.setAdSize(adSize);
        adView1.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));

        linear.addView(adView1);

        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);



    }

    public void setupNativeAdView(final NativeAd nativeAd) {
        // This is the method called inside onAdLoaded in the previous section.
        // It will fill in the views from our XML file with the native ad's data.

        // Get the relevant data from the native ad.
        String title = nativeAd.getTitle();
        String body = nativeAd.getBody();
        String callToAction = nativeAd.getCallToAction();
        NativeAd.Image icon = nativeAd.getIcon();
        NativeAd.Image coverImage = nativeAd.getCoverImage();



       // Get the ad container (these views would need to be in our XML file).
        // Let's assume the container is hidden by default (we will show it when
        // we're done setting it up).

        // Register our ad view with the NativeAd.
        // This is important for the third-party SDKs to track impressions,
        // clicks, etc.

    }

}
