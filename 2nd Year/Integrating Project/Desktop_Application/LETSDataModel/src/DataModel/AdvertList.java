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
 * This class represents an AdvertList that stores adverts.
 * @author PRCSA
 */
public class AdvertList implements ISubject,IObserver {
    
    private ArrayList<Advert> advertList;
    private Member member;
    
    private ISubject subjectDelegate;
    
    /**
     * Default constructor to initialise variables.
     */
    public AdvertList()
    {
        this.advertList = new ArrayList<>();
        this.subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Default constructor to initialise variables.
     * Setting the member as an owner of this to work alongside the observer pattern.
     * @param owner -  Owner is the registered observer of the list of adverts.
     */
    public AdvertList(Member owner)
    {
        this();
        this.member = owner;
        this.registerObserver(this.member);
        
    }
    
    /**
     * This method adds a new customer to the list of adverts that have
     * been listed on the LETS system.
     * @param newAdvert
     */
    public void addAdvert(Advert newAdvert) {
         if(null != newAdvert){
            if(null == this.advertList){
                this.advertList = new ArrayList<>();
            }
           
               if(this.advertList.add(newAdvert))
               {
                newAdvert.registerObserver(this);
                this.notifyObservers();
               }
        }
    }
        
    /**
     * This method returns an advert based on it's index.
     * @param index - int value to search the ArrayList.
     * @return - Advert being the advert at the specified index.
     */
    public Advert getAdvertAtIndex(int index)
    {
        if(advertList.isEmpty())
        {
            return null;
        }
        else
        {
            Advert returnAdv = advertList.get(index);
            return returnAdv;
        }
    }
    
    /**
     * Method to get the index of a specific advert.
     * @param ad - The advert to search for.
     * @return - The index of the advert.
     */
    public int getIndexOfAdvert(Advert ad)
    {
        return this.advertList.lastIndexOf(ad);
    }
    
    /**
     * Method to return an ArrayList<> of all the live adverts.
     * @return - An ArrayList<> of the live adverts/
     */
    public ArrayList<Advert> getLiveAdverts()
    {
        ArrayList<Advert> liveAds = null;
        for (Advert advertList1 : advertList) {
            if (advertList1.getAdCompleted() == null) {
                liveAds = new ArrayList<>();
                liveAds.add(advertList1);
            }
        }
       return liveAds;
    }
    
    /**
     * Method to return all completed adverts.
     * @return - An ArrayList<> of the completed adverts.
     */
    public ArrayList<Advert> getCompletedAdvert()
    {
       ArrayList<Advert> completedAds = null;
        for (Advert advertList1 : advertList) {
            if (advertList1.getAdCompleted() != null) {
                completedAds = new ArrayList<>();
                completedAds.add(advertList1);
            }
        }
       return completedAds;
    } 
    
    /**
     * Method to return the size of the ArrayList<>
     * @return - int value being the size of the ArrayList<>
     */
    public int totalAmountOfAdverts()
    {
       return advertList.size();
    }
    
    /**
     * Method to return an advert based on the Advert ID (not the index in the array).
     * @param advertId - int value of the Advert ID.
     * @return - An advert based on the advert ID.
     */
    public Advert getAdvertById(int advertId)
    {
        Advert ad = new Advert();
        for (Advert advertList1 : advertList) {
            if (advertList1.getAdvertNo() == advertId) {
                ad = advertList1;
            }
        }
        
        return ad;
    }   
    
    /**
     * Clears the advert list.
     */
    public void clearAdvertList()
    {
        if(this.advertList.size() > 0)
        {
        this.advertList.clear();
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
