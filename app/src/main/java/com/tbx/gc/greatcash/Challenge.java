package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.challengeAcceptPOJO.challengeAcceptBean;
import com.tbx.gc.greatcash.challengePOJO.Datum;
import com.tbx.gc.greatcash.challengePOJO.challengeBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
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

public class Challenge extends Fragment {


    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    ChallengeAdapter adapter;
    List<Datum> list;
    SharedPreferences pref;


    List<String> dones;

    Home h;

    public void setHome(Home h)
    {
        this.h = h;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.challenge_layout, container, false);

        list = new ArrayList<>();
        dones = new ArrayList<>();

        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        progress = view.findViewById(R.id.progressBar);
        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext(), 2);
        adapter = new ChallengeAdapter(getContext(), list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        progress.setVisibility(View.VISIBLE);
        list.clear();


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("challenge");

        data.setUserId(pref.getString("id", ""));

        Log.e("iddddddddd",""+pref.getString("id",""));

        body.setData(data);

        Call<challengeBean> call = cr.challenge(body);

        call.enqueue(new Callback<challengeBean>() {
            @Override
            public void onResponse(Call<challengeBean> call, Response<challengeBean> response) {

                if (response.body().getStatus().equals("1")) {
                    adapter.setGridData(response.body().getData());

                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<challengeBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();


        public ChallengeAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.challenge_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            final Datum item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getTaskImage(), holder.image, options);

            holder.price.setText("₹ " + item.getAmount());

            Log.d("package", item.getTaskPackage());


            if (isPackageInstalled(context, item.getTaskPackage()))
            {
                holder.status.setText("INSTALLED");
                updateId(item.getTaskId());
            } else {
                holder.status.setText("INSTALL NOW");
            }



            holder.line.setVisibility(View.GONE);
            holder.price.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.status.getText().toString().equals("INSTALL NOW")) {
                        String packageName = item.getTaskPackage();
                        String taskUrl = item.getTaskUrl();
                       // Log.e("linkkk", "" + taskUrl);

                        // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        //Copy App URL from Google Play Store.
                        //    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                        // intent.setData(Uri.parse(taskUrl);
                        //startActivity(intent);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(taskUrl));
                        startActivity(intent);


//                        Intent intent = new Intent(context , InstallApp.class);
//                        intent.putExtra("image" , item.getTaskImage());
//                        intent.putExtra("name" , item.getTaskUrl());
//                        intent.putExtra("package" , item.getTaskPackage());
//
//                        Log.e("seng_linkkkk",""+item.getTaskPackage());
//
//                        context.startActivity(intent);

                    }

                }
            });

            if (dones.size() == list.size())
            {
                Log.e("doneeee",""+dones);
                Log.e("listtt",""+list);

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

                body.setAction("challenge_earning");

                data.setUserId(pref.getString("id", ""));

                body.setData(data);

                Call<challengeAcceptBean> call = cr.challengeComplete(body);

                call.enqueue(new Callback<challengeAcceptBean>() {
                    @Override
                    public void onResponse(Call<challengeAcceptBean> call, Response<challengeAcceptBean> response) {
                        if (response.body().getStatus().equals("1"))
                        {
                            h.loadData();
                           // Toast.makeText(context , "Challenge Completeddddd" , Toast.LENGTH_SHORT).show();
                        }
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<challengeAcceptBean> call, Throwable t) {
                     progress.setVisibility(View.GONE);
                    }
                });




            }

        }


        public void updateId(String id)
        {
            if (!dones.contains(id))
            {
                dones.add(id);
            }
        }


        @Override
        public int getItemCount() {

            return list.size();

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RoundedImageView image;
            TextView price, status, line;

            public ViewHolder(View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.view);
                price = itemView.findViewById(R.id.textView2);
                status = itemView.findViewById(R.id.textView4);
                line = itemView.findViewById(R.id.textView3);


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
