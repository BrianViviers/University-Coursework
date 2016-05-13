package com.richardimms.www.android0303.Activities;



import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.Activities.CreateAdvert.CreateAdvertTypeActivity;
import com.richardimms.www.android0303.CustomAdapter.CustomList;
import com.richardimms.www.android0303.Fragments.Bids.MainNavigationBids;
import com.richardimms.www.android0303.Fragments.Transactions.MainNavigationTransaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.GetAdds;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.R;

import java.util.ArrayList;


public class ListAdvertsActivity extends ActionBarActivity {

    /*Creating the variables needed for the navigation*/
    private String[] mNavigationTitle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout progressList;
    /****************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adverts);

        /*Navigation slider variables being assigned *************************/
        mNavigationTitle = getResources().getStringArray(R.array.navigation_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item
                , mNavigationTitle));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        /*********************************************************************/
        /*Toggle ActionBar Navigation ****************************************/
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.hello_world,
                R.string.hello_world
        ){
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /*******************************************************************/
        /*Progress bar *****************************************************/
        progressList = (RelativeLayout) findViewById(R.id.listAdvertForm);
        /*******************************************************************/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        /*Set the search bar in the navigation *********************************************/
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this,SearchAdvertsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        /***********************************************************************************/
        /*Set the progress bar visibility **************************************************/
        progressList.setVisibility(View.VISIBLE);
        /***********************************************************************************/
        new GetAddsTask().execute();
        /*Set the status bar color ********************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
        /************************************************************************************/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /* Method used to create the advert table using a customlist adapter***************************/
    private void BuildAdvertTable(final ArrayList<Advert> adverts) {

        ListView list;
        final String[] web = new String[adverts.size()];
        Integer[] imageId = new Integer[adverts.size()];

        for (int i = 0; i < adverts.size(); i++) {
            web[i] = adverts.get(i).getTitle();
            imageId[i] = i;
        }

        CustomList adapter = new
                CustomList(ListAdvertsActivity.this, web, adverts);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(ListAdvertsActivity.this, AdvertDetailsActivity.class);
                Globals.selectedAdvert = adverts.get(position);
                startActivity(i);
            }
        });
    }

    /*** Swaps classes in the main content view***************************************************/
    private void selectItem(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, HomePageActivity.class);
                break;
            case 1:
                intent = new Intent(this,MemberDetailsActivity.class);
                break;
            case 2:
                intent = new Intent(this, ListAdvertsActivity.class);
                break;
            case 3:
                intent = new Intent(this, CreateAdvertTypeActivity.class);
                break;
            case 4:
                intent = new Intent(this, RulesActivity.class);
                break;
            case 5:
                intent = new Intent(this, MyAdvertsActivity.class);
                break;
            case 6:
                intent = new Intent(this, MainNavigationBids.class);
                break;
            case 7:
                intent = new Intent(this, MainNavigationTransaction.class);
                break;
            case 8:
                intent = new Intent(this, LogoutActivity.class);
                break;
            default:
                intent = new Intent(this, MemberDetailsActivity.class);
                break;

        }
        startActivity(intent);
    }
    /**********************************************************************************************/





    private class GetAddsTask extends AsyncTask<Void, Void, Boolean> {
        ArrayList<Advert> adverts = new ArrayList<>();


        @Override
        protected Boolean doInBackground(Void... params) {
            GetAdds getAdds = new GetAdds();
            adverts = getAdds.GetCurrentAdds();
            return true;
        }

        protected void onPostExecute(Boolean result)
        {
            BuildAdvertTable(adverts);
            progressList.setVisibility(View.GONE);
        }


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
