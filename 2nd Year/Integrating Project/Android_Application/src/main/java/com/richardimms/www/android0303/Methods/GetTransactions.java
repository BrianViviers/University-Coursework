package com.richardimms.www.android0303.Methods;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.DataModel.Transaction;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Richard on 07/04/2015.
 */
public class GetTransactions {

    /**
     * Method used to retrieve incoming transactions.
     * @return - ArrayList of transactions containing all relevant incoming transactions.
     * @throws IOException
     */
    public ArrayList<Transaction> incomingTransactions() throws IOException
    {
        ArrayList<Transaction> incoming = getTransactions(Globals.member,"incoming");
        return incoming;
    }

    /**
     * Method used to retrieve the outgoing transactions.
     * @return - ArrayList of transactions containing all relevant outgoing transactions.
     * @throws IOException
     */
    public ArrayList<Transaction> outgoingTransactions() throws IOException
    {
        ArrayList<Transaction> outgoing = getTransactions(Globals.member,"outgoing");
        return outgoing;
    }

    /**
     * Method used to retrieve the completed incoming transactions
     * @return - ArrayList of transactions containing all relevant completed incoming transactions.
     * @throws IOException
     */
    public ArrayList<Transaction> completedIncoming() throws IOException
    {
        ArrayList<Transaction> completedIncoming = getTransactions(Globals.member,"compincoming");
        return completedIncoming;
    }

    /**
     * Method used to retrieve the completed outgoing transactions.
     * @return - ArrayList of transactions containing all relevant outgoing transactions.
     * @throws IOException
     */
    public ArrayList<Transaction> completedOutgoing() throws IOException
    {
        ArrayList<Transaction> completedOutgoing = getTransactions(Globals.member,"compoutgoing");
        return completedOutgoing;
    }

    /**
     * Method used to retrieve transactions based on it's type.
     * @param member - Member being the member object to search for transactions.
     * @param whichTransactions - String value being the type of transactions to return.
     * @return - ArrayList of transactions being the transactions requested by previous methods.
     */
    public ArrayList<Transaction> getTransactions(Member member, String whichTransactions) {
        ArrayList<Transaction> transactionArrayList = null;
        if(member != null)
        {
            try
            {
                String url = Globals.URL + "membertransactions/"+ whichTransactions + "/" + member.getId().toString();
                WebService webService = new WebService();
                String response = webService.getWebServiceResponse(url, Globals.memberAPI);
                if(!response.startsWith("No transactions found"))
                {
                    Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy hh:mm:ss aa").create();
                    transactionArrayList = gson.fromJson(response,new TypeToken<ArrayList<Transaction>>() {
                    }.getType());
                }
            }catch (IOException | JsonSyntaxException ex)
            {
                Logger.getLogger(GetTransactions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return transactionArrayList;
    }


}
