/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LETSgui;

import DataModel.AdvertList;
import DataModel.MemberList;
import DataModel.Rule;
import DataModel.RuleList;
import DatabasePackage.SQLStatements;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
/**
 * Rules pane class, this class deals with all the information on the rules pane.
 * @author PRCSA
 */
public class RulesPane {


    

    /**
     ** deletes a rule based on the rule that has been selected
     * @param tblRules - Table value being the table to get information from.
     * @param ruleList - RuleList being the list of rules to search through
     */
    
    
    public void deleteRules(JTable tblRules,RuleList ruleList)
    {
        int index = tblRules.getSelectedRow();
        int indexOfRule = Integer.parseInt(tblRules.getValueAt(index, 0).toString());

        Rule removeRule = ruleList.getRuleByID(indexOfRule);
        int removeRuleIndex = ruleList.getIndexOfRule(removeRule);
  
        SQLStatements sql = new SQLStatements();
        try {
            sql.createConnection();
            sql.removeRule(removeRule);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.getLocalizedMessage();
        }
        
        ruleList.removeRule(removeRuleIndex);
    }
    

    /**
     * Modifies a rule based on the rule that has been selected
     * @param tblRules - Table value being the rules table
     * @param txtAreaRules - Text area being the rule to modify
     * @param ruleList - RuleList value being the list of rules.
     */
    
    
    public void modifyRules(JTable tblRules,JTextArea txtAreaRules,RuleList ruleList)
    {
        SQLStatements sql = new SQLStatements();
        int index = tblRules.getSelectedRow();
        int indexOfRule = Integer.parseInt(tblRules.getValueAt(index, 0).toString());

        Rule modifyRule = ruleList.getRuleByID(indexOfRule);

        modifyRule.setRule(txtAreaRules.getText());
        
        try {
            sql.createConnection();
            sql.modifyRules(modifyRule);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.getLocalizedMessage();
        }
    }
    


    /**
     **adds a rule to the database
     * @param addRule - Text area to get the string value to add.
     * @param list - RuleList value being the list to add the rule to.
     */
    
    
    public void addSQLRule(JTextArea addRule,RuleList list)
    {
        SQLStatements sql = new SQLStatements();
        Rule newRule = new Rule();
        newRule.setRule(addRule.getText());
        
        if(addRule.getText().length() > 0)
        {
            list.addRule(newRule);
            try {
              sql.createConnection();
              sql.addRules(newRule);
              sql.closeConnection();
            } catch (SQLException | ClassNotFoundException e) {
              e.getLocalizedMessage();
            }
        }
    }
    


    /**
     * enables the text fields and the buttons on the rule pane 
     * @param bool - Boolean value being the value to enable the set fields
     */
    
    
    public void ruleSelection(Boolean bool,JTextArea txtRules, JButton btnDeleteRule, JButton btnModifyRule,JButton cancelRule,JButton editRule)
    {
        txtRules.setEnabled(bool);
        btnDeleteRule.setEnabled(bool);
        btnModifyRule.setEnabled(bool);
        cancelRule.setEnabled(bool);
        editRule.setEnabled(!bool);
    }

    /**
     *Boolean value being the value to enable the set fields
     *@param bool - Boolean value being the value to enable the set fields
     */
    public void ruleSelectionAdd(Boolean bool, JButton addRules, JButton cancelRule, JButton btnNewRule, JTextArea txtAddRules)
    {
        addRules.setEnabled(bool);
        cancelRule.setEnabled(bool);
        btnNewRule.setEnabled(!bool);
        txtAddRules.setEnabled(bool);
    }
    
    /**
     * Sets the text field when a rule is selected
     * @param ruleList - RuleList to get the rule based off it's ID
     * @param tblRules - Table value to identify which rule has been selected
     * @param rules - TextArea to assign the selected rules text too.
     */
    public void setRuleText(RuleList ruleList,JTable tblRules, JTextArea rules)
    {
        int index = tblRules.getSelectedRow();
        int indexOfRule = Integer.parseInt(tblRules.getValueAt(index, 0).toString());

        Rule selectedRule = ruleList.getRuleByID(indexOfRule);

        rules.setText(selectedRule.getRule());
    }
    
    /**
     * Empties the rule modifications and add text fields
     */
    public void emptyRule(JTextArea ruleModification, JTextArea ruleAdd)
    {
        ruleModification.setText("");
        ruleAdd.setText("");
    }
    
    public void systemInformation(JLabel totalMembers, JLabel currentMembers, JLabel unactiveMembers, JLabel totalAdverts, 
            JLabel currentAdverts, JLabel completedAdverts, MemberList memberList, AdvertList advertList)
    {
        //Total amount of members******************************************/
        totalMembers.setText(String.valueOf(memberList.memberListSize()));
        //Total amount of active members & unactive members****************/
        int activeMemberCount = 0;
        int unactiveMemberCount = 0;
        
        for(int i =0; i < memberList.memberListSize();i++)
        {
            if(memberList.getMemberAt(i).getIsActive().equals("Y"))
            {
                activeMemberCount++;
            }
            else if(memberList.getMemberAt(i).getIsActive().equals("N"))
            {
                unactiveMemberCount++;
            }
        }
      //Set text for the labels*********************************************/  
      currentMembers.setText(String.valueOf(activeMemberCount));
      unactiveMembers.setText(String.valueOf(unactiveMemberCount));
      /*********************************************************************/
      /*Total amount of active adverts ***************************************/
      totalAdverts.setText(String.valueOf(advertList.totalAmountOfAdverts()));
      /*********************************************************************/
      int activeAdverts =0;
      int unactiveAdverts =0;
      for(int i =0 ; i < advertList.totalAmountOfAdverts();i++ )
      {
          if(advertList.getAdvertAtIndex(i).getIsActive().equals("Y"))
          {
              activeAdverts++;
          }
          else if(advertList.getAdvertAtIndex(i).getIsActive().equals("N"))
          {
              unactiveAdverts++;
          }
      }
      /************************************************************************/
      currentAdverts.setText(String.valueOf(activeAdverts));
      completedAdverts.setText(String.valueOf(unactiveAdverts));
      /***********************************************************************/
    }
   
}
