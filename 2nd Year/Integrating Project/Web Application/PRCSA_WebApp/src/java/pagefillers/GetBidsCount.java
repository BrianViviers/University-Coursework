/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagefillers;

import applicationconfig.AppServletContextListener;
import com.google.gson.JsonSyntaxException;
import entities.Member;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * Class which gets a count of bids depending on which type of bids have been
 * requested. Class implements callable so that it can be multi-threaded.
 *
 * @author PRCSA
 */
public class GetBidsCount implements Callable<String> {

    private final String whichBids;
    private final Member member;

    /**
     * Constructor which creates a new instance of GetBidsCount.
     *
     * @param whichBids - String representing which bids are requested such as
     * accepted, refused, incoming, outgoing, etc.
     * @param member - Member object holding the logged in member requesting a
     * count.
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    public GetBidsCount(String whichBids, Member member) throws NullPointerException, JsonSyntaxException {
        this.whichBids = whichBids;
        this.member = member;
    }

    @Override
    public String call() throws Exception {
        return getCount();
    }

    /**
     * Gets a count of the type of bids requested.
     *
     * @return - String representing the number of bids.
     */
    public String getCount() {
        String response = "0";
        try {
            AppServletContextListener context = new AppServletContextListener();
            String url;

            url = context.getApiURL() + "countbids/" + whichBids + "/" + member.getId().toString();
            WebServiceConnect webService = new WebServiceConnect();
            response = webService.getWebServiceResponse(url, member);

        } catch (IOException ex) {
            Logger.getLogger(GetBidsCount.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
