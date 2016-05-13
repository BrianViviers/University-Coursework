package com.richardimms.www.android0303.Fragments.Transactions;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.DataModel.Review;
import com.richardimms.www.android0303.DataModel.Transaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.ConfirmBids;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;

/**
 * Created by Richard on 12/04/2015.
 */
public class ConfirmTransaction {

    public String confirmTransaction(Review review)
    {
        String responseStr = "";
        WebService webService = new WebService();

        String json = new Gson().toJson(review);
        json = json.replace("[", "");
        json = json.replace("]", "");

        try {

            Member member = Globals.memberAPI;
            responseStr = webService.postContentWithResponse(Globals.URL + "finishtransaction/",json,member);



        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return responseStr;
    }
}
