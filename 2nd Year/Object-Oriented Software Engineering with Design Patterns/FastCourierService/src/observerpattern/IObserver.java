/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

/**
 * This interface should be implemented by any class (and therefore object) that
 * wishes to "observe" a "subject" object and respond to notifications that the
 * state of the subject object has been changed.
 * @author rtucker
 */
public interface IObserver {
    
    /**
     * This method executes when the object receives a notification that the observed
     * subjects state has changed.
     */
    void update();
    
}
