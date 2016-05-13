/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagefillers;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import entities.Bid;
import entities.Member;
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
 * Class to get all the bids a member has. Class implements Callable so that it
 * can be multi-threaded.
 *
 * @author PRCSA
 */
public class MemberBids implements Callable<String> {

    private final Member member;
    private final String whichBids;
    private final boolean runGetMemberBids;
    private final String advertID;

    /**
     * Constructor to create a new instance of MemberBids. This constructor is
     * used when member bids are wanted.
     *
     * @param member - Member object holding the logged in member's details.
     * @param whichBids - String representing which bids should be requested.
     */
    public MemberBids(Member member, String whichBids) {
        this.member = member;
        this.whichBids = whichBids;
        this.runGetMemberBids = true;
        this.advertID = null;
    }

    /**
     * Constructor to create a new instance of MemberBids. This constructor is
     * used when the bids on a certain advert are wanted.
     *
     * @param advertID - String representing the ID of the advert.
     * @param member - Member object holding the logged in member's details.
     */
    public MemberBids(String advertID, Member member) {
        this.member = member;
        this.advertID = advertID;
        this.runGetMemberBids = false;
        this.whichBids = null;
    }

    @Override
    public String call() throws Exception {
        String html;
        if (runGetMemberBids) {
            html = this.getMemberBids(this.member, this.whichBids);
        } else {
            html = this.getBidsByAdvertID(this.advertID, this.member);
        }
        return html;
    }

    private String getMemberBids(Member member, String whichBid) throws NullPointerException {
        StringBuilder sb = new StringBuilder(1000);
        AppServletContextListener context = new AppServletContextListener();
        if (member != null) {

            try {
                String url;

                url = context.getApiURL() + "memberbids/" + whichBid + "/" + member.getId().toString();

                WebServiceConnect webService = new WebServiceConnect();
                String response = webService.getWebServiceResponse(url, member);
                if (!response.startsWith("No bids found")) {

                    ArrayList<Bid> bidsList;
                    Gson gson = new Gson();
                    bidsList = gson.fromJson(response, new TypeToken<ArrayList<Bid>>() {
                    }.getType());

                    for (Bid bid : bidsList) {
                        if (whichBid.equals("incoming")) {
                            sb.append("<tr class=\"makepointer\" onclick=\"location.href='ViewMyAdvert?advertID=");
                            sb.append(bid.getAdvertID());
                            sb.append("'\">");
                        } else {
                            sb.append("<tr>");
                        }

                        sb.append("<td>");
                        sb.append(bid.getAdvertTitle());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(bid.getText());
                        sb.append("</td>");

                        if (whichBid.equals("refused") || whichBid.equals("accepted")) {
                            sb.append("<td>");
                            sb.append(bid.getReturnMessage());
                            sb.append("</td>");
                        }

                        sb.append("<td>");
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                        Date d = sdf.parse(bid.getBidDate().toString());
                        sdf.applyPattern("dd MMM yyyy HH:mm");
                        String bidDate = sdf.format(d);

                        sb.append(bidDate);
                        sb.append("</td>");

                        if (whichBid.equals("incoming")) {
                            sb.append("<td>");
                            sb.append(bid.getOffererName());
                            sb.append("</td>");
                        } else {
                            sb.append("<td>");
                            sb.append(bid.getOffereeName());
                            sb.append("</td>");
                        }
                        sb.append("</tr>");
                    }
                } else {
                    sb = new StringBuilder(100);
                    sb.append("<tr><td colspan=\"100%\"><h4>You have no bids.</h4></td></tr>");
                }
            } catch (IOException | JsonSyntaxException ex) {
                sb = new StringBuilder(100);
                Logger.getLogger(MemberBids.class.getName()).log(Level.SEVERE, null, ex);
                sb.append("<tr><td colspan=\"100%\"><h4>There was a problem retrieving your bids</h4></td></tr>");
            } catch (ParseException ex) {
                Logger.getLogger(MemberBids.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }

    private String getBidsByAdvertID(String id, Member member) throws NullPointerException {
        StringBuilder sb = new StringBuilder(1000);
        AppServletContextListener context = new AppServletContextListener();
        if (id != null) {

            try {
                String url;
                url = context.getApiURL() + "memberbids/advertid/" + id;

                WebServiceConnect webService = new WebServiceConnect();
                String response = webService.getWebServiceResponse(url, member);
                if (!response.startsWith("No bids found")) {

                    ArrayList<Bid> bidsList;
                    Gson gson = new Gson();
                    bidsList = gson.fromJson(response, new TypeToken<ArrayList<Bid>>() {
                    }.getType());

                    for (Bid bid : bidsList) {
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append(bid.getAdvertTitle());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(bid.getText());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(bid.getBidDate());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(bid.getOffererName());
                        sb.append("</td>");
                        sb.append("<td><form id='acceptReject' method='post' action='BidConfirm' novalidate>");
                        sb.append("<input type=\"text\" name=\"offererId\" hidden value=\"");
                        sb.append(bid.getOffererID());
                        sb.append("\"/>");
                        sb.append("<input type=\"text\" name=\"offereeId\" hidden value=\"");
                        sb.append(member.getId());
                        sb.append("\"/>");
                        sb.append("<input type=\"text\" name=\"advertTypeID\" hidden value=\"");
                        sb.append(bid.getAdvertTypeID());
                        sb.append("\"/>");
                        sb.append("<input type=\"text\" name=\"advertID\" hidden value=\"");
                        sb.append(bid.getAdvertID());
                        sb.append("\"/>");
                        sb.append("<input type=\"text\" name=\"isAccepted\" hidden value=\"");
                        sb.append(bid.getIsAccepted());
                        sb.append("\"/>");
                        sb.append("<a href='#' class=\"btn btn-default\" data-toggle=\"modal\" onclick='SetBidIDAccept(");
                        sb.append(bid.getID());
                        sb.append(")' data-target=\"#acceptModal\" style=\"width:100px\">Accept</a>");

                        sb.append("<a href='#' class=\"btn btn-default\" data-toggle=\"modal\" onclick='SetBidIDReject(");
                        sb.append(bid.getID());
                        sb.append(")' data-target=\"#refuseModal\" style=\"width:100px\">Reject</a>");

                        sb.append("</form></td>");
                    }
                } else {
                    sb = new StringBuilder(100);
                    sb.append("<tr><td colspan=\"100%\"><h4>You have no bids.</h4></td></tr>");
                }
            } catch (IOException | JsonSyntaxException ex) {
                sb = new StringBuilder(100);
                Logger.getLogger(MemberBids.class.getName()).log(Level.SEVERE, null, ex);
                sb.append("<tr><td colspan=\"100%\"><h4>There was a problem retrieving your bids</h4></td></tr>");
            }
        }
        return sb.toString();
    }
}
