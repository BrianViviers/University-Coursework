/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.commands;

import command.interfaces.ICommandBehaviour;
import datamodel.Customer;
import datamodel.CustomerList;

/**
 * A class that implements ICommandBehaviour and allows the doing and undoing
 * of adding a customer to the data model of Fast Courier Service.
 * @author Brian Viviers
 */
public class AddCustomerCommand implements ICommandBehaviour {

    private Customer customerAdded = null;
    private CustomerList objTargetCustomerList = null;

    /**
     * Constructor that creates the AddCustomerCommand object.
     * @param objTarget - Customer object which is the customer to add.
     * @param objNewTargetCustomerList - CustomerList object to where the 
     * customer is to be added
     */
    public AddCustomerCommand(Customer objTarget, CustomerList objNewTargetCustomerList) {
        this.customerAdded = objTarget;
        this.objTargetCustomerList = objNewTargetCustomerList;
    }

    private Boolean isValid() {
        Boolean blnValid = false;
        if (null != this.customerAdded && null != this.objTargetCustomerList) {
            blnValid = true;
        }
        return blnValid;
    }

    @Override
    public String doCommand() {
        String completed = "";
        if (this.isValid()) {
            if (this.objTargetCustomerList.addCustomer(customerAdded)) {
                completed = "<html>Customer <b>" + customerAdded.getFullName()
                        + "</b> added to the data model.</html>";
            }
        }
        return completed;
    }

    @Override
    public String undoCommand() {
        String completed = "";
        Customer removedCustomer = null;
        if (this.isValid()) {
            try {
                removedCustomer = this.objTargetCustomerList.removeCustomerAt(
                        this.objTargetCustomerList.getSize() - 1);
                if (removedCustomer != null) {
                    completed = "<html>Customer <b>" + customerAdded.getFullName()
                        + "</b> removed from the data model.</html>";;
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return completed;
    }
}
