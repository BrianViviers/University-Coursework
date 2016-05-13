package com.richardimms.www.android0303.Methods;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Created by Richard on 02/04/2015.
 */
public class GetBids {
    private Member member = Globals.member;
    private Member memberAPI = Globals.memberAPI;

    /**
     * Method used to retrieve incoming bids.
     * @return - ArrayList value containing all incoming bids.
     */
    public ArrayList<Bid> incomingBids()
    {
        ArrayList<Bid> incoming = getBids("incoming", member.getId());
        return incoming;
    }

    /**
     * Method used to retrieve all outgoing bids.
     * @return - ArrayList value containing all outgoing bids.
     */
    public ArrayList<Bid> outgoingBids()
    {
        ArrayList<Bid> outgoing = getBids("outgoing", member.getId());
        return outgoing;
    }

    /**
     * Method used to retrieve all rejected bids.
     * @return - ArrayList containing all the rejected bids.
     */
    public ArrayList<Bid> rejectedBids()
    {
        ArrayList<Bid> rejected = getBids("refused", member.getId());
        return rejected;
    }

    /**
     * Method used to retrieve all accepted bids.
     * @return - ArrayList containing all the accepted bids.
     */
    public ArrayList<Bid> acceptedBids()
    {
       ArrayList<Bid> accepted = getBids("accepted", member.getId());
       return accepted;
    }

    /**
     * Method used to get the Bids dependant on the type of Bids to return.
     * @param whichBids - String value being a statement of which Bids to get.
     * @param memberID - Long value being the ID number of a Member.
     * @return - String value being the response from the webservice.
     */
    public ArrayList<Bid> getBids(String whichBids, Long memberID)
    {
        ArrayList<Bid> bids = null;
        String response = "";

        try
        {
            String url;
            url = Globals.URL + "memberbids/"+ whichBids + "/" + memberID;
            Log.i("url",url);

            WebService webService = new WebService();
            response = webService.getWebServiceResponse(url,memberAPI);
        }
        catch(IOException ex)
        {
            Logger.getLogger(GetBids.class.getName()).log(Level.SEVERE,null,ex);
        }
        Gson gson = new Gson();
        if(response.startsWith("No"))
        {
            Log.i("!","No Bids");
        }
        else {
            bids = gson.fromJson(response, new TypeToken<ArrayList<Bid>>() {
            }.getType());
        }
        return bids;
    }
}
