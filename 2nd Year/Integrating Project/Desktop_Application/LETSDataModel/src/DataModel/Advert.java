/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;



import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;
import java.sql.Blob;


/**
 *  This class represents an advert associated to a Member being used by the LETS system.
 * @author PRCSA
 */
public class Advert implements ISubject, IObserver{

    private String isActive;
    private BidList offers;

   
    private String adTitle;
    private String adDescription;
    
    private java.sql.Date adPosted;
    private java.sql.Date adExpires;
    private java.sql.Date adCompleted;
   
    private int adCost;
    
   
    private String transportInc;
    private String tags;
    
    private int memberID;
    private int advertNo;
    private int catID;
    private int adTypeID;
    private int itemTypeID;
    
    private Blob image;
    
    private ISubject subjectDelegate;
    
    /**
     * Default constructor initialises all attributes to the string "UNKNOWN".
     * All integers have been initialised to 0 and dates have just been initialised.
     */
    public  Advert()
    {
        this.adCompleted = new java.sql.Date(0);
        this.adExpires = new java.sql.Date(0);
        this.adPosted = new java.sql.Date(0);
        this.memberID = 0;
        this.advertNo = 0;
        this.adCost = 0;

        this.tags = "UNKNOWN";
        this.adTitle = "UKNOWN";
        this.adDescription = "UNKNOWN";
        this.isActive = "UKNOWN";
        
        this.subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Constructor that initialises the advert with the provided advert details.
     * This information is gathered from the database.
     * 
     * @param advertID - int value to store the unique value of the Advert.
     * @param advertTypeID - int value to correspond to the relevant Advert Type.
     * @param memberID - int value to correspond to the relevant Member.
     * @param categoryID - int value to correspond to the relevant Category.
     * @param itemTypeID - int value to correspond to the relevant Item Type.
     * @param advertTitle - String being the title of the advert.
     * @param advertDescription - String being the description of the advert.
     * @param createdDate - SQL date being the date the advert was created.
     * @param expiryDate - SQL date being the date the advert expires.
     * @param cost - int value stating the cost associated with the advert.
     * @param trans - String value stating a Y or N for yes or no, in response to the advert.
     * @param imageBlob - Blob value storing the image of the advert.
     * @param isActive - String value determining whether an advert is active or not.
     * @param completedDate  - SQL date being the date the advert has been completed.
     */
    public Advert(int advertID, int advertTypeID, int memberID, int categoryID, int itemTypeID,
            String advertTitle,String advertDescription, java.sql.Date createdDate, java.sql.Date expiryDate, int cost, String trans,
           java.sql.Date completedDate ,java.sql.Blob imageBlob , String isActive )
    {
        this();
        this.adTitle = advertTitle;
        this.adDescription = advertDescription;
        this.memberID = memberID;
        this.adCost = cost;
        
        this.adExpires = expiryDate;
        this.adPosted = createdDate;
        

        this.transportInc = trans;
        this.catID = categoryID;
        this.adTypeID = advertTypeID;
        this.itemTypeID = itemTypeID;
        this.advertNo = advertID;
        this.isActive = isActive;
        this.image = imageBlob;
        
        this.adCompleted = completedDate;
    }

    /**
     * Accessor method to retrieve the Members ID associated with the advert.
     * @return - int value being the Members ID associated with the advert.
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Accessor method to set the Members ID to associate it with an advert.
     * @param memberID - int value being the Member ID to associate an advert with a member.
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the advert number.
     * @return - int value being the advert number to identify the advert.
     */
    public int getAdvertNo() {
        return advertNo;
    }

    /**
     * Accessor method to set the advert number
     * @param advertNo - int value being the advert number to identify the advert.
     */
    public void setAdvertNo(int advertNo) {
        this.advertNo = advertNo;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the offer history in relation to this advert.
     * @return - Offer History being the offer history related to this advert.
     */
    public BidList getOffers() {
        return offers;
    }

    /**
     * Accessor method to set the offer history related to this advert.
     * @param offers
     */
    public void setOffers(BidList offers) {
        this.offers = offers;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to get the advert title associated with this advert.
     * @return - String being the title of the advert.
     */
    public String getAdTitle() {
        return adTitle;
    }

    /**
     * Accessor method to set the advert title to the associated advert.
     * @param adTitle - String being the title of the advert.
     */
    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to get the description of the advert.
     * @return - String being the  description of the advert.
     */
    public String getAdDescription() {
        return adDescription;
    }

    /**
     * Accessor method to set the description of the advert.
     * @param adDescription - String being the description of the advert.
     */
    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the date the advert was posted.
     * @return - Date being the value the date was posted.
     */
    public java.sql.Date getAdPosted() {
        return adPosted;
    }

    /**
     * Accessor method to set the date the advert was posted.
     * @param adPosted - Date being the value the date was posted.
     */
    public void setAdPosted(java.sql.Date adPosted) {
        this.adPosted = adPosted;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the date the advert expires.
     * @return - Date being the value the date expires.
     */
    public java.sql.Date getAdExpires() {
        return adExpires;
    }

    /**
     * Accessor method to set the date the advert expires.
     * @param adExpires - Date value being the date the advert expires.
     */
    public void setAdExpires(java.sql.Date adExpires) {
        this.adExpires = adExpires;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the cost of the advert.
     * @return  - int value being the cost of the advert.
     */
    public int getAdCost() {
        return adCost;
    }

    /**
     * Accessor method to set the cost of the advert.
     * @param adCost - int value being the cost of the advert.
     */
    public void setAdCost(int adCost) {
        this.adCost = adCost;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the transport information of the advert.
     * @return - String being the transport information of the advert (Y or N).
     */
    public String getTransportInc() {
        return transportInc;
    }

    /** Accessor method to set the transport information of the advert.
     * @param transportInc - String being the transport information of the advert (Y or N).
     */
    public void setTransportInc(String transportInc) {
        this.transportInc = transportInc;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the tags of the advert
     * @return - String being the tags related to the advert.
     */
    public String getTags() {
        return tags;
    }

    /** 
     * Accessor method to set the tags of the advert.
     * @param tags - String being the tags related to the advert.
     */
    public void setTags(String tags) {
        this.tags = tags;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the image associated with the advert
     * @return - Blob value being the image.
     */
    public Blob getImage() {
        return image;
    }

    /**
     *  Accessor method to set the image to the advert
     * @param image - Blob value being the image related to the advert 
     */
    public void setImage(Blob image) {
        this.image = image;
        this.subjectDelegate.notifyObservers();
    }
    
    

    /**
     * Accessor method to retrieve the date the advert is completed.
     * @return - Date being the date the advert is completed.
     */
    public java.sql.Date getAdCompleted() {
        return adCompleted;
    }

    /**
     * Accessor method to set the date the advert is completed.
     * @param adCompleted - Date being the date the advert is completed.
     */
    public void setAdCompleted(java.sql.Date adCompleted) {
        this.adCompleted = adCompleted;
        this.subjectDelegate.notifyObservers();
    }
    
     /**
     * Accessor method to retrieve the category ID number.
     * @return - int value being the category ID number.
     */
    public int getCatID() {
        return catID;
    }

    /**
     * Accessor method to set the category ID number.
     * @param catID - int value being the category ID number.
     */
    public void setCatID(int catID) {
        this.catID = catID;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the advert ID number.
     * @return - int value being the advert ID number.
     */
    public int getAdTypeID() {
        return adTypeID;
    }

    /**
     * Accessor method to set the advert ID number.
     * @param adTypeID - int value being the advert ID number.
     */
    public void setAdTypeID(int adTypeID) {
        this.adTypeID = adTypeID;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the item type ID number.
     * @return - int value being the item type ID number.
     */
    public int getItemTypeID() {
        return itemTypeID;
    }

    /**
     * Accessor method to set the item type ID number. 
     * @param itemTypeID - int value being the item type ID number.
     */
    public void setItemTypeID(int itemTypeID) {
        this.itemTypeID = itemTypeID;
        this.subjectDelegate.notifyObservers();
    }


    
    /**
     * Accessor method to retrieve the active status of the advert.
     * @return - String being the status on activity for the advert.
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * Accessor method to set the active status of the advert.
     * @param isActive - String being the status on activity for the advert.
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
        this.subjectDelegate.notifyObservers();
    }

    // This has been commented in the associate library (Observer Pattern).
    
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
