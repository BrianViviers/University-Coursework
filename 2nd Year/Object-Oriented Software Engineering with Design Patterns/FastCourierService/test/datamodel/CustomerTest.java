/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.util.ArrayList;
import observerpattern.IObserver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testingsupportclasses.ConcreteObserver;

/**
 *
 * @author rtucker
 */
public class CustomerTest {
    
    private Customer testCust;
    
    public CustomerTest() {
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setForename method, of class Customer.
     */
    @Test
    public void testSetForename() {
        System.out.println("setForename");
        String forename = "Alan";
        this.testCust.setForename(forename);
        String expResult = "Alan";
        assertEquals("Forename should have been set to Alan", expResult, this.testCust.getForename());
    }

    /**
     * Test of setSurname method, of class Customer.
     */
    @Test
    public void testSetSurname() {
        System.out.println("setSurname");
        String surname = "Hocking";
        this.testCust.setSurname(surname);
        assertEquals("Surname should have been set to Hocking", surname, this.testCust.getSurname());
    }

    /**
     * Test of addDelivery method, of class Customer.
     */
    @Test
    public void testAddDelivery_Delivery() {
        System.out.println("addDelivery");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        boolean expResult = true;
        boolean result = this.testCust.addDelivery(newDelivery);
        assertEquals("Could not add a delivery to the customer", expResult, result);
    }

    /**
     * Test of addDelivery method, of class Customer.
     */
    @Test
    public void testAddDelivery_Delivery_Double() {
        System.out.println("addDelivery");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Double costPerKg = Delivery.DEFAULTCOST * 2;
        boolean expResult = true;
        boolean result = this.testCust.addDeliveryWithCost(newDelivery, costPerKg);
        assertEquals("Could not add delivery + non-standard cost ", expResult, result);
        assertEquals("Could not store a non-standard cost value for a delivery", costPerKg, newDelivery.getCostPerKg());
    }

    /**
     * Test of getDeliveryAt method, of class Customer.
     */
    @Test
    public void testGetDeliveryAt() {
        System.out.println("getDeliveryAt");
        int index = 0;
        String conNo = "001000";
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery(conNo, 2, 5, this.testCust.getColAddress(), destination);
        this.testCust.addDelivery(newDelivery);
        Delivery expResult = newDelivery;
        Delivery result = this.testCust.getDeliveryAt(index);
        assertEquals("Incorrect Delivery returned by getDeliveryAt(index)", expResult, result);
        assertEquals("Retrieved delivery does not have the correct consignment no", newDelivery.getConsignmentNo(), result.getConsignmentNo());
    }

    /**
     * Test of updateDeliveryStatus method, of class Customer.
     */
    @Test
    public void testUpdateDeliveryStatus() {
        System.out.println("updateDeliveryStatus");
        int index = 0;
        String conNo = "001000";
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery(conNo, 2, 5, this.testCust.getColAddress(), destination);
        this.testCust.addDelivery(newDelivery);
        DeliveryStatus newStatus = DeliveryStatus.DELIVERED;
        this.testCust.updateDeliveryStatus(index, newStatus);
        assertEquals("Delivery status should have changed to DELIVERED but has not", newStatus, newDelivery.getStatus());
    }

    /**
     * Test of getDeliveriesByStatus method, of class Customer.
     */
    @Test
    public void testGetDeliveriesByStatus() {
        System.out.println("getDeliveriesByStatus");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testCust.addDelivery(newDelivery1);
        this.testCust.addDelivery(newDelivery2);
        this.testCust.addDelivery(newDelivery3);
        this.testCust.updateDeliveryStatus(0, DeliveryStatus.DELIVERED);
        this.testCust.updateDeliveryStatus(1, DeliveryStatus.INTRANSIT);
        this.testCust.updateDeliveryStatus(2, DeliveryStatus.INTRANSIT);
        ArrayList expResult = new ArrayList();
        expResult.add(newDelivery2);
        expResult.add(newDelivery3);
        ArrayList notNull = this.testCust.getDeliveriesByStatus(DeliveryStatus.DELIVERYREFUSED);
        ArrayList result = this.testCust.getDeliveriesByStatus(DeliveryStatus.INTRANSIT);
        assertNotNull("If no deliverys exist with the requested status an empty array list should be returned. Instead NULL was returned", notNull);
        assertEquals("IN-TRANSIT delivery list should have held two (2) deliveries, it does not!", expResult.size(), result.size());
        result = this.testCust.getDeliveriesByStatus(DeliveryStatus.DELIVERED);
        assertEquals("DELIVERED items delivery list should have held one (1) delivery, it does not!", 1, result.size());
    }

    /**
     * Test of clearAll method, of class Customer.
     */
    @Test
    public void testClearAll() {
        System.out.println("clearAll");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testCust.addDelivery(newDelivery1);
        this.testCust.addDelivery(newDelivery2);
        this.testCust.addDelivery(newDelivery3);
        this.testCust.clearAll();
        int expResult = 0;
        int result = this.testCust.getNoOfDeliveries();
        assertEquals("Customer should have 0 records stored but this is not the case", expResult, result);
    }

    /**
     * Test of getNoOfDeliveries method, of class Customer.
     */
    @Test
    public void testGetNoOfDeliveries() {
        System.out.println("getNoOfDeliveries");
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testCust.addDelivery(newDelivery1);
        this.testCust.addDelivery(newDelivery2);
        this.testCust.addDelivery(newDelivery3);
        int expResult = 3;
        int result = this.testCust.getNoOfDeliveries();
        assertEquals("Stored three (3) deliveries but this is not the number of deliveries stored per the Customer object", expResult, result);
    }

    /**
     * Test of getIndexOfDelivery method, of class Customer.
     */
    @Test
    public void testGetIndexOfDelivery() {
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Address dest2 = new Address("2 Middleton Walk", null, "Plymouth", "PL5 2DF");
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        Delivery newDelivery2 = new Delivery("001001", 2, 5, destination, this.testCust.getColAddress());
        Delivery newDelivery3 = new Delivery("001002", 2, 5, this.testCust.getColAddress(), dest2);
        this.testCust.addDelivery(newDelivery1);
        this.testCust.addDelivery(newDelivery2);
        this.testCust.addDelivery(newDelivery3);
        int expResult = 2;
        int result = this.testCust.getIndexOfDelivery(newDelivery3);
        assertEquals("Incorrect index value returned for a specified delivery", expResult, result);
    }

    /**
     * Test of registerObserver method, of class Customer.
     */
    @Test
    public void testRegisterObserver() {
        System.out.println("registerObserver");
        IObserver o = new ConcreteObserver();
        Boolean expResult = true;
        Boolean result = this.testCust.registerObserver(o);
        assertEquals("Customer object rejected a valid observer", expResult, result);
    }

    /**
     * Test of removeObserver method, of class Customer.
     */
    @Test
    public void testRemoveObserver() {
        System.out.println("removeObserver");
        IObserver o = new ConcreteObserver();
        Boolean expResult = true;
        Boolean result = this.testCust.removeObserver(o);
        assertEquals("When trying to remove an observer that was never registered with an object the removeObserver method should return true", expResult, result);
    }

    /**
     * Test of notifyObservers method, of class Customer.
     */
    @Test
    public void testNotifyObservers() {
        System.out.println("notifyObservers");
        ConcreteObserver o = new ConcreteObserver();
        this.testCust.registerObserver(o);
        this.testCust.notifyObservers();
        int expResult = 1;
        int result = o.getCount();
        assertEquals("A call to notifyObservers on the customer object should create one (1) update", expResult, result);
    }

    /**
     * Test of update method, of class Customer.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ConcreteObserver o = new ConcreteObserver();
        this.testCust.registerObserver(o);
        this.testCust.update();
        int expResult = 1;
        int result = o.getCount();
        assertEquals("One call to Customers update method should lead to one observer update", expResult, result);
    }

    /**
     * Test of getDeliveryCost method, of class Customer.
     */
    @Test
    public void testGetDeliveryCost() {
        System.out.println("getDeliveryCost");
        double expResult = Delivery.DEFAULTCOST;
        double result = this.testCust.getDeliveryCost();
        assertEquals("By default the customers agreed delivery cost should be the Delivery.DEFAULTCOST constant", expResult, result, 0.0);
    }

    /**
     * Test of setDeliveryCost method, of class Customer.
     */
    @Test
    public void testSetDeliveryCost() {
        System.out.println("setDeliveryCost");
        double deliveryCost = 10.0;
        this.testCust.setDeliveryCost(deliveryCost);
        Address destination = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        Delivery newDelivery = new Delivery("001000", 2, 5, this.testCust.getColAddress(), destination);
        this.testCust.addDelivery(newDelivery);
        double result = newDelivery.getCostPerKg();
        assertEquals("Customer object does not use its default delivery cost properly", deliveryCost, result, 0.0);
    }

    /**
     * Test of getColAddress method, of class Customer.
     */
    @Test
    public void testGetColAddress() {
        System.out.println("getColAddress");
        String expResult = "12 Kathleaven St, St Budeaux, Plymouth, PL5 1PZ";
        Address result = this.testCust.getColAddress();
        assertEquals("The expected address value was not returned by the customer", expResult, result.toString());
    }

    /**
     * Test of setColAddress method, of class Customer.
     */
    @Test
    public void testSetColAddress() {
        System.out.println("setColAddress");
        Address colAddress = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        this.testCust.setColAddress(colAddress);
        assertEquals("The first line of the customers collection address was incorrect", "7 Torr Close", this.testCust.getColAddress().getAddressLine1());
        assertEquals("The second line of the customers collection address was incorrect", "", this.testCust.getColAddress().getAddressLine2());
        assertEquals("The city line of the customers collection address was incorrect", "Ivybridge", this.testCust.getColAddress().getCity());
        assertEquals("The postcode line of the customers collection address was incorrect", "PL21 9GE", this.testCust.getColAddress().getPostcode());
    }

    /**
     * Test of toString method, of class Customer.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Customer instance = new Customer();
        String expResult = "UNKNOWN UNKNOWN";
        String result = instance.toString();
        assertEquals("A Customer object built using the default constructor should have it's name fields initalised to UNKNOWN", expResult, result);
    }
}