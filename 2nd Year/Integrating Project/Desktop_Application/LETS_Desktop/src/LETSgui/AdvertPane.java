/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LETSgui;

import DataModel.Address;
import DataModel.Advert;
import DataModel.AdvertType;
import DataModel.AdvertTypeList;
import DataModel.Member;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import DataModel.Category;
import DataModel.CategoryList;
import DataModel.ItemType;
import DataModel.ItemTypeList;
import DatabasePackage.SQLStatements;
import com.toedter.calendar.JDateChooser;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

/**
 * 
 * @author PRCSA
 */
public class AdvertPane implements InterfacePane{

    /* ALL Comments are provided in the InterfacePane */
    @Override
    public void emptyMemberFields(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email) {
        forename.setText("");
        surname.setText("");
        add1.setText("");
        add2.setText("");
        city.setText("");
        postcode.setText("");
        contact.setText("");
        email.setText("");
    }


    @Override
    public void emptyAdvertFields(JTextField advertName, JTextArea description) {
        advertName.setText("");
        description.setText("");
    }


    @Override
    public void fieldStateAdvert(JTextField advertName, JTextArea description, Boolean bool, JButton edit, JButton confirm, JButton update, JButton UnactiveAdvert, JComboBox itemType, JComboBox advertType, JComboBox advertCat, JComboBox transport, JSpinner cost, JDateChooser datePosted, JDateChooser dateExpired) {
        advertName.setEnabled(bool);
        description.setEnabled(bool);
        edit.setEnabled(!bool);
        update.setEnabled(bool);
        confirm.setEnabled(bool);
        UnactiveAdvert.setEnabled(bool);
        cost.setEnabled(bool);
        itemType.setEnabled(bool);
        advertType.setEnabled(bool);
        advertCat.setEnabled(bool);
        transport.setEnabled(bool);
        datePosted.setEnabled(bool);
        dateExpired.setEnabled(bool);
    }


    @Override
    public void fieldStateMember(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email, Boolean bool, JButton edit, JButton confirm, JButton cancel, JButton UnactiveMember) {
        forename.setEnabled(bool);
        surname.setEnabled(bool);
        add1.setEnabled(bool);
        add2.setEnabled(bool);
        city.setEnabled(bool);
        postcode.setEnabled(bool);
        contact.setEnabled(bool);
        email.setEnabled(bool);
        edit.setEnabled(!bool);
        confirm.setEnabled(bool);
        cancel.setEnabled(bool);
        UnactiveMember.setEnabled(bool);
    }


    @Override
    public void setTextMembers(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email, Member member,JDateChooser dateofBirth) {
        Member meb = member;

        forename.setText(meb.getForename());
        surname.setText(meb.getSurname());
        email.setText(meb.getEmail());
        contact.setText(meb.getContactNo());

        Address memAddress = meb.getMemberAddress();

        add1.setText(memAddress.getAddress_1());
        add2.setText(memAddress.getAddress_2());
        city.setText(memAddress.getCity());
        postcode.setText(memAddress.getPostcode());
        dateofBirth.setDate(meb.getDateOfBirth());
    }


    @Override
    public void setTextAdverts(JTextField advertName, JTextArea description, Advert currentAd, JDateChooser dateCreated, JDateChooser dateExpired, JSpinner cost) {
        Advert ad = currentAd;
        
        advertName.setText(ad.getAdTitle());
        dateCreated.setDate(ad.getAdPosted());
        dateExpired.setDate(ad.getAdExpires());
        cost.setValue(currentAd.getAdCost());
        description.setText(ad.getAdDescription());
    }


    @Override
    public void setUnactiveAdvert(Advert ad) {
        SQLStatements sql = new SQLStatements();
        ad.setIsActive("N");

        try {
            sql.createConnection();
            sql.setUnactiveAdvert(ad);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void setUnactiveMember(Member member) {
         SQLStatements sql = new SQLStatements();
        member.setIsActive("N");

        try {
            sql.createConnection();
            sql.setUnactiveMember(member);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.getLocalizedMessage();
        }
    }


    @Override
    public void updateMember(JTextField forename, JTextField surname, JTextField contactNumber, JTextField email, JTextField addr1, JTextField add2, JTextField city, JTextField postcode, Member member,
    JDateChooser dob) {
        SQLStatements sql = new SQLStatements();

        Member meb = member;

        meb.setForename(forename.getText());
        meb.setSurname(surname.getText());
        meb.setContactNo(contactNumber.getText());
        meb.setEmail(email.getText());

        Address newMemberAddress = new Address(addr1.getText(), add2.getText(), city.getText(), postcode.getText());
        meb.setMemberAddress(newMemberAddress);
        java.sql.Date dateofbirth = new java.sql.Date(dob.getDate().getTime());
        meb.setDateOfBirth(dateofbirth);

        try {
            sql.createConnection();
            sql.updateMemberDetails(meb);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.getLocalizedMessage();
        }
    }


    @Override
    public void updateAdvert(JTextField advertName, JTextArea description, Advert currentAd, JComboBox itemCombo, JComboBox catNum, JComboBox adTypeNum, ItemTypeList itl, AdvertTypeList atl, CategoryList ctl, JDateChooser dateCreated, JDateChooser dateExpired, JSpinner cost, JComboBox transport) {

        SQLStatements sql = new SQLStatements();

        currentAd.setAdTitle(advertName.getText());
        currentAd.setAdDescription(description.getText());
        currentAd.setTransportInc(transport.getSelectedItem().toString());
        
        int costAdv = (int) cost.getValue();
        currentAd.setAdCost(costAdv);
        
        int itemComboInt = itemCombo.getSelectedIndex();
        ItemType saveItemType = itl.getItemTypeAt(itemComboInt);
        currentAd.setItemTypeID(saveItemType.getItemType_id());

        int advertTypeNum = adTypeNum.getSelectedIndex();
        AdvertType saveAdvType = atl.getAdvertTypeAt(advertTypeNum);
        currentAd.setAdTypeID(saveAdvType.getAdvertType_id());

        int adveryCatNum = catNum.getSelectedIndex();
        Category saveCatType = ctl.getCategoryAt(adveryCatNum);
        currentAd.setCatID(saveCatType.getCategory_id());

        
        java.sql.Date newAdvDate = new java.sql.Date(dateCreated.getDate().getTime());
        java.sql.Date newExpDate = new java.sql.Date(dateExpired.getDate().getTime());
        
        currentAd.setAdPosted(newAdvDate);
        currentAd.setAdExpires(newExpDate);
        try {
            sql.createConnection();
            sql.updateAdvertDetails(currentAd);
            sql.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.getLocalizedMessage();
        }
    }
}
