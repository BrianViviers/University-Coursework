/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagefillers;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import entities.Member;
import entities.Transaction;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * Class to get all the transactions a member has. Class implements Callable so
 * that it can be multi-threaded.
 *
 * @author PRCSA
 */
public class MemberTransactions implements Callable<String> {

    private final String whichTransactions;
    private final Member member;

    /**
     * Constructor to create a new instance of MemberTransactions.
     * 
     * @param member - Member object holding the logged in member's details.
     * @param whichTrans - String representing the transaction to get from
     * the web service.
     */
    public MemberTransactions(Member member, String whichTrans) {
        this.whichTransactions = whichTrans;
        this.member = member;
    }

    @Override
    public String call() throws Exception {
        return this.getMemberTransactions(this.member, this.whichTransactions);
    }

    private String getMemberTransactions(Member member, String whichTransaction) throws NullPointerException {
        StringBuilder sb = new StringBuilder(1000);
        AppServletContextListener context = new AppServletContextListener();
        if (member != null) {

            try {
                String url;
                url = context.getApiURL() + "membertransactions/" + whichTransaction + "/" + member.getId().toString();

                WebServiceConnect webService = new WebServiceConnect();
                String response = webService.getWebServiceResponse(url, member);
                if (!response.startsWith("No transactions found")) {

                    ArrayList<Transaction> transactionList;
                    Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy hh:mm:ss aa").create();
                    transactionList = gson.fromJson(response, new TypeToken<ArrayList<Transaction>>() {
                    }.getType());

                    for (Transaction transaction : transactionList) {
                        sb.append("<tr><td>");
                        sb.append(transaction.getTitle());
                        sb.append("</td>");
                        if (whichTransaction.equals("incoming") || whichTransaction.equals("compincoming")) {
                            sb.append("<td>");
                            sb.append(transaction.getPayer());
                            sb.append("</td>");
                        } else {
                            sb.append("<td>");
                            sb.append(transaction.getPayee());
                            sb.append("</td>");
                        }
                        sb.append("<td>");
                        sb.append(transaction.getCreditPaid());
                        sb.append("</td>");

                        if (whichTransaction.equals("compoutgoing") || whichTransaction.equals("compincoming")) {

                            sb.append("<td>");

                            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                            Date d = sdf.parse(transaction.getDateCompleted().toString());
                            sdf.applyPattern("dd MMM yyyy HH:mm");
                            String compDate = sdf.format(d);

                            sb.append(compDate);
                            sb.append("</td><td>");
                            sb.append(transaction.getReviewRating());
                            sb.append("</td><td>");
                            sb.append(transaction.getReviewText());
                            sb.append("</td>");
                        }
                        switch (whichTransaction) {
                            case "incoming":
                                sb.append("<td>You're awaiting confirmation from the bidder.</td>");
                                break;
                            case "outgoing":
                                sb.append("<td><a href='#' class=\"btn btn-default\" data-toggle=\"modal\" onclick=\"SetTransactionID(");
                                sb.append(transaction.getId());
                                sb.append(")\" data-target=\"#reviewModal\" style=\"width:230px\">Pay Credits and Submit Review</a></td>");
                                break;
                            default:
                                sb.append("</td><td>");
                                break;
                        }

                        sb.append("</tr>");
                    }
                } else {
                    sb.append("<tr><td colspan=\"100%\"><h4>You have no transactions.</h4></td></tr>");
                }
            } catch (IOException | JsonSyntaxException ex) {
                sb = new StringBuilder(100);
                Logger.getLogger(MemberTransactions.class.getName()).log(Level.SEVERE, null, ex);
                sb.append("<tr><td colspan=\"100%\"><h4>There was a problem retrieving your transactions</h4></td></tr>");
            } catch (ParseException ex) {
                Logger.getLogger(MemberTransactions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }
}
