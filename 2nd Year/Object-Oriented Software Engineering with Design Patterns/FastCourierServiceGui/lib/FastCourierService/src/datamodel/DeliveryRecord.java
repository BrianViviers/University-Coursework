/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class provides the concrete implementation of the IDeliveryRecord
 * interface. It is composed with the Customer class which "delegates" the
 * functionality of the IDeliveryRecord's methods to this class
 *
 * @author rtucker
 */
public class DeliveryRecord implements IDeliveryRecord, Serializable {

    private ArrayList<Delivery> deliveries;
    private Customer owner;
    private ISubject subjectDelegate;

    /**
     * Default Constructor creates an empty DeliveryRecord object that contains
     * no deliveries. Required for serialisation
     */
    public DeliveryRecord() {
        this.deliveries = new ArrayList<>();
        this.subjectDelegate = new ISubjectImpl();
    }

    /**
     * Constructor that creates a DeliveryRecord that is "owned" by the provided
     * customer
     *
     * @param owner - A Customer object that "owns" the DeliveryRecord object
     * that is being constructed
     */
    public DeliveryRecord(Customer owner) {
        this();
        this.owner = owner;
        this.registerObserver(this.owner);
    }

    @Override
    public boolean addDelivery(Delivery newDelivery) {
        boolean result = false;
        if (null != newDelivery) {
            if (null == this.deliveries) {
                this.deliveries = new ArrayList<>();
            }
            if (this.deliveries.add(newDelivery)) {
                result = true;
                if (null != this.owner) {
                    newDelivery.setCostPerKg(this.owner.getDeliveryCost());
                } else {
                    newDelivery.setCostPerKg(Delivery.DEFAULTCOST);
                }
                newDelivery.registerObserver(this);
                this.notifyObservers();
            }
        }
        return result;
    }

    @Override
    public boolean addDeliveryWithCost(Delivery newDelivery, Double costPerKg) {
        boolean result = false;
        if (null != newDelivery && costPerKg >= Delivery.DEFAULTCOST) {
            if (null == this.deliveries) {
                this.deliveries = new ArrayList<>();
            }
            if (this.deliveries.add(newDelivery)) {
                result = true;
                newDelivery.setCostPerKg(costPerKg);
                newDelivery.registerObserver(this);
                this.notifyObservers();
            }
        }
        return result;
    }
    
    @Override
    public Delivery removeDeliveryAt(int index) {
        Delivery result = null;
        if (null != this.deliveries && 0 < this.deliveries.size()) {
            if (index >= 0 && index < this.deliveries.size()) {
                result = this.deliveries.get(index);
                this.deliveries.remove(index);
                this.notifyObservers();
            } else {
                throw new IndexOutOfBoundsException("No delivery in customers "
                        + "delivery record at index " + index);
            }
        }
        return result;
    }

    @Override
    public Delivery getDeliveryAt(int index) throws IndexOutOfBoundsException {
        Delivery result = null;
        if (null != this.deliveries && deliveries.size() > 0 && index >= 0 && index < deliveries.size()) {
            result = this.deliveries.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist.");
        }
        return result;
    }

    @Override
    public void updateDeliveryStatus(int index, DeliveryStatus newStatus) throws IndexOutOfBoundsException {
        Delivery delivery = this.getDeliveryAt(index);
        if (null != delivery && null != newStatus) {
            delivery.setStatus(newStatus);
        }
    }

    @Override
    public ArrayList<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        ArrayList<Delivery> result = new ArrayList<>();
        if (null != this.deliveries && 0 < this.deliveries.size()) {
            for (Delivery currDelivery : this.deliveries) {
                if (currDelivery.getStatus() == status) {
                    result.add(currDelivery);
                }
            }
        }
        return result;
    }

    @Override
    public void clearAll() {
        if (null != this.deliveries) {
            for (Delivery currDelivery : this.deliveries) {
                currDelivery.removeObserver(this);
            }
            this.deliveries.clear();
            this.notifyObservers();
        }
    }

    @Override
    public int getNoOfDeliveries() {
        int result = 0;
        if (null != this.deliveries) {
            result = this.deliveries.size();
        }
        return result;
    }

    @Override
    public int getIndexOfDelivery(Delivery aDelivery) {
        int index = -1;
        if (this.deliveries.contains(aDelivery)) {
            index = this.deliveries.indexOf(aDelivery);
        }
        return index;
    }

    @Override
    public final Boolean registerObserver(IObserver o) {
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
}