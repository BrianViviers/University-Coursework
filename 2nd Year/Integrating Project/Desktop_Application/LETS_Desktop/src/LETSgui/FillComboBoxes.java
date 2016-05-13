/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LETSgui;

import DataModel.Advert;
import javax.swing.JComboBox;

/**
 *
 * @author PRCSA
 */
public class FillComboBoxes {
    
    /* Class used to fill all the combo boxes on each of the panes ******************************************************************/
    public void comboFill(Advert currentAd, JComboBox cbxAdvertType, JComboBox cbxItemType, JComboBox cbxAdvertCat,JComboBox transport)
    {
        //Occupys comboBox for advertType
        int indexOfAdType = currentAd.getAdTypeID();
        int indexCombo = indexOfAdType - 1;
        cbxAdvertType.setSelectedIndex(indexCombo);

        //Occupys comboBox for itemType
        int indexOfItemType = currentAd.getItemTypeID();
        int indexComboItem = indexOfItemType - 1;
        cbxItemType.setSelectedIndex(indexComboItem);

        //Occupys comboBox for category
        int indexOfCategory = currentAd.getCatID();
        int indexComboCat = indexOfCategory - 1;
        cbxAdvertCat.setSelectedIndex(indexComboCat);
        
        transport.setSelectedItem(currentAd.getTransportInc()); 
    }
}
