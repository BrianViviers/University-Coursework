package com.richardimms.www.android0303.Methods;


import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class conects the GUI to the web service and allows an advert to be created in the database
 * Created by Richard on 26/03/2015.
 */
public class CreateAdvert {
    private String responseStr;
    private Integer newAdvertID;
    private Globals globals = new Globals();
    Member member = globals.member;
    private Member memberAPI = globals.memberAPI;

    /**
     * Takes in an advert object and an image converts them both to Gson objects and sends them to the
     * web service
     * @param advert the new advert to be created
     * @param image the image for the new advert
     * @return bool being if the advert was created sucsefully
     */
    public Boolean createAdvert(Advert advert,Bitmap image) {
        boolean result = false;
            Log.i("Image", image.toString());
            String json = new Gson().toJson(advert);
            json = json.replace("[", "");
            json = json.replace("]", "");

            WebService webService = new WebService();

            try { // try to send the advert to the database
                responseStr = webService.postContentWithResponse(Globals.URL + "createadvert",json,memberAPI);
                newAdvertID = Integer.parseInt(responseStr);
                if(newAdvertID > 0)// if the response is greater than 0 then the advert is created
                {
                    // Converts the raw image
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] b = baos.toByteArray();
                    String imageToString = Base64.encodeToString(b,Base64.DEFAULT);
                    if(!imageToString.isEmpty())//if the image converted
                    {
                        imageToString = imageToString.replace("image:/jpeg;base64,", "");
                        Gson jsonImage = new Gson();

                        //convert the image to Gson
                        JsonObject jObject = new JsonObject();
                        jObject.addProperty("advert_id",newAdvertID);
                        jObject.addProperty("image",imageToString);

                        String toSend = jsonImage.toJson(jObject);
                        Log.i("json Object ", toSend);
                        toSend = toSend.replace("[", "");
                        toSend = toSend.replace("]", "");

                        //send the converted image to the database
                        responseStr = webService.postContentWithResponse(Globals.URL + "uploadadvertimage"
                        ,toSend,memberAPI);
                        if(!responseStr.isEmpty())
                        {
                            if(Integer.parseInt(responseStr) > -1)
                            {
                                Log.i("Advert Created!","!");
                                result = true;
                            }
                            else
                            {
                                Log.i("Advert","NotCreated");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
    }
}
