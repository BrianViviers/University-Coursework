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
 * This class represents a consignment of packages that is being or has been processed
 * by the Fast Courier Service application.
 * @author rtucker
 */
public class Delivery implements ISubject, IObserver, Serializable {
    
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
    
    /**
     * The Fast Courier Services Default cost per KG shipped. Individual customers
     * may have different agreed rates but in the absence of any customer specific
     * rate this value is used for a deliveries cost per KG shipped value.
     */
    public static final double DEFAULTCOST = 5.00d;
    
    private ISubject subjectDelegate;
    
    /**
     * Default Constructor - Builds a Delivery object with an UNKNOW consignment number
     * for collection on the current date at the default shipping cost. The collection
     * and delivery addresses are unspecified as are the number of packages to 
     * collect and the total consignment weight.
     * A Default constructor is required for serialisation
     */
    public Delivery()
    {
        this.consignmentNo = "UNKNOWN";
        this.startDate = new Date();
        this.status = DeliveryStatus.PENDINGCOLLECTION;
        this.noOfPackages = 0;
        this.weight = 0.0d;
        this.costPerKg = Delivery.DEFAULTCOST;
        this.notes = "";
        this.subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Constructor to create a Delivery initialised with the provided attributes
     * @param consNo - A String being the consignment number to use for this delivery
     * @param packageCount - An int being the number of packages the driver should collect
     * @param weight - A double being the total weight of all packages in KG's
     * @param colAddress - An Address object holding details of where the consignment 
     * packages can be collected
     * @param delAddress - An Address object holding details of where the consignment 
     * packages need to be delivered
     */
    public Delivery(String consNo, int packageCount, double weight, Address colAddress, Address delAddress)
    {
        this();
        this.consignmentNo = consNo;
        this.noOfPackages = packageCount;
        this.weight = weight;
        this.colAddress = colAddress;
        this.delAddress = delAddress;
        this.costPerKg = Delivery.DEFAULTCOST;
        
        this.colAddress.registerObserver(this);
        this.delAddress.registerObserver(this);
    }
    
    /**
     * Accessor method to append a new note to the comma separated notes held 
     * about this delivery
     * @param newNote - A String containing the note that the driver wishes to
     * append to the deliveries notes
     */
    public void appendToNotes(String newNote){
        if(null != newNote){
            if(!newNote.isEmpty()){
                if(!this.notes.isEmpty()){
                    this.notes = this.notes + "," + newNote;
                } else {
                    this.notes = newNote;
                }
                this.notifyObservers();
            }
        }
    }
    
    /**
     * This method retrieves the total cost that the customer will be billed for
     * making this delivery. The cost is defined as the total weight of the 
     * deliveries packages * by the deliveries cost per KG shipped.
     * @return - A Double being the total cost to be charged to the customer
     * for making this delivery.
     */
    public Double getTotalCost()
    {
        return this.weight * this.costPerKg;
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
    
    /**
     * Accessor method to retrieve the date when the customer requested collection
     * of this consignment.
     * @return - A Date object representing the date when the customer requested 
     * collection of this consignment.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Accessor method to retrieve the date on which the driver made delivery of 
     * this consignment. 
     * @return - A Date object being the day on which to delivery was made to its
     * destination or NULL if the delivery has not yet reached its destination
     */
    public Date getDeliveredDate() {
        return deliveredDate;
    }

    /**
     * Accessor method to set the date on which the driver made delivery of 
     * this consignment. After this method is called the deliveries status
     * will be DeliveryStatus.DELIVERED
     * @param deliveredDate - A Date object being the date on which the driver 
     * completed the delivery.
     */
    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
        this.status = DeliveryStatus.DELIVERED;
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the current delivery status of this consignment
     * @return - An enumerated value from the DeliveryStatus enumeration.
     */
    public DeliveryStatus getStatus() {
        return status;
    }

    /**
     * Accessor method to set the current delivery status of this consignment
     * @param status - An enumerated value from the DeliveryStatus enumeration.
     */
    public void setStatus(DeliveryStatus status) {
        this.status = status;
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the total number of packages being delivered
     * on this consignment
     * @return - An int representing the physical number of packages to be transported
     */
    public int getNoOfPackages() {
        return noOfPackages;
    }

    /**
     * Accessor method to set the total number of packages being delivered
     * on this consignment
     * @param noOfPackages - An int representing the physical number of packages 
     * to be transported
     */
    public void setNoOfPackages(int noOfPackages) {
        this.noOfPackages = noOfPackages;
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the total weight in KG's of all packages transported
     * on this consignment
     * @return - A double being the total weight in KG's of all packages transported
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Accessor method to set the total weight in KG's of all packages transported
     * on this consignment
     * @param weight - A double being the total weight in KG's of all packages transported
     */
    public void setWeight(double weight) {
        this.weight = weight;
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve a string containing a comma separated list of
     * notes made by the driver during transportation of the delivery
     * @return - A String being a comma separated list of notes.
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Accessor method to set a string containing a comma separated list of
     * notes made by the driver during transportation of the delivery
     * @param notes - A String being a comma separated list of notes.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Accessor method to retrieve the delivery address to which the packages should be 
     * delivered.
     * @return - An Address object that encapsulates the delivery address for this
     * consignment
     */
    public Address getDelAddress() {
        return delAddress;
    }

    /**
     * Accessor method to set the delivery address to which the packages should be 
     * delivered.
     * @param delAddress - An Address object that encapsulates the delivery address for this
     * consignment
     */
    public void setDelAddress(Address delAddress) {
        if(null != this.delAddress){
            this.delAddress.removeObserver(this);
        }
        this.delAddress = delAddress;
        if(null != this.delAddress){
            this.delAddress.registerObserver(this);
        }
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the collection address from which the packages should be 
     * collected.
     * @return - An Address object that encapsulates the collection address for this
     * consignment
     */
    public Address getColAddress() {
        return colAddress;
    }

    /**
     * Accessor method to set the collection address from which the packages should be 
     * collected.
     * @param colAddress - An Address object that encapsulates the collection address for this
     * consignment
     */
    public void setColAddress(Address colAddress) {
        if(null != this.colAddress){
            this.colAddress.removeObserver(this);
        }
        this.colAddress = colAddress;
        if(null != this.colAddress){
            this.colAddress.registerObserver(this);
        }
        this.notifyObservers();
    }

    /**
     * Accessor method to retrieve the consignment number assigned to this delivery
     * by the Fast Courier Service administration team when the customer booked
     * the delivery
     * @return - A String being this deliveries consignment number.
     */
    public String getConsignmentNo() {
        return consignmentNo;
    }

    @Override
    public String toString() {
        return "Delivery: " + this.getConsignmentNo();
    }
    
    /**
     * Accessor method to retrieve this deliveries cost per a KG shipped.
     * @return - A Double being the cost to the customer of shipping 1 KG
     */
    public Double getCostPerKg() {
        return costPerKg;
    }

    /**
     * Accessor method to set this deliveries cost per a KG shipped.
     * @param costPerKg - A Double being the cost to the customer of shipping 1 KG
     */
    public void setCostPerKg(Double costPerKg) {
        this.costPerKg = costPerKg;
        this.notifyObservers();
    }

    @Override
    public void update() {
        this.notifyObservers();
    }
}
