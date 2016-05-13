/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import datamodel.interfaces.IDeliveryRecord;
import java.io.Serializable;
import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class provides a concrete implementation of the IDeliveryRecord interface. 
 * Composed of a customer class.
 * @author Brian Viviers
 */
public class DeliveryRecord implements IDeliveryRecord, Serializable{
    private ArrayList<Delivery> deliveries;
    private Customer owner;
    private ISubject iSubjectImpl;
    
    /**
     * Default constructor required for serialisation, builds "empty" object
     */
    public DeliveryRecord(){ 
        this.iSubjectImpl = new ISubjectImpl();
        this.deliveries = new ArrayList();
    }
   
    /**
     * Constructor setting the owner of this delivery record to the value 
     * given and registering this delivery record to observe itself.
     * @param owner - Customer object holding the customer who 
     * owns this delivery record.
     */
    public DeliveryRecord(Customer owner){
        this();
        this.owner = owner;
        this.iSubjectImpl.registerObserver(this.owner);
    }
    
    /**
     * Accessor to retrieve the owner  of the delivery record.
     * @return - Customer object holding the owner of the delivery record.
     */
    public Customer getOwner() {
        return this.owner;
    }

    @Override
    public boolean addDelivery(Delivery newDelivery) {
        boolean blnAdded = false;
        
        if (newDelivery != null){
            if (!this.deliveries.contains(newDelivery)){
                blnAdded = this.deliveries.add(newDelivery);
                if (blnAdded){
                    newDelivery.registerObserver(this);
                    this.notifyObservers();   
                }
            } else {
                System.out.println("Failed to add delivery as it is already contained in the list");
            }
            if (this.owner != null){
                newDelivery.setCostPerKg(this.owner.getDeliveryCost());
                if (newDelivery.getColAddress() == null) {
                    newDelivery.setColAddress(this.owner.getColAddress());
                }
            } 
        } else {
            System.out.println("Failed to add a new delivery as it was null");
        }
        return blnAdded;
    }

    @Override
    public boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg) {
        boolean blnAdded = false;
        
        if (newDelivery != null){ 
            if (!this.deliveries.contains(newDelivery)){
                blnAdded = this.deliveries.add(newDelivery);
                if (blnAdded){
                    newDelivery.registerObserver(this);
                    newDelivery.setCostPerKg(costPerKg);
                    this.notifyObservers();   
                }
            } else {
                System.out.println("Failed to add delivery as it is already contained in the list");
            }
            if (this.owner != null){
                if (newDelivery.getColAddress() == null) {
                    newDelivery.setColAddress(this.owner.getColAddress());
                }
            }
        } else {
            System.out.println("Failed to add a new delivery as it was null");
        }
        return blnAdded;
    }

    @Override
    public String toString() {
        return "DeliveryRecord{" + "deliveries=" + this.deliveries + ", owner=" + this.owner + '}';
    }

    @Override
    public void clearAll() {
        this.deliveries = new ArrayList();
    }

    @Override
    public ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        ArrayList<Delivery> deliveriesByStatus = new ArrayList();
        for (Delivery currDelivery : this.deliveries) {
            if (currDelivery.getStatus() == status) {
                deliveriesByStatus.add(currDelivery);
            }
        }
        return deliveriesByStatus;
    }

    @Override
    public Delivery getDeliveryAt(int index) {
        Delivery deliveryAt = null;
        if (this.deliveries.size() > index && index >= 0){
            deliveryAt = this.deliveries.get(index);
        } else {
            System.out.println("Failed to getDeliveryAt "
                    + "because index out of bounds");
        }
        return deliveryAt;
    }

    @Override
    public int getIndexOfDelivery(Delivery aDelivery) {
        if (this.deliveries.contains(aDelivery))
            return this.deliveries.indexOf(aDelivery);
        else  
            return -1;
    }

    @Override
    public int getNoOfDeliveries() {
        return this.deliveries.size();
    }

    @Override
    public void updateDeliveryStatus(int index, DeliveryStatus newStatus) {
        if (newStatus != null) {
            if (this.getNoOfDeliveries() > index && index >= 0){
                this.deliveries.get(index).setStatus(newStatus);
            } else {
                System.out.println("Failed to update delivery status "
                        + "due to the index being out of bounds");
            }
        } else {
            System.out.println("Failed to update delivery status due "
                    + "to the new status being null");
        }
    }

    @Override
    public void update() {
        this.notifyObservers();
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
