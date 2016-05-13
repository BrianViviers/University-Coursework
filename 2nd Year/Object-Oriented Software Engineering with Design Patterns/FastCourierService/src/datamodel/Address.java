
package datamodel;

import java.io.Serializable;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class represents an address used in the Fast Courier Service application.
 * @author Brian Viviers
 */
public class Address implements ISubject, Serializable{
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postcode;
    private ISubject iSubjectImpl;
    
    /**
     * Default constructor required for serialisation
     * that initialises all attributes to UNKNOWN.
     */
    public Address(){
        addressLine1 = "UNKNOWN";
        addressLine2 = "UNKNOWN";
        city = "UNKNOWN";
        postcode = "UNKNOWN";
        iSubjectImpl = new ISubjectImpl();
    }
    
    /**
     * Constructor initialising addressLine1, addressLine2, city and postcode of the address.
     * @param addrLine1 - String being the first line of the address.
     * @param addrLine2 - String being the second line of the address.
     * @param city - String being the name of the city in which the address is located.
     * @param postcode - String being the address’s postal / zip code.
     */
    public Address(String addrLine1, String addrLine2, String city, String postcode){
        this();
        if (checkValid(addrLine1, "first line"))
            this.addressLine1 = addrLine1;
        
        this.addressLine2 = addrLine2;
        
        if (checkValid(city, "city name")) {
            if (city.matches("[a-zA-Z]+"))
                this.city = city;
            else
                System.out.println("The city should contain only letters.");
        }   
        
        if (checkValid(postcode, "post code"))
            this.postcode = postcode;
    }
    
    private Boolean checkValid(String toCheck, String message) { 
        if (toCheck != null && !toCheck.isEmpty()) {
            return true;
        } else {
            System.out.println("Failed to set the " + message
                    + " of the address due to it being null or empty.");
        }
        return false;
    }
    
    @Override
    public String toString() {
        return getAddressLine1() + ", " + getAddressLine2() + ", " + getCity() + ", " + getPostcode();
        
    }

    /**
     * Accessor method to retrieve the first line of the address.
     * @return - String holding the first line of the address.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Accessor method to store the first line of the address.
     * @param addressLine1 - String being the value to store as the first 
     * line of the address. Cannot be null or empty.
     */
    public void setAddressLine1(String addressLine1) {
        if (addressLine1 != null && !addressLine1.isEmpty()){
            this.addressLine1 = addressLine1;
            notifyObservers();
        } else {
            System.out.println("Failed to set the first line of the address "
                    + "due to it being null or empty.");
        }
    }

    /**
     * Accessor method to retrieve the second line of the address.
     * @return - String holding the second line of the address.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Accessor method to store the second line of the address.
     * @param addressLine2 - String being the value to store as the second line of the address.
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        notifyObservers();        
    }

    /**
     * Accessor method to retrieve the name of the city in which the address is located.
     * @return - String holding the name of the city in which the address is located.
     */
    public String getCity() {
        return city;
    }

    /**
     * Accessor method to store the name of the city in which the address is located. 
     * When changed all observers are notified.
     * @param city - String being the value to store as the 
     * name of the city in which the address is located. The city can
     * contain only letters and cannot be null or empty.
     */
    public void setCity(String city) {
        if (city != null && !city.isEmpty()) {
            if (city.matches("[a-zA-Z]+")) {
                this.city = city;
                notifyObservers();
            } else {
                System.out.println("The city should contain only letters.");
            }
        } else {
            System.out.println("Failed to set the city name of the address "
                    + "due to it being null or empty.");
        }
    }

    /**
     * Accessor method to retrieve the address’s postal code.
     * @return - A String holding the address’s postal code.
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Accessor method to store the address’s postal code.
     * When changed all observers are notified.
     * @param postcode - String being the value to store as address’s 
     * postal / zip code. Cannot be null or empty.
     */
    public void setPostcode(String postcode) {
        if (postcode != null && !postcode.isEmpty()) {
            this.postcode = postcode;
            notifyObservers();
        } else {
            System.out.println("Failed to set the postcode of the address "
                    + "due to it being null or empty.");
        }
    }

    @Override
    public void notifyObservers() {
        this.iSubjectImpl.notifyObservers();
    }

    @Override
    public Boolean registerObserver(IObserver o) {
        return this.iSubjectImpl.registerObserver(o);
    }

    @Override
    public Boolean removeObserver(IObserver o) {
        return this.iSubjectImpl.removeObserver(o);
    }
}
