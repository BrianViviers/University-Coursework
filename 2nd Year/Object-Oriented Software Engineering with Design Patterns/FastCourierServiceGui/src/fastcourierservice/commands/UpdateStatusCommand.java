/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.commands;

import command.interfaces.ICommandBehaviour;
import datamodel.Delivery;
import datamodel.DeliveryStatus;
import java.util.Date;

/**
 *
 * @author Brian
 */
public class UpdateStatusCommand implements ICommandBehaviour {

    private Delivery deliveryToEdit = null;
    private String oldNotes;
    private DeliveryStatus oldStatus;
    
    private String newNotes;
    private Date newDate;
    private DeliveryStatus newStatus;

    /**
     * Constructor which creates a new UpdateStatusCommand object.
     * @param objTarget - Delivery object which is being updated.
     * @param notes - String being the notes to add to the delivery
     * @param delDate - Date of when this update occurred.
     * @param status - DeliveryStatus being the new status for the delivery.
     */
    public UpdateStatusCommand(Delivery objTarget, String notes, Date delDate, DeliveryStatus status) {
        this.deliveryToEdit = objTarget;
        this.newNotes = notes;
        this.newDate = delDate;
        this.newStatus = status;
        
        this.oldNotes = objTarget.getNotes();
        this.oldStatus = objTarget.getStatus();       
    }

    private Boolean isValid() {
        Boolean blnValid = false;
        if (null != this.deliveryToEdit) {
            blnValid = true;
        }
        return blnValid;
    }

    @Override
    public String doCommand() {
        String completed = "";
        if (this.isValid()) {
            deliveryToEdit.appendToNotes(newNotes);
            deliveryToEdit.setDeliveredDate(newDate);
            deliveryToEdit.setStatus(newStatus);
            completed = "<html>Updating of status on delivery <b>" 
                    + deliveryToEdit.getConsignmentNo().toString() 
                    + "</b> has been done.</html>";  ;
        }
        return completed;
    }

    @Override
    public String undoCommand() {
        String completed = "";
        if (this.isValid()) {
            deliveryToEdit.setDeliveredDate(null);
            deliveryToEdit.setStatus(oldStatus);
            deliveryToEdit.setNotes(oldNotes);
            completed = "<html>Previous update of status on delivery <b>" 
                    + deliveryToEdit.getConsignmentNo().toString() 
                    + "</b> has been undone.</html>";  
        }
        return completed;
    }
}
