/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.sql.Date;

import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;





/**
 * This class represents a Transaction.
 * @author PRCSA
 */
public class Transaction implements ISubject, IObserver{
    	
        private int transaction_id;
	private int credits_paid;
	private int payeeNo;
	private int payerNo; 
	private Date date_completed;
        private int advertId;
        private ISubject subjectDelegate;

    /**
     * Default constructor initialising all the variables.
     */
    public Transaction()
    {
         this.transaction_id = 0;
         this.credits_paid = 0;
         this.payeeNo = 0;
         this.payerNo = 0;
         this.advertId =0 ;
         subjectDelegate = new ISubjectImpl();
    }


    /**
     * Default constructor assigning values to the variables.
     * @param transactionNo - int value being the unique transaction number.
     * @param advert_id  - int value being the advert id associated with the transaction.
     * @param payee - int value being the identifier of the Member.
     * @param payer - int value being the identifier of the Member.
     * @param paid - int value being the units paid.
     * @param completed - Date value being the date the transaction was completed on.
     */
    public Transaction(int transactionNo,int advert_id, int payee, int payer, int paid, Date completed)
    {
        this();
        this.transaction_id = transactionNo;
        this.payeeNo = payee;
        this.payerNo = payer;
        this.credits_paid = paid;
        this.date_completed = completed;
        this.advertId = advert_id;
    }

    /**
     * Accessor method used to retrieve the Transaction ID.
     * @return - int value being the Transaction ID.
     */
    public int getTransaction_id() {
        return transaction_id;
    }

    /**
     * Accessor method used to set the Transaction ID.
     * @param transaction_id - int value being the Transaction ID.
     */
    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method used to retrieve the amount of credits paid.
     * @return - int value being the amount of credits paid.
     */
    public int getCredits_paid() {
        return credits_paid;
    }

    /**
     * Accessor method used to set the amount of credits paid.
     * @param credits_paid
     */
    public void setCredits_paid(int credits_paid) {
        this.credits_paid = credits_paid;
        this.subjectDelegate.notifyObservers();
    }

    /**
     *Accessor method used to retrieve the Member ID for the Payee
     * @return - int value being the unique Member ID.
     */
    public int getPayeeNo() {
        return payeeNo;
    }

    /**
     * Accessor method used to set the Member ID for the Payee.
     * @param payeeNo - int value being the unique Member ID.
     */
    public void setPayeeNo(int payeeNo) {
        this.payeeNo = payeeNo;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method used to retrieve the Member ID for the Payer.
     * @return - int value being the unique Member ID.
     */
    public int getPayerNo() {
        return payerNo;
    }

    /**
     * Accessor method to set the Member ID for the Payer.
     * @param payerNo - int value being the unique Member ID.
     */
    public void setPayerNo(int payerNo) {
        this.payerNo = payerNo;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the Date the transaction was completed.
     * @return - Date value being the date the transaction was completed.
     */
    public Date getDate_completed() {
        return date_completed;
    }

    /**
     * Accessor method to set the Date the transaction was completed.
     * @param date_completed - Date value being the date the transaction was completed.
     */
    public void setDate_completed(Date date_completed) {
        this.date_completed = date_completed;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the unique Advert ID
     * @return - int value being the unique Advert ID.
     */
    public int getAdvertId() {
        return advertId;
    }

    /**
     * Accessor method to set the unique Advert ID.
     * @param advertId - int value being the unique Advert ID.
     */
    public void setAdvertId(int advertId) {
        this.advertId = advertId;
        this.subjectDelegate.notifyObservers();
    }

    /* Comments for this section is in the Observer Pattern library */
    
    
    @Override
    public Boolean registerObserver(IObserver o) {
        return this.subjectDelegate.registerObserver(o);
    }


    @Override
    public Boolean removeObserver(IObserver o) {
        return this.subjectDelegate.removeObserver(o);
    }


    @Override
    public void notifyObservers() {
         this.subjectDelegate.notifyObservers();
    }

    @Override
    public void update() {
        this.notifyObservers();
    }
}
