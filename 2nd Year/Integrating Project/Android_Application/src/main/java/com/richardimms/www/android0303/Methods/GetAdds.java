package com.richardimms.www.android0303.Methods;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;


/**
 * This class connects the GUI to the webservice and allows the system to get diferent lists of adverts
 * Created by philip on 18/03/2015.
 */
public class GetAdds {
    private ArrayList<Advert> adverts = new ArrayList<>();
    private Iterable<String[]> advertImages = new ArrayList<>();
    private Globals globals = new Globals();
    private Member member = globals.member;


    public GetAdds(){
    }

    /**
     * Gets an ArrayList of all current adverts in the system
     * @return an ArrayList of adverts
     */
    public ArrayList GetCurrentAdds()
    {

        WebService webService = new WebService();
        String responseStr = "";

        try {
            responseStr = webService.getWebServiceResponse(Globals.URL + "currentadverts",member);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        adverts = gson.fromJson(responseStr, new TypeToken<ArrayList<Advert>>(){}.getType());
        getCurrentAdImages();
        return adverts;
    }

    /**
     * Gets the images for the current adds and connects them to the correct advert
     */
    public void getCurrentAdImages()
    {
        WebService webService = new WebService();
        String responseStr = "";

        try {
            responseStr = webService.getWebServiceResponse(Globals.URL + "currentadvertsimages",member);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        advertImages = gson.fromJson(responseStr, new TypeToken<ArrayList<String[]>>(){}.getType());

        for(String[] image : advertImages)
        {
            if(!image[1].isEmpty())
            {
                /*Convert the image blob into a base 64 array */
                byte[] base64 = Base64.decode(image[1], Base64.DEFAULT);
                /*Create a bitmap based on that byte array */
                Bitmap bm = BitmapFactory.decodeByteArray(base64, 0, base64.length);
                /*If the advert id matches the image id, set the image to that advert */
                for(int i =0 ; i < adverts.size();i++) {
                    if(adverts.get(i).getId() == Integer.parseInt(image[0])) {
                        adverts.get(i).setAdvertImage(bm);
                    }
                }
            }
        }
    }

    /**
     * Gets an ArrayList of adverts from the database based on a search string passed in
     * @param tag the search string
     * @return an ArrayList of adverts
     */
    public ArrayList getSearchedAdverts(String tag)
    {


        WebService webService = new WebService();
        String responseStr = "";
        String decodedTags = "";

        try {
            decodedTags = URLDecoder.decode(tag,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try
        {
            responseStr = webService.getWebServiceResponse(Globals.URL + "searchadverts/" + decodedTags,member);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        try {
            adverts = gson.fromJson(responseStr, new TypeToken<ArrayList<Advert>>() {
            }.getType());
            getSearchedImages(decodedTags);
        } catch (Exception ex)
        {
            Log.i("Exception: ", ex.getMessage());
        }
        return adverts;
    }

    /**
     * gets the images for the searched adverts
     * @param decodedTags
     */
    public void getSearchedImages(String decodedTags)
    {
        WebService webService = new WebService();
        String responseStr = "";

        try {
            responseStr = webService.getWebServiceResponse(Globals.URL + "searchadvertsimages/" + decodedTags,member);
            Log.i("response : ", responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        advertImages = gson.fromJson(responseStr, new TypeToken<ArrayList<String[]>>(){}.getType());

        for(String[] image : advertImages)
        {
            if(!image[1].isEmpty())
            {
                /*Convert the image blob into a base 64 array */
                byte[] base64 = Base64.decode(image[1], Base64.DEFAULT);
                /*Create a bitmap based on that byte array */
                Bitmap bm = BitmapFactory.decodeByteArray(base64, 0, base64.length);
                /*If the advert id matches the image id, set the image to that advert */
                for(int i =0 ; i < adverts.size();i++) {
                    if(adverts.get(i).getId() == Integer.parseInt(image[0])) {
                        adverts.get(i).setAdvertImage(bm);
                        Log.i("COUNT",String.valueOf(i));
                    }
                }
            }

        }
    }

    /**
     * Takes in a member id and reterns any current adverts that the user has created
     * @param id a string of the user id
     * @return an ArrayList of adverts
     */
    public ArrayList GetMemberCurrentAdds(String id) {
        WebService webService = new WebService();
        String responseStr = "";

        try {
            responseStr = webService.getWebServiceResponse(Globals.URL + "currentadverts/" + id, member);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        try {
            adverts = gson.fromJson(responseStr, new TypeToken<ArrayList<Advert>>() {
            }.getType());
            getCurrentAdImages();
        } catch (Exception ex)
        {
            Log.i("Exception: ", ex.getMessage());
        }
        return adverts;

    }
}
