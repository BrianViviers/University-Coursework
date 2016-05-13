/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;


/**
 * This class represent an advert type, this is either an Offer or Request. 
 * @author rimms
 */
public class AdvertType {
    
    
    private int advertType_id;
    private String advertTypeName;
    
    /**
     * Default constructor initialising variables to 0;
     */
    public AdvertType()
    {
        advertType_id = 0;
        advertTypeName = "";
    }
    
    /**
     * Default constructor assigning correct values to the variables.
     * @param id  - int value representing the id of the advert type.
     * @param type - String value representing the advert type.
     */
    public AdvertType(int id, String type)
    {
        this.advertType_id = id;
        this.advertTypeName = type;
    }

    /**
     * Accessor method to retrieve the advert type ID
     * @return - int being the advert type ID.
     */
    public int getAdvertType_id() {
        return advertType_id;
    }

    /**
     * Accessor method to set the advert type ID.
     * @param advertType_id - int being the advert type ID.
     */
    public void setAdvertType_id(int advertType_id) {
        this.advertType_id = advertType_id;
    }

    /**
     * Accessor method to retrieve the advert type descriptor.
     * @return - String value being the advert type.
     */
    public String getAdvertType() {
        return advertTypeName;
    }

    /**
     * Accessor method to set the advert type descriptor.
     * @param advertType - String value being the advert type.
     */
    public void setAdvertType(String advertType) {
        this.advertTypeName = advertType;
    }
    
    
}
