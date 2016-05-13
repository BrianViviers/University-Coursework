/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.util.ArrayList;

import ObserverPattern.IObserver;
import ObserverPattern.ISubject;
import ObserverPattern.ISubjectImpl;

/**
 * This class represents the Rule List.
 * @author PRCSA
 */
public class RuleList implements ISubject, IObserver {
    
    private ArrayList<Rule> ruleList = new ArrayList<>();
    private final ISubject subjectDelegate;
    
    /**
     * Default Constructor initialising the variables.
     */
    public RuleList()
    {
        subjectDelegate = new ISubjectImpl();
        ruleList = new ArrayList<>();
    }
    
    /**
     * Method used to add a Rule to the list of Rules.
     * @param rule - Rule value being a rule to add.
     */
    public void addRule(Rule rule)
    {
        if(rule != null)
        {
            if(null == this.ruleList)
            {
                this.ruleList  = new ArrayList<>();
            }
            
            if(this.ruleList.add(rule))
            {
               rule.registerObserver(this);

               this.notifyObservers();
            }

        }
    }
    
    /**
     * Method used to remove a Rule from the list of Rules.
     * @param ruleIndex - int value being the index of the rule being wished to remove.
     * @return - Returns the Rule that has been removed.
     */
    public Rule removeRule(int ruleIndex)
    {
        Rule result = null;
        if(null != this.ruleList && 0 < this.ruleList.size()){
            if(ruleIndex >= 0 && ruleIndex < this.ruleList.size()){
                result = this.ruleList.get(ruleIndex);
                this.ruleList.remove(ruleIndex); 
                this.removeObserver(this);
                this.notifyObservers();
            } else {
                throw new IndexOutOfBoundsException("No customer in customer list at index " + ruleIndex);
            }
        }
        return result;
    }
    
    /**
     * Method used to return this size of the Rule List.
     * @return - int value being the size of the list.
     */
    public int size()
    {
        return ruleList.size();
    }
    
    /**
     * Method used to get a rule based on it's index.
     * @param index  - int value being the index to get the Rule from.
     * @return - Rule value being the Rule at the index.
     */
    public Rule getRuleAt(int index)
    {
        Rule returnedRule = null;
        if(ruleList != null)
        {
            returnedRule = ruleList.get(index);
        }
        return returnedRule;
    }
    
    /**
     * Method used to get a rule by it's ID
     * @param id - int value being the ID of the Rule.
     * @return - Rule value being the Rule found associated with the ID.
     */
    public Rule getRuleByID(int id)
    {
        Rule selectedRule = new Rule();
        for (Rule ruleList1 : ruleList) {
            if (ruleList1.getRuleID() == id) {
                selectedRule = ruleList1;
            }
        } 
       return selectedRule;
    }
    
    /**
     * Method used to return the index of a specific Rule.
     * @param ruleIndex - Rule value being the Rule to search for.
     * @return - int value being the index of the Rule.
     */
    public int getIndexOfRule(Rule ruleIndex)
    {
        int rule = 0;

        if (ruleIndex != null) {
            rule = this.ruleList.indexOf(ruleIndex);
        }

        return rule;
    }
    
    /**
     * Method used to clear the list of Rules.
     */
    public void clearList()
    {
        this.ruleList.clear();
    }

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
    public void update() {
        this.notifyObservers();
    }
}
