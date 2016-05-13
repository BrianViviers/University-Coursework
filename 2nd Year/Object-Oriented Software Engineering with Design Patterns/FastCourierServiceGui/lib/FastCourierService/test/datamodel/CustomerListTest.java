/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

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
public class CustomerListTest {
    
    private CustomerList testCustList;
    private ConcreteObserver iAmTheGui;
    private Customer testCust;
    private Customer testCust2;
    
    public CustomerListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testCustList = new CustomerList();
        Address colAddr = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
        this.testCust = new Customer("Roy", "Tucker", Delivery.DEFAULTCOST, colAddr);
        Address colAddr2 = new Address("7 Torr Close", "", "Ivybridge", "PL21 9GE");
        this.testCust2 = new Customer("Alan", "Tucker", Delivery.DEFAULTCOST, colAddr2);
        Delivery newDelivery1 = new Delivery("001000", 2, 5, this.testCust.getColAddress(), this.testCust2.getColAddress());
        Delivery newDelivery2 = new Delivery("001001", 2, 5, this.testCust2.getColAddress(), this.testCust.getColAddress());
        this.testCust.addDelivery(newDelivery1);
        this.testCust2.addDelivery(newDelivery2);
        this.testCustList.addCustomer(testCust);
        this.testCustList.addCustomer(testCust2);
        this.iAmTheGui = new ConcreteObserver();
        this.testCustList.registerObserver(iAmTheGui);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCustomer method, of class CustomerList.
     */
    @Test
    public void testAddCustomer() {
        System.out.println("addCustomer");
        Customer newCust = new Customer();
        this.testCustList.addCustomer(newCust);
        int expResult = 3;
        int result = this.testCustList.getSize();
        assertEquals("Added a customer but the numer of customer is different to the value expected", expResult, result);
    }

    /**
     * Test of removeCustomerAt method, of class CustomerList.
     */
    @Test
    public void testRemoveCustomerAt() {
        System.out.println("removeCustomerAt");
        Customer removeCustomerAt = this.testCustList.removeCustomerAt(0);
        assertEquals("Incorrect customer removed by the removeCustomerAt method", this.testCust, removeCustomerAt);
    }

    /**
     * Test of getAllNames method, of class CustomerList.
     */
    @Test
    public void testGetAllNames() {
        System.out.println("getAllNames");
        String[] expResult = new String[2];
        expResult[0] = this.testCust.toString();
        expResult[1] = this.testCust2.toString();
        String[] result = this.testCustList.getAllNames();
        assertArrayEquals("CustomerLists getAllNames method has incorrect entries in the array", expResult, result);
    }

    /**
     * Test of getCustomerAt method, of class CustomerList.
     */
    @Test
    public void testGetCustomerAt() {
        System.out.println("getCustomerAt");
        Customer expResult = this.testCust2;
        Customer result = this.testCustList.getCustomerAt(1);
        assertEquals("CustomerLists getCustomerAt retrieved the wrong customer", expResult, result);
    }

    /**
     * Test of getSize method, of class CustomerList.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        int expResult = 2;
        int result = this.testCustList.getSize();
        assertEquals("CustomerLists size method does not return the correct value", expResult, result);
    }

    /**
     * Test of update method, of class CustomerList.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        this.testCustList.update();
        int expResult = 1;
        int result = this.iAmTheGui.getCount();
        assertEquals("CustomerLists update method did not call an observers update method the correct number of times", expResult, result);
    }

    /**
     * Test of registerObserver method, of class CustomerList.
     */
    @Test
    public void testRegisterObserver() {
        System.out.println("registerObserver");
        IObserver o = new ConcreteObserver();
        Boolean expResult = true;
        Boolean result = this.testCustList.registerObserver(o);
        assertEquals("CustomerList reports it failed to add a valid observer", expResult, result);
    }

    /**
     * Test of removeObserver method, of class CustomerList.
     */
    @Test
    public void testRemoveObserver() {
        System.out.println("removeObserver");
        Boolean expResult = true;
        Boolean result = this.testCustList.removeObserver(this.iAmTheGui);
        assertEquals("CustomerList failed to remove a valid observer when asked", expResult, result);
    }

    /**
     * Test of notifyObservers method, of class CustomerList.
     */
    @Test
    public void testNotifyObservers() {
        System.out.println("notifyObservers");
        this.testCustList.notifyObservers();
        this.testCustList.notifyObservers();
        int expResult = 2;
        int result = this.iAmTheGui.getCount();
        assertEquals("Two call to CustomerLists notifyObservers method failed to generate to updates to an observer", expResult, result);
    }
}