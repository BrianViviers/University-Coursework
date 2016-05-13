package com.richardimms.www.android0303.DataModel;

/**
 * This class holds all of the information relating to a Review in the system
 * Created by Richard on 12/04/2015.
 */
public class Review {
    public Review() {
    }

    private Long reviewID;
    private Long transactionID;
    private int reviewValue;
    private String reviewText;

    /**
     * Accessor method used to retrieve the Review ID.
     * @return - Long value being the unique Review ID.
     */
    public Long getReviewID() {
        return reviewID;
    }

    /**
     * Accessor method used to assign the Review ID.
     * @param reviewID - Long value being the unique Review ID.
     */
    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    /**
     * Accessor method used to retrieve the Transaction ID.
     * @return - Long value being the Transactions ID.
     */
    public Long getTransactionID() {
        return transactionID;
    }

    /**
     * Accessor method used to assign the Transaction ID.
     * @param transactionID - Long value being the Transactions ID.
     */
    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * Accessor method used to retrieve the Review value.
     * @return - int value being the review value.
     */
    public int getReviewValue() {
        return reviewValue;
    }

    /**
     * Accessor method used to assign the review value of a review.
     * @param reviewValue - int value being the review value.
     */
    public void setReviewValue(int reviewValue) {
        this.reviewValue = reviewValue;
    }

    /**
     * Accessor method used to retrieve the review text.
     * @return - String value being the review text.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Accessor method used to assign the review text.
     * @param reviewText - String value being the review text.
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

}
