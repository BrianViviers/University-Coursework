package com.richardimms.www.android0303.DataModel;

import java.sql.Date;

/**
 * Created by Richard on 27/03/2015.
 * This Class holds all of the information relating to a bid in the system
 */
public class Bid {
    public Bid() {
    }


    private Long ID;
    private Long advertID;
    private Long offererID;
    private String offererName;
    private Long offereeID;
    private String offereeName;
    private String text;
    private String returnMessage;
    private Date bidDate;
    private String advertTitle;
    private int advertTypeID;
    private String isAccepted;
    private String bidCompleted;

    /**
     * Accessor method used to get the Bids ID.
     * @return  - Long value being the Bids ID.
     */
    public Long getID() {
        return ID;
    }

    /**
     * Accessor method used to assign an ID to a Bid
     * @param ID - Long value being the Bids ID.
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     * Accessor method used to retrieve the ID of the advert
     * @return - Long value being the advert ID.
     */
    public Long getAdvertID() {
        return advertID;
    }

    /**
     * Accessor method used to assign the ID of an Advert to a Bid.
     * @param advertID - Long value being the ID of an Advert.
     */
    public void setAdvertID(Long advertID) {
        this.advertID = advertID;
    }

    /**
     * Accessor method used to retrieve the Offerer ID of the Bid
     * @return - Long value being the Offerer ID.
     */
    public Long getOffererID() {
        return offererID;
    }

    /**
     * Accessor method used to assign the Offerer ID to a Bid.
     * @param offererID - Long value being the Offerer ID.
     */
    public void setOffererID(Long offererID) {
        this.offererID = offererID;
    }

    /**
     * Accessor method used to retrieve the Offerer Name.
     * @return - String value being the Offerer Name.
     */
    public String getOffererName() {
        return offererName;
    }

    /**
     * Accessor method used to assign the Offerer Name.
     * @param offererName - String value being the Offerer Name.
     */
    public void setOffererName(String offererName) {
        this.offererName = offererName;
    }

    /**
     * Accessor method used to retrieve the Offeree ID.
     * @return - Long value being the Offeree ID.
     */
    public Long getOffereeID() {
        return offereeID;
    }

    /**
     * Accessor method used to assign the Offeree ID.
     * @param offereeID - Long value being the Offeree ID.
     */
    public void setOffereeID(Long offereeID) {
        this.offereeID = offereeID;
    }

    /**
     * Accessor method used to retrieve the Offerees Name.
     * @return - String value being the current Offeree Name.
     */
    public String getOffereeName() {
        return offereeName;
    }

    /**
     * Accessor method used to assign the Offerees Name.
     * @param offereeName - String value being the Offeree Name.
     */
    public void setOffereeName(String offereeName) {
        this.offereeName = offereeName;
    }

    /**
     * Accessor method used to retrieve the Bid text.
     * @return - String value the Bid text.
     */
    public String getText() {
        return text;
    }

    /**
     * Accessor method used to assign the Bid text.
     * @param text - String value being the Bid text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Accessor method used to retrieve a ReturnMessage
     * @return - String value being the ReturnMessage of a Bid.
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * Accessor method used to assign a ReturnMessage.
     * @param returnMessage - String value being the ReturnMessage to assign to the bid.
     */
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * Accessor method used to retrieve the Date the bid was made.
     * @return - Date value being the date the Bid was made.
     */
    public Date getBidDate() {
        return bidDate;
    }

    /**
     * Accessor method used to assign the Date the bid was made.
     * @param bidDate - Date value being the date the bid was made.
     */
    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    /**
     * Accessor method used to retrieve the Adverts title.
     * @return - String value being the advert title.
     */
    public String getAdvertTitle() {
        return advertTitle;
    }

    /**
     * Accessor method used to assign an adverts title to a Bid object.
     * @param advertTitle - String value being the advert title.
     */
    public void setAdvertTitle(String advertTitle) {
        this.advertTitle = advertTitle;
    }

    /**
     * Accessor method used to retrieve an Adverts Type ID.
     * @return - Int value being the adverts type ID.
     */
    public int getAdvertTypeID() {
        return advertTypeID;
    }

    /**
     * Accessor method used to assign an Adverts Type ID.
     * @param advertTypeID - int value being the adverts type ID.
     */
    public void setAdvertTypeID(int advertTypeID) {
        this.advertTypeID = advertTypeID;
    }

    /**
     * Accessor method used to retrieve the acceptance status of a Bid.
     * @return - String value being the acceptance status of a Bid.
     */
    public String getIsAccepted() {
        return isAccepted;
    }

    /**
     * Accessor method used to assign the acceptance status of a Bid.
     * @param isAccepted - String value being the acceptance status of a Bid.
     */
    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    /**
     * Accessor method used to retrieve the date a Bid was completed.
     * @return - String value being the completed status of a Bid.
     */
    public String getBidCompleted() {
        return bidCompleted;
    }

    /**
     * Accessor method used to assign the completed status of a Bid.
     * @param bidCompleted - String value being the completed status of a Bid.
     */
    public void setBidCompleted(String bidCompleted) {
        this.bidCompleted = bidCompleted;
    }
}
