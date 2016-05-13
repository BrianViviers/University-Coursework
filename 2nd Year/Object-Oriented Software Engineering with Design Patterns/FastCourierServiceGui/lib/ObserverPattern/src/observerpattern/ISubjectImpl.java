/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class provides a concrete implementation of the ISubject interface. Many 
 * classes may need to implement the ISubject interface, instead of repeating its
 * code many times in different classes this class may be "composed" with the class
 * that needs to implement the ISubject interface which "delegates" the interfaces
 * functions to this class. See Chapter 1 of Head First Design Patterns, especially
 * page 23.
 * @author rtucker
 */
public class ISubjectImpl implements ISubject, Serializable {

    private ArrayList<IObserver> observers;

    /**
     * Default constructor required for serialisation, builds "empty" object
     */
    public ISubjectImpl() {
    }

    @Override
    public Boolean registerObserver(IObserver o) {
        Boolean result = false;
        if (null != o) {
            if (null == this.observers) {
                this.observers = new ArrayList<>();
            }
            //This check ensures an observer can only be added once!
            if (!this.observers.contains(o)) {
                result = this.observers.add(o);
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Boolean removeObserver(IObserver o) {
        Boolean result = false;
        if (null != o) {
            if (null != this.observers) {
                if (this.observers.contains(o)) {
                    result = this.observers.remove(o);
                    //If the observer list is empty save memory by freeing the unused variable
                    if (0 == this.observers.size()) {
                        this.observers = null;
                    }
                } else {
                    result = true;
                }
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void notifyObservers() {
        if(null != this.observers){
            for(IObserver currObserver : this.observers){
                currObserver.update();
            }
        }
    }
}
