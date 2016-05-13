/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.interfaces;

import datamodel.Delivery;
import datamodel.DeliveryStatus;
import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;

/**
 * This interface represents the abstract concept of a record of all 
 * the consignments (deliveries) shipped by a customer. 
 * @author Brian Viviers
 */
public interface IDeliveryRecord extends IObserver, ISubject{

    /**
     * Adds a new delivery (consignment) to the list of deliveries for a customer.
     * @param newDelivery - Delivery object containing all details of a new delivery to add.
     * @return - Boolean True if delivery successfully added to the 
     * list of deliveries for a customer, False otherwise.
     */
    boolean addDelivery(Delivery newDelivery);

    /**
     * Adds a new delivery (consignment) to the list of deliveries for a 
     * customer AND sets the delivery cost per KG to the provided value.
     * @param newDelivery - Delivery object containing all details of a new delivery.
     * @param costPerKg - Double holding the new cost per KG of the delivery.
     * @return - Boolean True if delivery successfully added to the 
     * list of deliveries for a customer.
     */
    boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg);

    /**
     * This method clears all delivery entries in this record.
     */
    void clearAll();

    /**
     * This method retrieves a list of Delivery objects which have 
     * the specified delivery status.
     * @param status - DeliveryStatus enumeration value holding 
     * the specified delivery status.
     * @return - ArrayList of Delivery objects that have 
     * the specified delivery status. 
     */
    ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status);

    /**
     * Retrieves a specific delivery from the records list of Delivery objects.
     * @param index - Integer being the specific index at which to retrieve a delivery.
     * @return - Delivery object holding the delivery from the specified index.
     */
    Delivery getDeliveryAt(int index);

    /**
     * Given a Delivery object this method retrieves the index value for the 
     * object in this record. If the delivery is not included in this 
     * records list then -1 is returned.
     * @param aDelivery - Delivery object of which the index is unknown.
     * @return - Integer holding the index of the given Delivery object.
     */
    int getIndexOfDelivery(Delivery aDelivery);

    /**
     * Retrieves the total number of deliveries currently 
     * included in this records delivery list.
     * @return - Integer holding the total number of deliveries 
     * in this records delivery list.
     */
    int getNoOfDeliveries();

    /**
     * Given the index for a delivery and a delivery status this method 
     * updates the delivery at the given index to the given status.
     * @param index - Integer being the index of a delivery to update.
     * @param newStatus - DeliveryStatus enumeration value being the 
     * new status of the delivery.
     */
    void updateDeliveryStatus(int index, DeliveryStatus newStatus);
}
