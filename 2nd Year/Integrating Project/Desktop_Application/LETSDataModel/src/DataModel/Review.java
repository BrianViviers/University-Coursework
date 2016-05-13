/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

/**
 * This class represents a review.
 * @author PRCSA
 */
public class Review {
    private int reviewNo;
    private int transactionID;
    private int reviewValue;
    private String reviewText;
    
    
    /**
     * Default Constructor used to initialise variables.
     */
    public Review()
    {
        this.reviewNo = 0;
        this.reviewValue = 0;
        this.transactionID = 0;
        this.reviewText = "";
    }
    
    /**
     * Default constructor to associate the data with the variables.
     * @param revNo - int vaue being the unique review number.
     * @param transactionID
     * @param revVal - int value being the rating of the review.
     * @param reviewText
     */
    public Review(int revNo, int transactionID, int revVal ,String reviewText)
    {
        this.reviewNo = revNo;
        this.reviewValue = revVal; 
        this.transactionID = transactionID;
        this.reviewText = reviewText;
    }

    /**
     * Accessor method to retrieve the unique review number
     * @return - int value being the unique review number.
     */
    public int getReviewNo() {
        return reviewNo;
    }

    /**
     * Accessor method to set the unique review number
     * @param reviewNo - int value being the unique review number.
     */
    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    /**
     * Accessor method used to retrieved the transaction ID.
     * @return - int value being the transaction ID.
     */
    public int getTransactionID() {
        return transactionID;
    }

    /**
     * Accessor method used to set the transaction ID
     * @param transactionID - int value being the transaction ID.
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * Accessor method used to retrieve the review value.
     * @return - int value being the review value.
     */
    public int getReviewValue() {
        return reviewValue;
    }

    /**
     * Accessor method used to set the review value.
     * @param reviewValue - int value being the review value.
     */
    public void setReviewValue(int reviewValue) {
        this.reviewValue = reviewValue;
    }

    /**
     * Accessor method used to retireve the review text
     * @return - String value being the review text.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Accessor method used to set the review tex.
     * @param reviewText - String value being the review text.
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
