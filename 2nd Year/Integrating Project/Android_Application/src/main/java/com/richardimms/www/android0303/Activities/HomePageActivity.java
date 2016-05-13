package com.richardimms.www.android0303.Activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.richardimms.www.android0303.Activities.CreateAdvert.CreateAdvertTypeActivity;
import com.richardimms.www.android0303.Fragments.Bids.MainNavigationBids;
import com.richardimms.www.android0303.Fragments.Transactions.MainNavigationTransaction;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.R;

public class HomePageActivity extends ActionBarActivity {
    /* These initialised variables help create the sliding navigation */
    private String[] mNavigationTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /******************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /* This creates the navigation bar ********************************************/
        mNavigationTitle = getResources().getStringArray(R.array.navigation_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item
                , mNavigationTitle));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        /*******************************************************************************/
        /*Action Bar Toggling **********************************************************/
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
        /********************************************************************************/
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
        getMenuInflater().inflate(R.menu.options_menu, menu);
        /*Search components *****************************************************************/
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this,SearchAdvertsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        /**************************************************************************************/
        /*Set the images on the dashboards to respond to touch***********************************/
        ImageView bid = (ImageView) findViewById(R.id.bidButton);
        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainNavigationBids.class);
                startActivity(intent);
            }
        });

        ImageView transaction = (ImageView) findViewById(R.id.transactionButton);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainNavigationTransaction.class);
                startActivity(intent);
            }
        });

        ImageView profile = (ImageView) findViewById(R.id.profileButton);
        profile.setImageResource(R.drawable.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,MemberDetailsActivity.class);
                startActivity(intent);
            }
        });

        ImageView adverts = (ImageView) findViewById(R.id.listAdvertButton);
        adverts.setImageResource(R.drawable.listadverts);
        adverts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,ListAdvertsActivity.class);
                startActivity(intent);
            }
        });

        ImageView createAdvert = (ImageView) findViewById(R.id.createadvertButton);
        createAdvert.setImageResource(R.drawable.createadvert);
        createAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CreateAdvertTypeActivity.class);
                startActivity(intent);
            }
        });

        ImageView rules = (ImageView) findViewById(R.id.rulesButton);
        rules.setImageResource(R.drawable.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,MyAdvertsActivity.class);
                startActivity(intent);
            }
        });
        /*************************************************************************************/
        /* Import a font style and assign it to a variable ***********************************/
        Typeface font = Typeface.createFromAsset(getAssets(),"Quicksand-Regular.otf");
        /*************************************************************************************/
        /*Assign text field with the font ***************************************************/
        TextView title = (TextView) findViewById(R.id.txtDashboard);
        title.setTypeface(font);

        TextView profileTitle = (TextView) findViewById(R.id.txtProfile);
        profileTitle.setTypeface(font);

        TextView currentAdvertTitle = (TextView) findViewById(R.id.txtCurrentAdverts);
        currentAdvertTitle.setTypeface(font);

        TextView createAdvertTitle = (TextView) findViewById(R.id.txtCreateAdvert);
        createAdvertTitle.setTypeface(font);

        TextView viewBids = (TextView) findViewById(R.id.txtBids);
        viewBids.setTypeface(font);

        TextView viewTransactions = (TextView) findViewById(R.id.txtTransactions);
        viewTransactions.setTypeface(font);

        TextView viewRules = (TextView) findViewById(R.id.txtRules);
        viewRules.setTypeface(font);
        /*************************************************************************************/
        /*Set status bar color ***************************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
        /************************************************************************************/
        /*Set Status bar color **********************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
        /********************************************************************************/

        return super.onCreateOptionsMenu(menu);
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

    /***Swaps classes in the main content view*/
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
    /*******************************************************************************/
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        /* Handles the navigation drawer click ***********************************/
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
        /*************************************************************************/
    }
}
