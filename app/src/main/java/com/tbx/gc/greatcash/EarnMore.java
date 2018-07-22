package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbx.gc.greatcash.EarnMorePOJO.DataEarn;
import com.tbx.gc.greatcash.EarnMorePOJO.earnMoreBean;
import com.tbx.gc.greatcash.achieversPOJO.Datum;
import com.tbx.gc.greatcash.achieversPOJO.achieversBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Priyanka on 7/9/2018.
 */

public class EarnMore extends AppCompatActivity
{
    private RecyclerView recyclerView_earnMore;
    private RelativeLayout rel_back;
    private RecyclerView.LayoutManager layoutManager;

    private EarnMore_Adapter adapter;
    private List<DataEarn>listEarn=new ArrayList<>();

    ProgressBar progress;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earn_more);
        setId();

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView_earnMore.setLayoutManager(layoutManager);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        rel_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        adapter=new EarnMore_Adapter(getApplicationContext(),listEarn);
        recyclerView_earnMore.setAdapter(adapter);

        earnMoreListApi();

    }

    private void earnMoreListApi()
    {
        Log.e("1111","1111");
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("earn_more_item");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<earnMoreBean> call = cr.earnMoreApi(body);


        call.enqueue(new Callback<earnMoreBean>()
        {

            @Override
            public void onResponse(Call<earnMoreBean> call, Response<earnMoreBean> response)
            {
                Log.e("222","222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_earn().equals("1"))
                {

                    adapter.setGridData(response.body().getData_earn());


                    Log.e("3333","3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<earnMoreBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void setId()
    {
        recyclerView_earnMore=findViewById(R.id.recycler_earn);
        rel_back=findViewById(R.id.rel_back);
        progress =findViewById(R.id.progress);
    }




    private class EarnMore_Adapter  extends RecyclerView.Adapter<EarnMore.ViewHolder>
    {
         private Context context;
         private List<DataEarn>dataEarnList;

        public EarnMore_Adapter(Context applicationContext, List<DataEarn> listEarn)
        {
           this.context=applicationContext;
           this.dataEarnList=listEarn;
        }

        public void setGridData(List<DataEarn> list)
        {
            this.dataEarnList = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public EarnMore.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.earnmore_icon , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EarnMore.ViewHolder viewHolder, final int i)
        {
            final DataEarn item = dataEarnList.get(i);
            viewHolder.text_head.setText(item.getHeading());
            viewHolder.text_amount.setText(item.getEarnAmount());

            Glide.with(context).load(Uri.parse(item.getImagee())).into(viewHolder.img_earn);


            viewHolder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String type=item.getTypee();
                    Log.e("typeeeee",""+type);

                    String app_link=item.getRedirectUrl();
                    Log.e("applinkk",""+app_link);

                    String video_link=item.getVideo();
                    Log.e("videolink",""+video_link);

                    String image_link=item.getImagee();
                    Log.e("imagelink",""+image_link);

                    if(type.equals("video"))
                    {
                        Toast.makeText(getApplicationContext(),"video",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context,YouTube.class);
                        intent.putExtra("url",video_link);
                        startActivity(intent);

                    }

                    if(type.equals("app"))
                    {
                        Toast.makeText(getApplicationContext(),"app",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(app_link));
                        startActivity(intent);

                    }

                    if(type.equals("audio"))
                    {
                        Toast.makeText(getApplicationContext(),"audio",Toast.LENGTH_LONG).show();
                    }

                    if(type.equals("image"))
                    {
                        Toast.makeText(getApplicationContext(),"image",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context,Showimage.class);
                        intent.putExtra("url",image_link);
                        startActivity(intent);

                    }
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataEarnList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView text_head,text_amount;
        private ImageView img_earn;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_head=itemView.findViewById(R.id.text_heading);
            text_amount=itemView.findViewById(R.id.text_amount_value);
            img_earn=itemView.findViewById(R.id.img_earn);
            cardView=itemView.findViewById(R.id.cardView_earnMore);
        }
    }
}
