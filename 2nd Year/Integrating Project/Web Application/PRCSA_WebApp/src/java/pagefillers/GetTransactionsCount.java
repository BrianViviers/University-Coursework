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
 * Class to get a count of all transactions that need attention by the logged in
 * member. Class implements Callable so that when a new instance is created it
 * can be multi-threaded.
 *
 * @author PRCSA
 */
public class GetTransactionsCount implements Callable<String> {

    private final String whichTransactions;
    private final Member member;

    /**
     * Constructor to create a new instance of GetTransactionsCount
     *
     * @param whichTransactions - String representing which transaction should
     * be counted.
     * @param member - Member object holding the logged in member's details.
     */
    public GetTransactionsCount(String whichTransactions, Member member) {
        this.whichTransactions = whichTransactions;
        this.member = member;
    }

    @Override
    public String call() throws Exception {
        return getCount();
    }

    // Method which calls the web service and returns the response.
    private String getCount() throws NullPointerException, JsonSyntaxException {
        String response = "0";
        try {
            AppServletContextListener context = new AppServletContextListener();
            String url;

            url = context.getApiURL() + "counttransactions/" + whichTransactions + "/" + member.getId().toString();
            WebServiceConnect webService = new WebServiceConnect();
            response = webService.getWebServiceResponse(url, member);

        } catch (IOException ex) {
            Logger.getLogger(GetTransactionsCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

}
