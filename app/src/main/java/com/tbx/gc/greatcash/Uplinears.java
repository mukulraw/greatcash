package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbx.gc.greatcash.achieversPOJO.Datum;
import com.tbx.gc.greatcash.achieversPOJO.achieversBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Uplinears extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    UplinerAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.uplinerfragment , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        list = new ArrayList<>();
        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);

        manager = new GridLayoutManager(getContext() , 1);

        adapter = new UplinerAdapter(getContext() , list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume()
    {
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

        body.setAction("upliners");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);


        Call<achieversBean> call = cr.achievers(body);

        call.enqueue(new Callback<achieversBean>()
        {
            @Override
            public void onResponse(Call<achieversBean> call, Response<achieversBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<achieversBean> call, Throwable t) {
              progress.setVisibility(View.GONE);
            }
        });

    }

    public class UplinerAdapter extends RecyclerView.Adapter<UplinerAdapter.MyViewHolder>{

        Context context;
        List<Datum> list = new ArrayList<>();


        public UplinerAdapter(Context context , List<Datum> list){

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
        public UplinerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.upliner_list_model , parent , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UplinerAdapter.MyViewHolder holder, int position) {


            Datum item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getUserPic() , holder.image , options);

            holder.name.setText(item.getName());
            holder.amount.setText(item.getPhone());
            holder.date.setText(item.getJoinDate());
            holder.refid.setText("Ref. Id : " + item.getRefId());



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name , amount , date , refid;
            RoundedImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textView99);
                amount = itemView.findViewById(R.id.textView100);
                image = itemView.findViewById(R.id.view4);
                date = itemView.findViewById(R.id.textView123);
                refid = itemView.findViewById(R.id.textView137);
            }
        }
    }
}
