/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author rtucker
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({datamodel.DeliveryTest.class, datamodel.AddressTest.class, datamodel.DeliveryRecordTest.class, datamodel.CustomerTest.class, datamodel.CustomerListTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}