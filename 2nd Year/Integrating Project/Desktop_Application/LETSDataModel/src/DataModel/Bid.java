/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.util.Date;


/**
 * This class represents an offer.
 * @author PRCSA
 */
public class Bid {
    
    private int bidNo;
    private int advertID;
    private int biddingMember;
    
    private String bidText;
    private Date offerDate;

    /**
     * Default constructor for initialising variables.
     */
    public Bid()
    {
        this.bidNo = 0;
        this.advertID = 0;
        this.biddingMember = 0;
        this.bidText = "UNKNOWN";
        this.offerDate = new Date();
    }
    
    /**
     * Default constructor assigning variables.
     * @param bidNo - int value being the unique Offer ID.
     * @param adID  - int value being the unique Advert ID.
     * @param biddingMember - int value being the identifier of the Member.
     * @param bidText - String value being the message sent with the offer.
     * @param date - Date value being the date the offer was made.
     */
    public Bid(int bidNo, int adID, int biddingMember, String bidText, 
                   Date date)
    {
        this.bidNo = bidNo;
        this.advertID = adID;
        this.bidText = bidText;
        this.biddingMember = biddingMember;
        this.offerDate = date;
    }
    
    /**
     * Accesor method to retrieve the Offer Number.
     * @return - int value being the Offer Number.
     */
    public int getOfferNo() {
        return bidNo;
    }

    /**
     * Accessor method to set the Offer Number.
     * @param offerNo - int value being the Offer Number.
     */
    public void setOfferNo(int offerNo) {
        this.bidNo = offerNo;
    }

    /**
     * Accesor method to retrieve the unique Advert Number.
     * @return - int value being the Advert Number.
     */
    public int getAdvertNo() {
        return advertID;
    }

    /**
     * Accessor method to set the unique Advert Number.
     * @param advertNo - int value being the Advert Number.
     */
    public void setAdvertNo(int advertNo) {
        this.advertID = advertNo;
    }

    /**
     * Accessor method to retrieve the Offerer identifier.
     * @return - int value being the identifier of the Offerer.
     */
    public int getOffererNo() {
        return biddingMember;
    }

    /**
     * Accessor method to set the Offerer identifier.
     * @param offererNo  - int value being the identifier of the Offerer.
     */
    public void setOffererNo(int offererNo) {
        this.biddingMember = offererNo;
    }

    /**
     * Accessor method to retrieve the message for the Offer
     * @return - String value being the text of the message.
     */
    public String getOfferText() {
        return bidText;
    }

    /**
     * Accessor method to set the message for the Offer.
     * @param offerText - String value being the text of the message.
     */
    public void setOfferText(String offerText) {
        this.bidText = offerText;
    }

    /**
     * Accessor method to retrieve the Date the offer was made.
     * @return
     */
    public Date getOfferDate() {
        return offerDate;
    }

    /**
     * Accessor method to set the Date the offer was made.
     * @param offerDate
     */
    public void setOfferDate(Date offerDate) {
        this.offerDate = offerDate;
    }
    
    
}
