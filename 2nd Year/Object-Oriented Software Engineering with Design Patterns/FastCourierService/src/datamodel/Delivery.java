/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;
import java.util.Date;
import observerpattern.IObserver;
import observerpattern.ISubject;
import observerpattern.ISubjectImpl;

/**
 * This class represents a delivery / consignment that is or has been handled
 * by the courier company. It provides a means to track the delivery and bill the customer.
 * @author Brian Viviers
 */
public class Delivery implements ISubject, IObserver, Serializable{
    private String consignmentNo;
    private Date startDate;
    private Date deliveredDate;
    private DeliveryStatus status;
    private int noOfPackages;
    private double weight;
    private String notes;
    private Address delAddress;
    private Address colAddress;
    private Double costPerKg;
    private ISubject iSubjectImpl;
    /**
     * Defines the default delivery cost per 1 KG. Used when a customer does not
     * have an agreed cost for shipping.
     */
    protected static final double DEFAULTCOST = 5.00;
    
    /**
     * Default constructor required for serialisation initialises 
     * consignment number to UNKNOWN, cost per KG to the default cost, 
     * collection date to the current date, notes to empty string, and 
     * the delivery status to pending collection.
     */
    public Delivery(){
        iSubjectImpl = new ISubjectImpl();
        costPerKg = DEFAULTCOST;
        notes = "";
        status = DeliveryStatus.PENDINGCOLLECTION;
        startDate = new Date();
        consignmentNo = "UNKNOWN";
    }

    /**
     * Constructor setting consignment number, package count, weight, 
     * collection address and delivery address of a delivery to given values.
     * @param consNo - String being the value to set the consignment number.
     * @param packageCount - Integer being the number of packages in this delivery.
     * @param weight - A double representing the weight of the delivery.
     * @param colAddress - Address object holding the collection address of the delivery.
     * @param delAddress - -Address object holding the delivery/destination address of the delivery.
     */
    public Delivery(String consNo, int packageCount, double weight, Address colAddress, Address delAddress){
        this();
        this.setConsignmentNoHelper(consNo);
        this.setNoOfPackagesHelper(packageCount);
        this.setWeightHelper(weight);
        this.setDelAddressHelper(delAddress);
        this.setColAddressHelper(colAddress);
    }

    /**
     * Accessor method to retrieve the consignment number assigned to this delivery.
     * @return - String holding the consignment number assigned to this delivery.
     */
    public String getConsignmentNo() {
        return consignmentNo;
    }

    /**
     * Accessor method to store the consignment number assigned to this delivery.
     * @param consignmentNo - String being the value to 
     * store as the consignment number assigned to this delivery.
     */
    public void setConsignmentNo(String consignmentNo) {
        this.setConsignmentNoHelper(consignmentNo);
    }
    
    private void setConsignmentNoHelper(String consignmentNo) {
        if (consignmentNo != null && !consignmentNo.isEmpty()) {
            this.consignmentNo = consignmentNo;
            notifyObservers();
        } else {
            System.out.println("Failed to set the consignment number "
                    + "due to it being null or empty.");
        } 
    }

    /**
     * Accessor method to retrieve the date on which the customer 
     * requested this delivery to be collected from them.
     * @return - Date on which the customer requested this delivery to be collected from them.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Accessor method to retrieve the date on which the driver made delivery of the 
     * packages(s) either to the destination or if the package(s)
     * where returned to the customer.
     * @return - Date on which the driver made delivery of the 
     * packages(s) either to the destination or if the package(s)
     * where returned to the customer. If delivery is still active then date
     * is null.
     */
    public Date getDeliveredDate() {
        return deliveredDate;
    }

    /**
     * Accessor method to store the date on which the driver made delivery of the 
     * packages(s) either to the destination or if the package(s)
     * where returned to the customer and updates the status to delivered.
     * @param deliveredDate - Date value to be stored as the date on which the 
     * driver made delivery of the packages(s) either to the destination or if 
     * the package(s) where returned to the customer.
     */
    public void setDeliveredDate(Date deliveredDate) {
        if (deliveredDate != null) {
            this.deliveredDate = deliveredDate;
            this.setStatus(DeliveryStatus.DELIVERED);
            notifyObservers();
        } else {
            System.out.println("Failed to set the delivered date "
                    + "due to it being null.");
        }
    }

    /**
     * Accessor method to retrieve the current delivery status of a delivery.
     * @return - DeliveryStatus being the current delivery status of a delivery.
     */
    public DeliveryStatus getStatus() {
        return this.status;
    }

    /**
     * Accessor method to store as the current delivery status of a delivery.
     * @param status - DeliveryStatus being the value to set the current 
     * delivery status of a delivery.
     */
    public void setStatus(DeliveryStatus status) {
        if (status != null) {
            this.status = status;
            notifyObservers();
        } else {
            System.out.println("Failed to set the delivery status "
                    + "due to it being null.");
        } 
    }

    /**
     * Accessor method to retrieve the total number of packages in this delivery / consignment.
     * @return - An int holding the total number of packages in this delivery / consignment.
     */
    public int getNoOfPackages() {
        return this.noOfPackages;
    }

    /**
     * Accessor method to store as the total number of packages in this delivery / consignment.
     * @param noOfPackages - An int value to store as the total number of 
     * packages in this delivery / consignment.
     */
    public void setNoOfPackages(int noOfPackages) {
        this.setNoOfPackagesHelper(noOfPackages);
    }
    
    private void setNoOfPackagesHelper(int noOfPackages) {
        if (noOfPackages >= 0) {
            this.noOfPackages = noOfPackages;
            notifyObservers();
        } else {
            System.out.println("Failed to set the number of packages "
                    + "due to it being less than zero.");
        }
    }

    /**
     * Accessor method to retrieve the chargeable weight of the package(s).
     * @return - double holding the the chargeable weight of the package(s).
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Accessor method to store as the chargeable weight of the package(s).
     * @param weight - double being the value to store as the chargeable 
     * weight of the package(s).
     */
    public void setWeight(double weight) {
        this.setWeightHelper(weight);
    }
    
    private void setWeightHelper(double weight) {
        if (weight >= 0) {
            this.weight = weight;
            notifyObservers();
        } else {
            System.out.println("Failed to set the weight "
                    + "due to it being less than zero.");
        }
    }

    /**
     * Accessor method to retrieve a String being a comma separated list of all 
     * notes made for this delivery. 
     * @return - String being a comma separated list of all notes made for this delivery. 
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Accessor method to retrieve the delivery address for this delivery / consignment.
     * @return - Address object holding the delivery address for 
     * this delivery / consignment.
     */
    public Address getDelAddress() {
        return this.delAddress;
    }

    /**
     * Accessor method to store the delivery address for this delivery / consignment.
     * @param delAddress - Address object to store as the 
     * address class being the delivery address for this delivery / consignment.
     */
    public void setDelAddress(Address delAddress) {
        this.setDelAddressHelper(delAddress);
    }
    
    private void setDelAddressHelper(Address delAddress) {
        this.delAddress = delAddress;
        if (delAddress != null) {
            this.delAddress.registerObserver(this);
        }
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the collection address from which this 
     * deliveries package(s) where collected.
     * @return - Address holding the collection address from which 
     * this deliveries package(s) where collected.
     */
    public Address getColAddress() {
        return this.colAddress;
    }

    /**
     * Accessor method to store the collection address from which this 
     * deliveries package(s) where collected.
     * @param colAddress - Address object to store as the collection address 
     * from which this deliveries package(s) where collected.
     */
    public void setColAddress(Address colAddress) {
        this.setColAddressHelper(colAddress);
    }
    
    private void setColAddressHelper(Address colAddress) {
        this.colAddress = colAddress;
        if (colAddress != null){
            this.colAddress.registerObserver(this);
        }
        notifyObservers();
    }

    /**
     * Accessor method to retrieve the cost to charge the customer for 
     * each 1 KG of delivery weight.
     * @return - Double being the cost to charge the customer for 
     * each 1 KG of delivery weight.
     */
    public Double getCostPerKg() {
        return this.costPerKg;
    }

    /**
     * Accessor method to store the cost to charge the customer for 
     * each 1 KG of delivery weight.
     * @param costPerKg - Double being the value to store as the cost to 
     * charge the customer for each 1 KG of delivery weight.
     */
    public void setCostPerKg(Double costPerKg) {
        if (costPerKg >= Delivery.DEFAULTCOST) {
            this.costPerKg = costPerKg;
            notifyObservers();
        } else {
            System.out.println("Failed to set the cost per KG "
                    + "due to it being less than zero.");
        }
    }
    
    /**
     * This method appends the newNote string to the comma separated 
     * list of strings stored in the notes attribute.
     * @param newNote - String holding the new notes to append to the comma 
     * separated list of strings stored in the notes attribute.
     */
    public void appendToNotes(String newNote){
        if (newNote != null && !newNote.isEmpty()) {
            if (notes.contentEquals("")) {
                this.notes += newNote;            
            } else {
                this.notes += "," + newNote;
            }
            this.notifyObservers();
        } else {
            System.out.println("Failed to append to notes "
                    + "due to it being null or empty.");
        }
    }
    
    /**
     * This method calculates the total cost of making the delivery. 
     * Total cost is defined as the chargeable weight of the delivery 
     * multiplied by the cost per a KG. (Total Cost = weight * costPerKg).
     * @return - A double representing the total cost of making the delivery.
     */
    public double getTotalCost(){
        return this.costPerKg * this.weight;
    }
    
    @Override
    public String toString() {
        return "Delivery{" + "consignmentNo=" + consignmentNo + ", startDate=" 
                + startDate + ", deliveredDate=" + deliveredDate + ", status=" 
                + status + ", noOfPackages=" + noOfPackages + ", weight=" + weight 
                + ", notes=" + notes + ", delAddress=" + delAddress + ", colAddress=" 
                + colAddress + ", costPerKg=" + costPerKg + '}';
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

    @Override
    public void update() {
        this.notifyObservers();
    }
}
