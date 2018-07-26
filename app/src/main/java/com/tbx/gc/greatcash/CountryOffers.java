package com.tbx.gc.greatcash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CountryOffers extends Fragment {

    TabLayout tabs;
    ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_offer_layout , container , false);


        tabs = view.findViewById(R.id.view6);
        pager = view.findViewById(R.id.pager);

        tabs.addTab(tabs.newTab().setText("OFFERS"));
        tabs.addTab(tabs.newTab().setText("COMBO OFFERS"));


        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("OFFERS");
        tabs.getTabAt(1).setText("COMBO OFFERS");

        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            if (i == 0)
            {

                return new SimpleOffer();

            }
            else if (i == 1)
            {
                return new Offer();
            }
            else
            {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
