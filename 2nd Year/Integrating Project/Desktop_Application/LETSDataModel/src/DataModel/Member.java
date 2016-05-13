/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.sql.Date;
import java.util.ArrayList;
import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;

/**
 * This class represents a Member of the LETS System.
 *
 * @author PRCSA
 */
public class Member extends AbstractPerson implements ISubject, IObserver {

    private int memberID;
    private Address memberAddress;

    private int balance;
    private String isActive;

    private TransactionList transactionHistory;
    private AdvertList advertHistory;

    private final ISubject subjectDelegate;

    /**
     * Default constructor used to initialise all variables.
     */
    public Member() {
        super();
        this.subjectDelegate = new ISubjectImpl();
        Address userAddress = new Address();
        this.advertHistory = new AdvertList(this);
        this.transactionHistory = new TransactionList(this);
        this.setMemberAddress(userAddress);
    }

    /**
     * Default constructor used to associate the appropriate information with
     * the appropriate variables.
     *
     * @param memberID - int value being the unique ID of the Member.
     * @param fName - String value being the forename of the Member.
     * @param sName - String value being the surname of the Member.
     * @param dOB - Date value being the date of borth of the Member.
     * @param email - String value being the email address of the Member.
     * @param contactNo - String value being the contact number of the Member.
     * @param defaultAdd - Address value being the address of the Member.
     * @param isActive - String value showing the status of the Member (active
     * or inactive).
     */
    public Member(int memberID, String fName, String sName, Date dOB, String email,
            String contactNo, Address defaultAdd, String isActive) {
        super(fName,sName,email,contactNo,dOB);
        this.subjectDelegate = new ISubjectImpl();
        Address userAddress = new Address();
        this.advertHistory = new AdvertList(this);
        this.transactionHistory = new TransactionList(this);
        this.setMemberAddress(userAddress);
        this.memberAddress = defaultAdd;
        this.memberID = memberID;
        this.isActive = isActive;
    }

    /**
     * Accessor method used to retrieve the Members ID.
     *
     * @return - int value being the Member ID.
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Accessor method use to set the Members ID.
     *
     * @param memberNo - int value being the Member ID.
     */
    public void setMemberID(int memberNo) {
        this.memberID = memberNo;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method used to retrieve the Members address.
     *
     * @return - Address value being the Members address.
     */
    public Address getMemberAddress() {
        return memberAddress;
    }

    /**
     * Accessor method used to set the Members address.
     *
     * @param memberAddress - Address value being the Members Address.
     */
    public void setMemberAddress(Address memberAddress) {
        //This may not be right
        if (null != this.memberAddress) {
            this.memberAddress.removeObserver(this);
        }
        this.memberAddress = memberAddress;
        if (null != this.memberAddress) {
            this.memberAddress.registerObserver(this);
        }
        this.subjectDelegate.notifyObservers();
    }



    /**
     * Accessor method used to retrieve the Members balance.
     *
     * @return - int value being the Members balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Accessor method used to set the Members balance.
     *
     * @param balance - int value being the Members balance.
     */
    public void setBalance(int balance) {
        this.balance = balance;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method used to retrieve the Members transaction history.
     *
     * @return - Transaction History value being the Members transaction
     * history.
     */
    public TransactionList getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Accessor method used to set the Members transaction history.
     *
     * @param transactionHistory - Transaction History value being the Members
     * transaction history.
     */
    public void setTransactionHistory(TransactionList transactionHistory) {
        this.transactionHistory = transactionHistory;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method used to retrieve the Members advert history.
     *
     * @return - AdvertList value of the Members adverts.
     */
    public AdvertList getAdvertHistory() {
        return advertHistory;
    }

    /**
     * Accessor method used to set the Members advert history.
     *
     * @param advertHistory - AdvertList value being the list of the Members
     * adverts.
     */
    public void setAdvertHistory(AdvertList advertHistory) {
        this.advertHistory = advertHistory;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Method used to get an advert associated with a Member.
     *
     * @param index - int value being the index to search the Members advert
     * history.
     * @return - Advert value being the advert at the specified index.
     */
    public Advert getAdvertAt(int index) {
        if (advertHistory.totalAmountOfAdverts() == 0) {
            return null;
        } else {
            Advert rtnAdv = advertHistory.getAdvertAtIndex(index);
            return rtnAdv;
        }
    }

    /**
     * Accessor method used to retrieve the status of the Member.
     *
     * @return - String value being the active status of the Member.
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * Accesor method used to set the status of the Member.
     *
     * @param isActive - String value being the active status of the Member.
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Method used to get the index of an advert.
     *
     * @param ad - Advert value being the advert to search for
     * @return - int value being the index the advert is at.
     */
    public int getIndexOfAdvert(Advert ad) {
        return advertHistory.getIndexOfAdvert(ad);
    }

    /**
     * Method used to get all live adverts.
     *
     * @return - An ArrayList of the live adverts associated with this Member.
     */
    public ArrayList<Advert> getLiveAdverts() {
        return advertHistory.getLiveAdverts();
    }

    /**
     * Method used to get all completed adverts.
     *
     * @return - ArrayList of the completed adverts associated with this Member.
     */
    public ArrayList<Advert> getCompletedAdverts() {
        return advertHistory.getCompletedAdvert();
    }

    /**
     * Method used to get the number of adverts.
     *
     * @return - int value being the number of adverts.
     */
    public int getNoAdverts() {
        if (advertHistory == null) {
            return 0;
        } else {
            return advertHistory.totalAmountOfAdverts();
        }
    }

    /**
     * Method used to get a transaction from an index.
     *
     * @param index - int value being the index of the transaction.
     * @return - Transaction at the index being searched.
     */
    public Transaction getTransactionAt(int index) {
        Transaction transaction = null;
        if (this.transactionHistory != null) {
            transaction = transactionHistory.getTransAt(index);
        }

        return transaction;
    }

    /**
     * Method used to get the index of a specific transaction.
     *
     * @param ta - Transaction value to search for.
     * @return - int value being the index of the transaction.
     */
    public int getIndexOfTransaction(Transaction ta) {
        return transactionHistory.getIndexOfTransaction(ta);
    }

    /**
     * Method used to get the number of transactions.
     *
     * @return - int value being the number of transactions.
     */
    public int getNoTransactions() {
        return transactionHistory.getNoTransactions();
    }

    /**
     * Method used to add an advert to a Member.
     * @param ad - Advert value being the advert to add.
     */
    public void addAdvert(Advert ad) {
        this.advertHistory.addAdvert(ad);
    }

    /*This has been commented in the Observer pattern library.*/

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
    /****************************************************************/
}
