package com.richardimms.www.android0303.Methods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.DataModel.Rules;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.WebServices.WebService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Richard on 28/03/2015.
 */
public class GetRules {
    private ArrayList<Rules> rules;
    private String url = Globals.URL + "allrules";
    private Globals globals = new Globals();
    private Member member = globals.member;

    /**
     * Method used to get the rules from the API.
     * @return - ArrayList value being the list of Rules.
     */
    public ArrayList getRules()
    {
        WebService webService = new WebService();
        String response = null;
        try {
            response = webService.getWebServiceResponse(url,member);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        rules = gson.fromJson(response, new TypeToken<ArrayList<Rules>>() {
        }.getType());

        return rules;
    }
}
