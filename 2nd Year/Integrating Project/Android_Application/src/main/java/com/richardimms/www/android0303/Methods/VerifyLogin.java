package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

/**
 * Created by philip on 18/03/2015.
 */
public class VerifyLogin {
    private Globals globals = new Globals();
    Member member = globals.member;

    public VerifyLogin()
    {
    }

    /**
     * Method used to login a user.
     * @param mEmail - String value being the members email address.
     * @param mPassword - String value being the members password.
     * @return - Boolean value identifying whether the login has been successful.
     */
    public boolean LoginResponse(String mEmail, String mPassword)
    {
        Member member;
        String responseStr = "";
        WebService webService = new WebService();
        boolean result = false;

        try {
            responseStr = webService.getWebServiceResponseNoLogin(Globals.URL + "memberpassword/" + mEmail.toLowerCase());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Log.i("Respnse: ", responseStr);

        if (responseStr.startsWith("No password found for email") || responseStr.trim().length() == 0){
            Log.i("Error: ", "there was a problem retrieving your details");
        } else {
            responseStr = responseStr.replace("\"", "");
            if (BCrypt.checkpw(mPassword, responseStr)) {
                Log.i("response: ", "password matched!");

                // Get the member's ID from their email address
                Member memberNew = new Member();
                memberNew.setPassword(responseStr);
                memberNew.setEmail(mEmail);


                try {
                    responseStr = webService.getWebServiceResponse(Globals.URL + "memberdetails/" + mEmail.toLowerCase(),memberNew);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                member = gson.fromJson(responseStr, new TypeToken<Member>() {
                }.getType());
                Globals.member = member;
                result = true;
                try {
                    /*Record the users login */
                    responseStr = webService.getWebServiceResponse(Globals.URL + "recordsession/" + member.getId().toString(),memberNew);
                    Globals.memberAPI.setEmail(mEmail);
                    Globals.memberAPI.setPassword(memberNew.getPassword());
                    /************************/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("response: ", "Password failed");
                result = false;
            }

        }
        return result;
    }
  }

