package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
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

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.dashboardPOJO.dashboardBean;
import com.tbx.gc.greatcash.networkRequestOJO.networkRequestBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TreeView extends Fragment  {

    RecyclerView grid;

    GridLayoutManager manager;

    TreeViewAdapter adapter;

    List<netBean> list;
    ProgressBar progress;

    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.treeview_fragment , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);

        manager = new GridLayoutManager(getContext() , 1);

        adapter = new TreeViewAdapter(getContext() , list);

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

        body.setAction("dashboard");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<dashboardBean> call = cr.dashboard(body);
        call.enqueue(new Callback<dashboardBean>() {
            @Override
            public void onResponse(Call<dashboardBean> call, Response<dashboardBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    if (response.body().getData().getDashboardStatus().equals("1"))
                    {

                    }
                    else
                    {

                        netBean l1 = new netBean();
                        l1.setAmount(response.body().getData().getLevel1Amount());
                        l1.setJoins(response.body().getData().getLevel1Joins());
                        list.add(l1);

                        netBean l2 = new netBean();
                        l2.setAmount(response.body().getData().getLevel2Amount());
                        l2.setJoins(response.body().getData().getLevel2Joins());
                        list.add(l2);

                        netBean l3 = new netBean();
                        l3.setAmount(response.body().getData().getLevel3Amount());
                        l3.setJoins(response.body().getData().getLevel3Joins());
                        list.add(l3);

                        netBean l4 = new netBean();
                        l4.setAmount(response.body().getData().getLevel4Amount());
                        l4.setJoins(response.body().getData().getLevel4Joins());
                        list.add(l4);

                        netBean l5 = new netBean();
                        l5.setAmount(response.body().getData().getLevel5Amount());
                        l5.setJoins(response.body().getData().getLevel5Joins());
                        list.add(l5);

                        netBean l6 = new netBean();
                        l6.setAmount(response.body().getData().getLevel6Amount());
                        l6.setJoins(response.body().getData().getLevel6Joins());
                        list.add(l6);

                        netBean l7 = new netBean();
                        l7.setAmount(response.body().getData().getLevel7Amount());
                        l7.setJoins(response.body().getData().getLevel7Joins());
                        list.add(l7);

                        adapter.setGridData(list);

                    }
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<dashboardBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



    }

    public class  TreeViewAdapter extends RecyclerView.Adapter<TreeViewAdapter.MyViewHolder>{

        Context context;
        List<netBean> list = new ArrayList<>();


        public TreeViewAdapter(Context context , List<netBean> list){

            this.context = context;
            this.list = list;
        }

        public void setGridData(List<netBean> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TreeViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.treeview_list_model , parent , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TreeViewAdapter.MyViewHolder holder, final int position) {

            final netBean item = list.get(position);

            holder.level.setText("level " + String.valueOf(position + 1));
            holder.amount.setText("$ " + item.getAmount());
            holder.joins.setText("Joining : " + item.getJoins());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , JoinList.class);
                    intent.putExtra("userId" , pref.getString("id" , ""));
                    intent.putExtra("level" , String.valueOf(position + 1));
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView level , joins , amount;

            public MyViewHolder(View itemView) {
                super(itemView);

                level = itemView.findViewById(R.id.level);
                joins = itemView.findViewById(R.id.joins);
                amount = itemView.findViewById(R.id.amount);

            }
        }
    }
}
