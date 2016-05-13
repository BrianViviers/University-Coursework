/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import datamodel.Delivery;
import datamodel.DeliveryStatus;
import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;

/**
 * This interface represents the "abstract concept" of a delivery record for a customer.
 * A delivery record is simply a list of all fast courier delivers made on behalf
 * of a customer.
 * @author rtucker
 */
public interface IDeliveryRecord extends ISubject, IObserver {
    
    /**
     * This method adds a new delivery to the list of deliveries being managed 
     * by this delivery record. The cost per kg delivered should be set to the
     * customers default cost
     * @param newDelivery - The Delivery object to add to this delivery records list.
     * @return - boolean True if the delivery was added to the record, false otherwise.
     */
    boolean addDelivery(Delivery newDelivery);
    
    /**
     * This method adds a new delivery to the list of deliveries being managed 
     * by this delivery record. The cost per kg delivered should be set to the
     * value provided by the costPerKg parameter
     * @param newDelivery - The Delivery object to add to this delivery records list.
     * @param costPerKg - The cost per a kg delivered to be used when pricing this
     * delivery
     * @return - boolean True if the delivery was added to the record, false otherwise.
     */
    boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg);
    
    
    /**
     * This method removes the delivery given in as a parameter from
     * the customers delivery record. 
     * @param index - The index at which the delivery should be removed.
     * @return - Boolean True if the delivery was removed , false otherwise.
     */
    Delivery removeDeliveryAt(int index);
    
    /**
     * Deliveries are stored in the order they are added. This method retrieves 
     * the delivery specified by the given zero based index number
     * @param index - The zero based index number for the required delivery.
     * For example the first delivery added to the record may be retrieved by
     * using an index of 0, the second by and index of 1 etc.
     * @return - The retrieved Delivery object or NULL if no object was found.
     * @throws IndexOutOfBoundsException - if the value of the index parameter is 
     * negative or greater than or equal to the value returned by a call to the
     * getNoOfDeliveries() method
     */
    Delivery getDeliveryAt(int index) throws IndexOutOfBoundsException;
    
    /**
     * This method changes the delivery status for a specified delivery
     * @param index - The zero based index number for the delivery to update.
     * @param newStatus - A DeliveryStatus enumerated value which the specified
     * deliveries status should be changed to.
     * @throws IndexOutOfBoundsException - if the value of the index parameter is 
     * negative or greater than or equal to the value returned by a call to the
     * getNoOfDeliveries() method
     */
    void updateDeliveryStatus(int index, DeliveryStatus newStatus) throws IndexOutOfBoundsException;
    
    /**
     * This method retrieves an array list of delivery objects stored in this delivery
     * that have a delivery status matching the provided status parameter.
     * @param status - A DeliveryStatus enumerated value, the list of all deliveries
     * stored by this delivery record will be filtered so that the returned list
     * only includes deliveries with a delivery status that matches this value.
     * @return An ArrayList of delivery objects stored in this delivery record 
     * with a delivery status that matches the status parameter
     */
    ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status);
    
    /**
     * This method removes all deliveries stored in this delivery record. After
     * this method completes a call to getNoOfDeliveries() is guaranteed to 
     * return a value of zero (0).
     */
    void clearAll();
    
    /**
     * This method retrieves the total number of delivery objects stored by this
     * delivery record
     * @return An int being the total number of delivery objects stored in this
     * delivery record.
     */
    int getNoOfDeliveries();
    
    /**
     * This method tests to determine if the specified delivery object is stored
     * by this delivery record. If so the deliveries zero based index number is 
     * retrieved. Otherwise a value of -1 is returned.
     * @param aDelivery - A delivery object for which the index number is required
     * @return - An int being the deliveries zero based index number or -1 if the
     * delivery record does not hold the delivery
     */
    int getIndexOfDelivery(Delivery aDelivery);
}
