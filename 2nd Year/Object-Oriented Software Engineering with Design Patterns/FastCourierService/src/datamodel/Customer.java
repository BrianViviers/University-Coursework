/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import datamodel.interfaces.IDeliveryRecord;
import java.io.Serializable;
import observerpattern.IObserver;
import java.util.ArrayList;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class represents a customer who is registered 
 * with the Fast Courier Service. It contains a customer name, 
 * collection address and a cost for shipping.
 * @author Brian Viviers
 */
public class Customer extends Person implements IDeliveryRecord, Serializable {
    private double deliveryCost;
    private DeliveryRecord deliveryHistory;
    private Address colAddress;
    private ISubject iSubjectImpl;
    
    /**
     * Default constructor required for serialisation creating a 
     * new DeliveryRecord, and sets the shipping cost 
     * to the default delivery cost.
     */
    public Customer(){
        super();
        deliveryHistory = new DeliveryRecord(this);
        iSubjectImpl = new ISubjectImpl();
        this.deliveryCost = Delivery.DEFAULTCOST;
    }
    
    /**
     * Constructor setting the first name, surname, cost per KG of delivery
     * and the default collection address of a customer.
     * @param firstname - String being the first name of a customer. 
     * @param surname - String being the surname of a customer.
     * @param costPerKg - A double being the cost per KG of delivery.
     * @param defaultColAddress - Address object being the default collection address.
     */
    public Customer(String firstname, String surname, double costPerKg, Address defaultColAddress){
        super(firstname, surname);
        deliveryHistory = new DeliveryRecord(this);
        iSubjectImpl = new ISubjectImpl();
        this.setColAddressHelper(defaultColAddress);
        this.setDeliveryCostHelper(costPerKg);
    }

    /**
     * Accessor method to retrieve the agreed shipping rate per a KG in this customer’s contract.
     * @return - A double representing the agreed shipping rate per a KG in this customer’s contract.
     */
    public double getDeliveryCost() {
        return deliveryCost;
    }

    /**
     * Accessor method to store the agreed shipping rate per a KG in this customer’s contract.
     * @param deliveryCost - Double being the value to store as the agreed shipping rate per a KG in this customer’s contract.
     */
    public void setDeliveryCost(double deliveryCost) {
        this.setDeliveryCostHelper(deliveryCost);
    }
    
    private void setDeliveryCostHelper(double deliveryCost) {
        if (deliveryCost >= Delivery.DEFAULTCOST) {
            this.deliveryCost = deliveryCost;
            this.notifyObservers();
        } else {
            System.out.println("The delivery cost was not set as it cannot "
                    + "be lower than the default cost");
        }
    }

    /**
     * Accessor method to retrieve a customer's default collection address. 
     * Usually this is the address of the customer’s place of business.
     * @return - Address object holding a customer's default collection address.
     */
    public Address getColAddress() {
        return colAddress;
    }

    /**
     * Accessor method to store a customer's default collection address. 
     * Usually this is the address of the customer’s place of business.
     * @param colAddress - Address object to store as a customer's default collection address.
     */
    public void setColAddress(Address colAddress) {
        this.setColAddressHelper(colAddress);
    }
    
    private void setColAddressHelper(Address colAddress) {
        if (colAddress != null) {
            this.colAddress = colAddress;
            this.colAddress.registerObserver(this);
            this.notifyObservers();
        } else {
            System.out.println("Collection address is null");
        }
    }

    @Override
    public void setForename(String forename) {
        if (forename != null && !forename.isEmpty()) {
            if (forename.matches("[a-zA-Z]+")) {
                this.notifyObservers();
                super.setForename(forename);
            } else {
                System.out.println("The forename should contain only letters.");
            } 
        } else {
            System.out.println("The forename is null or empty.");
        }
    }

    @Override
    public void setSurname(String surname) {
        if (surname != null && !surname.isEmpty()) {
            if (surname.matches("[a-zA-Z]+")) {
                this.notifyObservers();
                super.setSurname(surname);
            } else {
                System.out.println("The surname should contain only letters.");
            }
        } else {
            System.out.println("The surname is null or empty.");
        }
    }    

    @Override
    public boolean addDelivery(Delivery newDelivery) {
        return this.deliveryHistory.addDelivery(newDelivery);
    }

    @Override
    public boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg){
        return this.deliveryHistory.addDeliveryWithCost(newDelivery, costPerKg);
    }

    @Override
    public void clearAll() {
        this.deliveryHistory.clearAll();
    }

    @Override
    public ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        return this.deliveryHistory.getDeliveriesByStatus(status);
    }

    @Override
    public Delivery getDeliveryAt(int index) {
        return this.deliveryHistory.getDeliveryAt(index);
    }

    @Override
    public int getIndexOfDelivery(Delivery aDelivery) {
        return this.deliveryHistory.getIndexOfDelivery(aDelivery);
    }

    @Override
    public int getNoOfDeliveries() {
        return this.deliveryHistory.getNoOfDeliveries();
    }

    @Override
    public void updateDeliveryStatus(int index, DeliveryStatus newStatus) {
        this.deliveryHistory.updateDeliveryStatus(index, newStatus);
    }
    
    @Override
    public void update(){
        this.notifyObservers();
    }
    
    @Override
    public String toString() {
        return this.getFullName();
    }

    @Override
    public Boolean registerObserver(IObserver o) {
        return this.iSubjectImpl.registerObserver(o);
    }

    @Override
    public Boolean removeObserver(IObserver o) {
        return this.iSubjectImpl.removeObserver(o);
    }

    @Override
    public void notifyObservers() {
        this.iSubjectImpl.notifyObservers();
    }
}

