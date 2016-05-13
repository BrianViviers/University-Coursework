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
        
        //Code your INTEGRATION TESTS here
        
        
        // Create a collection address for delivery2
        Address colAddress = new Address("21 Happy Street", "Sad Camp", "Smallville", "SM13 9LL");
         
        // Create a customer
        Customer customer = new Customer("Brian", "Viviers", 5.0, colAddress);
        
        // Adding customer to data model
        System.out.println("Adding a customer to the data model\n-----------------"); 
        theDataModel.addCustomer(customer);
        
        System.out.println("\nUpdates expected (1)");

        

        // Test a customer
        //--------------------------------------------
        // ----------------Setup ---------------------
        
        // Create a collection address for delivery1
        Address colAddressDelivery1 = new Address("55 Go Street", "Beach", "Tokyo", "TY7 9PL");
         // Create a delivery address for delivery1
        Address delAddressDelivery1 = new Address("The Oyster Box", "Salt Rock", "Durban", "DR5 9OB");
        // Create a first delivery
        Delivery delivery1 = new Delivery("Cons: 100", 2, 2, colAddressDelivery1, delAddressDelivery1);
        
        // Create a collection address for delivery2
        Address colAddressDelivery2 = new Address("32 Mopani Street", "Amamzimtoti", "Durban", "DR6 3AM");
         // Create a delivery address for delivery2
        Address delAddressDelivery2 = new Address("56 Lake Drive", "Efford", "Plymouth", "PL4 8IP");
        // Create a second delivery
        Delivery delivery2 = new Delivery("Cons: 101", 3, 3, colAddressDelivery2, delAddressDelivery2);
        
        // Create new address to add to customers collection address
        Address colAddress3 = new Address("4 Hoe Road", "Citadel", "Plymouth", "PL1 2PD");
        
        // -------------End-setup---------------------
        System.out.println("\n\nTesting changing details of a customer\n-----------------");
        System.out.println("Adding a delivery to a customer");
        theDataModel.getCustomerAt(0).addDelivery(delivery1);      
        System.out.println("\nAdding a delivery with cost to a customer");
        theDataModel.getCustomerAt(0).addDeliveryWithCost(delivery2, 8.0);
        System.out.println("\nSetting collection address");
        theDataModel.getCustomerAt(0).setColAddress(colAddress3);      
        System.out.println("\nSetting delivery cost");
        theDataModel.getCustomerAt(0).setDeliveryCost(9.0);       
        System.out.println("\nSetting forename");
        theDataModel.getCustomerAt(0).setForename("Clark");       
        System.out.println("\nSetting surname");
        theDataModel.getCustomerAt(0).setSurname("Kent");
        System.out.println("\nUpdating delivery status");
        theDataModel.getCustomerAt(0).updateDeliveryStatus(0, DeliveryStatus.INTRANSIT);
        
        System.out.println("\nUpdates expected (9)");
        // End test a customer
        
        
        
        // Test a delivery
        //--------------------------------------------
        // ----------------Setup ---------------------
        
        // Create new address to replace existing collection address
        Address colAddressCustomer = new Address("12 Spam Road", "Ford", "Plymouth", "PL5 6HH");
        // Create new address to replace existing delivery address
        Address delAddressCustomer = new Address("45 Egg Street", "Barbican", "Plymouth", "PL2 8IO");
        
        // ----------------End-setup -----------------
        
        System.out.println("\n\nTesting changing details of a delivery\n-----------------");
        System.out.println("Appending to notes");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).appendToNotes("To be left with neighbour");
        System.out.println("\nSetting collection address");    
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setColAddress(colAddressCustomer);      
        System.out.println("\nSetting consignment number");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setConsignmentNo("Cons: 111");       
        System.out.println("\nSetting cost per KG");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setCostPerKg(7.0);       
        System.out.println("\nSetting delivery address");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setDelAddress(delAddressCustomer);       
        System.out.println("\nSetting delivered date");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setDeliveredDate(new Date());        
        System.out.println("\nSetting number of packages");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setNoOfPackages(4);                  
        System.out.println("\nSetting delivery status");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setStatus(DeliveryStatus.INTRANSIT);      
        System.out.println("\nSetting weight");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).setWeight(9.0);
        System.out.println("\nUpdates expected (10)");
      
        // End test a delivery
        
        

        // Test changing the details of the address of a customer
        //--------------------------------------------
        System.out.println("\n\nTesting changing details of the address of a customer\n-----------------");
        System.out.println("Setting address line 1");
        theDataModel.getCustomerAt(0).getColAddress().setAddressLine1("4 Western Drive");
        System.out.println("\nSetting address line 2");
        theDataModel.getCustomerAt(0).getColAddress().setAddressLine2("Laira");
        System.out.println("\nSetting city");
        theDataModel.getCustomerAt(0).getColAddress().setCity("Plymouth");
        System.out.println("\nSetting postcode");
        theDataModel.getCustomerAt(0).getColAddress().setPostcode("PL3 6BQ");
        
        System.out.println("\nUpdates expected (4)");
        // End test changing the address of a customer
        
        
        
        // Test changing the details of the delivery address of a delivery
        //--------------------------------------------
        System.out.println("\n\nTesting changing details of a delivery address of a delivery\n-----------------");
        System.out.println("Setting address line 1");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getDelAddress().setAddressLine1("4 Western Drive");    
        System.out.println("\nSetting address line 2");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getDelAddress().setAddressLine2("Laira");      
        System.out.println("\nSetting city");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getDelAddress().setCity("Plymouth");
        System.out.println("\nSetting postcode");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getDelAddress().setPostcode("PL3 6BQ");
        
        System.out.println("\nUpdates expected (4)");
        // End test changing the delivery address of a delivery
        
        
        
        // Test changing the details of the collection address of a delivery
        //--------------------------------------------
        System.out.println("\n\nTesting changing details of a collection address of a delivery\n-----------------");
        System.out.println("Setting address line 1");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getColAddress().setAddressLine1("7 Eastern Road");    
        System.out.println("\nSetting address line 2");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getColAddress().setAddressLine2("Lipson");      
        System.out.println("\nSetting city");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getColAddress().setCity("Plymouth");
        System.out.println("\nSetting postcode");
        theDataModel.getCustomerAt(0).getDeliveryAt(0).getColAddress().setPostcode("PL2 6PO");
        
        System.out.println("\nUpdates expected (4)");
        // End test changing the collection address of a delivery
        
        
     
        // Remove a customer from the data model
        //--------------------------------------------
        System.out.println("\n\nRemoving a customer from the data model\n-----------------");      
        theDataModel.removeCustomerAt(0);
        System.out.println("\nUpdates expected (1)");
    }
}
