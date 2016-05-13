/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LETSgui;

import DataModel.AdvertList;
import DataModel.BidList;
import DataModel.Member;
import DataModel.MemberList;
import DataModel.RuleList;
import DataModel.TransactionList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Tables class is used to create all the tables.
 * @author PRCSA
 */
public class Tables {

    /**
     * Creates the table on the members pane for the members
     * @param memList - MemberList being the list to create the table from
     * @param table - Table vaule being the table to assign the list to.
     * @param combo - ComboBox value being the filtered combo box
     * @param memberIndex - MemberIndex used to check the member values against.
     */
    public void createTableMember(MemberList memList, JTable table, JComboBox combo, int memberIndex) {

        String[] columns = new String[4];
        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        newModel.addColumn("ID");
        newModel.addColumn("Customer Name");
        newModel.addColumn("Number of adds");
        newModel.addColumn("Email Address");

        for (int i = 0; i < memList.memberListSize(); i++) {
            if (memList.getMemberAt(i).getIsActive().equals("Y") && combo.getSelectedItem().equals("All")) {

                columns[0] = Integer.toString(memList.getMemberAt(i).getMemberID());
                columns[1] = memList.getMemberAt(i).getFullName();
                columns[2] = Integer.toString(memList.getMemberAt(i).getNoAdverts());
                columns[3] = memList.getMemberAt(i).getEmail();

                newModel.addRow(columns);
            } else if (combo.getSelectedItem().equals("With Adverts") && memList.getMemberAt(i).getNoAdverts() > 0) {

                columns[0] = Integer.toString(memList.getMemberAt(i).getMemberID());
                columns[1] = memList.getMemberAt(i).getFullName();
                columns[2] = Integer.toString(memList.getMemberAt(i).getNoAdverts());
                columns[3] = memList.getMemberAt(i).getEmail();
                newModel.addRow(columns);

            } else if (combo.getSelectedItem().equals("Without Adverts") && memList.getMemberAt(i).getNoAdverts() == 0) {

                columns[0] = Integer.toString(memList.getMemberAt(i).getMemberID());
                columns[1] = memList.getMemberAt(i).getFullName();
                columns[2] = Integer.toString(memList.getMemberAt(i).getNoAdverts());
                columns[3] = memList.getMemberAt(i).getEmail();
                newModel.addRow(columns);

            }
        }
        table.setModel(newModel);
        table.setRowHeight(32);
        table.getColumn("ID").setMaxWidth(40);
    }

    public void createTableMemberReports(MemberList memList, JTable table) {

        String[] columns = new String[3];

        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newModel.addColumn("Member ID");
        newModel.addColumn("Customer Name");
        newModel.addColumn("Email Address");

        for (int i = 0; i < memList.memberListSize(); i++) {
            columns[0] = Integer.toString(memList.getMemberAt(i).getMemberID());
            columns[1] = memList.getMemberAt(i).getFullName();
            columns[2] = memList.getMemberAt(i).getEmail();

            newModel.addRow(columns);
        }
        table.setModel(newModel);
        table.setRowHeight(32);

    }

    
    /**
     * creates the table transaction table
     * @param list - TransactionList value being the list to create the table from
     * @param table
     * @param memList
     */
    public void transactionsTable(TransactionList list, JTable table, MemberList memList) {
        String[] columns = new String[3];

        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newModel.addColumn("ID");
        newModel.addColumn("Owner");
        newModel.addColumn("Payer");
        Member payee = new Member();
        Member payer = new Member();
        for (int i = 0; i < list.getNoTransactions(); i++) {
            columns[0] = Integer.toString(list.getTransAt(i).getTransaction_id());
            payee = memList.findMemberByID(list.getTransAt(i).getPayeeNo());
            columns[1] = payee.getFullName();
            payer = memList.findMemberByID(list.getTransAt(i).getPayerNo());
            columns[2] = payer.getFullName();

            newModel.addRow(columns);
        }

        table.setModel(newModel);
        table.setRowHeight(32);
        table.getColumn("ID").setMaxWidth(40);

    }

    
    /**
     * creates an advert table on the members pane
     * @param tableMembers - Table to decide which member is selected
     * @param tblAdverts - Table to assign the advert list to.
     * @param memList - Member List used to assign the advert list
     * @param memberIndex - Index of the member selected.
     */
    public void createTableAdvertsMembers(JTable tableMembers, JTable tblAdverts, MemberList memList, int memberIndex) {
        try
        {
        if (tableMembers.getSelectedRow() ==  -1) {
            tableMembers.setRowSelectionInterval(memberIndex, memberIndex);
        }
            int indexMember = tableMembers.getSelectedRow();

            int testIndex = Integer.parseInt(tableMembers.getValueAt(indexMember, 0).toString());
            
            Member meb = memList.findMemberByID(testIndex);

//            tblAdverts.removeAll();

            String[] rows = new String[2];

            DefaultTableModel newModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            newModel.addColumn("ID");
            newModel.addColumn("Advert Title");
            if (meb != null) {
                for (int i = 0; i < meb.getNoAdverts(); i++) {
                    if(meb.getAdvertAt(i).getIsActive().equals("Y"))
                    {
                            rows[0] = String.valueOf(meb.getAdvertAt(i).getAdvertNo());
                            rows[1] = meb.getAdvertAt(i).getAdTitle();
                            newModel.addRow(rows);                        
                    }
                }
            }
            tblAdverts.setModel(newModel);
            tblAdverts.setRowHeight(32);
            tblAdverts.getColumn("ID").setMaxWidth(40);

        }catch(IllegalArgumentException e)
        {
            
        }
    }
    

    
    /**
     * creates an advert table on the advert pane
     * @param adv - AdvertList to create the table from
     * @param combo - ComboBox which is the filter.
     * @param adverts - Adverts table to assign the list to.
     */
    public void createTableAdvertsAd(AdvertList adv, JComboBox combo, JTable adverts) {

        String[] columns = new String[4];

        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newModel.addColumn("ID");
        newModel.addColumn("Advert Name");

        for (int i = 0; i < adv.totalAmountOfAdverts(); i++) {
            if (adv.getAdvertAtIndex(i).getIsActive().equals("Y")) {
                if (combo.getSelectedItem() == "All") {
                    columns[0] = String.valueOf(adv.getAdvertAtIndex(i).getAdvertNo());
                    columns[1] = adv.getAdvertAtIndex(i).getAdTitle();

                    newModel.addRow(columns);
                } else if (combo.getSelectedItem() == "Completed Adverts") {
                    if (adv.getAdvertAtIndex(i).getAdCompleted() != null) {
                        columns[0] = String.valueOf(adv.getAdvertAtIndex(i).getAdvertNo());
                        columns[1] = adv.getAdvertAtIndex(i).getAdTitle();

                        newModel.addRow(columns);
                    }
                } else if (combo.getSelectedItem() == "Uncompleted Adverts") {
                    if (adv.getAdvertAtIndex(i).getAdCompleted() == null) {
                        columns[0] = String.valueOf(adv.getAdvertAtIndex(i).getAdvertNo());
                        columns[1] = adv.getAdvertAtIndex(i).getAdTitle();

                        newModel.addRow(columns);
                    }
                }
            }
        }

        adverts.setModel(newModel);
        adverts.setRowHeight(32);
        adverts.getColumn("ID").setMaxWidth(40);
    }


    /**
     * creates the rule table
     * @param tableRules - Table to assign the rules to
     * @param ruleList - List of rules to create the table off.
     */
    public void createRulesTable(JTable tableRules, RuleList ruleList) {
        String[] columns = new String[4];

        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newModel.addColumn("ID");
        newModel.addColumn("Rule");

        for (int i = 0; i < ruleList.size(); i++) {
            columns[0] = String.valueOf(ruleList.getRuleAt(i).getRuleID());
            columns[1] = ruleList.getRuleAt(i).getRule();
            newModel.addRow(columns);
        }

        tableRules.setModel(newModel);
        tableRules.setRowHeight(32);
        tableRules.getColumn("ID").setMaxWidth(40);
    }
    
    /**
     * Creates a bids table
     * @param bidTable - Table to assign the bids too
     * @param bidList - List of the bids to create the table
     * @param advertSelected - Index of the selected advert
     * @param memberList - List of members to see if they have any bids
     */
    public void createBidTable(JTable bidTable, BidList bidList, int advertSelected,MemberList memberList)
    {
        String[] columns = new String[4];

        DefaultTableModel newModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newModel.addColumn("Bid Number");
        newModel.addColumn("Bidder");
        newModel.addColumn("Bid Text");
        
        for (int i = 0; i < bidList.getNoBids(); i++) {
            if(bidList.getBidAt(i).getAdvertNo() == advertSelected )
            {
                Member member = memberList.findMemberByID(bidList.getBidAt(i).getOffererNo());
                columns[0] = String.valueOf(bidList.getBidAt(i).getOfferNo());
                columns[1] = member.getFullName();
                columns[2] = bidList.getBidAt(i).getOfferText();
                newModel.addRow(columns);
            }
        }

        bidTable.setModel(newModel);
        bidTable.setRowHeight(32);
        bidTable.getColumn("Bid Number").setMaxWidth(40);
    }
}
