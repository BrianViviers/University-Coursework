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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * A class which calls a web service to get adverts from the database and then
 * returns them to the requesting servlet.
 *
 * @author PRCSA
 */
public class GetAdverts {

    /**
     * Default constructor to create a new instance of GetAdverts.
     */
    public GetAdverts() {
    }

    /**
     * This method is an overloaded method of getAdverts. It is called when
     * there is no search tags used to search for an advert. getAdverts is
     * called with the tags parameter set to an empty string.
     *
     * @param member - Member object holding the logged in members details
     * @param path - String representing the API URL for this get adverts
     * request.
     * @param guestBrowse - Boolean representing whether this request is from
     * @return - String representing all adverts formatted into a HTML table.
     */
    public String getAdverts(Member member, String path, Boolean guestBrowse) throws NullPointerException, JsonSyntaxException {
        return getAdverts(member, path, guestBrowse, "");
    }

    /**
     * A method which gets adverts from the database. If the user has entered
     * search tags then only adverts with those words or phrases will be
     * returned.
     *
     * @param member - Member object holding the logged in members details
     * @param path - String representing the API  for this get adverts
     * request.
     * @param guestBrowse - Boolean representing whether this request is from
     * the guest browse page or the member browse page.
     * @param tags - String representing the search words or phrases entered by
     * a searcher
     * @return - String representing all adverts formatted into a HTML table.
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    public String getAdverts(Member member, String path, Boolean guestBrowse, String tags) throws NullPointerException, JsonSyntaxException {
        StringBuilder sb = new StringBuilder(1000);
        AppServletContextListener context = new AppServletContextListener();

        try {
            ArrayList<String[]> images;

            String url;

            // Get all the adverts images first.
            if (tags.isEmpty()) {
                url = context.getApiURL() + path + "images/";
            } else {
                url = context.getApiURL() + path + "images/" + URLEncoder.encode(tags);
            }
            WebServiceConnect webService = new WebServiceConnect();
            String response = webService.getWebServiceResponseNoLogin(url);

            Gson gson = new Gson();
            images = gson.fromJson(response, new TypeToken<ArrayList<String[]>>() {
            }.getType());

            // Get the adverts details
            if (tags.isEmpty()) {
                url = context.getApiURL() + path;
            } else {
                url = context.getApiURL() + path + "/" + URLEncoder.encode(tags);
            }

            response = webService.getWebServiceResponseNoLogin(url);
            if (!response.startsWith("No ")) {

                ArrayList<Advert> adverts;
                gson = new Gson();
                adverts = gson.fromJson(response, new TypeToken<ArrayList<Advert>>() {
                }.getType());

                for (int i = 0; i < adverts.size(); i++) {
                    if (guestBrowse) {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                        Date d = sdf.parse(adverts.get(i).getDate_exp().toString());
                        sdf.applyPattern("dd MMM yyyy");
                        String expDate = sdf.format(d);

                        sb.append("<tr class='makepointer tableRowHover' onclick=\"location.href='viewAdvert.jsp?addID=");
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
                        sb.append("</h2><br><h4 class='advert-types'>");
                        sb.append(adverts.get(i).getAdvert_type());
                        sb.append("<span class='adverts-span pull-right'>");
                        sb.append(adverts.get(i).getCost());
                        sb.append(" credits</span>");
                        sb.append("</h4><br><b>Expiry Date:</b> ");
                        sb.append(expDate);
                        sb.append("<br><b>Added:</b> ");
                        sb.append(getDays(adverts.get(i).getDate_adv()));
                        sb.append("<br>");
                        sb.append("</td>");
                        sb.append("</tr>");

                    } else if (!Objects.equals(adverts.get(i).getMember_id(), member.getId())) {

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                        Date d = sdf.parse(adverts.get(i).getDate_exp().toString());
                        sdf.applyPattern("dd MMM yyyy");
                        String expDate = sdf.format(d);

                        sb.append("<tr class=\"makepointer tableRowHover\" onclick=\"location.href='memberViewAdvert.jsp?Transport=");
                        sb.append(adverts.get(i).getTransport());
                        sb.append("&addID=");
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

                        sb.append("<td style=\"vertical-align: middle;\"><h2 class=\"h2-green\">");
                        sb.append(adverts.get(i).getTitle());
                        sb.append("</h2><br><h4 class=\"advert-types\">");
                        sb.append(adverts.get(i).getAdvert_type());
                        sb.append("<span class=\"adverts-span pull-right\">");
                        sb.append(adverts.get(i).getCost());
                        sb.append(" credits</span>");
                        sb.append("</h4><br><b>Expiry Date:</b> ");
                        sb.append(expDate);
                        sb.append("<br><b>Added:</b> ");
                        sb.append(getDays(adverts.get(i).getDate_adv()));
                        sb.append("<br>");
                        sb.append("</td></tr>");
                    }
                }
            } else {
                sb = new StringBuilder(100);
                sb.append("<tr><td><h3 class='text-info'>");
                sb.append(response);
                sb.append("</h3></td></tr>");
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(GetAdverts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(GetAdverts.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sb.toString();
    }

    // This method returns how many days since an advert was uploaded.
    private String getDays(Date startDate) {

        //milliseconds     
        long different = new Date().getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        String days = elapsedDays + " days, " + elapsedHours + " hours";
        return days;
    }
}
