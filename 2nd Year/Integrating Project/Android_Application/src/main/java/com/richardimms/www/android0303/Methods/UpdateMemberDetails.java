package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;

/**
 * Created by Richard on 25/03/2015.
 */
public class UpdateMemberDetails {

    /**
     * Method used to update a members name.
     * @param member - Member object to change the name of.
     * @return - Boolean value identifying whether the change has been successful.
     */
    public Boolean changeName(Member member)
    {
        boolean result = false;
        String responseStr = "";

        String json = new Gson().toJson(member);
        json = json.replace("[","");
        json = json.replace("]","");

        WebService webService = new WebService();
        try {
            responseStr = webService.postContentWithResponse(Globals.URL + "updatemember/name",json,Globals.memberAPI);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Method used to update a members email address.
     * @param member - Member object to change the email of.
     * @return - Boolean value identifying whether the change has been successful.
     */
    public Boolean changeEmail(Member member)
    {
        boolean result = false;
        String responseStr = "";

        String json = new Gson().toJson(member);
        json = json.replace("[","");
        json = json.replace("]","");


        WebService webService = new WebService();
        try
        {
            responseStr = webService.postContentWithResponse(Globals.URL + "updatemember/email",json,Globals.memberAPI);
            Log.i("Email Update: ", responseStr);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method used to update a members contact number.
     * @param member - Member object being the Member whose contact number to change.
     * @return - Boolean value identifying whether the change has been successful.
     */
    public Boolean changeContactNumber(Member member)
    {
        boolean result = false;
        String json = new Gson().toJson(member);
        json = json.replace("[","");
        json = json.replace("]","");

        WebService webService = new WebService();
        try {
            String responseStr = webService.postContentWithResponse(Globals.URL + "updatemember/telephone", json,Globals.memberAPI);
            Log.i("contact number Update: ", responseStr);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method used to change the Address of a Member.
     * @param member - Member object being the Member whose address to change.
     * @return - Boolean value identifying whether the change has been successful.
     */
    public Boolean changeAddress(Member member)
    {
        boolean result = false;
        String json = new Gson().toJson(member);
        json = json.replace("[","");
        json = json.replace("]","");

        WebService webService = new WebService();
        try {
            String responseStr = webService.postContentWithResponse(Globals.URL + "updatemember/address",json,Globals.memberAPI);
            Log.i("address Update: ", responseStr);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method used to change the date of birth of a Member.
     * @param member - Memember object being the Member whose date of birth to change.
     * @return - bBoolean value identifying whether the change has been successful.
     */
    public Boolean changeDateOfBirth(Member member)
    {
        boolean result = false;
        String responseStr = null;


            String json = new Gson().toJson(member);

            // Connect to the web service and pass in the updated member address
            WebService webService = new WebService();
        try {
            responseStr = webService.postContentWithResponse(Globals.URL + "updatemember/dob", json,Globals.memberAPI);
            Log.i("date Update: ", responseStr);
        }catch (IOException e)
        {
         e.printStackTrace();
        }
        return result;
    }
}
