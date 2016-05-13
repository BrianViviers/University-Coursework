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
import entities.Member;
import entities.Review;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * Class which gets all the reviews which have been given to a member. Class
 * implements callable so that it can be multi-threaded.
 *
 * @author PRCSA
 */
public class GetMemberReviews implements Callable<String> {

    private final String memberID;
    private final Member loggedInMember;
    private final String balance;

    /**
     * Constructor which creates a new instance of GetMemberReviews.
     *
     * @param memb - String representing the member for which reviews are
     * requested.
     * @param member - Member object holding the member logged into the system.
     * @param bal - String representing the balance of the member for which
     * reviews are being requested.
     */
    public GetMemberReviews(String memb, Member member, String bal) {
        this.memberID = memb;
        this.loggedInMember = member;
        this.balance = bal;
    }

    @Override
    public String call() throws Exception {
        return this.GetMemberReviews();
    }

    /**
     * Method which calls a web service to get the requested members reviews.
     *
     * @return - String representing the members reviews formatted into a HTML
     * table.
     * @throws NullPointerException
     */
    public String GetMemberReviews() throws NullPointerException {
        StringBuilder sb = new StringBuilder(500);
        AppServletContextListener context = new AppServletContextListener();
        if (!"".equals(memberID) && this.loggedInMember != null) {

            try {
                String url;

                url = context.getApiURL() + "memberreviews/" + memberID;

                WebServiceConnect webService = new WebServiceConnect();
                String response = webService.getWebServiceResponse(url, loggedInMember);

                sb.append("<div class='col-sm-6 col-sm-offset-2'");
                sb.append("<div class='table-responsive'>");
                sb.append("<table class='table table-striped'>");
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th>Review</th>");
                sb.append("<th>Rating</th>");
                sb.append("</tr>");
                sb.append("</thead>");
                sb.append("<tbody>");
                int averageRating = 0;
                int count = 0;
                if (!response.startsWith("No member reviews found")) {

                    ArrayList<Review> reviewsList;
                    Gson gson = new Gson();
                    reviewsList = gson.fromJson(response, new TypeToken<ArrayList<Review>>() {
                    }.getType());

                    for (Review review : reviewsList) {
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append(review.getReviewText());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(review.getReviewValue());
                        sb.append("</td>");
                        sb.append("</tr>");
                        averageRating += review.getReviewValue();
                        count++;
                    }

                } else {
                    sb = new StringBuilder(100);
                    sb.append("<tr><td colspan='100%'><h4>This member has no reviews.</h4></td></tr>");
                }
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");
                sb.append("<div class='col-sm-4'");
                sb.append("<h4></h4>");
                if (count != 0) {
                    averageRating = averageRating / count;
                    sb.append("<h4 class='label-green'>Average Rating:</h4><br>");
                    for (int i = 0; i < averageRating; i++) {
                        sb.append("<img src='img/green-star.png' alt='star' style='border:0;' />");
                    }
                } else {
                    sb.append("<h4 class='label-green'>Average Rating: No rating yet.</h4><br>");
                }

                sb.append("<h4 class='label-green'>Balance:&nbsp&nbsp<b> ");
                sb.append(this.balance);
                sb.append("</b></h4><br>");
                sb.append("<a href='SearchMembers'><button type='button' class='btn btn-default'>");
                sb.append("<img src='img/accountx24.png' alt='Back'/><br>View Members</button></a>");
                sb.append("<a href='BrowseAdverts'><button type='button' class='btn btn-default'>");
                sb.append("<img src='img/browsex24.png' alt='Browse'/><br>Browse Adverts</button></a>");
                sb.append("</div>");
                sb.append("</div>");
            } catch (IOException | JsonSyntaxException ex) {
                sb = new StringBuilder(100);
                Logger.getLogger(MemberBids.class.getName()).log(Level.SEVERE, null, ex);
                sb.append("<tr><td colspan='100%'><h4>There was a problem retrieving the reviews.</h4></td></tr>");
            }
        }
        return sb.toString();
    }
}
