/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LETSgui;

import DataModel.Advert;
import DataModel.AdvertTypeList;
import DataModel.CategoryList;
import DataModel.ItemTypeList;
import DataModel.Member;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author PRCSA
 */
public interface InterfacePane {
    
    /**
     *
     * @param forename
     * @param surname
     * @param add1
     * @param add2
     * @param city
     * @param postcode
     * @param contact
     * @param email
     */
    public void emptyMemberFields(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email);
        
    /**
     *
     * @param advertName
     * @param description
     */
    public void emptyAdvertFields(JTextField advertName, JTextArea description);
        
    /**
     *
     * @param advertName
     * @param description
     * @param bool
     * @param edit
     * @param confirm
     * @param update
     * @param UnactiveAdvert
     * @param itemType
     * @param advertType
     * @param advertCat
     * @param transport
     * @param cost
     * @param datePosted
     * @param dateExpired
     */
    public void fieldStateAdvert(JTextField advertName, JTextArea description,
        Boolean bool, JButton edit, JButton confirm, JButton update, JButton UnactiveAdvert,
        JComboBox itemType, JComboBox advertType,JComboBox advertCat, JComboBox transport, JSpinner cost, JDateChooser datePosted, JDateChooser dateExpired);
        
    /**
     *
     * @param forename
     * @param surname
     * @param add1
     * @param add2
     * @param city
     * @param postcode
     * @param contact
     * @param email
     * @param bool
     * @param edit
     * @param confirm
     * @param cancel
     * @param UnactiveMember
     */
    public void fieldStateMember(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email, Boolean bool,
        JButton edit, JButton confirm, JButton cancel, JButton UnactiveMember);
         
    /**
     *
     * @param forename
     * @param surname
     * @param add1
     * @param add2
     * @param city
     * @param postcode
     * @param contact
     * @param email
     * @param member
     */
    public void setTextMembers(JTextField forename, JTextField surname, JTextField add1, JTextField add2, JTextField city, JTextField postcode, JTextField contact, JTextField email, Member member
    ,JDateChooser dateOfBirth);

    /**
     *
     * @param advertName
     * @param description
     * @param currentAd
     * @param dateCreated
     * @param dateExpired
     * @param cost
     */
    public void setTextAdverts(JTextField advertName, JTextArea description, Advert currentAd, JDateChooser dateCreated, JDateChooser dateExpired,JSpinner cost);
        
    /**
     *
     * @param ad
     */
    public void setUnactiveAdvert(Advert ad);
        
    /**
     *
     * @param member
     */
    public void setUnactiveMember(Member member);
        
    /**
     *
     * @param forename
     * @param surname
     * @param contactNumber
     * @param email
     * @param addr1
     * @param add2
     * @param city
     * @param postcode
     * @param member
     */
    public void updateMember(JTextField forename, JTextField surname, JTextField contactNumber, JTextField email, JTextField addr1, JTextField add2, JTextField city, JTextField postcode, Member member
    ,JDateChooser dob);
         
    /**
     *
     * @param advertName
     * @param description
     * @param currentAd
     * @param itemCombo
     * @param catNum
     * @param adTypeNum
     * @param itl
     * @param atl
     * @param ctl
     * @param dateCreated
     * @param dateExpired
     * @param cost
     * @param transport
     */
    public void updateAdvert(JTextField advertName, JTextArea description, Advert currentAd, JComboBox itemCombo,
        JComboBox catNum, JComboBox adTypeNum, ItemTypeList itl, AdvertTypeList atl, CategoryList ctl, JDateChooser dateCreated, JDateChooser dateExpired, JSpinner cost, JComboBox transport);
}
