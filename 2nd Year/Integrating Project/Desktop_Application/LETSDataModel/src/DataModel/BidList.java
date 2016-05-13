/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.util.ArrayList;

/**
 * This class represents an Offer History.
 * @author PRCSA
 */
public class BidList {
    private ArrayList<Bid> bidList;
    private final Member owner;
    
    /**
     * Default Constructor initialising variables.
     */
    public BidList()
    {
        bidList = new ArrayList<>();
        owner = new Member();
    }
    
    /**
     *  Default constructor defining an owner of the offer history.
     * @param owner - Member value being the owner of the offer history.
     */
    public BidList(Member owner)
    {
        this.owner = owner;
    }
    
    /**
     *
     * @param newBid
     */
    public void addBid(Bid newBid) {
        if (null != newBid) {
            if (null == bidList) {
                bidList = new ArrayList<>();
            }
            bidList.add(newBid);
        }
    }

    
    /**
     * Method used to retrieve an offer at specific index.
     * @param index - int index being the index in the ArrayList.
     * @return - Offer value at the index given.
     */
    public Bid getBidAt(int index)
    {
        Bid returnOffer = null;
        if(bidList != null)
        {
            returnOffer = bidList.get(index);
        }
        return returnOffer;
    }
    
    /**
     * Method used to retrieve the number of offers.
     * @return - int value being the size of the offer list.
     */
    public int getNoBids()
    { 
        int size = 0;
        if(bidList.size() >= 0)
        {
         size =  bidList.size();
        }
        return size;
    }
    
    /**
     * Method used to retrieve the index based off an Offer.
     * @param bid - Offer being the offer to search for.
     * @return - int value being the index the offer is at.
     */
    public int getIndexOfBid(Bid bid)
    {   int indexOfOffer = -1;
        
        if(this.bidList.size() >= 0)
        {
            indexOfOffer = this.bidList.indexOf(bid);
        }
        return indexOfOffer;
    }
    
    /**
     *
     */
    public void emptyBidsList()
    {
        this.bidList.clear();
    }

}
