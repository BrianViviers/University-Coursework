/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LETSgui;

import java.awt.Image;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * SetImage class is used to set the image to the advert
 * @author PRCSA
 */
public class SetImage {

    /**
     * Method used to set the image.
     * @param blobImage - Blob value being the image to decode
     * @param labelPhoto - Label being the label to set the image to.
     */
    public void setImageFromBlob(Blob blobImage, JLabel labelPhoto) {
        if (blobImage != null) {
            try {
                int bloblLength = (int) blobImage.length();
                byte[] blobByte = blobImage.getBytes(1, bloblLength);
                ImageIcon ic = new ImageIcon(blobByte);
                Image iconImage = ic.getImage().getScaledInstance(200, 200, Image.SCALE_AREA_AVERAGING);
                ImageIcon scaledIcon = new ImageIcon(iconImage);
                labelPhoto.setIcon(scaledIcon);
            } catch (SQLException ex) {
                Logger.getLogger(LETSMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
