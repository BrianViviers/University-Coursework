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
import entities.Rule;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * A class which calls a web service to get all the rules in the database and
 * then fills a HTML table with the data before returning it to the requesting
 * servlet.
 *
 * @author PRCSA
 */
public class AllRules {

    /**
     * Default constructor to create a new instance of AllRules.
     */
    public AllRules() {
    }

    /**
     * Method which calls the web service to get all the rules in the database.
     *
     * @return - String holding all the rules formatted into a HTML table.
     * @throws NullPointerException
     */
    public String getAllRules() throws NullPointerException {
        String html = "";
        AppServletContextListener context = new AppServletContextListener();
        try {
            String url = context.getApiURL() + "allrules";
            WebServiceConnect webService = new WebServiceConnect();
            String response = webService.getWebServiceResponseNoLogin(url);

            ArrayList<Rule> rules;
            Gson gson = new Gson();
            rules = gson.fromJson(response, new TypeToken<ArrayList<Rule>>() {
            }.getType());

            for (int i = 0; i < rules.size(); i++) {
                html += "<tr>"
                        + "<td style=\"vertical-align: middle;\">" + (i + 1) + "</td>"
                        + "<td style=\"vertical-align: middle;\">" + rules.get(i).getRule() + "</td>"
                        + "</tr>";
            }
        } catch (MalformedURLException | JsonSyntaxException ex) {
            Logger.getLogger(AllRules.class.getName()).log(Level.SEVERE, null, ex);
            html = "<tr><td colspan='100%'><p class='text-info'>There was a server error.</p></td></tr>";
        } catch (IOException ex) {
            Logger.getLogger(AllRules.class.getName()).log(Level.SEVERE, null, ex);
            html = "<tr><td colspan='100%'><p class='text-info'>There was a server error.</p></td></tr>";
        }
        return html;
    }
}
