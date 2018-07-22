package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.notificaitonPOJO.Datum;
import com.tbx.gc.greatcash.notificaitonPOJO.notificationBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Notification extends Fragment{


    RecyclerView grid;

    GridLayoutManager manager;

    NotificationAdapter adapter;
    List<Datum> list;
    SharedPreferences pref;
    ProgressBar progress;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notification , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);
        list = new ArrayList<>();
        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);


        manager = new GridLayoutManager(getContext(), 1);

        adapter = new NotificationAdapter(getContext() , list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

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

        body.setAction("user_notification");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<notificationBean> call = cr.notification(body);

        call.enqueue(new Callback<notificationBean>() {
            @Override
            public void onResponse(Call<notificationBean> call, Response<notificationBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                            adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<notificationBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });


    }

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();


        public NotificationAdapter(Context context , List<Datum> list) {

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
        public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.noti_list_model, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Datum item = list.get(position);

            holder.desc.setText(item.getMessage());
            holder.date.setText(item.getDate());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView date , desc;

            public MyViewHolder(View itemView) {
                super(itemView);

                date = itemView.findViewById(R.id.date);
                desc = itemView.findViewById(R.id.des);

            }
        }
    }




}
