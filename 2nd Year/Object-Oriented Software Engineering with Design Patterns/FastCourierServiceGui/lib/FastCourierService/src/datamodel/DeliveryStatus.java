/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

/**
 * This enumeration describes the possible states that a delivery can be in as 
 * it is processed by the Fast Courier Service application
 * @author rtucker
 */
public enum DeliveryStatus {
    
    /**
     * The customer has book collection of this delivery with the Fast Courier Service
     * administration team but the delivery driver has not yet collected the packages.
     */
    PENDINGCOLLECTION,
    /**
     * The delivery driver has collected the packages from the collection address.
     * The driver is proceeding to the delivery address with the packages.
     */
    INTRANSIT,
    /**
     * The delivery driver has successfully delivered the packages to the requested
     * delivery address.
     */
    DELIVERED,
    /**
     * The driver reached the delivery address but no-one would accept the packages
     * (or no-one was avaliable to accept delivery). The driver has contacted the
     * customer to request instructions on how to proceed.
     */
    DELIVERYREFUSED,
    /**
     * The delivery could not be made to the delivery address AND the customer
     * cannot provide an alternative address / instructions. The packages are or
     * have been returned to the dispatching customer.
     */
    UNDELIVERABLE;
    
}
