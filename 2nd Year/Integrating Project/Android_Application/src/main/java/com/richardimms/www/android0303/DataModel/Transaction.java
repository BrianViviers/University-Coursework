package com.richardimms.www.android0303.DataModel;

import java.sql.Date;

/**
 * This class holds all of the information relating to a Transaction in the system
 * Created by Richard on 27/03/2015.
 */
public class Transaction {

    private Long id;
    private Long advertID;
    private int advertTypeID;
    private String payer;
    private String payee;
    private String title;
    private Long creditPaid;
    private Date dateCompleted;
    private String reviewText;
    private int reviewRating;


    public Transaction() {
    }

    /**
     * Accessor method used to retrieve the Transaction ID.
     * @return - Long value being the Transaction ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Accessor method used to assign the Transaction ID.
     * @param id - Long value being the Transaction ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Accessor method used to retrieve the Advert ID.
     * @return - Long value being the Advert ID.
     */
    public Long getAdvertID() {
        return advertID;
    }

    /**
     * Accessor method used to assign the Advert ID.
     * @param advertID - Long value being the Advert ID.
     */
    public void setAdvertID(Long advertID) {
        this.advertID = advertID;
    }

    /**
     * Accessor method used to retrieve the Advert Type ID.
     * @return - int value being the Advert Type ID.
     */
    public int getAdvertTypeID() {
        return advertTypeID;
    }

    /**
     * Accessor method used to assign the Advert Type ID.
     * @param advertTypeID - int value being the Advert Type ID.
     */
    public void setAdvertTypeID(int advertTypeID) {
        this.advertTypeID = advertTypeID;
    }

    /**
     * Accessor method used to retrieve the Payer
     * @return - String value being the Payer.
     */
    public String getPayer() {
        return payer;
    }

    /**
     * Accessor method used to assign the Payer
     * @param payer - String value being the Payer.
     */
    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * Accessor method used to retrieve the Payee.
     * @return - String value being the Payee.
     */
    public String getPayee() {
        return payee;
    }

    /**
     * Accessor method used to assign the Payee.
     * @param payee - String value being the Payee.
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }

    /**
     * Accessor method used to retrieve the transaction title.
     * @return - String value being the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor method used to assign the transaction title.
     * @param title - String value being the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor method used to retrieve the amount of credits paid for a transaction.
     * @return - Long value being the amount of credits paid.
     */
    public Long getCreditPaid() {
        return creditPaid;
    }

    /**
     * Accessor method used to assign the amount of credits paid for a transaction.
     * @param creditPaid - Long value being the amount of credits paid.
     */
    public void setCreditPaid(Long creditPaid) {
        this.creditPaid = creditPaid;
    }

    /**
     * Accessor method used to retrieve the date the transaction is completed.
     * @return - Date value being the date the transaction was/is completed.
     */
    public Date getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Accessor method used to assign the date the transaction is completed.
     * @param dateCompleted - Date value being the date the transaction was/is completed.
     */
    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    /**
     * Accessor method used to retrieve a review text to a transaction.
     * @return - String value being the review given to a transaction.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Accessor method used to assign a review text to a transaction.
     * @param reviewText - String value being the review given to a transaction.
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    /**
     * Accessor method used to retrieve the  rating of a transaction.
     * @return - int value being the rating of a transaction.
     */
    public int getReviewRating() {
        return reviewRating;
    }

    /**
     * Accessor method used to assign the rating of a transaction.
     * @param reviewRating - int value being the rating of a transaction.
     */
    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
}
