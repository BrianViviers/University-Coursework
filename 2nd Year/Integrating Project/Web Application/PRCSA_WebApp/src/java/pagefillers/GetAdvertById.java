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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserviceconnector.WebServiceConnect;

/**
 * A class which calls a web service to get an advert by its ID in the database
 * and then returns it to the requesting servlet.
 *
 * @author PRCSA
 */
public class GetAdvertById {

    /**
     * Default constructor to create a new instance of GetAdvertById.
     */
    public GetAdvertById() {
    }

    /**
     * Method which calls the get advert by ID web service and returns the
     * Advert object.
     *
     * @param appRoot - String representing the root of the web application.
     * @param ID - String representing the ID of the requested advert.
     * @return - Advert object containing all the adverts details.
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    public Advert GetAdvert(String appRoot, String ID) throws NullPointerException, JsonSyntaxException {
        Advert advert = new Advert();
        AppServletContextListener context = new AppServletContextListener();

        try {
            // Get the adverts images first so they can load.
            String url = context.getApiURL() + "advertimagesbyid/" + ID;

            WebServiceConnect webService = new WebServiceConnect();
            String response = webService.getWebServiceResponseNoLogin(url);

            String[] image;
            Gson gson = new Gson();
            image = gson.fromJson(response, new TypeToken<String[]>() {
            }.getType());

            // Get the adverts details
            url = context.getApiURL() + "advertbyid/" + ID;

            response = webService.getWebServiceResponseNoLogin(url);
            if (!response.startsWith("No ")) {
                gson = new Gson();
                advert = gson.fromJson(response, new TypeToken<Advert>() {
                }.getType());
            }
            advert.setImage(image[1]);

        } catch (MalformedURLException ex) {
            Logger.getLogger(GetAdvertById.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetAdvertById.class.getName()).log(Level.SEVERE, null, ex);
        }
        return advert;
    }
}
