package com.ibra.moviecatalog.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibra.moviecatalog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager mviewPager;
    TabLayout mtabLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mviewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mviewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        mtabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mtabLayout.setupWithViewPager(mviewPager);

        mtabLayout.post(new Runnable() {
            @Override
            public void run() {
                mtabLayout.setupWithViewPager(mviewPager);
            }
        });

        mtabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private class sliderAdapter extends FragmentPagerAdapter {

        String now_playing = getResources().getString(R.string.nowplaying);
        String up_coming = getResources().getString(R.string.upcoming);
        String search = getResources().getString(R.string.searchtab);
        final String tabs[] = {now_playing,up_coming,search};

        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NowPlayingFragment();
                case 1 :
                    return new UpcomingFragment();
                case 2 :
                    return new SearchFragment();
            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }


    }
}
