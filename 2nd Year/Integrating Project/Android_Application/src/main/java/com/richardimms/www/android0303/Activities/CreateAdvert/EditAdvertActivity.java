package com.richardimms.www.android0303.Activities.CreateAdvert;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.Spinner;

import com.richardimms.www.android0303.Activities.HomePageActivity;
import com.richardimms.www.android0303.Activities.ListAdvertsActivity;
import com.richardimms.www.android0303.Activities.LogoutActivity;
import com.richardimms.www.android0303.Activities.MemberDetailsActivity;
import com.richardimms.www.android0303.Activities.MyAdvertsActivity;
import com.richardimms.www.android0303.Activities.RulesActivity;
import com.richardimms.www.android0303.Activities.SearchAdvertsActivity;
import com.richardimms.www.android0303.Fragments.Bids.MainNavigationBids;
import com.richardimms.www.android0303.Fragments.Transactions.MainNavigationTransaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.R;

import java.util.ArrayList;

public class EditAdvertActivity extends ActionBarActivity {

    private Spinner advertType;
    private Spinner itemType;
    private Spinner categoryCombo;
    private ArrayList<String> categories = new ArrayList<String>();

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
        /*Populate category list ***************************************************************/
        categories.add("Accounting");
        categories.add("Cleaning");
        categories.add("Computing and Electronics");
        categories.add("Education");
        categories.add("Health");
        categories.add("Home and Garden");
        categories.add("Maintenance");
        categories.add("Printing");
        categories.add("Recreation");
        categories.add("Transport");
        /***************************************************************************************/

        setContentView(R.layout.activity_edit_advert);

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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this,SearchAdvertsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        /*Set Status bar color **********************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
        /********************************************************************************/
        /* Progmatically create  combo boxes ****************************************************/
        advertType = (Spinner) findViewById(R.id.spinnerAdvertType);
        ArrayAdapter<CharSequence> adapterAdvertType = ArrayAdapter.createFromResource(this,
                R.array.combo_advertType,
                android.R.layout.simple_spinner_dropdown_item);
        advertType.setAdapter(adapterAdvertType);

        itemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapterItemType = ArrayAdapter.createFromResource(this,
                R.array.combo_itemType,
                android.R.layout.simple_spinner_dropdown_item);
        itemType.setAdapter(adapterItemType);

        categoryCombo = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        categoryCombo.setAdapter(adapterCategory);
        /***************************************************************************************/
        /*Assign variables *********************************************************************/
        final EditText advertTitle = (EditText) findViewById(R.id.inputAdvertTitle);
        final EditText advertDescription = (EditText) findViewById(R.id.inputAdvertDescription);
        final NumberPicker advertCost = (NumberPicker) findViewById(R.id.numberPickerEdit);
        advertCost.setMinValue(1);
        advertCost.setMaxValue(10);

        final Spinner advertType = (Spinner) findViewById(R.id.spinnerAdvertType);
        final Spinner itemType = (Spinner) findViewById(R.id.spinnerItemType);
        final Spinner category = (Spinner) findViewById(R.id.spinnerCategory);
        ImageView imageCreate = (ImageView) findViewById(R.id.imageAdvert);
        CheckBox transport = (CheckBox) findViewById(R.id.checkBoxTransport);
        /*************************************************************************************/
        advertTitle.setText(Globals.newAdvert.getTitle());
        advertDescription.setText(Globals.newAdvert.getDescription());
        advertCost.setValue(Globals.newAdvert.getCost());
        transport.setChecked(Globals.newAdvert.getTransport());
        imageCreate.setImageBitmap(Globals.newAdvert.getAdvertImage());

        for(int i =0; i < categories.size();i++)
        {
            if(categories.get(i).equals(Globals.newAdvert.getCategory()))
            {
                category.setSelection(i);
            }
        }

        if(Globals.newAdvert.getAdvert_type().equals("Offer"))
        {
            advertType.setSelection(0);
        }
        else if(Globals.newAdvert.getAdvert_type().equals("Request"))
        {
            advertType.setSelection(1);
        }

        if(Globals.newAdvert.getItem_type().equals("Product"))
        {
            itemType.setSelection(0);
        }
        else if(Globals.newAdvert.getItem_type().equals("Service"))
        {
            itemType.setSelection(1);
        }

        Button edit = (Button) findViewById(R.id.buttonConfirmEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.newAdvert.setTitle(advertTitle.getText().toString());
                Globals.newAdvert.setDescription(advertDescription.getText().toString());
                Globals.newAdvert.setItem_type(itemType.getSelectedItem().toString());
                Globals.newAdvert.setAdvert_type(advertType.getSelectedItem().toString());
                Globals.newAdvert.setCategory(category.getSelectedItem().toString());

                Integer cost = advertCost.getValue();
                Globals.newAdvert.setCost(cost);

                boolean isChecked = ((CheckBox) findViewById(R.id.checkBoxTransport)).isChecked();
                Globals.newAdvert.setTransport(isChecked);
                Log.i("Advert", Globals.newAdvert.toString());
                Intent intent = new Intent(EditAdvertActivity.this,CreateAdvertPreview.class);
                startActivity(intent);
            }
        });

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

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        /* Handles the navigation drawer click ***********************************/
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
        /*************************************************************************/
    }

}
