/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.gui;

/**
 * Enumeration for declaring the three types of reports available.
 * @author Brian Viviers
 */
public enum DeliveryReportType {
    
    /**
     * Used to declare that the report created 
     * should be of the completed type.
     */
    COMPLETED,
    /**
     * Used to declare that the report created 
     * should be of the work in progress type.
     */
    WORKINPROGRESS,
    /**
     * Used to declare that the report created
     * should be of all deliveries type.
     */
    AllDELIVERIES;   
}
