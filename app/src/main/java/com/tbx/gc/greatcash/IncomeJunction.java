package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.hotListPOJO.DataHotList;
import com.tbx.gc.greatcash.hotListPOJO.hotListBean;
import com.tbx.gc.greatcash.incomeJunctionPOJO.DataJunction;
import com.tbx.gc.greatcash.incomeJunctionPOJO.JuctionBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Priyanka on 7/12/2018.
 */

public class IncomeJunction extends AppCompatActivity
{
    //private RecyclerView recyclerView_junction;
    //private RecyclerView.LayoutManager layoutManager;
    private TextView text_userName,text_ReferID;
   // private View view;
   // private String[] earnToday={"Earn Toady","Earn yesterday","Earn this week","Earn this month","Earn this year"};
    //private String[] earnPrice={"G $46","G $5","G $3","G $7","G $8"};

    //private JunctionAdapter adapter;
    private ProgressBar progressBar;
    SharedPreferences pref;
    private List<DataJunction> dataJunctionList=new ArrayList<>();
    private TextView text_today,text_yes,text_week,text_month,text_year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_junction);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        String regID=pref.getString("refId", "");
        Log.e("rrrriddddd",""+regID);

        String userName=pref.getString("name", "");
        Log.e("userNAme",""+userName);

        setID();

        text_ReferID.setText(regID);
        text_userName.setText(userName);

        Junction_Api();
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        view=inflater.inflate(R.layout.income_junction,container,false);
//        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);
//
//        String regID=pref.getString("refId", "");
//        Log.e("rrrriddddd",""+regID);
//
//        String userName=pref.getString("name", "");
//        Log.e("userNAme",""+userName);
//
//        setID();
//
//        text_ReferID.setText(regID);
//        text_userName.setText(userName);
//
//
//        //layoutManager=new LinearLayoutManager(getContext());
//       // recyclerView_junction.setLayoutManager(layoutManager);
//
//       // adapter=new JunctionAdapter(getActivity(),dataJunctionList,earnToday);
//       // recyclerView_junction.setAdapter(adapter);
//
//        Junction_Api();
//
//        return view;
//    }

    private void Junction_Api()
    { Log.e("111","1111");

        progressBar.setVisibility(View.VISIBLE);

        bean b=(bean)getApplicationContext();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean boby=new challengeRequestBean();

        final Data data=new Data();

        boby.setAction("income_junction");
        data.setUserId(pref.getString("id", ""));

        boby.setData(data);

        Call<JuctionBean> call=cr.junctionApi(boby);
        Log.e("222","222");

        call.enqueue(new Callback<JuctionBean>()
        {

            @Override
            public void onResponse(Call<JuctionBean> call, Response<JuctionBean> response)
            {
                progressBar.setVisibility(View.GONE);

                if (response.body().getStatus().equals("1"))
                {
                    Log.e("333333","33333");

                    String str_today=response.body().getData().getTodayEarnAmount();
                    String str_yesterday=response.body().getData().getYesterdayEarnAmount();
                    String str_week=response.body().getData().getThisWeekEarnAmount();
                    String str_month=response.body().getData().getThisMonthEarnAmount();
                    String str_year=response.body().getData().getThisYearEarnAmount();

                    if(str_today.equals(""))
                    {
                        text_today.setText("G $ 0");
                    }
                    else
                        {
                            text_today.setText("G $"+str_today);
                        }


                        if(str_yesterday.equals(""))
                        {
                            text_yes.setText("G $ 0");
                        }
                        else
                            {
                                text_yes.setText("G $"+str_yesterday);
                            }


                            if(str_week.equals(""))
                            {
                                text_week.setText("G $ 0");
                            }

                            else
                                {
                                    text_week.setText("G $"+str_week);
                                }


                    if(str_month.equals(""))
                    {
                        text_month.setText("G $ 0");
                    }

                    else
                    {
                        text_month.setText("G $"+str_month);
                    }



                    if(str_year.equals(""))
                    {
                        text_year.setText("G $ 0");
                    }

                    else
                    {
                        text_year.setText("G $"+str_year);
                    }

                    Log.e("todayyyyy",""+str_today);

                    // adapter.setGridData(response.body().getData());

                }


            }

            @Override
            public void onFailure(Call<JuctionBean> call, Throwable t)
            {
              progressBar.setVisibility(View.GONE);
            }


        });




    }

    private void setID()
    {
     // recyclerView_junction=view.findViewById(R.id.recycler_junction);
      text_userName=findViewById(R.id.text_UserName);
        text_ReferID=findViewById(R.id.text_referId_value);
        progressBar=findViewById(R.id.progress);
        text_today=findViewById(R.id.text_price_today);
        text_yes=findViewById(R.id.text_price_Yes);
        text_week=findViewById(R.id.text_price_Week);
        text_month=findViewById(R.id.text_price_Month);
        text_year=findViewById(R.id.text_price_Year);

    }

    private class JunctionAdapter extends RecyclerView.Adapter<JunctionAdapter.ViewHolder>
    {
        private Context context;
        List<DataJunction>dataJunctions;
        private String[] str_earn;



        public JunctionAdapter(FragmentActivity activity, List<DataJunction> dataJunctionList, String[] earnToday)
        {
            this.context=activity;
            this.dataJunctions=dataJunctionList;
            this.str_earn=earnToday;
        }

        public void setGridData(List<DataJunction> list)
        {
            this.dataJunctions=list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public JunctionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.junction_icon , viewGroup , false);

            return new ViewHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull JunctionAdapter.ViewHolder viewHolder, int i)
        {
            DataJunction dataJunction=dataJunctions.get(i);

            if(i==0)
            {
              viewHolder.text_price.setText("G $"+ dataJunction.getTodayEarnAmount());
                viewHolder.text_earnToday.setText(str_earn[0]);
            }

            if(i==1)
            {
                viewHolder.text_price.setText("G $"+ dataJunction.getYesterdayEarnAmount());
                viewHolder.text_earnToday.setText(str_earn[1]);
            }
            if(i==2)
            {
                viewHolder.text_price.setText("G $"+ dataJunction.getThisWeekEarnAmount());
                viewHolder.text_earnToday.setText(str_earn[2]);
            }

            if(i==3)
            {
                viewHolder.text_price.setText("G $"+ dataJunction.getThisMonthEarnAmount());
                viewHolder.text_earnToday.setText(str_earn[3]);
            }

            if(i==4)
            {
                viewHolder.text_price.setText("G $"+ dataJunction.getThisYearEarnAmount());
                viewHolder.text_earnToday.setText(str_earn[4]);
            }

        }

        @Override
        public int getItemCount()
        {
            return dataJunctions.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView text_price,text_earnToday;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);

                text_price=itemView.findViewById(R.id.text_price);
                text_earnToday=itemView.findViewById(R.id.text_eranToday);
            }
        }
    }
}
