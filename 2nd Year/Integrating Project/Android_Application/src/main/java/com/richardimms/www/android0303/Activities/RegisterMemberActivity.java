package com.richardimms.www.android0303.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Methods.RegisterMember;
import com.richardimms.www.android0303.Methods.SetStatusBarColour;
import com.richardimms.www.android0303.R;

import java.util.ArrayList;


public class RegisterMemberActivity extends ActionBarActivity {

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<>();

    private Spinner daysCombo;
    private Spinner monthsCombo;
    private Spinner yearsCombo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

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
        /****************************************************************************/

        Button button = (Button) findViewById(R.id.btnRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member newMember = new Member();
                /* Assign the variables to the layout ***************************************/
                EditText txtForename = (EditText) findViewById(R.id.txtForename);
                EditText txtSurname = (EditText) findViewById(R.id.txtSurname);
                EditText txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
                EditText txtAddress2 = (EditText) findViewById(R.id.txtAddress2);
                EditText txtPostcode = (EditText) findViewById(R.id.txtPostcode);
                EditText txtCity = (EditText) findViewById(R.id.txtCity);
                EditText txtContactNo = (EditText) findViewById(R.id.txtContact);
                EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
                EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
                /****************************************************************************/
                /*Set the text of the fields to a member ************************************/
                newMember.setForename(txtForename.getText().toString());
                newMember.setSurname(txtSurname.getText().toString());
                newMember.setAddline1(txtAddress1.getText().toString());
                newMember.setAddline2(txtAddress2.getText().toString());
                newMember.setPostcode(txtPostcode.getText().toString());
                newMember.setCity(txtCity.getText().toString());
                newMember.setContact_number(txtContactNo.getText().toString());
                newMember.setEmail(txtEmail.getText().toString());
                /*****************************************************************************/

                String day;
                String month;
                String year;

                day = daysCombo.getSelectedItem().toString();
                month = monthsCombo.getSelectedItem().toString();
                year = yearsCombo.getSelectedItem().toString();

                String password = txtPassword.getText().toString();
                String date = (day + "/" + month + "/" + year);
                Log.i("testing date: ", date);

                Toast.makeText(RegisterMemberActivity.this, "Attempting to register new Member", Toast.LENGTH_LONG).show();
                MyTaskParams params = new MyTaskParams(newMember, password, date);

                new RegMemberTask().execute(params);
            }
        });

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

        return super.onOptionsItemSelected(item);
    }

    private class MyTaskParams {
        Member member;
        String password;
        String dob;

        MyTaskParams(Member member, String password, String dob) {
            this.member = member;
            this.password = password;
            this.dob = dob;
        }
    }

    private class RegMemberTask extends AsyncTask<MyTaskParams, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            Boolean result = false;
            Member member = params[0].member;
            String password = params[0].password;
            String dob = params[0].dob;

            RegisterMember registerMember = new RegisterMember();

            result = registerMember.Register(member, password, dob);

            return result;
        }

        protected void onPostExecute(Boolean result) {
            Log.i("On Post Ex", result.toString());
            if (!result) {
                Toast.makeText(RegisterMemberActivity.this, "There was an error creating your account", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterMemberActivity.this, "Success, redirecting to log in page", Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterMemberActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
        }
    }


}
