/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;

/**
 *  This class represents an address of a Member being used by the LETS system.
 * @author PRCSA
 */
public class Address implements ISubject {
        
        private String address_1;
	private String address_2;
	private String city;
	private String postcode;
        
        private ISubject subjectDelegate;

    /**
     * Default constructor initialises all attributes to the string "UNKNOWN".
     */
    public Address()
    {
        this.subjectDelegate = new ISubjectImpl();
        this.address_1 = "UNKNOWN";
        this.address_2 = "UNKNOWN";
        this.city = "UNKNOWN";
        this.postcode = "UNKNOWN";
    }
    
    /**
     * Constructor that initialises the object with the provided address details
     * @param addrLine1 - String being line 1 of the address
     * @param addrLine2 - String being line 2 of the address
     * @param city - String being the city of the address
     * @param postcode - String being the postcode of the address.
     */
    public Address(String addrLine1, String addrLine2, String city, String postcode)
    {
        this();
        this.address_1 = addrLine1;
        this.address_2 = addrLine2;
        this.city = city;
        this.postcode = postcode;
    }
    
    /**
     * Accessor method to retrieve the first line of address
     * @return - String being the first line of the Members address
     */
    public String getAddress_1() {
        return address_1;
    }

    /**
     * Accessor method to set the first line of the address
     * @param address_1 - String being the first line of the Members address
     */
    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the second line of the address.
     * @return - String being the second line of the Members address.
     */
    public String getAddress_2() {
        return address_2;
    }

    /**
     * Accessor method to set the second line of the address
     * @param address_2 - String being the second line of the Members address.
     */
    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to retrieve the city from the address.
     * @return - String being the city of the Members address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Accesor method to set the city in which the address is located.
     * @param city - String being the city of the Members address.
     */
    public void setCity(String city) {
        this.city = city;
        this.subjectDelegate.notifyObservers();
    }

    /**
     * Accessor method to get the postcode from the address.
     * @return - String being the postcode of the Members address
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Accessor method to set the postcode of the address
     * @param postcode - String being the postcode of the address.
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
        this.subjectDelegate.notifyObservers();
    }


    // This has been commented in the associated library (Observer Pattern).

    /**
     *
     * @param o
     * @return
     */
        @Override
    public Boolean registerObserver(IObserver o) {
        return this.subjectDelegate.registerObserver(o);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public Boolean removeObserver(IObserver o) {
        return this.subjectDelegate.removeObserver(o);
    }

    /**
     *
     */
    @Override
    public void notifyObservers() {
        this.subjectDelegate.notifyObservers();
    } 
    
    
    /* Overriden toString method. Use Stringbuilder as it uses less memory than
       concantenating a string.
    */
    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(this.address_1);
        resultBuilder.append(", ");
        if(null != this.address_2 && !this.address_2.isEmpty()){
            resultBuilder.append(this.address_2);
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
