/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import observerpattern.IObserver;
import observerpattern.ISubject;

/**
 * This class represents all the customers that have registered with the Fast
 * Courier Service company. Each registered customer object maintains a list of
 * every delivery the Fast Courier Service company has undertaken on their
 * behalf.
 *
 * @author rtucker
 */
public class CustomerList implements IObserver, ISubject, Serializable {

    private ArrayList<Customer> customerList;
    private transient ArrayList<IObserver> observers;

    /**
     * Default constructor builds an empty customer list object with no
     * customers yet registered to use the delivery services. Required for
     * serialisation.
     */
    public CustomerList() {
        this.customerList = new ArrayList<>();
    }

    /**
     * This method gets the most recent 
     * consignment number and returns the next number
     * @return - String containing the next consignment number in the sequence.
     */
    public String getNewConsNo() {
        int consNo = 0;
        int previousConsNo = 0;
       
        for (int i = 0; i < getSize(); i++) {
            Customer cust = getCustomerAt(i);
            for (int j = 0; j < cust.getNoOfDeliveries(); j++) {
                consNo = Integer.parseInt(((
                        cust.getDeliveryAt(j).getConsignmentNo()).replace(
                        "CONS: ", "")));

                if (consNo > previousConsNo){
                    previousConsNo = consNo;
                }
            }
        }
        return "CONS: " + String.format("%05d", previousConsNo + 1);
    }

    /**
     * This method adds a new customer to the list of customers that have
     * registered to use the Fast Courier Service.
     * 
     * @param newCustomer - Customer object to add to the list.
     * @return  Boolean True if customer added successfully, False otherwise.
     */
    public Boolean addCustomer(Customer newCustomer) {
        Boolean blnAdded = false;
        if (null != newCustomer) {
            if (null == this.customerList) {
                this.customerList = new ArrayList<>();
            }
            if (this.customerList.add(newCustomer)) {
                newCustomer.registerObserver(this);
                this.notifyObservers();
                blnAdded = true;
            }
        }
        return blnAdded;
    }

    /**
     * Customers are stored in a zero based array, this method removes the
     * customer at the given index
     *
     * @param index - The zero based index of the customer to remove from the
     * customer list
     * @return - The Customer object removed from the list or NULL if no
     * customer was removed.
     * @throws IndexOutOfBoundsException - If index is negative or greater than
     * or equal to the size of the customer list.
     */
    public Customer removeCustomerAt(int index) throws IndexOutOfBoundsException {
        Customer result = null;
        if (null != this.customerList && 0 < this.customerList.size()) {
            if (index >= 0 && index < this.customerList.size()) {
                result = this.customerList.get(index);
                this.customerList.remove(index);
                this.notifyObservers();
            } else {
                throw new IndexOutOfBoundsException("No customer in customer list at index " + index);
            }
        }
        return result;
    }

    /**
     * This method retrieves an array of customer names (full names) providing a
     * complete list of all registered customers.
     *
     * @return - An array of String objects where each element contains a
     * registered customers full name
     */
    public String[] getAllNames() {
        String[] result = null;
        if (null != this.customerList && 0 < this.customerList.size()) {
            result = new String[this.customerList.size()];
            for (int i = 0; i < this.customerList.size(); i++) {
                result[i] = this.customerList.get(i).getFullName();
            }
        } else {
            result = new String[0];
        }
        return result;
    }

    /**
     * This method retrieves the customer object at the specified zero based
     * index in the CustomerList
     *
     * @param index - The zero based index of the customer to retrieve from the
     * customer list
     * @return - A Customer object at the specified index position.
     */
    public Customer getCustomerAt(int index) {
        return this.customerList.get(index);
    }

    /**
     * This method retrieves the total number of customers that are registered
     * to use the Fast Courier Service.
     *
     * @return - An int being the total number of registered customers.
     */
    public int getSize() {
        return this.customerList.size();
    }

    @Override
    public void update() {
        this.notifyObservers();
    }

    @Override
    public Boolean registerObserver(IObserver o) {
        Boolean result = false;
        if (null != o) {
            if (null == this.observers) {
                this.observers = new ArrayList<>();
            }
            //This check ensures an observer can only be added once!
            if (!this.observers.contains(o)) {
                result = this.observers.add(o);
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Boolean removeObserver(IObserver o) {
        Boolean result = false;
        if (null != o) {
            if (null != this.observers) {
                if (this.observers.contains(o)) {
                    result = this.observers.remove(o);
                    //If the observer list is empty save memory by freeing the unused variable
                    if (0 == this.observers.size()) {
                        this.observers = null;
                    }
                } else {
                    result = true;
                }
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void notifyObservers() {
        if (null != this.observers) {
            for (IObserver currObserver : this.observers) {
                currObserver.update();
            }
        }
    }

    /**
     * This method creates and returns a list of all observers
     * watching the data model.
     * @return - ArrayList of IObserver objects.
     */
    public ArrayList<IObserver> getObservers() {
        ArrayList<IObserver> arlResult = new ArrayList<>();
        for (IObserver currObserver : this.observers) {
            arlResult.add(currObserver);
        }
        return arlResult;
    }
}
