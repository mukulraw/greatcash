package com.tbx.gc.greatcash;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyNativeAdView;
import com.adcolony.sdk.AdColonyNativeAdViewListener;
import com.tbx.gc.greatcash.affPOJO.Datum;
import com.tbx.gc.greatcash.affPOJO.affBean;
import com.tbx.gc.greatcash.audioPOJO.audioBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.shoppingPOJO.Sale;
import com.tbx.gc.greatcash.shoppingPOJO.shoppingBean;
import com.tbx.gc.greatcash.videoAmountPOJO.videoAmountBean;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.NativeAd;
import com.heyzap.sdk.ads.NativeListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Shopping extends Fragment {

    RecyclerView grid;
    SaleAdapter adapter;
    GridLayoutManager manager;
    List<Datum> list;
    ProgressBar progress;
    SharedPreferences pref;
    TextView thisMonth, today, text, amount;

    TextView audio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_layout, container, false);

        /*HeyzapAds.setGdprConsent(true, getActivity());
         */
        audio = view.findViewById(R.id.textView31);

        //HeyzapAds.start("9d60ad7678088ee38a3b8f7112199c0c", getActivity(), HeyzapAds.DISABLE_AUTOMATIC_FETCH);


        //final NativeAd nativeAd = new NativeAd();


// Set a listener to notify us when certain actions occur.
        /*nativeAd.setListener(new NativeListener() {

            @Override
            public void onAdLoaded(NativeAd nativeAd) {
                Log.d("ttaagg", "The ad has been loaded");
                // This is where we will create the views to display the ad.
                // See the next section of these docs.
                //setupNativeAdView(nativeAd);
            }

            @Override
            public void onAdShown(NativeAd nativeAd) {
                Log.d("ttaagg", "The ad was shown");
            }

            @Override
            public void onAdClicked(NativeAd nativeAd) {
                Log.d("ttaagg", "The ad was clicked");
            }

            @Override
            public void onError(HeyzapAds.NativeError nativeError) {
                Log.d("ttaagg", "There was an error: " + nativeError.getErrorMessage());
            }
        });

// Load the native ad with a custom tag (the tag parameter is optional).
        nativeAd.load();
*/

        list = new ArrayList<>();


        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        grid = view.findViewById(R.id.grid);
        thisMonth = view.findViewById(R.id.textView24);
        today = view.findViewById(R.id.textView25);
        text = view.findViewById(R.id.textView29);
        amount = view.findViewById(R.id.textView30);

        progress = view.findViewById(R.id.progressBar2);
        adapter = new SaleAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


//        adapter = new SaleAdapter(getContext(), list);
//        manager = new GridLayoutManager(getContext(), 1);
//
//        grid.setAdapter(adapter);
//        grid.setLayoutManager(manager);


        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progress.setVisibility(View.VISIBLE);


                bean b = (bean) getContext().getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                ApiInterface cr = retrofit.create(ApiInterface.class);

                challengeRequestBean body = new challengeRequestBean();

                Data data = new Data();

                body.setAction("audio_list");

                data.setUserId(pref.getString("id", ""));

                Log.d("iidd", pref.getString("id", ""));

                body.setData(data);


                Call<audioBean> call = cr.audio(body);

                call.enqueue(new Callback<audioBean>() {
                    @Override
                    public void onResponse(Call<audioBean> call, final Response<audioBean> response) {


                        if (response.body().getStatus().equals("1")) {

                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.audio_player);
                            dialog.setCancelable(false);
                            dialog.show();


                            Button cancel = dialog.findViewById(R.id.button9);
                            final ProgressBar bar = dialog.findViewById(R.id.progressBar15);


                            final MediaPlayer mPlayer = new MediaPlayer();

                            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                            try {

                                mPlayer.setDataSource(response.body().getData().get(0).getFileUrl());
                                mPlayer.prepare();
                                mPlayer.start();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                    try {

                                        mPlayer.stop();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(final MediaPlayer mediaPlayer) {

                                    bar.setVisibility(View.VISIBLE);


                                    bean b = (bean) getContext().getApplicationContext();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.BASE_URL)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();


                                    ApiInterface cr = retrofit.create(ApiInterface.class);


                                    videoAmountBean body = new videoAmountBean();

                                    body.setAction("audio_video_earning");

                                    com.tbx.gc.greatcash.videoAmountPOJO.Data data = new com.tbx.gc.greatcash.videoAmountPOJO.Data();

                                    data.setAmount(response.body().getData().get(0).getAmount());
                                    data.setFileId(response.body().getData().get(0).getAudioId());
                                    data.setUserId(pref.getString("id", ""));

                                    body.setData(data);

                                    Call<registerResponseBean> call = cr.videoAmount(body);

                                    call.enqueue(new Callback<registerResponseBean>() {
                                        @Override
                                        public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                                            if (response.body().getStatus().equals("1")) {
                                                dialog.dismiss();
                                                try {
                                                    mediaPlayer.reset();
                                                    mediaPlayer.release();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            bar.setVisibility(View.GONE);

                                        }

                                        @Override
                                        public void onFailure(Call<registerResponseBean> call, Throwable t) {
                                            bar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            });

                        }

                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<audioBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("shopping");

        data.setUserId(pref.getString("id", ""));

        body.setData(data);

        Call<shoppingBean> call = cr.shopping(body);

        call.enqueue(new Callback<shoppingBean>() {
            @Override
            public void onResponse(Call<shoppingBean> call, Response<shoppingBean> response) {

                if (response.body().getStatus().equals("1")) {

                    thisMonth.setText("GC $ " + response.body().getData().getEarningSummary().getThismonth());

                    String strtoday = response.body().getData().getEarningSummary().getToday();
                    if (strtoday.equals("")) {
                        today.setText("GC $ 0");
                    } else {
                        today.setText("GC $ " + response.body().getData().getEarningSummary().getToday());
                    }


                    text.setText(response.body().getData().getSummary().getText());
                    amount.setText("GC $ " + response.body().getData().getSummary().getAmount());

                    // adapter.setGridData(response.body().getData().getSale());
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<shoppingBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);


        challengeRequestBean body1 = new challengeRequestBean();

        Data data1 = new Data();

        body1.setAction("shopping_ad");

        data1.setUserId(pref.getString("id", ""));

        body1.setData(data);


        Call<affBean> call1 = cr.affList(body1);

        call1.enqueue(new Callback<affBean>() {
            @Override
            public void onResponse(Call<affBean> call, Response<affBean> response) {

                if (response.body().getStatus().equals("1")) {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<affBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();
        //int[] ll = {R.drawable.aa, R.drawable.cc, R.drawable.bb};
/*

        private String urlList[] = {
                "https://www.amazon.in/ap/signin?openid.return_to=https%3A%2F%2Faffiliate-program.amazon.in%2F%3Fopenid.assoc_handle%3Damzn_associates_in%26openid.claimed_id%3Dhttp%253A%252F%252Fspecs.openid.net%252Fauth%252F2.0%252Fidentifier_select%26openid.identity%3Dhttp%253A%252F%252Fspecs.openid.net%252Fauth%252F2.0%252Fidentifier_select%26openid.mode%3Dlogout%26openid.ns%3Dhttp%253A%252F%252Fspecs.openid.net%252Fauth%252F2.0%26openid.return_to%3Dhttps%253A%252F%252Faffiliate-program.amazon.in%252F%26&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=amzn_associates_in&openid.mode=checkid_setup&marketPlaceId=A21TJRUUN4KGV&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.pape.max_auth_age=0",
                "https://sessions.viglink.com/sign-in",
                "http://www.cj.com/"};
*/


//        public SaleAdapter(Context context, List<Sale> list) {
//            this.context = context;
//            this.list = list;
//        }


        public SaleAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

//        public void setGridData(List<Sale> list) {
//            this.list = list;
//            notifyDataSetChanged();
//        }


        public void setGridData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.sale_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            final Datum item = list.get(position);
//            Sale item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getAffilateThumbnail(), holder.image, options);

            holder.textView.setText("Shop to earn $ " + item.getAmount());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), Shoppingweb.class);
                    i.putExtra("url", item.getAffilateUrl());
                    startActivity(i);
                }
            });


        }

        @Override
        public int getItemCount() {
            // return list.size();

            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RoundedImageView image;
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.view2);
                textView = itemView.findViewById(R.id.textView116);
            }
        }
    }


}
