package com.tbx.gc.greatcash;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbx.gc.greatcash.transactionPOJO.Datum;
import com.tbx.gc.greatcash.transactionPOJO.transactionBean;
import com.tbx.gc.greatcash.transactionRequestPOJO.Data;
import com.tbx.gc.greatcash.transactionRequestPOJO.transactionRequetBean;
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

public class TrasnsactionList extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    String userId;
    String type;
    TransAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasnsaction_list);

        userId = getIntent().getStringExtra("userId");
        type = getIntent().getStringExtra("type");

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        grid = findViewById(R.id.grid);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Transaction History");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        manager = new GridLayoutManager(this, 1);
        adapter = new TransAdapter(this, list);

        grid.setLayoutManager(manager);
        grid.setAdapter(adapter);


    }


    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        transactionRequetBean body = new transactionRequetBean();

        body.setAction("transaction_history");

        Data data = new Data();
        data.setType(type);
        data.setUserId(userId);

        body.setData(data);

        Call<transactionBean> call = cr.transaction(body);

        call.enqueue(new Callback<transactionBean>() {
            @Override
            public void onResponse(Call<transactionBean> call, Response<transactionBean> response) {

                Log.e("qqq",""+response.body().getData());
                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<transactionBean> call, Throwable t) {

                progress.setVisibility(View.GONE);

            }
        });






    }

    class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();

        public TransAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.trans_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Datum item = list.get(i);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getSenderPic(), viewHolder.image, options);

            viewHolder.name.setText(item.getSenderName());
            viewHolder.message.setText(item.getMessage());
            viewHolder.amount.setText("$ " + item.getAmount());
            viewHolder.date.setText(item.getDate());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RoundedImageView image;
            TextView name, amount, message, date;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.view5);
                name = itemView.findViewById(R.id.textView112);
                amount = itemView.findViewById(R.id.textView111);
                message = itemView.findViewById(R.id.textView113);
                date = itemView.findViewById(R.id.textView114);


            }
        }
    }

}
