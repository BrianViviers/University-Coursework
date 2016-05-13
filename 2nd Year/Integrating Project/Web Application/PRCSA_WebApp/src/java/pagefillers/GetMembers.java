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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * Class to call a web service and return all the registered members formatted
 * in HTML accordions.
 *
 * @author PRCSA
 */
public class GetMembers {

    /**
     * Constructor which creates a new instance of GetMembers.
     */
    public GetMembers() {
    }

    /**
     * Method which will get all registered members by calling a web service.
     *
     * @param path - The URL of the web service to call
     * @param member - Member object holding the logged in members details
     * @return - String representing the members formatted in HTML
     * @throws NullPointerException
     */
    public String GetMembers(String path, Member member) throws NullPointerException {
        return GetMembers(path, member, "");
    }

    /**
     * Method which will get all registered members that have a name that is the
     * same as the search phrase entered by calling a web service.
     *
     * @param path - The URL of the web service to call
     * @param member - Member object holding the logged in members details
     * @param tags - String representing the search phrase or words.
     * @return - String representing the members formatted in HTML
     * @throws NullPointerException
     */
    public String GetMembers(String path, Member member, String tags) throws NullPointerException {
        StringBuilder sb = new StringBuilder(2000);

        AppServletContextListener context = new AppServletContextListener();
        try {
            String url;
            WebServiceConnect webService = new WebServiceConnect();

            if (tags.isEmpty() || "".equals(tags)) {
                url = context.getApiURL() + path;
            } else {
                url = context.getApiURL() + path + "/" + URLEncoder.encode(tags);
            }
            String response = webService.getWebServiceResponse(url, member);
            if (!response.startsWith("No ")) {

                ArrayList<Member> members;
                Gson gson = new Gson();
                members = gson.fromJson(response, new TypeToken<ArrayList<Member>>() {
                }.getType());

                int count = 1;
                sb.append("<div class=\"panel-group\" id=\"accordion\">");
                for (Member member1 : members) {
                    sb.append("<div class=\"panel panel-default\">");
                    sb.append("  <div class=\"panel-heading text-left\">");
                    sb.append("    <h4 class=\"panel-title\">");
                    sb.append("      <a class=\"accordion-toggle collapsed\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse");
                    sb.append(count);
                    sb.append("\">");
                    sb.append(member1.getSurname());
                    sb.append(", ");
                    sb.append(member1.getForename());
                    sb.append("      </a>");
                    sb.append("    </h4>");
                    sb.append("  </div>");
                    sb.append("  <div id=\"collapse");
                    sb.append(count);
                    sb.append("\" class=\"panel-collapse collapse\">");
                    sb.append("    <div class=\"panel-body\">");
                    sb.append("      Balance: ");
                    sb.append(member1.getBalance());
                    sb.append("<br>");
                    sb.append("    <a class='text-info' href='MemberReviews?memberID=");
                    sb.append(member1.getId());
                    sb.append("&balance=");
                    sb.append("'>View Ratings & Reviews</a>");
                    sb.append("    </div>");
                    sb.append("  </div>");
                    sb.append("</div>");
                    count++;
                }
                sb.append("</div>");
            } else {
                sb = new StringBuilder(200);
                sb.append("<tr><td><h3>");
                sb.append(response);
                sb.append("</h3></td></tr>");
            }
        } catch (MalformedURLException | JsonSyntaxException ex) {
            Logger.getLogger(GetMembers.class.getName()).log(Level.SEVERE, null, ex);
            sb = new StringBuilder(200);
            sb.append("<tr><td><h3>There was a server error.</h3></td></tr>");
        } catch (IOException ex) {
            Logger.getLogger(GetMembers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
}
