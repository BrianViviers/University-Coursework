/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LETSgui;

import DataModel.Address;
import DataModel.Advert;
import DataModel.AdvertList;
import DataModel.Member;
import DataModel.MemberList;
import DataModel.Review;
import DataModel.ReviewList;
import DataModel.Transaction;
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author PRCSA
 */
public class TransactionsPane {
    


    /**
     * sets the information based on the selected information
     * @param selectedTransaction - Transaction value being the selected Transaction
     * @param txtTransId - Transaction ID text field
     * @param txtCreditsPaid - Credits paid text field
     */
    
    
    public void setTransactionInformation(Transaction selectedTransaction, JTextField txtTransId, JTextField txtCreditsPaid)
    {
        txtTransId.setText(String.valueOf(selectedTransaction.getTransaction_id()));
        txtCreditsPaid.setText(String.valueOf(selectedTransaction.getCredits_paid()));
    }
    

    /**
     * sets the payer information on the transaction pane 
     * @param memberList - MemberList being the list of members to create a payer from
     * @param txtPayer -
     * @param txtForenamePayer - Payer forename
     * @param txtEmailPayer - Email for the Payer
     * @param txtSurnamePayer - Payer surname
     * @param txtContactPayer - Contact number of the payer
     * @param txtAddrLine1Payer - Address Line of the payer
     * @param txtAddrLine2Payer - Address Line 2 of the payer
     * @param txtPostcodePayer - Postcode of the payer
     * @param txtCityPayer - City of the payer
     * @param selectedTransaction - Which transaction has been selected
     */
    
    
    public void setPayer(MemberList memberList, JTextField txtPayer, JTextField txtForenamePayer, JTextField txtEmailPayer, JTextField txtSurnamePayer, JTextField txtContactPayer, JTextField txtAddrLine1Payer,
            JTextField txtAddrLine2Payer, JTextField txtPostcodePayer, JTextField txtCityPayer, Transaction selectedTransaction)
    {
        Member payer = memberList.findMemberByID(selectedTransaction.getPayerNo());
        txtPayer.setText(payer.getFullName());
        txtForenamePayer.setText(payer.getForename());
        txtEmailPayer.setText(payer.getEmail());
        txtSurnamePayer.setText(payer.getSurname());
        txtContactPayer.setText(payer.getContactNo());
        Address payerAddress = payer.getMemberAddress();
        txtAddrLine1Payer.setText(payerAddress.getAddress_1());
        txtAddrLine2Payer.setText(payerAddress.getAddress_2());
        txtPostcodePayer.setText(payerAddress.getPostcode());
        txtCityPayer.setText(payerAddress.getCity());
    }
    
   

    /**
     *sets the payee information on the transaction pane
     * @param memberList - Member 
     * @param txtPayee
     * @param txtForenamePayee - Payee forename
     * @param txtEmailPayee - Email for the Payee
     * @param txtSurnamePayee - Payer surname
     * @param txtContactPayee - Contact number of the payee
     * @param txtAddrLine1Payee - Address Line of the payee
     * @param txtAddrLine2Payee - Address Line 2 of the payee
     * @param txtPostcodePayee - Postcode of the payee
     * @param txtCityPayee - City of the payee
     * @param selectedTransaction - Which transaction has been selected
     */
    
    
    public void setPayee(MemberList memberList, JTextField txtPayee, JTextField txtForenamePayee, JTextField txtEmailPayee, JTextField txtSurnamePayee, JTextField txtContactPayee, JTextField txtAddrLine1Payee,
            JTextField txtAddrLine2Payee, JTextField txtPostcodePayee, JTextField txtCityPayee, Transaction selectedTransaction)
    {
        Member payee = memberList.findMemberByID(selectedTransaction.getPayeeNo());
        txtPayee.setText(payee.getFullName());
        txtForenamePayee.setText(payee.getForename());
        txtEmailPayee.setText(payee.getEmail());
        txtSurnamePayee.setText(payee.getSurname());
        txtContactPayee.setText(payee.getContactNo());
        Address payeeAddress = payee.getMemberAddress();
        txtAddrLine1Payee.setText(payeeAddress.getAddress_1());
        txtAddrLine2Payee.setText(payeeAddress.getAddress_2());
        txtPostcodePayee.setText(payeeAddress.getPostcode());
        txtCityPayee.setText(payeeAddress.getCity());   
    }
    


    /**
     * Sets the advert information on the transaction pane 
     * @param adv - List of adverts to search for transactions
     * @param selectedTransaction - The index of the selected transaction
     * @param txtAdvertNameTransaction - Name of the transaction
     * @param txtDescriptionTransaction - Description of the transaction
     * @param txtCostTransaction - Cost of the transaction
     * @param txtTransportTransaction - Transport value of the transaction
     * @param cbxAdvertTypeTransaction - Advert type of the transaction
     * @param cbxItemTypeTransaction - Item Type of the transaction
     * @param cbxAdvertCatTransaction - Advert Categories of the transaction
     * @param dateCreated - Date the advert was created
     * @param dateExpired - Date the advert expired.
     */
    
    
    public void setAdvertInformation(AdvertList adv, Transaction selectedTransaction, JTextField txtAdvertNameTransaction, JTextArea txtDescriptionTransaction, JTextField txtCostTransaction,
           JTextField txtTransportTransaction, JComboBox cbxAdvertTypeTransaction, JComboBox cbxItemTypeTransaction, JComboBox cbxAdvertCatTransaction,JDateChooser dateCreated, JDateChooser dateExpired )
    {
        int advertID = selectedTransaction.getAdvertId();
        Advert ad = adv.getAdvertById(advertID);
        dateCreated.setDate(ad.getAdPosted());
        dateExpired.setDate(ad.getAdExpires());
        txtAdvertNameTransaction.setText(ad.getAdTitle());
        txtDescriptionTransaction.setText(ad.getAdDescription());
        txtCostTransaction.setText(String.valueOf(ad.getAdCost()));
        txtTransportTransaction.setText(ad.getTransportInc());

    }
    
    /**
     * Sets the review value stars.
     * @param star1 - First Star
     * @param star2 - Second Star
     * @param star3 - Third Star
     * @param star4 - Fourth Star
     * @param star5 - Fifth Star
     * @param transaction - Transaction selected
     * @param revList - Review List
     */
    public void setReviewValue(JLabel star1, JLabel star2, JLabel star3, JLabel star4, JLabel star5, Transaction transaction, ReviewList revList)
    {
        int transID = transaction.getTransaction_id();
        Review review = revList.getReviewByTransactionID(transID);
        int reviewValue = review.getReviewValue();

        ImageIcon star = new ImageIcon("C:/Users/Richard/repos/prcsa/Desktop_Application/LETS_Desktop/src/Desktop_Icons/star.png") ;
        ImageIcon grey_star = new ImageIcon("C:/Users/Richard/repos/prcsa/Desktop_Application/LETS_Desktop/src/Desktop_Icons/star_grey.png");
        System.out.println(reviewValue);
        switch(reviewValue)
        {

            case 0 : 
                star1.setIcon(grey_star);
                star2.setIcon(grey_star);
                star3.setIcon(grey_star);
                star4.setIcon(grey_star);
                star5.setIcon(grey_star);
                break;
            case 1 : 
                star1.setIcon(star);
                star2.setIcon(grey_star);
                star3.setIcon(grey_star);
                star4.setIcon(grey_star);
                star5.setIcon(grey_star);
                break;
            case 2 : 
                star1.setIcon(star);
                star2.setIcon(star);
                star3.setIcon(grey_star);
                star4.setIcon(grey_star);
                star5.setIcon(grey_star);
                break;
            case 3 : 
                star1.setIcon(star);
                star2.setIcon(star);
                star3.setIcon(star);
                star4.setIcon(grey_star);
                star5.setIcon(grey_star);
                break;
            case 4 : 
                 star1.setIcon(star);
                star2.setIcon(star);
                star3.setIcon(star);
                star4.setIcon(star);
                star5.setIcon(grey_star);
                break;
            case 5 : 
                star1.setIcon(star);
                star2.setIcon(star);
                star3.setIcon(star);
                star4.setIcon(star);
                star5.setIcon(star);
                break;
        }
    }
    
    /**
     * Sets the review text on the transaction pane
     * @param reviewText - Text Value being the review text
     * @param transaction - Which transaction the review is assigned too
     * @param revList - ReviewList of to search for.
     */
    public void setReviewText(JTextArea reviewText, Transaction transaction, ReviewList revList)
    {
        int transID = transaction.getTransaction_id();
        Review review = revList.getReviewByTransactionID(transID);
        String textReview = review.getReviewText();
        reviewText.setText(textReview);
    }
}
