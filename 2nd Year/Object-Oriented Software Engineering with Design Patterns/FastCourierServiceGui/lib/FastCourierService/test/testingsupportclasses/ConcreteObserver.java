/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testingsupportclasses;

import observerpattern.IObserver;

/**
 * A concrete implementation of the observer interface for testing purposes
 * @author rtucker
 */
public class ConcreteObserver implements IObserver {
    
    private int count;
    
    public ConcreteObserver(){
        count = 0;
    }

    @Override
    public void update() {
        count++;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
    
    public void resetCount(){
        count = 0;
    }
    
}
