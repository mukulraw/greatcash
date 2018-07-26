package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.dashboardPOJO.dashboardBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Home extends Fragment {

    TabLayout tabs;
    public static ViewPager pager;
    int pos;

    ProgressBar progress;
    SharedPreferences pref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout , container , false);

        progress = view.findViewById(R.id.progressBar16);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        tabs = view.findViewById(R.id.tabLayout);
        pager = view.findViewById(R.id.pager);


        loadData();


        return view;
    }

    public void loadData()
    {
        bean b = (bean) getContext().getApplicationContext();

        progress.setVisibility(View.VISIBLE);

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

                try {
                    if (response.body().getStatus().equals("1"))
                    {
                        //Toast.makeText(getContext() , response.body().getData().getDashboardStatus() , Toast.LENGTH_SHORT).show();

                        if (response.body().getData().getDashboardStatus().equals("1"))
                        {
                            setChallenge();
                        }
                        else
                        {
                            setIncomeJunction();
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }



                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<dashboardBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    public void setChallenge()
    {
        pos = getArguments().getInt("position");

        tabs.addTab(tabs.newTab().setText("DASHBOARD"));
        tabs.addTab(tabs.newTab().setText("CHALLENGE"));
        tabs.addTab(tabs.newTab().setText("SHOPPING"));

        PAgerAdapter adapter = new PAgerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("DASHBOARD");
        tabs.getTabAt(1).setText("CHALLENGE");
        tabs.getTabAt(2).setText("SHOPPING");


        pager.setCurrentItem(pos);

    }


    public void setIncomeJunction()
    {
        pos = getArguments().getInt("position");

        tabs.addTab(tabs.newTab().setText("DASHBOARD"));
        tabs.addTab(tabs.newTab().setText("HOME"));
        tabs.addTab(tabs.newTab().setText("SHOPPING"));

        PAgerAdapter2 adapter = new PAgerAdapter2(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("DASHBOARD");
        tabs.getTabAt(1).setText("HOME");
        tabs.getTabAt(2).setText("SHOPPING");


        pager.setCurrentItem(pos);

    }

    public Home getH()
    {
        return this;
    }

    class PAgerAdapter extends FragmentStatePagerAdapter
    {

        public PAgerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                return new Dashboard();
            }
            else if (position == 1)
            {
                Challenge c = new Challenge();
                c.setHome(getH());
                return c;
            }
            else if (position == 2)
            {
                return new Shopping();
            }
            else
            {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    static class PAgerAdapter2 extends FragmentStatePagerAdapter
    {

        public PAgerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                return new Dashboard();
            }
            else if (position == 1)
            {
                return new IncomeJunc();
            }
            else if (position == 2)
            {
                return new Shopping();
            }
            else
            {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
