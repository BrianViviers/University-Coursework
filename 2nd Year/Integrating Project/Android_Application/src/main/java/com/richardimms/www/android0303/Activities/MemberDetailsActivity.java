package com.richardimms.www.android0303.Activities;

import android.app.Activity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.richardimms.www.android0303.Activities.CreateAdvert.CreateAdvertTypeActivity;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Fragments.Bids.MainNavigationBids;
import com.richardimms.www.android0303.Fragments.Transactions.MainNavigationTransaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.Methods.UpdateMemberDetails;
import com.richardimms.www.android0303.R;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MemberDetailsActivity extends ActionBarActivity {

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<>();

    private Spinner daysCombo;
    private Spinner monthsCombo;
    private Spinner yearsCombo;




    /*Navigation variables set up *********************/
    private String[] mNavigationTitle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /**************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        /* Assigning the variables to the resources available. **********************/
        mNavigationTitle = getResources().getStringArray(R.array.navigation_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item
                , mNavigationTitle));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        /****************************************************************************/
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

         /*filling out date stuff, i know its messy but im tired**********************/
        for(int i = 1; i < 10; i ++)
        {
            days.add(String.valueOf(0) + String.valueOf(i));
        }
        for(int i = 10; i < 32; i ++)
        {
            days.add(String.valueOf(i));
        }

        for(int i = 1; i < 10; i++)
        {
            months.add(String.valueOf(0) + String.valueOf(i));
        }

        for(int i = 10; i < 13; i++)
        {
            months.add(String.valueOf(i));
        }

        for(int i = 2015; i > 1900; i--)
        {
            years.add(String.valueOf(i));
        }

        daysCombo = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, days);
        daysCombo.setAdapter(adapterCategory);

        monthsCombo = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<String> adapterCategory2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, months);
        monthsCombo.setAdapter(adapterCategory2);

        yearsCombo = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<String> adapterCategory3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, years);
        yearsCombo.setAdapter(adapterCategory3);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this,SearchAdvertsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        final Member newMember = Globals.member;

        /* Assigning the variables to the layout *********************************************/
        final EditText txtForename = (EditText) findViewById(R.id.txtForename);
        final EditText txtSurname = (EditText) findViewById(R.id.txtSurname);
        final EditText txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
        final EditText txtAddress2 = (EditText) findViewById(R.id.txtAddress2);
        final EditText txtPostcode = (EditText) findViewById(R.id.txtPostcode);
        final EditText txtCity = (EditText) findViewById(R.id.txtCity);
        final EditText txtContactNo = (EditText) findViewById(R.id.txtContact);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        /**************************************************************************************/

        /*Setting the text in the layout ******************************************************/
        txtForename.setText(newMember.getForename());
        txtSurname.setText(newMember.getSurname());

        txtAddress1.setText(newMember.getAddline1());
        txtAddress2.setText(newMember.getAddline2());
        txtPostcode.setText(newMember.getPostcode());
        txtCity.setText(newMember.getCity());

        txtContactNo.setText(newMember.getContact_number());
        txtEmail.setText(newMember.getEmail());

        /**************************************************************************************/
        /*Button to update the member ********************************************************/
        Button button = (Button) findViewById(R.id.updateMember);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("!", "Button Pressed");
                newMember.setForename(txtForename.getText().toString());
                newMember.setSurname(txtSurname.getText().toString());

                newMember.setAddline1(txtAddress1.getText().toString());
                newMember.setAddline2(txtAddress2.getText().toString());
                newMember.setPostcode(txtPostcode.getText().toString());
                newMember.setCity(txtCity.getText().toString());

                newMember.setContact_number(txtContactNo.getText().toString());
                newMember.setEmail(txtEmail.getText().toString());

                String day;
                String month;
                String year;

                day = daysCombo.getSelectedItem().toString();
                month = monthsCombo.getSelectedItem().toString();
                year = yearsCombo.getSelectedItem().toString();

                //String date = (year + "-" + month + "-" + day);
                String date = (day + "/" + month + "/" + year);
                Log.i("Date: ", date);

                    final String UK_FORMAT = "dd/MM/yyyy";
                    final String US_FORMAT = "MM/dd/yyyy";
                    String newDateString;

                    // Convert the date from UK format to US
                    // format to enter in the database.
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(UK_FORMAT);
                        java.util.Date d = sdf.parse(date);
                        sdf.applyPattern(US_FORMAT);
                        newDateString = sdf.format(d);
                        java.sql.Date dateOfBirth = new java.sql.Date(new SimpleDateFormat(US_FORMAT)
                                .parse(newDateString).getTime());
                        newMember.setDob(dateOfBirth);
                    } catch (ParseException ex) {
                        Log.i("Date failed: ", ex.toString());
                    }

                MyTaskParams params = new MyTaskParams(newMember);
                new UpdateMemberTask().execute(params);
            }
        });
        /***********************************************************************************/
        SetStatusBarColour.setTaskbar(this.getWindow());
        SetStatusBarColour.setActionbarColour(getSupportActionBar());
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

    /***Swaps classes in the main content view***************************************************/
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
    /********************************************************************************************/

    /************************Updating Membe Details**********************************************/
    private Boolean updateMember(Member member) {
        UpdateMemberDetails updateMemberDetails = new UpdateMemberDetails();
        updateMemberDetails.changeName(member);
        updateMemberDetails.changeContactNumber(member);
        updateMemberDetails.changeEmail(member);
        updateMemberDetails.changeAddress(member);
        updateMemberDetails.changeDateOfBirth(member);

        return true;
    }
    /*******************************************************************************************/

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class MyTaskParams {
        Member member;

        MyTaskParams(Member member) {
            this.member = member;
        }
    }

    private class UpdateMemberTask extends AsyncTask<MyTaskParams, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            Boolean result = false;
            Member member = params[0].member;

            result = updateMember(member);
            if (result == true && Globals.memberAPI.getEmail() != member.getEmail())
            {
                Globals.memberAPI.setEmail(member.getEmail());
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            Log.i("On Post Ex", result.toString());
            if (!result) {
                Toast.makeText(MemberDetailsActivity.this, "There was an error Updateing your account", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MemberDetailsActivity.this, "Success, redirecting to Home page", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MemberDetailsActivity.this, HomePageActivity.class);
                startActivity(i);
            }
        }
    }
}
