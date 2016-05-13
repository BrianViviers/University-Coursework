package com.richardimms.www.android0303.Fragments.Transactions;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.richardimms.www.android0303.R;

/**
 * Created by Richard on 08/04/2015.
 */
public class MainNavigationTransaction extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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

                case 0: return IncomingTransactionsFragment.newInstance();
                case 1: return OutgoingTransactionsFragment.newInstance();
                case 2: return CompletedIncomingTransactions.newInstance();
                case 3: return CompletedOutgoingTransactions.newInstance();
                default: return IncomingTransactionsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
