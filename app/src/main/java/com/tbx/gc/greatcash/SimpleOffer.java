package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.tbx.gc.greatcash.challengePOJO.challengeBean;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbx.gc.greatcash.offerPOJO.Datum;
import com.tbx.gc.greatcash.offerPOJO.offerBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SimpleOffer extends Fragment {


    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    ChallengeAdapter adapter;
    List<Datum> list;
    SharedPreferences pref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_offer_layout , container , false);

        list = new ArrayList<>();

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

        body.setAction("single_offer");

        data.setUserId(pref.getString("id", ""));

        body.setData(data);

        Call<offerBean> call = cr.offers(body);

        call.enqueue(new Callback<offerBean>() {
            @Override
            public void onResponse(Call<offerBean> call, Response<offerBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<offerBean> call, Throwable t) {
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

            loader.displayImage(item.getOfferImage(), holder.image, options);

            holder.price.setText("$ " + item.getAmount());

            Log.d("package" , item.getOfferkPackage());

            if (isPackageInstalled(context , item.getOfferkPackage()))
            {
                holder.status.setText("INSTALLED");
            }
            else
            {
                holder.status.setText("INSTALL NOW");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.status.getText().toString().equals("INSTALL NOW"))
                    {

                        Intent intent = new Intent(context , InstallApp.class);
                        intent.putExtra("image" , item.getOfferImage());
                        intent.putExtra("name" , item.getOfferkPackage());
                        intent.putExtra("package" , item.getOfferkPackage());
                        intent.putExtra("id" , item.getOfferId());
                        intent.putExtra("amount" , item.getAmount());

                        context.startActivity(intent);

                    }

                }
            });



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RoundedImageView image;
            TextView price , status;

            public ViewHolder(View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.view);
                price = itemView.findViewById(R.id.textView2);
                status = itemView.findViewById(R.id.textView4);

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
