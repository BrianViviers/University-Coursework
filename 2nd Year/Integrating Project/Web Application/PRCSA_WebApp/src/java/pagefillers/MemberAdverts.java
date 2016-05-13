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
import entities.Advert;
import entities.Member;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * Class to get all a members adverts either currently active or not.
 *
 * @author PRCSA
 */
public class MemberAdverts {

    /**
     * Constructor to create a new instance of MemberAdverts
     */
    public MemberAdverts() {
    }

    /**
     * Method used to get a members adverts.
     *
     * @param path - The path of the web service.
     * @param member - Member object holding the logged in member's details.
     * @return - String representing the adverts formatted into a HTML table.
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    public String getMemberAdverts(String path, Member member)
            throws NullPointerException, JsonSyntaxException {
        StringBuilder sb = new StringBuilder(1000);
        AppServletContextListener context = new AppServletContextListener();

        if (member != null) {
            try {

                // Get all the adverts images first so they can load.
                String url = context.getApiURL() + path + "images/" + member.getId().toString();
                WebServiceConnect webService = new WebServiceConnect();
                String response = webService.getWebServiceResponse(url, member);
                ArrayList<String[]> images;
                Gson gson = new Gson();
                images = gson.fromJson(response, new TypeToken<ArrayList<String[]>>() {
                }.getType());

                // Get the adverts details
                url = context.getApiURL() + path + "/" + member.getId().toString();
                response = webService.getWebServiceResponse(url, member);

                if (!response.startsWith("No adverts found for ID:")) {
                    ArrayList<Advert> adverts;
                    gson = new Gson();
                    adverts = gson.fromJson(response, new TypeToken<ArrayList<Advert>>() {
                    }.getType());

                    for (int i = 0; i < adverts.size(); i++) {
                        sb.append("<tr class='makepointer h2-green' onclick=\"location.href='ViewMyAdvert?Transport=");
                        sb.append(adverts.get(i).getTransport());
                        sb.append("&advertID=");
                        sb.append(adverts.get(i).getId());
                        sb.append("'\">");

                        // This section matches the image with the advert. They should have
                        // been received from the web services in the same order but if not
                        // then loop through to match advert ID's
                        sb.append("<td><img style='width:100px;height:100px;' src='");
                        if (null != images) {
                            if (!images.isEmpty()) {
                                sb.append("data:image/jpeg;base64,");
                                if (images.get(i)[0].equalsIgnoreCase(adverts.get(i).getId().toString())) {
                                    sb.append(images.get(i)[1]);
                                } else {
                                    for (String[] image : images) {
                                        if (image[0].equalsIgnoreCase(adverts.get(i).getId().toString())) {
                                            sb.append(image[1]);
                                        }
                                    }
                                }
                            }
                        }
                        sb.append("' alt=''/></td>");

                        sb.append("<td style='vertical-align: middle;'><h2 class='h2-green'>");
                        sb.append(adverts.get(i).getTitle());
                        sb.append("</h2><span>");
                        sb.append(adverts.get(i).getCost());
                        sb.append(" credits</span><h4 class='pull-right'>");
                        sb.append(adverts.get(i).getAdvert_type());
                        sb.append("</h4></td></tr>");
                    }
                } else {
                    sb = new StringBuilder(100);
                    sb.append("<tr><td colspan='100%'><h4>You have no adverts.</h4></td></tr>");
                }

            } catch (FileNotFoundException ex) {
                sb = new StringBuilder(100);
                sb.append("<tr><td><h3>You have no adverts.</h3></td></tr>");
            } catch (MalformedURLException ex) {
                Logger.getLogger(MemberAdverts.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MemberAdverts.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sb.toString();
        } else {
            sb = new StringBuilder(200);
            sb.append("<tr><td colspan='100%'><h4>Could not retrieve your adverts. Please login again.</h4></td></tr>");
            return sb.toString();
        }
    }
}
