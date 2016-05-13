/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.text.DateFormat;
import java.util.Date;
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
public class DeliveryTest {
    
    private Delivery testDel;
    private Address colAddress;
    private Address delAddress;
    
    public DeliveryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.colAddress = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        this.delAddress = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        this.testDel = new Delivery("Cons: 001000", 3, 2, colAddress, delAddress);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of appendToNotes method, of class Delivery.
     */
    @Test
    public void testAppendToNotes() {
        System.out.println("appendToNotes");
        String newNote1 = "Delayed by 10 min road works";
        String newNote2 = "No one answering door at delivery address awaiting instructions";
        String expResult = newNote1 + "," + newNote2;
        testDel.appendToNotes(newNote1);
        testDel.appendToNotes(newNote2);
        String result = testDel.getNotes();
        assertEquals("Delivery notes incorrectly stored", expResult, result);
    }

    /**
     * Test of getTotalCost method, of class Delivery.
     */
    @Test
    public void testGetTotalCost() {
        System.out.println("getTotalCost");
        Double expResult = 2 * Delivery.DEFAULTCOST;
        Double result = testDel.getTotalCost();
        assertEquals("Delivery cost incorrectly valued", expResult, result);
    }

    /**
     * Test of getStartDate method, of class Delivery.
     */
    @Test
    public void testGetStartDate() {
        System.out.println("getStartDate");
        DateFormat df = DateFormat.getDateInstance();
        Date today = new Date();
        String expResult = df.format(today);
        String result = df.format(testDel.getStartDate());
        assertEquals("The deliveries start date does not default to today", expResult, result);
    }

    /**
     * Test of getDeliveredDate method, of class Delivery.
     */
    @Test
    public void testGetDeliveredDate() {
        System.out.println("getDeliveredDate");
        Date expResult = null;
        Date result = testDel.getDeliveredDate();
        assertEquals("Expected delivered date to be NULL but it is not", expResult, result);
    }

    /**
     * Test of setDeliveredDate method, of class Delivery.
     */
    @Test
    public void testSetDeliveredDate() {
        System.out.println("setDeliveredDate");
        Date deliveredDate = new Date();
        testDel.setDeliveredDate(deliveredDate);
        assertEquals("Incorrect date stored when delivered date is set", deliveredDate, testDel.getDeliveredDate());
    }

    /**
     * Test of getStatus method, of class Delivery.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        DeliveryStatus expResult = DeliveryStatus.PENDINGCOLLECTION;
        DeliveryStatus result = testDel.getStatus();
        assertEquals("A deliveries default status should be PENDINGCOLLECTION but it is not", expResult, result);
    }

    /**
     * Test of setStatus method, of class Delivery.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        DeliveryStatus status = DeliveryStatus.INTRANSIT;
        testDel.setStatus(status);
        assertEquals("Cannot update deliveries status to INTRANSIT", status, testDel.getStatus());
    }

    /**
     * Test of getNoOfPackages method, of class Delivery.
     */
    @Test
    public void testGetNoOfPackages() {
        System.out.println("getNoOfPackages");
        int expResult = 3;
        int result = testDel.getNoOfPackages();
        assertEquals("Constructor did not set correct number of packages", expResult, result);
    }

    /**
     * Test of setNoOfPackages method, of class Delivery.
     */
    @Test
    public void testSetNoOfPackages() {
        System.out.println("setNoOfPackages");
        int noOfPackages = 4;
        testDel.setNoOfPackages(noOfPackages);
        assertEquals("Could not set number of packages in delivery to four (4)", noOfPackages, testDel.getNoOfPackages());
    }

    /**
     * Test of getWeight method, of class Delivery.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        double expResult = 2.0;
        double result = testDel.getWeight();
        assertEquals("Constructor does not correctly set the deliveries weight", expResult, result, 0.0);
    }

    /**
     * Test of setWeight method, of class Delivery.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        double weight = 20.0;
        testDel.setWeight(weight);
        assertEquals("Set Weight method failed to correctly set delivery weight", weight, testDel.getWeight(), 0.0);
    }

    /**
     * Test of getNotes method, of class Delivery.
     */
    @Test
    public void testGetNotes() {
        System.out.println("getNotes");
        boolean expResult = true;
        boolean result = testDel.getNotes().isEmpty();
        assertEquals("By default a deliveries notes should be an empty string", expResult, result);
    }

    /**
     * Test of getDelAddress method, of class Delivery.
     */
    @Test
    public void testGetDelAddress() {
        System.out.println("getDelAddress");
        Address expResult = this.delAddress;
        Address result = testDel.getDelAddress();
        assertEquals("Constructor does not store consignment delivery address correctly", expResult, result);
    }

    /**
     * Test of setDelAddress method, of class Delivery.
     */
    @Test
    public void testSetDelAddress() {
        System.out.println("setDelAddress");
        Address aDelAddress = new Address("38 Stormcloak Hill", "Morgoth Mountain", "Mordor", "EY2 3EE");
        testDel.setDelAddress(aDelAddress);
        assertEquals("Cannot set consignment delivery address correctly", aDelAddress, testDel.getDelAddress());
    }

    /**
     * Test of getColAddress method, of class Delivery.
     */
    @Test
    public void testGetColAddress() {
        System.out.println("getColAddress");
        Address expResult = this.colAddress;
        Address result = testDel.getColAddress();
        assertEquals("Constructor does not store consignment collection address correctly", expResult, result);
    }

    /**
     * Test of setColAddress method, of class Delivery.
     */
    @Test
    public void testSetColAddress() {
        System.out.println("setColAddress");
        Address aColAddress = null;
        testDel.setColAddress(aColAddress);
        assertEquals("Cannot set consignment collection address correctly", aColAddress, testDel.getColAddress());
    }

    /**
     * Test of getConsignmentNo method, of class Delivery.
     */
    @Test
    public void testGetConsignmentNo() {
        System.out.println("getConsignmentNo");
        String expResult = "Cons: 001000";
        String result = testDel.getConsignmentNo();
        assertEquals("Constructor did not store consignment number correctly", expResult, result);
    }

    /**
     * Test of getCostPerKg method, of class Delivery.
     */
    @Test
    public void testGetCostPerKg() {
        System.out.println("getCostPerKg");
        Double expResult = Delivery.DEFAULTCOST;
        Double result = testDel.getCostPerKg();
        assertEquals("Default cost per KG not used in constructor", expResult, result);
    }

    /**
     * Test of setCostPerKg method, of class Delivery.
     */
    @Test
    public void testSetCostPerKg() {
        System.out.println("setCostPerKg");
        Double costPerKg = 10.5;
        testDel.setCostPerKg(costPerKg);
        assertEquals("Cost per KG could not be correctly set", costPerKg, testDel.getCostPerKg());
    }

    /**
     * Test of update method, of class Delivery.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ConcreteObserver o = new ConcreteObserver();
        testDel.registerObserver(o);
        testDel.update();
        assertEquals("Delivery class does not tell an observer to update correctly", 1, o.getCount());
    }
}