package com.tbx.gc.greatcash;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbx.gc.greatcash.networkPOJO.Datum;
import com.tbx.gc.greatcash.networkPOJO.networkBean;
import com.tbx.gc.greatcash.networkRequestOJO.Data;
import com.tbx.gc.greatcash.networkRequestOJO.networkRequestBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class JoinList extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    JoinAdapter adapter;
    String userId;
    String level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_list);
        list = new ArrayList<>();

        userId = getIntent().getStringExtra("userId");
        level = getIntent().getStringExtra("level");

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar14);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Join List");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });



        manager = new GridLayoutManager(this , 1);
        adapter = new JoinAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);




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


        networkRequestBean body = new networkRequestBean();

        body.setAction("level_joining");

        Data data = new Data();

        data.setLevel(level);
        data.setUserId(userId);

        body.setData(data);

        Call<networkBean> call = cr.network(body);

        call.enqueue(new Callback<networkBean>() {
            @Override
            public void onResponse(Call<networkBean> call, Response<networkBean> response) {

                Log.e("qwerty",""+response.body().getData());

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<networkBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });



    }

    class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.ViewHolder>
    {
        List<Datum> list = new ArrayList<>();
        Context context;

        public JoinAdapter(Context context , List<Datum> list)
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.join_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Datum item = list.get(i);

            viewHolder.name.setText(item.getJoiningName());
            viewHolder.email.setText(item.getJoiningEmail());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getJoiningPic() , viewHolder.image , options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView name , email;
            RoundedImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.textView109);
                email = itemView.findViewById(R.id.textView110);
                image = itemView.findViewById(R.id.view8);

            }
        }
    }

}
