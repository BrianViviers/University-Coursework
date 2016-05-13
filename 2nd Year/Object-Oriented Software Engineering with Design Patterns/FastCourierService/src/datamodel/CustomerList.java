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
 * This class holds a list of all the customers who have registered 
 * with the Fast Courier Service. Each customer object holds a list of all 
 * past deliveries done for the customer.
 * @author Brian Viviers
 */
public class CustomerList implements IObserver, ISubject, Serializable{
    private ArrayList<Customer> customerList;
    private ISubject iSubjectImpl;
    
    /**
     * Default constructor required for serialisation, 
     * builds "empty" customer object
     */
    public CustomerList(){
        iSubjectImpl = new ISubjectImpl();
    }
    
    /**
     * This method adds a new customer to the list of customers 
     * being managed by this class.
     * @param newCustomer - Customer object being the new customer to store.
     */
    public void addCustomer(Customer newCustomer){
        if (null != newCustomer) {
            if (null == this.customerList) {
                this.customerList = new ArrayList<>();
            }
            if (!this.customerList.contains(newCustomer)) {
                this.customerList.add(newCustomer);
                newCustomer.registerObserver(this);
                this.notifyObservers();
            }
        } else {
            System.out.println("Failed to add a new customer as it was null.");
        }
    }
    
    /**
     * Given the index in the array of a Customer object this method removes 
     * the customer from the array and returns the removed Customer object. 
     * If the provided index is invalid an IndexOutOfBoundsException is thrown.
     * @param index - An int being the index at which to remove a customer.
     * @return - Customer object holding the removed customer.
     */
    public Customer removeCustomerAt(int index){
        Customer removedCustomer = null;
        if (null != this.customerList) {
            try {
                removedCustomer = this.customerList.remove(index);
                removedCustomer.removeObserver(this);
                this.notifyObservers();
                if (0 == this.customerList.size()) {
                        this.customerList = null;
                }
            } catch (IndexOutOfBoundsException ex){
                System.out.println("Index was not in range of customers.");
            }
        }
        return removedCustomer;
    } 
    
    /**
     * This method returns an array of strings. Each entry in the array 
     * represents the ‘full name’ of a registered customer.
     * @return - String array with each element containing 
     * the full name of a customer.
     */
    public String[] getAllNames(){
        Customer tempCustomer;
        String[] names = null;
        if (null != this.customerList) {
            names = new String[this.customerList.size()];
            for (int i = 0; i < this.customerList.size(); i++){
                tempCustomer = this.customerList.get(i);
                names[i] = tempCustomer.getFullName();
            }
        }
        return names;
    }
    
    /**
     * Retrieve a customer at a specific index within the customer list array.
     * @param index - An int being the index at which to retrieve the customer.
     * @return - Customer object being the customer retrieved at index given.
     */
    public Customer getCustomerAt(int index){
        Customer tempCustomer = null;
        if (null != this.customerList) {
            if (this.customerList.size() > index && index >= 0) {
                tempCustomer = this.customerList.get(index);
            }
        }
        return tempCustomer;
    }
    
    /**
     * Retrieves the number ( i.e. size) of customers in the customer list array.
     * @return - An int being the number of customer in the customer list array.
     */
    public int getSize(){
        int size = 0;
        if (null != this.customerList) {
            size = this.customerList.size();
        }
        return size;
    }

    @Override
    public String toString() {
        return "CustomerList{" + "customerList=" + customerList + '}';
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
