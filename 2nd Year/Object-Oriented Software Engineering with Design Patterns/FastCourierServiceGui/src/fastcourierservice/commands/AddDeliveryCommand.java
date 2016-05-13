/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.commands;

import command.interfaces.ICommandBehaviour;
import datamodel.Customer;
import datamodel.Delivery;

/**
 *
 * @author Brian
 */
public class AddDeliveryCommand implements ICommandBehaviour {

    private Delivery deliveryAdded = null;
    private Customer objTargetCustomer = null;
    private double cost;

    /**
     *
     * @param objTarget
     * @param objNewTargetCustomer
     */
    public AddDeliveryCommand(Delivery objTarget, Customer objNewTargetCustomer) {
        this.deliveryAdded = objTarget;
        this.objTargetCustomer = objNewTargetCustomer;
    }
    
    /**
     *
     * @param objTarget
     * @param cost
     * @param objNewTargetCustomer
     */
    public AddDeliveryCommand(Delivery objTarget, double cost, Customer objNewTargetCustomer) {
        this.deliveryAdded = objTarget;
        this.objTargetCustomer = objNewTargetCustomer;
        this.cost = cost;
    }

    private Boolean isValid() {
        Boolean blnValid = false;
        if (null != this.deliveryAdded && null != this.objTargetCustomer) {
            blnValid = true;
        }
        return blnValid;
    }

    /**
     *
     * @return
     */
    @Override
    public String doCommand() {
        String completed = "";
        if (this.isValid()) {
            if (this.cost == 0) {
                this.objTargetCustomer.addDelivery(deliveryAdded);
            } else {
                this.objTargetCustomer.addDeliveryWithCost(deliveryAdded, cost);
            }
            completed = "<html>Delivery <b>" + deliveryAdded.getConsignmentNo()
                    + "</b> has successfully been added for the customer.</html>";
        }
        return completed;
    }

    /**
     *
     * @return
     */
    @Override
    public String undoCommand() {
        Delivery deliveryRemoved;
        String completed = "";;
        if (this.isValid()) {
            try {
                deliveryRemoved = this.objTargetCustomer.removeDeliveryAt(
                        this.objTargetCustomer.getIndexOfDelivery(deliveryAdded));
                if (deliveryRemoved != null) {
                    completed = "<html>Delivery <b>" + deliveryAdded.getConsignmentNo()
                    + "</b> has successfully been removed from the customer.</html>";
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return completed;
    }
}
