/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class represents an address being used by the Fast Courier Service application
 * @author rtucker
 */
public class Address implements ISubject, Serializable {
    
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postcode;
    
    private ISubject subjectDelegate;
    
    /**
     * Default constructor initialises all attributes to the string "UNKNOWN".
     * Required for serialisation
     */
    public Address(){
        this.subjectDelegate = new ISubjectImpl();
        this.addressLine1 = "UNKNOWN";
        this.addressLine2 = "UNKNOWN";
        this.city = "UNKNOWN";
        this.postcode = "UNKNOWN";
    }
    
    /**
     * Constructor that initialises the object with the provided address details
     * @param addrLine1 - String being line 1 of the address
     * @param addrLine2 - String being line 2 of the address
     * @param city - String being the city in which the address is located
     * @param postcode - String being the postcode / zip code of the address
     */
    public Address(String addrLine1, String addrLine2, String city, String postcode){
        this();
        this.addressLine1 = addrLine1;
        this.addressLine2 = addrLine2;
        this.city = city;
        this.postcode = postcode;
    }

    /**
     * Accessor method to retrieve the first line of the address
     * @return - String being the first line of the postal address
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Accessor method to set the first line of the address
     * @param addressLine1 - String being the first line of the postal address
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the second line of the address
     * @return - String being the second line of the postal address
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Accessor method to set the second line of the address
     * @param addressLine2 - String being the second line of the postal address
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the city from the address
     * @return - String being the city in which the address is located
     */
    public String getCity() {
        return city;
    }

    /**
     * Accessor method to set the city in which the address is located
     * @param city - String being the city in which the address is located
     */
    public void setCity(String city) {
        this.city = city;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the postcode of the address
     * @return - String being the postcode of the address
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Accessor method to set the postcode of the address
     * @param postcode - String being the postcode of the address
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
        this.subjectDelegate.notifyObservers();
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
    public String toString() {
        //Using a StringBuilder it is more memory efficient than concatenating strings with "+"
        //This is because strings are "immutable objects" so building up a string using "+"
        //means allocating memory for a whole new object each time we add to the string.
        //StringBuilder uses a "mutable array of characters" allocating memory for 
        //only one object.
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(this.addressLine1);
        resultBuilder.append(", ");
        if(null != this.addressLine2 && !this.addressLine2.isEmpty()){
            resultBuilder.append(this.addressLine2);
            resultBuilder.append(", ");
        }
        if(null != this.city && !this.city.isEmpty()){
            resultBuilder.append(this.city);
            resultBuilder.append(", ");
        }
        if(null != this.postcode && !this.postcode.isEmpty()){
            resultBuilder.append(this.postcode);
        }
        return resultBuilder.toString();
    }
}
