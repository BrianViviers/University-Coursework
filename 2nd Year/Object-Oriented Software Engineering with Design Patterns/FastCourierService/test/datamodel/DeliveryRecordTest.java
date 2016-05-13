/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rtucker
 */
public class DeliveryRecordTest {
    
    private DeliveryRecord testDelRec;
    private Customer testCust;
    
    public DeliveryRecordTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        this.testCust = new Customer("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        this.testDelRec = new DeliveryRecord(testCust);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addDelivery method, of class DeliveryRecord.
     */
    @Test
    public void testAddDelivery_Delivery() {
        System.out.println("addDelivery");
        Delivery newDelivery = null;
        boolean expResult = false;
        boolean result = this.testDelRec.addDelivery(newDelivery);
        assertEquals("Cannot add a NULL delivery to a delivery record", expResult, result);
    }

    /**
     * Test of addDelivery method, of class DeliveryRecord.
     */
    @Test
    public void testAddDelivery_Delivery_Double() {
        System.out.println("addDelivery");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Double costPerKg = Delivery.DEFAULTCOST * 2;
        boolean expResult = true;
        boolean result = this.testDelRec.addDeliveryWithCost(newDelivery, costPerKg);
        assertEquals("Could not add delivery + non-standard cost ", expResult, result);
        assertEquals("Could not store a non-standard cost value for a delivery", costPerKg, newDelivery.getCostPerKg());
    }

    /**
     * Test of getDeliveryAt method, of class DeliveryRecord.
     */
    @Test
    public void testGetDeliveryAt() {
        System.out.println("getDeliveryAt");
        int index = 0;
        String conNo = "001000";
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery(conNo, 2, 5, this.testCust.getColAddress(), destination);
        this.testDelRec.addDelivery(newDelivery);
        Delivery expResult = newDelivery;
        Delivery result = this.testDelRec.getDeliveryAt(index);
        assertEquals("Incorrect Delivery returned by getDeliveryAt(index)", expResult, result);
        assertEquals("Retrieved delivery does not have the correct consignment no", newDelivery.getConsignmentNo(), result.getConsignmentNo());
    }

    /**
     * Test of updateDeliveryStatus method, of class DeliveryRecord.
     */
    @Test
    public void testUpdateDeliveryStatus() {
        System.out.println("updateDeliveryStatus");
        int index = 0;
        String conNo = "001000";
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery(conNo, 2, 5, this.testCust.getColAddress(), destination);
        this.testDelRec.addDelivery(newDelivery);
        DeliveryStatus newStatus = DeliveryStatus.DELIVERED;
        this.testDelRec.updateDeliveryStatus(index, newStatus);
        assertEquals("Delivery status should have changed to DELIVERED but has not", newStatus, newDelivery.getStatus());
    }

    /**
     * Test of getDeliveriesByStatus method, of class DeliveryRecord.
     */
    @Test
    public void testGetDeliveriesByStatus() {
        System.out.println("getDeliveriesByStatus");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testDelRec.addDelivery(newDelivery1);
        this.testDelRec.addDelivery(newDelivery2);
        this.testDelRec.addDelivery(newDelivery3);
        this.testDelRec.updateDeliveryStatus(0, DeliveryStatus.DELIVERED);
        this.testDelRec.updateDeliveryStatus(1, DeliveryStatus.INTRANSIT);
        this.testDelRec.updateDeliveryStatus(2, DeliveryStatus.INTRANSIT);
        ArrayList expResult = new ArrayList();
        expResult.add(newDelivery2);
        expResult.add(newDelivery3);
        ArrayList notNull = this.testDelRec.getDeliveriesByStatus(DeliveryStatus.DELIVERYREFUSED);
        ArrayList result = this.testDelRec.getDeliveriesByStatus(DeliveryStatus.INTRANSIT);
        assertNotNull("If no deliverys exist with the requested status an empty array list should be returned. Instead NULL was returned", notNull);
        assertEquals("IN-TRANSIT delivery list should have held two (2) deliveries, it does not!", expResult.size(), result.size());
        result = this.testDelRec.getDeliveriesByStatus(DeliveryStatus.DELIVERED);
        assertEquals("DELIVERED items delivery list should have held one (1) delivery, it does not!", 1, result.size());
    }

    /**
     * Test of clearAll method, of class DeliveryRecord.
     */
    @Test
    public void testClearAll() {
        System.out.println("clearAll");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testDelRec.addDelivery(newDelivery1);
        this.testDelRec.addDelivery(newDelivery2);
        this.testDelRec.addDelivery(newDelivery3);
        this.testDelRec.clearAll();
        int expResult = 0;
        int result = this.testDelRec.getNoOfDeliveries();
        assertEquals("DeliveryRecord should have 0 records stored but this is not the case", expResult, result);
    }

    /**
     * Test of getNoOfDeliveries method, of class DeliveryRecord.
     */
    @Test
    public void testGetNoOfDeliveries() {
        System.out.println("getNoOfDeliveries");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testDelRec.addDelivery(newDelivery1);
        this.testDelRec.addDelivery(newDelivery2);
        this.testDelRec.addDelivery(newDelivery3);
        int expResult = 3;
        int result = this.testDelRec.getNoOfDeliveries();
        assertEquals("Stored three (3) deliveries but this is not the number of deliveries stored per the DeliveryRecord", expResult, result);
    }

    /**
     * Test of getIndexOfDelivery method, of class DeliveryRecord.
     */
    @Test
    public void testGetIndexOfDelivery() {
        System.out.println("getIndexOfDelivery");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testDelRec.addDelivery(newDelivery1);
        this.testDelRec.addDelivery(newDelivery2);
        this.testDelRec.addDelivery(newDelivery3);
        int expResult = 2;
        int result = this.testDelRec.getIndexOfDelivery(newDelivery3);
        assertEquals("Incorrect index value returned for a specified delivery", expResult, result);
    }

    /**
     * Test of registerObserver method, of class DeliveryRecord.
     */
    @Test
    public void testRegisterObserver() {
        System.out.println("registerObserver");
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        OwnerTester testOwner = new OwnerTester("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        this.testDelRec = new DeliveryRecord(testOwner);
        Boolean expResult = true;
        Boolean result = this.testDelRec.registerObserver(testOwner);
        assertEquals("Attempting to register the same object twice should report TRUE as the object is registered", expResult, result);
    }

    /**
     * Test of removeObserver method, of class DeliveryRecord.
     */
    @Test
    public void testRemoveObserver() {
        System.out.println("removeObserver");
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        OwnerTester testOwner = new OwnerTester("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        this.testDelRec = new DeliveryRecord(testOwner);
        Boolean expResult = true;
        Boolean result = this.testDelRec.removeObserver(testOwner);
        assertEquals("The delivery records owner should be able to be removed as the constructor should have registered it as an observer", expResult, result);
    }

    /**
     * Test of notifyObservers method, of class DeliveryRecord.
     */
    @Test
    public void testNotifyObservers() {
        System.out.println("notifyObservers");
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        OwnerTester testOwner = new OwnerTester("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        this.testDelRec = new DeliveryRecord(testOwner);
        this.testDelRec.notifyObservers();
        int expResult = 1;
        int result = testOwner.getCount();
        assertEquals("A single call to notifyObservers should lead to a single update call", expResult, result);
    }

    /**
     * Test of update method, of class DeliveryRecord.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        OwnerTester testOwner = new OwnerTester("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        this.testDelRec = new DeliveryRecord(testOwner);
        this.testDelRec.update();
        int expResult = 1;
        int result = testOwner.getCount();
        assertEquals("An update call to the DeliveryRecord should lead to an update call on its owner", expResult, result);
    }
    
    private class OwnerTester extends Customer {
        
        private int count;
        
        public OwnerTester(){
            super();
            this.count = 0;
        }
        
        public OwnerTester(String firstname, String surname, double costPerKg, Address defaultColAddress){
            super(firstname, surname, costPerKg, defaultColAddress);
            this.count = 0;
        }
        
        public int getCount(){
            return this.count;
        }
        
        public void resetCount(){
            this.count = 0;
        }

        @Override
        public void update() {
            this.count++;
        }
    }
}