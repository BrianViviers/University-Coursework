/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice;

import observerpattern.IObserver;

/**
 * This class is used by the integration test harness to determine if the GUI
 * would have received a notification to update its display. Each time the "update"
 * method is called a message will be printed to the console.
 * @author rtucker
 */
public class GUISimulator implements IObserver {

    @Override
    public void update() {
        System.out.println("The GUI would have received a notification to update");
    }
    
}
