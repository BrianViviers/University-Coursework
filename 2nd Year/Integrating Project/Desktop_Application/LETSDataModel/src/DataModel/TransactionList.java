/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.util.ArrayList;

import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;

/**
 * This class represents a Transaction List. Storing the transactions that have happened.
 * @author PRCSA
 */
public class TransactionList implements ISubject,IObserver{
   
     private ArrayList<Transaction> transactionList;
     private ISubject subjectDelegate;
     private Member member;
    
    /**
     * Default constructor used to initialise variables.
     */
    public TransactionList() {
        
        this.transactionList = new ArrayList<>();
        subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Constructor used to assign a member as an owner of the transaction.
     * @param owner
     */
    public TransactionList(Member owner)
    {
        this();
        this.member = owner;
        this.registerObserver(member);
    }
     
    /**
     *  Method used to add a new transaction
     * @param newTrans - new transaction to ad..
     */
    public void addTransaction(Transaction newTrans) {
        transactionList.add(newTrans);
        newTrans.registerObserver(this);
        this.notifyObservers();
    }
    
    /**
     * Method used to get the index of a transaction.
     * @param ta - Transaction to search for.
     * @return - int value being the index the transaction was found at.
     */
    public int getIndexOfTransaction(Transaction ta)
    {
        return transactionList.indexOf(ta);
    }
    
    /**
     * Method used to a transaction based on it's index.
     * @param index - int value being the index to search for.
     * @return - Transaction found at the index.
     */
    public Transaction getTransAt(int index) {
            return transactionList.get(index);
    }
    
    /**
     * Method to return the number of transactions.
     * @return - int value being the number of transactions.
     */
    public int getNoTransactions()
    {
        return this.transactionList.size();
    }
    
    /**
     * Method used to get a transaction based on a transaction ID.
     * @param id - int value being the ID of the transaction.
     * @return - Transaction associated with the ID.
     */
    public Transaction getTransAtID(int id)
    {
        Transaction selectTrans = new Transaction();
        for (Transaction transactionList1 : transactionList) {
            if (transactionList1.getTransaction_id() == id) {
                selectTrans = transactionList1;
            }
        } 
       return selectTrans;
    }
    
    /**
     * Method used to clear the trnasaction list.
     */
    public void clear()
    {   
        if(transactionList.size() > 0 )
        {
            this.transactionList.clear();
        }
    }


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
