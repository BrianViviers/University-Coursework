/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.ArrayList;

/**
 *
 * @author PRCSA
 */
public class ReviewList {

    private ArrayList<Review> reviewArrayList = new ArrayList();

    /**
     * Method used to add a review to the list of reviews.
     * @param review - Review value being the review to add to the list.
     */
    public void addReview(Review review) {
        if (review == null) {
            reviewArrayList = new ArrayList();
        }
        if (reviewArrayList != null) {

            reviewArrayList.add(review);
        }
    }
    
    /**
     * Method used to clear the list of the reviews.
     */
    public void clear() {
        if (reviewArrayList != null) {
            this.reviewArrayList.clear();
        }
    }
    
    /**
     * Method used to get a review at a specified index.
     * @param index - int value being the index to search at
     * @return - Review value being the identified review.
     */
    public Review getReviewAt(int index) {
        Review review = null;
        if (reviewArrayList != null) {
            review = reviewArrayList.get(index);
        }
        return review;
    }
    
    /**
     * Method used to return a review based on it's transaction ID
     * @param transactionID - int value being the transaction ID to search for
     * @return - Review value being the review identified.
     */
    public Review getReviewByTransactionID(int transactionID)
    {
        Review review = new Review();
        for (Review reviewList1 : reviewArrayList) {
            if (reviewList1.getTransactionID() == transactionID) {
                review = reviewList1;
            }
        }
        return review;
    }
}
