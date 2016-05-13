package com.richardimms.www.android0303.Activities.CreateAdvert;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.richardimms.www.android0303.Activities.HomePageActivity;
import com.richardimms.www.android0303.Activities.ListAdvertsActivity;
import com.richardimms.www.android0303.Activities.LogoutActivity;
import com.richardimms.www.android0303.Activities.MemberDetailsActivity;
import com.richardimms.www.android0303.Activities.MyAdvertsActivity;
import com.richardimms.www.android0303.Activities.RulesActivity;
import com.richardimms.www.android0303.Activities.SearchAdvertsActivity;
import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.Fragments.Bids.MainNavigationBids;
import com.richardimms.www.android0303.Fragments.Transactions.MainNavigationTransaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.CreateAdvert;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.R;

public class CreateAdvertPreview extends ActionBarActivity {

    /* These initialised variables help create the sliding navigation */
    private String[] mNavigationTitle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /******************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert_previe);

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
                getSupportActionBar().setTitle("LETS");
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /********************************************************************************/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this,SearchAdvertsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));


        final Advert newAdvert = Globals.newAdvert;
        /*Assign the text fields with the advert details ********************************/
        TextView advertTitle = (TextView) findViewById(R.id.txtAdvertTitle);
        advertTitle.setText(newAdvert.getTitle());

        TextView advertDescription = (TextView) findViewById(R.id.txtDescripton);
        advertDescription.setText(newAdvert.getDescription());

        TextView advertCategory = (TextView) findViewById(R.id.txtAdvertCategory);
        advertCategory.setText(newAdvert.getCategory());

        TextView advertItemType = (TextView) findViewById(R.id.txtItemType);
        advertItemType.setText(newAdvert.getItem_type());

        TextView advertType = (TextView) findViewById(R.id.txtAdvertType);
        advertType.setText(newAdvert.getAdvert_type());

        TextView advertTransport = (TextView) findViewById(R.id.txtTransport);
        if(newAdvert.getTransport() != null) {
            if (newAdvert.getTransport()) {
                advertTransport.setText("Included");
            } else {
                advertTransport.setText("Not Included");
            }
        }

        ImageView imageView = (ImageView) findViewById(R.id.imgAdvert);
        imageView.setImageBitmap(newAdvert.getAdvertImage());

        TextView advertCost = (TextView) findViewById(R.id.txtAdvertCost);
        advertCost.setText(String.valueOf(newAdvert.getCost()));


        Button editAdvert = (Button) findViewById(R.id.btnEditAdvert);
        editAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAdvertPreview.this, EditAdvertActivity.class);
                startActivity(intent);
            }
        });

        Button createAdvert = (Button) findViewById(R.id.btnConfirmAdvert);
        createAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.newAdvert.setMember_id(Globals.member.getId());
                Bitmap tempImage = null;
                if(Globals.newAdvert.getAdvertImage() != null) {
                    tempImage = Globals.newAdvert.getAdvertImage();
                }
                Globals.newAdvert.setAdvertImage(null);
                MyTaskParams params = new MyTaskParams(newAdvert, tempImage);
                new CreateAdvertTask().execute(params);
            }
        });

         /*Set Status bar color **********************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
        /********************************************************************************/
        return true;
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

    private class MyTaskParams {
        Advert advert;
        Bitmap image;

        MyTaskParams(Advert advert, Bitmap image) {
            this.image = image;
            this.advert = advert;
        }
    }
    /*Inner class used to create an advert************************************************/
    private class CreateAdvertTask extends AsyncTask<MyTaskParams, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            Boolean result = false;
            Advert advert = params[0].advert;
            Bitmap bm = params[0].image;

            CreateAdvert createAdvert = new CreateAdvert();
            result = createAdvert.createAdvert(advert, bm);

            return result;
        }

        protected void onPostExecute(Boolean result) {
            Log.i("On Post Ex", result.toString());
            if (!result) {
                Toast.makeText(CreateAdvertPreview.this, "There was an error creating your advert", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CreateAdvertPreview.this, "Success, created Advert", Toast.LENGTH_LONG).show();
                Intent i = new Intent(CreateAdvertPreview.this, HomePageActivity.class);
                startActivity(i);
            }
        }
    }
    /**********************************************************************************************/
    /*This method handles the list selection of the navigation slide ******************************/
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        /* Handles the navigation drawer click ***********************************/
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
        /*************************************************************************/
    }
    }
