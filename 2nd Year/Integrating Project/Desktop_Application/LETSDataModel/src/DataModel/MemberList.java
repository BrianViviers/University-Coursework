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
 * This class represents a MemberList ( a list of all the members).
 * @author PRCSA
 */
public class MemberList implements IObserver, ISubject {
    
    private ArrayList<Member> memberList = new ArrayList<>();
    private final ISubject subjectDelegate;

    /**
     * Default constructor to initialise the variables.
     */
    public MemberList() {
        this.memberList = new ArrayList<>();
        this.subjectDelegate = new ISubjectImpl();
    }
    
    /**
     * Method used to add a Member to the Member List.
     * @param newMember - Member being the member to add.
     */
    public void addMember(Member newMember) {
        if(null != newMember){
            if(null == this.memberList){
                this.memberList = new ArrayList<>();
            }
           
               if(this.memberList.add(newMember))
               {
                newMember.registerObserver(this);
                //This is the error.
                this.notifyObservers();
               }
        }
    }
    
    /**
     * Method used to get the size of the Member List.
     * @return - int value being the size of the Member List.
     */
    public int memberListSize() {
        int size = 0 ;
        if(this.memberList.size() > 0)
        {
            size =  memberList.size();
        }  
        return size;
    }
    
    /**
     * Method used to get all the names of the Members into a String array.
     * @return - String[] value being all the names in MemberList.
     */
    public String[] getAllNames()
    {
        String[] result = null;
        if(null != this.memberList && 0 < this.memberList.size())
        {
            result = new String[this.memberList.size()];
            for(int i = 0; i < this.memberList.size(); i++){
                result[i] = this.memberList.get(i).getFullName();
            }
        } 
        else 
        {
            result = new String[0];
        }
        return result;
        
    }
 
    /**
     * Method used to get the member at a specific index.
     * @param index - int value being the index of the Member.
     * @return - Member value being the member found at the specific index.
     */
    public Member getMemberAt(int index) {

        if (index < 0 || index >= memberList.size())
        {
            
            return null;
        }
        else
        {
            return memberList.get(index);
        }
    }
        
    /**
     * Method used to get a Member based on their ID.
     * @param id - int value being the unique ID to search for.
     * @return - Member value being the member with the unique ID.
     */
    public Member findMemberByID(int id)
    {
        Member selectedMember = new Member();
        for (Member memberList1 : memberList) {
            if (memberList1.getMemberID() == id) {
                selectedMember = memberList1;
            }
        } 
       return selectedMember;
    }
    
    /**
     * Clears the member list.
     */
    public void clear()
    {
        if(memberList.size()> 0 )
        {
            this.memberList.clear();
        }
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
