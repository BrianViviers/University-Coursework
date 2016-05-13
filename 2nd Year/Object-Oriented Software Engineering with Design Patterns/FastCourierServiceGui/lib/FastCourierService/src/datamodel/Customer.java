/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class represents a customer who has signed up to use the Fast Courier Service
 * It stores the customers name, address and contract agreed shipping price.
 * @author rtucker
 */
public class Customer extends Person implements IDeliveryRecord {
    
    private double deliveryCost;
    private DeliveryRecord deliveryHistory;
    private Address colAddress;
    
    private ISubject subjectDelegate;
    
    /**
     * Default Constructor initialises attributes to "UNKNOWN" with a default 
     * shipping cost defined by the value of Delivery.DEFAULTCOST.
     * Required for serialisation
     */
    public Customer(){
        super();
        this.subjectDelegate = new ISubjectImpl();
        this.deliveryCost = Delivery.DEFAULTCOST;
        this.deliveryHistory = new DeliveryRecord(this);
        Address defaultColAddress = new Address();
        this.setColAddress(defaultColAddress);
    }
    
    /**
     * Constructor to initialise a customer object with the provided attribute values
     * @param firstname - A String being the customers first name
     * @param surname - A String being the customers surname name
     * @param costPerKg - A double being the contract agreed cost per KG shipped
     * @param defaultColAddress - An Address object being the customers default address
     */
    public Customer(String firstname, String surname, double costPerKg, Address defaultColAddress){
        super(firstname, surname);
        this.subjectDelegate = new ISubjectImpl();
        this.deliveryHistory = new DeliveryRecord(this);
        this.deliveryCost = costPerKg;
        this.setColAddress(defaultColAddress);
    }

    @Override
    public void setForename(String forename) {
        //Both set forename and surname methods of the parent person class must 
        //be overridden to issue notifications to observers of this class
        super.setForename(forename);
        this.notifyObservers();
    }

    @Override
    public void setSurname(String surname) {
        //Both set forename and surname methods of the parent person class must 
        //be overridden to issue notifications to observers of this class
        super.setSurname(surname);
        this.notifyObservers();
    }

    @Override
    public boolean addDelivery(Delivery newDelivery) {
        return this.deliveryHistory.addDeliveryWithCost(newDelivery, this.deliveryCost);
    }
    
    @Override
    public boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg)
    {
        return this.deliveryHistory.addDeliveryWithCost(newDelivery, costPerKg);
    }
    
    @Override
    public Delivery removeDeliveryAt(int index) {
        return this.deliveryHistory.removeDeliveryAt(index);
    }

    @Override
    public Delivery getDeliveryAt(int index) {
        return this.deliveryHistory.getDeliveryAt(index);
    }

    @Override
    public void updateDeliveryStatus(int index, DeliveryStatus newStatus) {
        this.deliveryHistory.updateDeliveryStatus(index, newStatus);
    }

    @Override
    public ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        return this.deliveryHistory.getDeliveriesByStatus(status);
    }

    @Override
    public void clearAll() {
        this.deliveryHistory.clearAll();
    }

    @Override
    public int getNoOfDeliveries() {
        return this.deliveryHistory.getNoOfDeliveries();
    }
    
    @Override
    public int getIndexOfDelivery(Delivery aDelivery) {
        return this.deliveryHistory.getIndexOfDelivery(aDelivery);
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
    public void update() {
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the agreed cost per a KG shipped for this customer
     * @return - A double being the cost per a KG shipped agreed in the customers contract
     */
    public double getDeliveryCost() {
        return deliveryCost;
    }

    /**
     * Accessor method to set the agreed cost per a KG shipped for this customer
     * @param deliveryCost - The default cost per a KG shipped to use for
     * this customers consignments.
     */
    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the customers default collection address. This
     * will usually be the address of the customers main office.
     * @return - An Address object encapsulating the default address details for
     * this customer.
     */
    public Address getColAddress() {
        return this.colAddress;
    }

    /**
     * Accessor method to set the customers default collection address. This
     * will usually be the address of the customers main office.
     * @param colAddress - An Address object encapsulating the default address 
     * details for this customer.
     */
    public final void setColAddress(Address colAddress) {
        if(null != this.colAddress){
            this.colAddress.removeObserver(this);
        }
        this.colAddress = colAddress;
        if(null != this.colAddress){
            this.colAddress.registerObserver(this);
        }
        this.notifyObservers();
    }

    @Override
    public String toString() {
        return this.getFullName();
    }   
}
