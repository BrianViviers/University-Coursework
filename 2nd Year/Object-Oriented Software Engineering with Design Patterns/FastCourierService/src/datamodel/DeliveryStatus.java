/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

/**
 * This enumeration represents the various 
 * states a consignment / delivery can be in
 * @author Brian Viviers
 */
public enum DeliveryStatus {
    /**
     * The customer has requested a delivery to be collected and 
     * the driver is still on his / her way to collect the package(s).
     */
    PENDINGCOLLECTION,
    /**
     * The package(s) have been collected and are 
     * being transported to the delivery address.
     */
    INTRANSIT,
    /**
     * The package(s) have been successfully delivered to the destination.
     */
    DELIVERED,
    /**
     * The package(s) where refused at the delivery address. 
     * The driver is holding them awaiting further instructions from the customer.
     */
    DELIVERYREFUSED,
    /**
     * The goods have been returned by the driver 
     * to the customer as delivery cannot be made.
     */
    UNDELIVERABLE;
}
