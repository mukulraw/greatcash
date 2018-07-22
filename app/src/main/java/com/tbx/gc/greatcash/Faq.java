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

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.faqPOJO.Datum;
import com.tbx.gc.greatcash.faqPOJO.faqBean;
import com.tbx.gc.greatcash.shoppingPOJO.shoppingBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Faq extends Fragment {

    RecyclerView grid;
    ProgressBar progress;
    GridLayoutManager manager;
    List<Datum> list;
    SharedPreferences pref;
    FaqAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faq , container , false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        list = new ArrayList<>();
        progress = view.findViewById(R.id.progressBar9);
        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext() , 1);
        adapter = new FaqAdapter(getContext() , list);

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

        body.setAction("faq");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<faqBean> call = cr.faq(body);

        call.enqueue(new Callback<faqBean>() {
            @Override
            public void onResponse(Call<faqBean> call, Response<faqBean> response) {

                if (response.body().getStatus().equals("1"))
                {
adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<faqBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });



    }

    class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder>
    {

        Context context;
        List<Datum> list = new ArrayList<>();

        public FaqAdapter(Context context , List<Datum> list)
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
            View view = inflater.inflate(R.layout.faq_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

            Datum item = list.get(i);
            holder.index.setText("Q " + String.valueOf(i + 1));

            holder.ques.setText(item.getQuestion());

            holder.answer.setText(item.getAnswer());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.line.getVisibility() == View.GONE)
                    {
                        holder.line.setVisibility(View.VISIBLE);
                        holder.answer.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        holder.line.setVisibility(View.GONE);
                        holder.answer.setVisibility(View.GONE);
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView index , ques , line , answer;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                index = itemView.findViewById(R.id.textView91);
                ques = itemView.findViewById(R.id.textView92);
                line = itemView.findViewById(R.id.textView94);
                answer = itemView.findViewById(R.id.textView93);

            }
        }
    }

}
