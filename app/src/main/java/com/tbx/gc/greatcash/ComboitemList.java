package com.tbx.gc.greatcash;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Movie;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.comboPOJO.ComboItem;
import com.tbx.gc.greatcash.comboPOJO.comboBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.submitComboPOJO.Data;
import com.tbx.gc.greatcash.submitComboPOJO.submitComboBean;
import com.tbx.gc.greatcash.videoAmountPOJO.videoAmountBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ComboitemList extends AppCompatActivity {

    String id;


    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;

    List<ComboItem> list;
    List<String> dones;
    SharedPreferences pref;

    ComboAdapter adapter;

    ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comboitemlist);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        list = new ArrayList<>();
        dones = new ArrayList<>();

        id = getIntent().getStringExtra("id");
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar17);
        manager = new GridLayoutManager(this, 1);
        adapter = new ComboAdapter(ComboitemList.this, list);

        back = findViewById(R.id.back);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        loadData();

    }

    public void loadData() {

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data = new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("combo_offer");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<comboBean> call = cr.comboOffers(body);

        call.enqueue(new Callback<comboBean>() {
            @Override
            public void onResponse(Call<comboBean> call, Response<comboBean> response) {

                if (response.body().getStatus().equals("1")) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getComboId().equals(id)) {
                            adapter.setGridData(response.body().getData().get(i).getComboItem() , response.body().getData().get(i).getComboId() , response.body().getData().get(i).getEarnAmount());
                        }
                    }


                }


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<comboBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ViewHolder> {
        Context context;
        List<ComboItem> list = new ArrayList<>();

        String comboId;
        String amount;


        public ComboAdapter(Context context, List<ComboItem> list) {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<ComboItem> list , String comboId , String amount) {
            this.list = list;
            this.comboId = comboId;
            this.amount = amount;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.combo_item_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            final ComboItem item = list.get(i);

            viewHolder.type.setText(item.getType().toUpperCase());

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getImage(), viewHolder.image, options);


            if (item.getType().equals("app")) {

                if (isPackageInstalled(context, item.getAppId())) {

                    updateId(item.getId());

                }

            }


            final boolean b = dones.contains(item.getId());


            if (b) {

                String ty = item.getType();

                if (ty.equals("app")) {
                    viewHolder.install.setText("INSTALLED");
                } else if (ty.equals("image")) {
                    viewHolder.install.setText("CLICKED");
                } else if (ty.equals("audio")) {
                    viewHolder.install.setText("EARNED");
                } else if (ty.equals("video")) {
                    viewHolder.install.setText("VIEWED");
                }

            } else {
                String ty = item.getType();

                if (ty.equals("app")) {
                    viewHolder.install.setText("INSTALL");
                } else if (ty.equals("image")) {
                    viewHolder.install.setText("CLICK");
                } else if (ty.equals("audio")) {
                    viewHolder.install.setText("EARN");
                } else if (ty.equals("video")) {
                    viewHolder.install.setText("VIEW");
                }

            }


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!b) {

                        String ty = item.getType();
                        if (ty.equals("app")) {

                            if (viewHolder.install.getText().toString().equals("INSTALL"))
                            {
                                String taskUrl = item.getRedirectUrl();
                                Log.e("linkkk", "" + taskUrl);

                                // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                //Copy App URL from Google Play Store.
                                //    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                                // intent.setData(Uri.parse(taskUrl);
                                //startActivity(intent);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(taskUrl));
                                startActivity(intent);


                            }
                        } else if (ty.equals("image")) {
                        } else if (ty.equals("audio")) {
                            if (viewHolder.install.getText().toString().equals("EARN"))
                            {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.audio_player);
                                dialog.setCancelable(false);
                                dialog.show();


                                Button cancel = dialog.findViewById(R.id.button9);
                                final ProgressBar bar = dialog.findViewById(R.id.progressBar15);


                                final MediaPlayer mPlayer = new MediaPlayer();

                                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                try {

                                    mPlayer.setDataSource(item.getAudio());
                                    mPlayer.prepare();
                                    mPlayer.start();

                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }




                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();

                                        try {

                                            mPlayer.stop();

                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(final MediaPlayer mediaPlayer) {

                                        updateId(item.getId());
                                        notifyDataSetChanged();

                                    }
                                });

                            }
                        } else if (ty.equals("video")) {
                            if (viewHolder.install.getText().toString().equals("VIEW"))
                            {


                                final Dialog dialog = new Dialog((ComboitemList)context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.activity_yttest);
                                dialog.setCancelable(false);
                                dialog.show();



                                final YouTubePlayerSupportFragment playerSupportFragment = (YouTubePlayerSupportFragment) ((ComboitemList) context).getSupportFragmentManager().findFragmentById(R.id.youtubeFragment);

/*

                                LinearLayout replace = dialog.findViewById(R.id.youtubeFragment);

                                FragmentTransaction fragmentTransaction = ((ComboitemList) context).getSupportFragmentManager().beginTransaction();
                                YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                                fragmentTransaction.add(replace.getId() , youTubePlayerFragment);
                                //fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
*/


                                //YouTubePlayerView youTubeView = dialog.findViewById(R.id.player);
                                playerSupportFragment.initialize("AIzaSyBfeag9QVBnHvBziJrDuaQIqATSU0Tn4aw", new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean b) {

                                        if (!b) {

                                            // loadVideo() will auto play video
                                            // Use cueVideo() method, if you don't want to play it automatically
                                            try {
                                                player.loadVideo(extractYoutubeId(item.getVideo()));
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }

                                            // Hiding player controls
                                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);


                                            player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                                                @Override
                                                public void onPlaying() {

                                                }

                                                @Override
                                                public void onPaused() {

                                                }

                                                @Override
                                                public void onStopped() {

                                                }

                                                @Override
                                                public void onBuffering(boolean b) {

                                                }

                                                @Override
                                                public void onSeekTo(int i) {

                                                }
                                            });

                                            player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                                                @Override
                                                public void onLoading() {

                                                }

                                                @Override
                                                public void onLoaded(String s) {

                                                }

                                                @Override
                                                public void onAdStarted() {

                                                }

                                                @Override
                                                public void onVideoStarted() {

                                                }

                                                @Override
                                                public void onVideoEnded() {

                                                    player.release();

                                                    //Toast.makeText(context , "Ended" , Toast.LENGTH_LONG).show();
                                                    updateId(item.getId());
                                                    notifyDataSetChanged();
                                                    dialog.dismiss();

                                                }

                                                @Override
                                                public void onError(YouTubePlayer.ErrorReason errorReason) {

                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                                    }
                                });



                            }

                        }

                    }

                }
            });



            if (dones.size() == list.size())
            {

                progress.setVisibility(View.VISIBLE);


                bean b1 = (bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b1.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                ApiInterface cr = retrofit.create(ApiInterface.class);

                submitComboBean body = new submitComboBean();

                Data data = new Data();

                body.setAction("combo_offer_earn");
                data.setComboId(comboId);
                data.setEarnAmount(amount);
                data.setUserId(pref.getString("id", ""));

                body.setData(data);

                Call<comboBean> call = cr.submitCombo(body);

                call.enqueue(new Callback<comboBean>() {
                    @Override
                    public void onResponse(Call<comboBean> call, Response<comboBean> response) {

                        if (response.body().getStatus().equals("1"))
                        {
                            Toast.makeText(context , "Offer Completed" , Toast.LENGTH_SHORT).show();
                            ((ComboitemList)context).finish();
                        }

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<comboBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }


        }

        public String extractYoutubeId(String url) throws MalformedURLException {
            String query = new URL(url).getQuery();
            String[] param = query.split("&");
            String id = null;
            for (String row : param) {
                String[] param1 = row.split("=");
                if (param1[0].equals("v")) {
                    id = param1[1];
                }
            }
            return id;
        }

        public void updateId(String id) {
            if (!dones.contains(id)) {
                dones.add(id);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView type, install;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                type = itemView.findViewById(R.id.textView117);
                install = itemView.findViewById(R.id.textView118);
                image = itemView.findViewById(R.id.imageView9);

            }
        }

        public boolean isPackageInstalled(Context context, String packageName) {
            final PackageManager packageManager = context.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            if (intent == null) {
                return false;
            }
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return !list.isEmpty();
        }

    }


}
