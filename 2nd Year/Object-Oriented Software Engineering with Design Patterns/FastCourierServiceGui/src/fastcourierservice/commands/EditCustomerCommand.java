/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.commands;

import command.interfaces.ICommandBehaviour;
import datamodel.Address;
import datamodel.Customer;
import datamodel.CustomerList;

/**
 *
 * @author Brian
 */
public class EditCustomerCommand implements ICommandBehaviour {

    private Customer customerToEdit = null;
    private Customer newCust = null;
    private Customer copyOldCust = null;

    /**
     * Constructor which creates a new EditCustomerCommand object.
     * @param objTarget - Customer object which is to be edited.
     * @param copy - Customer object which is the new customer details.
     */
    public EditCustomerCommand(Customer objTarget, Customer copy) {
        this.customerToEdit = objTarget;
        this.newCust = copy;
        Address tempAdd = new Address(objTarget.getColAddress().getAddressLine1(),
                objTarget.getColAddress().getAddressLine2(),
                objTarget.getColAddress().getCity(),
                objTarget.getColAddress().getPostcode());
        this.copyOldCust = new Customer(objTarget.getForename(), 
                objTarget.getSurname(), 
                objTarget.getDeliveryCost(), 
                tempAdd);
    }

    private Boolean isValid() {
        Boolean blnValid = false;
        if (null != this.customerToEdit) {
            blnValid = true;
        }
        return blnValid;
    }

    @Override
    public String doCommand() {
        String completed = "";
        if (this.isValid()) {
            customerToEdit.setForename(newCust.getForename());
            customerToEdit.setSurname(newCust.getSurname());
            customerToEdit.getColAddress().setAddressLine1(newCust.getColAddress().getAddressLine1());
            customerToEdit.getColAddress().setAddressLine2(newCust.getColAddress().getAddressLine2());
            customerToEdit.getColAddress().setCity(newCust.getColAddress().getCity());
            customerToEdit.getColAddress().setPostcode(newCust.getColAddress().getPostcode());
            customerToEdit.setDeliveryCost(newCust.getDeliveryCost());
            completed = "<html>Customer <b>" + customerToEdit.getFullName() 
                    + "</b> successfully edited.</html>";
        }
        return completed;
    }

    @Override
    public String undoCommand() {
        String completed = "";
        if (this.isValid()) {
            customerToEdit.setForename(copyOldCust.getForename());
            customerToEdit.setSurname(copyOldCust.getSurname());
            customerToEdit.getColAddress().setAddressLine1(copyOldCust.getColAddress().getAddressLine1());
            customerToEdit.getColAddress().setAddressLine2(copyOldCust.getColAddress().getAddressLine2());
            customerToEdit.getColAddress().setCity(copyOldCust.getColAddress().getCity());
            customerToEdit.getColAddress().setPostcode(copyOldCust.getColAddress().getPostcode());
            customerToEdit.setDeliveryCost(copyOldCust.getDeliveryCost());
            completed = "<html>Edit on customer <b>" + customerToEdit.getFullName() 
                    + "</b> successfully undone.</html>";
        }
        return completed;
    }
}
