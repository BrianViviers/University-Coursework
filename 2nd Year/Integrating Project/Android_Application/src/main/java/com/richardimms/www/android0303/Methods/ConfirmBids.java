package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;

/**
 * This class connects the GUI to the Webservice and allows the system to accept and reject bids
 * Created by Richard on 02/04/2015.
 */
public class ConfirmBids {

    /**
     * Takes a bid, converts it to a Gson object and sends it to the web service
     * @param bid being the bid to be accepted
     */
    public void acceptBids(Bid bid) {

        String responseStr;
        WebService webService = new WebService();

        String json = new Gson().toJson(bid);
        json = json.replace("[", "");
        json = json.replace("]", "");
        Log.i("JSON : ", json);

        try {

            Member member = Globals.memberAPI;
            responseStr = webService.postContentWithResponse(Globals.URL + "bidconfirm/accept",json,member);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Takes a bid, converts it to a Gson object and sends it to the web service
     * @param bid
     */
    public void rejectBids(Bid bid)
    {
        String responseStr;
        WebService webService = new WebService();

        String json = new Gson().toJson(bid);
        json = json.replace("[", "");
        json = json.replace("]", "");
        Log.i("JSON : ", json);

        try {
            Globals globals = new Globals();
            Member member = globals.memberAPI;
            responseStr = webService.postContentWithResponse(Globals.URL + "bidconfirm/reject",json,member);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
