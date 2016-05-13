/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.richardimms.www.android0303.DataModel;



import android.graphics.Bitmap;

import java.util.Date;


/**
 * This class holds all the information relating to an advert in the system
 * @author PhilipEdwards
 */

public class Advert {

    private Long id;
    private String title;
    private String description;
    private Long member_id;
    private Date date_adv;
    private String advert_type;
    private String category;
    private String item_type;
    private Date date_exp;
    private Integer cost;
    private Boolean transport;
    private String is_active;
    private Bitmap advertImage;

    public Advert() {
    }

    /**
     * Accessor method used to get ID for the advert
     * @return - Long value returning the ID of the advert
     */
    public Long getId() {
        return id;
    }

    /**
     * Accessor method used to set the adverts ID
     * @param id - Long value being the adverts ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Accessor method used to get the title of the advert
     * @return - String value being the title of the advert
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor method used to set the title of the adverts
     * @param title - String value being the title of the advert
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor method used to retrieve the description of the advert
     * @return - String value being the description of the advert
     */
    public String getDescription() {
        return description;
    }

    /**
     * Accessor method used to set the description of the advert
     * @param description - String value being the new description of the advert
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Accessor method used to retrieve the date of the advert
     * @return - Date value being the date the advert was posted.
     */
    public Date getDate_adv() {
        return date_adv;
    }

    /**
     * Accessor method used to set the date of the advert
     * @param date_adv - Date value being the new date the advert is posted.
     */
    public void setDate_adv(Date date_adv) {
        this.date_adv = date_adv;
    }

    /**
     * Accessor method used to retrieve the category the advert is assigned too.
     * @return - String value being the category assigned to the advert
     */
    public String getCategory() {
        return category;
    }

    /**
     * Accessor method used to set the new advert category.
     * @param category - String value being the new category to assign to the advert
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *  Accessor method used to retrieve the item type for the advert
     * @return - String value being the item type of the advert
     */
    public String getItem_type() {
        return item_type;
    }

    /**
     * Accessor method used to set the item type for the advert
     * @param item_type - String value being the new adverts item_type
     */
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    /**
     * Accessor method used to retrieve the date the advert expires.
     * @return - Date value being the date the advert expires.
     */
    public Date getDate_exp() {
        return date_exp;
    }

    /**
     * Accessor method used to set the expiry date of the advert.
     * @param date_exp - Date value being the expiry date of the advert.
     */
    public void setDate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }

    /**
     * Accessor method used to retrieve the cost of the advert.
     * @return - Integer value being the cost of the advert.
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * Accessor method used to set the cost of the advert.
     * @param cost - Integer value to set the advert cost.
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * Accessor method used to retrieve the transport status of the advert
     * @return - Boolean value being the transport status.
     */
    public Boolean getTransport() {
        return transport;
    }

    /**
     * Accessor method used to set the transport value for the advert.
     * @param transport - Boolean value being the transport status to set.
     */
    public void setTransport(Boolean transport) {
        this.transport = transport;
    }

    /**
     * Accessor method used to retrieve the Members ID.
     * @return - Long value being the ID of the Member.
     */
    public Long getMember_id() {
        return member_id;
    }

    /**
     * Accessor method used to assign the advert with a Member.
     * @param member_id - Long value being the new Members ID.
     */
    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    /**
     * Accessor method used to retrieve activity status of the advert
     * @return - String value being the status of adverts activity.
     */
    public String getIs_active() {
        return is_active;
    }

    /**
     * Accessor method used to set activity status for the advert
     * @param is_active - String value being the status of activity for the advert/
     */
    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    /**
     * Accessor method used to retrieve the adverts advert type.
     * @return - String value being the current advert type.
     */
    public String getAdvert_type() {
        return advert_type;
    }

    /**
     * Accessor method used to assign the adverts advert type.
     * @param advert_type - String value being the new advert type to assign to an advert
     */
    public void setAdvert_type(String advert_type) {
        this.advert_type = advert_type;
    }

    /**
     * Accessor method used to retrieve the adverts current image.
     * @return - Bitmap value being the adverts image.
     */
    public Bitmap getAdvertImage() {
        return advertImage;
    }

    /**
     * Accessor method used to assign an image to an advert.
     * @param advertImage - Bitmap value being the new image to assign to an advert.
     */
    public void setAdvertImage(Bitmap advertImage) {
        this.advertImage = advertImage;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", member_id=" + member_id +
                ", date_adv=" + date_adv +
                ", advert_type='" + advert_type + '\'' +
                ", category='" + category + '\'' +
                ", item_type='" + item_type + '\'' +
                ", date_exp=" + date_exp +
                ", cost=" + cost +
                ", transport=" + transport +
                ", is_active='" + is_active + '\'' +
                '}';
    }
}
