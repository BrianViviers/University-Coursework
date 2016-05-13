package com.richardimms.www.android0303.Globals;


import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.DataModel.Transaction;

/**
 * Created by Richard on 09/03/2015.
 */
public class Globals {
    /*Gloabl variables for access across the application */
    public static Member member = new Member();
    public static Advert selectedAdvert = new Advert();
    public static Bid selectedBid = new Bid();
    public static Member memberAPI = new Member();
    public static Advert newAdvert = new Advert();
    public static Transaction selectedTransaction = new Transaction();
    public static String URL = "https://eeyore.fost.plymouth.ac.uk:8282/PRCSA_API/resources/";
    /*****************************************************/
}
