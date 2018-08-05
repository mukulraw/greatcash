package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tbx.gc.greatcash.EarnMorePOJO.earnMoreBean;
import com.tbx.gc.greatcash.achieversPOJO.Datum;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.hotListPOJO.DataHotList;
import com.tbx.gc.greatcash.hotListPOJO.hotListBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HotLIst extends Fragment {


    RecyclerView grid;

    RecyclerView.LayoutManager manager;
    HotlistAdapter adapter;
    private List<DataHotList> HotList = new ArrayList<>();
    SharedPreferences pref;
    ProgressBar progress;

    TextView date;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hot, container, false);


        date = view.findViewById(R.id.date);
        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);

        manager = new LinearLayoutManager(getContext());

        grid.setLayoutManager(manager);

        adapter = new HotlistAdapter(getContext(), HotList);

        grid.setAdapter(adapter);


        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();



        SimpleDateFormat spf = new SimpleDateFormat("M d");
        try {
            Date newDate = spf.parse(firstDateOfPreviousMonth.getMonth() + " " + String.valueOf(firstDateOfPreviousMonth.getDate()));

            spf = new SimpleDateFormat("MMM dd");
            String newDateString = spf.format(newDate);
            Log.d("ddaattee",  newDateString);

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("MMM dd");
            String formattedDate = df.format(c);

            date.setText("Joining between " + newDateString + " - " + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        HotListApi();

        return view;
    }

    private void HotListApi() {
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean boby = new challengeRequestBean();

        Data data = new Data();

        boby.setAction("hot_list");
        data.setUserId(pref.getString("id", ""));

        boby.setData(data);

        Call<hotListBean> call = cr.hotListApi(boby);
        Log.e("222", "222");

        call.enqueue(new Callback<hotListBean>() {

            @Override
            public void onResponse(Call<hotListBean> call, Response<hotListBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus().equals("1")) {

                    adapter.setGridData(response.body().getDataHot());


                    Log.e("3333", "3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<hotListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

//    @Override
//    public void onResume()
//    {
//        Log.e("1111","1111");
//        super.onResume();
//
//    }

    public class HotlistAdapter extends RecyclerView.Adapter<HotlistAdapter.MyViewHolder> {


        Context context;
        List<DataHotList> dataHotLists;

        public HotlistAdapter(Context context, List<DataHotList> list) {
            this.context = context;
            this.dataHotLists = list;
        }


        public void setGridData(List<DataHotList> list) {
            this.dataHotLists = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public HotlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.hot_list_model_new, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HotlistAdapter.MyViewHolder holder, int position) {
            DataHotList hotList = dataHotLists.get(position);

            holder.text_name.setText(hotList.getNamee());
            holder.text_totalJoin.setText(hotList.getTotalEarning());

            Log.e("444", "444");

            Glide.with(context).load(Uri.parse(hotList.getUserPic())).into(holder.img_user);
        }

        @Override
        public int getItemCount() {
            return dataHotLists.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView text_name, text_totalJoin;
            private ImageView img_user;

            public MyViewHolder(View itemView) {
                super(itemView);

                text_name = itemView.findViewById(R.id.user_name);
                text_totalJoin = itemView.findViewById(R.id.text_totaljoin);
                img_user = itemView.findViewById(R.id.img_user);

            }
        }
    }


}
