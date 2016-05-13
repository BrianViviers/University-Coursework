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
public class AddressTest {
    
    private Address testAddress;
    
    public AddressTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testAddress = new Address("12 Kathleaven St", "St Budeaux", "Plymouth", "PL5 1PZ");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAddressLine1 method, of class Address.
     */
    @Test
    public void testGetAddressLine1() {
        System.out.println("getAddressLine1");
        String expResult = "12 Kathleaven St";
        String result = testAddress.getAddressLine1();
        assertEquals("Line 1 of the address does not match expected result", expResult, result);
    }

    /**
     * Test of setAddressLine1 method, of class Address.
     */
    @Test
    public void testSetAddressLine1() {
        System.out.println("setAddressLine1");
        String addressLine1 = "14 Kathleaven St";
        testAddress.setAddressLine1(addressLine1);
        assertEquals("Line 1 of the address was not SET correctly", addressLine1, testAddress.getAddressLine1());
    }

    /**
     * Test of getAddressLine2 method, of class Address.
     */
    @Test
    public void testGetAddressLine2() {
        System.out.println("getAddressLine2");
        String expResult = "St Budeaux";
        String result = testAddress.getAddressLine2();
        assertEquals("Line 2 of the address does not match expected result", expResult, result);
    }

    /**
     * Test of setAddressLine2 method, of class Address.
     */
    @Test
    public void testSetAddressLine2() {
        System.out.println("setAddressLine2");
        String addressLine2 = "Crownhill";
        testAddress.setAddressLine2(addressLine2);
        assertEquals("Line 2 of the address was not SET correctly", addressLine2, testAddress.getAddressLine2());
    }

    /**
     * Test of getCity method, of class Address.
     */
    @Test
    public void testGetCity() {
        System.out.println("getCity");
        String expResult = "Plymouth";
        String result = testAddress.getCity();
        assertEquals("The addresses city does not match expected result", expResult, result);
    }

    /**
     * Test of setCity method, of class Address.
     */
    @Test
    public void testSetCity() {
        System.out.println("setCity");
        String city = "Brighton";
        testAddress.setCity(city);
        assertEquals("The addresses city was not SET correctly", city, testAddress.getCity());
    }

    /**
     * Test of getPostcode method, of class Address.
     */
    @Test
    public void testGetPostcode() {
        System.out.println("getPostcode");
        String expResult = "PL5 1PZ";
        String result = testAddress.getPostcode();
        assertEquals("The addresses postcode does not match expected result", expResult, result);
    }

    /**
     * Test of setPostcode method, of class Address.
     */
    @Test
    public void testSetPostcode() {
        System.out.println("setPostcode");
        String postcode = "PL21 9GE";
        testAddress.setPostcode(postcode);
        assertEquals("The addresses postcode was not SET correctly", postcode, testAddress.getPostcode());
    }

    /**
     * Test of registerObserver method, of class Address.
     */
    @Test
    public void testRegisterObserver() {
        System.out.println("registerObserver");
        IObserver o = new ConcreteObserver();
        Boolean expResult = true;
        Boolean result = testAddress.registerObserver(o);
        assertEquals("Failed to register an observer with the address object", expResult, result);
    }

    /**
     * Test of removeObserver method, of class Address.
     */
    @Test
    public void testRemoveObserver() {
        System.out.println("removeObserver");
        IObserver o = new ConcreteObserver();
        testAddress.registerObserver(o);
        Boolean expResult = true;
        Boolean result = testAddress.removeObserver(o);
        assertEquals("Could not remove an observer from the test class", expResult, result);
    }

    /**
     * Test of notifyObservers method, of class Address.
     */
    @Test
    public void testNotifyObservers() {
        System.out.println("notifyObservers");
        ConcreteObserver o = new ConcreteObserver();
        testAddress.registerObserver(o);
        testAddress.notifyObservers();
        int expResult = 1;
        int result = o.getCount();
        assertEquals("One call to notify observers should produce 1 update NOT " + result + " updates", expResult, result);
    }

    /**
     * Test of toString method, of class Address.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Address instance = new Address();
        String expResult = "UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}