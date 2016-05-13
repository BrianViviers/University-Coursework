/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

/**
 * This interface must be implemented by any class (and therefore object) that 
 * will be the "subject" to be "observed" by another object.
 * @author rtucker
 */
public interface ISubject {
    
    /**
     * This method registers a new observer with the subject. After this method 
     * completes successfully the newly registered object will receive notifications
     * to update when the state of this subject object is changed.
     * @param o - The object that needs to receive notification of changes to this
     * subjects state
     * @return - Boolean True if the provided object has been added to the list 
     * of observers and will receive notification when this subject changes state,
     * False otherwise.
     */
    Boolean registerObserver(IObserver o);
    
    /**
     * This method removes an object from the list of objects receiving notification
     * of state changes to this subject object.
     * @param o - The object that no longer needs to receive notification of 
     * changes to this subjects state.
     * @return - Boolean True if this object will not receive notification of changes
     * to this subjects state, False otherwise.
     */
    Boolean removeObserver(IObserver o);
    
    /**
     * This method informs all currently registered observers that this subject
     * has changed its state and they need to update as a result.
     */
    void notifyObservers();
    
}
