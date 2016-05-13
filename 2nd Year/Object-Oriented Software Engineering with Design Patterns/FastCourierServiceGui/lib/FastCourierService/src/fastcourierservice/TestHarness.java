/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice;

import datamodel.Address;
import datamodel.Customer;
import datamodel.CustomerList;
import datamodel.Delivery;
import datamodel.DeliveryStatus;
import java.util.Date;

/**
 * This class executes the integration tests for the data model
 * @author rtucker
 */
public class TestHarness {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Test Harness for data model integration testing
        
        //Setup a simulated GUI and a real data model
        GUISimulator theGUI = new GUISimulator();
        CustomerList theDataModel = new CustomerList();
        //Tell the simulated GUI to observe the data model
        theDataModel.registerObserver(theGUI);
        
        // <editor-fold defaultstate="collapsed" desc="Test 1 - Add myself as a new Customer, this should generate a total of 1 GUI update">
        System.out.println("STARTING ADD CUSTOMER TEST - EXPECT 1 GUI UPDATE");
        //Create my default collection address
        Address defaultAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        Customer me = new Customer("Roy", "Tucker", Delivery.DEFAULTCOST, defaultAddr);
        theDataModel.addCustomer(me);
        System.out.println("FINISHED ADD CUSTOMER TEST");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 2 - Add myself as a new Customer, this should generate a total of 2 GUI updates">
        System.out.println("STARTING CHANGE DETAILS OF DEFAULT COLLECTION ADDRESS TEST - EXPECT 2 GUI UPDATES");
        defaultAddr.setAddressLine1(defaultAddr.getAddressLine1() + "X");
        defaultAddr.setAddressLine2(defaultAddr.getAddressLine2() + "X");
        System.out.println("FINISHED CHANGE DETAILS OF DEFAULT COLLECTION ADDRESS TEST - EXPECT 2 GUI UPDATES");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 3 - Add a delivery, this should generate a total of 1 GUI update">
        System.out.println("STARTING BOOK A NEW DELIVERY INTO THE DATA MODEL TEST - EXPECT 1 GUI UPDATE");
        //Create Delivery Address
        Address delAddr1 = new Address("7 Torr Close", null, "Ivybridge", "PL21 9GE");
        //Create Delivery
        Delivery delivery1 = new Delivery("CONS: 001000", 2, 5, me.getColAddress(), delAddr1);
        me.addDelivery(delivery1);
        System.out.println("FINISHED BOOK A NEW DELIVERY INTO THE DATA MODEL TEST - EXPECT 1 GUI UPDATE");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 4 - Ensure that default delivery cost has been used and cost for delivery 1 is £25.00, this should generate a total of 0 GUI updates">
        System.out.println("STARTING COST OF DELIVERY 1 PRICE TEST - EXPECT 0 GUI UPDATES");
        System.out.println(delivery1.toString());
        System.out.println("Weight shipped in KG: " + delivery1.getWeight());
        System.out.println("Cost per KG shipped: " + delivery1.getCostPerKg());
        System.out.println("Total Cost should be 5KG * £5.00 / KG = £25.00. Actual Cost is: " + delivery1.getTotalCost());
        System.out.println("FINISHED COST OF DELIVERY 1 PRICE TEST - EXPECT 0 GUI UPDATES");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 5 - Ammend delivery cost for delivery 1 is £36.00, this should generate a total of 2 GUI updates">
        System.out.println("STARTING AMMEND COST OF DELIVERY 1 PRICE TEST - EXPECT 2 GUI UPDATES");
        //Ammend weight to 6KG
        delivery1.setWeight(6.0d);
        //Ammend cost per KG
        delivery1.setCostPerKg(6.00d);
        //Print out delivery 1 details
        System.out.println(delivery1.toString());
        System.out.println("Weight shipped in KG: " + delivery1.getWeight());
        System.out.println("Cost per KG shipped: " + delivery1.getCostPerKg());
        System.out.println("Total Cost should be 6KG * £6.00 / KG = £36.00. Actual Cost is: " + delivery1.getTotalCost());
        System.out.println("FINISHED AMMEND COST OF DELIVERY 1 PRICE TEST - EXPECT 2 GUI UPDATES");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 6 - Ammend delivery status for delivery 1 to each possible state, this should generate a total of 5 GUI updates">
        System.out.println("STARTING DELIVERY STATUS FLAG TEST - EXPECT 5 GUI UPDATES");
        delivery1.setStatus(DeliveryStatus.INTRANSIT);
        delivery1.setStatus(DeliveryStatus.DELIVERED);
        delivery1.setStatus(DeliveryStatus.DELIVERYREFUSED);
        delivery1.setStatus(DeliveryStatus.UNDELIVERABLE);
        delivery1.setStatus(DeliveryStatus.PENDINGCOLLECTION);
        System.out.println("FINISHING DELIVERY STATUS FLAG TEST - EXPECT 5 GUI UPDATES");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 7 - Ammend collection & delivery address for delivery 1, this should generate a total of 2 GUI updates">
        System.out.println("STARTING DELIVERY1 COLLECTION & DELIVERY ADDRESS CHANGE TEST - EXPECT 3 GUI UPDATES");
        delivery1.getColAddress().setAddressLine1("14 Kathleaven St");
        delivery1.getDelAddress().setAddressLine1("8 Torr Close");
        System.out.println("FINISHING DELIVERY1 COLLECTION & DELIVERY ADDRESS CHANGE TEST - EXPECT 3 GUI UPDATES");
        // </editor-fold>
        
        //Create a second test delivery for a package coming back to me for a non-standard cost
        Delivery delivery2 = new Delivery("CONS: 001001", 1, 3, delivery1.getDelAddress(), me.getColAddress());
        
        // <editor-fold defaultstate="collapsed" desc="Test 8 - Add a second delivery at non-standard cost, this should generate a total of 1 GUI update">
        System.out.println("STARTING ADD SECOND DELIVERY AT NON-STANDARD COST TEST - EXPECT 1 GUI UPDATE");
        me.addDeliveryWithCost(delivery2, 5.50d);
        //Print out delivery2 details
        System.out.println(delivery2.toString());
        System.out.println("Weight shipped in KG: " + delivery2.getWeight());
        System.out.println("Cost per KG shipped: " + delivery2.getCostPerKg());
        System.out.println("Total Cost should be 3KG * £5.50 / KG = £16.50. Actual Cost is: " + delivery2.getTotalCost());
        System.out.println("FINISHING ADD SECOND DELIVERY AT NON-STANDARD COST TEST - EXPECT 1 GUI UPDATE");
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Test 9 - Set delivery date for both deliveries to today, this should generate a total of 4 GUI updates">
        System.out.println("STARTING SET BOTH DELIVERIES DATE TO TODAY TEST - EXPECT 2 GUI UPDATES");
        delivery1.setDeliveredDate(new Date());
        delivery2.setDeliveredDate(new Date());
        System.out.println("Printing both deliveries status and date delivered. Expect today with both marked as DELIVERED");
        System.out.println("Delivery 1 was delivered on: " + delivery1.getDeliveredDate().toString() + ", Current delivery status is: " + delivery1.getStatus());
        System.out.println("Delivery 1 was delivered on: " + delivery2.getDeliveredDate().toString() + ", Current delivery status is: " + delivery2.getStatus());
        System.out.println("FINISHING SET BOTH DELIVERIES DATE TO TODAY TEST - EXPECT 2 GUI UPDATES");
        // </editor-fold>
    }
}
