package com.richardimms.www.android0303.Fragments.Bids;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.richardimms.www.android0303.R;

/**
 * Created by Richard on 09/04/2015.
 */
public class MainNavigationBids extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bids);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return IncomingBidsFragment.newInstance();
                case 1: return OutgoingBidsFragment.newInstance();
                case 2: return AcceptedBidsFragment.newInstance();
                case 3: return RefusedBidsFragment.newInstance();
                default: return AcceptedBidsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
