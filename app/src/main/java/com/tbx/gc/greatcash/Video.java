package com.tbx.gc.greatcash;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.videoAmountPOJO.videoAmountBean;
import com.tbx.gc.greatcash.videoPOJO.Datum;
import com.tbx.gc.greatcash.videoPOJO.videoBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Video extends Fragment {

    RecyclerView grid;
    ProgressBar progress;
    GridLayoutManager manager;
    VideoAdapter adapter;
    List<Datum> list;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);
        list = new ArrayList<>();
        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);
        manager = new GridLayoutManager(getContext() , 1);

        adapter = new VideoAdapter(getContext() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

    }

    public void loadData()
    {

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

        body.setAction("video_list");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<videoBean> call = cr.video(body);

        call.enqueue(new Callback<videoBean>() {
            @Override
            public void onResponse(Call<videoBean> call, Response<videoBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<videoBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });

    }

    class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>
    {
        Context context;
        List<Datum> list = new ArrayList<>();


        public VideoAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.video_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


            final Datum item = list.get(i);

            viewHolder.title.setText(item.getTittle());
            viewHolder.amount.setText("$ " + item.getAmount());


                Glide.with(context).load(Uri.parse(item.getVideo_thumnail())).into(viewHolder.img_video);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.video_dialog);
                    dialog.show();

                    VideoView videoView = dialog.findViewById(R.id.videoView);
                    final ProgressBar bar = dialog.findViewById(R.id.progressBar11);


                    bar.setVisibility(View.VISIBLE);

                    Uri uri = Uri.parse(item.getFileUrl());

                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            bar.setVisibility(View.GONE);
                        }
                    });

                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

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

                            data.setAmount(item.getAmount());
                            data.setFileId(item.getAudioId());
                            data.setUserId(pref.getString("id" , ""));

                            body.setData(data);

                            Call<registerResponseBean> call = cr.videoAmount(body);

                            call.enqueue(new Callback<registerResponseBean>() {
                                @Override
                                public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                                    if (response.body().getStatus().equals("1"))
                                    {
                                        dialog.dismiss();
                                        loadData();
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
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView title , amount;
            ImageView img_video;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.textView102);
                amount = itemView.findViewById(R.id.textView103);
                img_video=itemView.findViewById(R.id.img_video);
            }
        }
    }

}
