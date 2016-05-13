/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;
import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;

/**
 * This class represents a Rule
 * @author PRCSA
 */
public class Rule implements ISubject, IObserver {
    private int ruleID;
    private String ruleString;
    private ISubject subjectDelegate;
    
    /**
     * Default Constructor initialising the variables.
     */
    public Rule()
    {
       ruleID =0;
       ruleString = "UKNNOWN";
       subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Default Constructor associating the variables with the data.
     * @param ruleID - int value being the Rule ID.
     * @param rule - String value being the rule.
     */
    public Rule(int ruleID, String rule)
    {
        this();
        this.ruleString = rule;
        this.ruleID = ruleID;
    }

    /**
     * Accessor method to retrieve the Rule ID.
     * @return - int value being the Rule ID.
     */
    public int getRuleID() {
        return ruleID;
    }

    /**
     * Accessor method to set the Rule ID.
     * @param ruleID - int value being the Rule ID.
     */
    public void setRuleID(int ruleID) {
        this.ruleID = ruleID;
        this.subjectDelegate.notifyObservers();
        
    }

    /**
     * Accessor method to retrieve the Rule
     * @return - String value being the Rule.
     */
    public String getRule() {
        return ruleString;
    }

    /**
     * Accessor method to set the Rule.
     * @param rule - String value being the Rule.
     */
    public void setRule(String rule) {
        this.ruleString = rule;
        this.subjectDelegate.notifyObservers();
    }


    
    /* This has been commented in the Observer Pattern Library */
    
    @Override
    public Boolean registerObserver(IObserver o) {     
        return this.subjectDelegate.registerObserver(o);
    }


    @Override
    public Boolean removeObserver(IObserver o) {
        return this.subjectDelegate.removeObserver(o);
    }

    
    @Override
    public void notifyObservers() {
        this.subjectDelegate.notifyObservers();
    }


    @Override
    public void update() 
    {
        this.notifyObservers();
    }
}
