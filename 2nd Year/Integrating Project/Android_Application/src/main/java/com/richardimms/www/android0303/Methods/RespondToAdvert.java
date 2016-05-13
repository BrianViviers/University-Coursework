package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;

/**
 * Created by Richard on 27/03/2015.
 */
public class RespondToAdvert {

    private String responseStr;
    private Globals globals = new Globals();
    private Member member = globals.memberAPI;

    /**
     * Method used to make a Bid on an Advert
     * @param bid - Bid value being the bid object to add to the Advert.
     * @return - Boolean value being identifying whether the Bid has been successful.
     */
    public Boolean respondToAdvert(Bid bid) {

        boolean result = false;
        WebService webService = new WebService();

        String json = new Gson().toJson(bid);
        json = json.replace("[", "");
        json = json.replace("]", "");

        try {
            responseStr = webService.postContentWithResponse(Globals.URL + "createbid", json,member);
            Log.i("response : ", responseStr);
            Log.i("Json : ", json);
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

        if (responseStr.equalsIgnoreCase("Bid created")) {
            String message = "Your bid has been sent successfully.";
            result = true;
        } else {
            String message = "There was a problem sending your bid.";
        }
        return result;
    }
}
