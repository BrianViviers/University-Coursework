package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by philip on 18/03/2015.
 */
public class RegisterMember {

    /**
     * Method used to register a Member.
     * @param newMember - Member object being the member to register
     * @param password - String value being the password of to register to the user.
     * @param dob - String value being the D.O.B of the member.
     * @return - Boolean statement stating whether the member has been registered or not.
     */
    public boolean Register(Member newMember, String password, String dob)
    {
        boolean result = false;
        String responseStr = "";

        try {
            final String UK_FORMAT = "dd/MM/yyyy";
            final String US_FORMAT = "MM/dd/yyyy";
            String newDateString;

            // Convert the date from UK format to US
            // format to enter in the database.
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(UK_FORMAT);
                Date d = sdf.parse(dob);
                sdf.applyPattern(US_FORMAT);
                newDateString = sdf.format(d);
                java.sql.Date dateOfBirth = new java.sql.Date(new SimpleDateFormat(US_FORMAT)
                        .parse(newDateString).getTime());
                newMember.setDob(dateOfBirth);
            } catch (ParseException ex) {
                Log.i("Date failed: ", ex.toString());
            }

            //hash the password
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
            newMember.setPassword(hashed);
        } catch(Exception ex) {
            Log.i("pass failed: ", ex.toString());
        }

        String json = new Gson().toJson(newMember);
        json = json.replace("[", "");
        json = json.replace("]", "");

        WebService webService = new WebService();

        try {
            responseStr = webService.postContentWithResponseNoLogin(Globals.URL + "registermember", json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseStr.equals("Created member")) {
            String message = "Your account has been created succesfully.\r\nPlease login.";
            result = true;
        } else {
            String message = "There was an error creating your account.";
            result = false;
        }

        return result;
    }
}
