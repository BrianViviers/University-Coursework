/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;


/**
 * This class represents an Item Type.
 * @author PRCSA
 */
public class ItemType {
    private int itemType_id;
    private String itemTypeUnknown;
    
    /**
     * Default constructor to initialise variables.
     */
    public ItemType()
    {
        itemType_id = 0;
        itemTypeUnknown = "UKNOWN";
    }
    
    /**
     * Default constructor associating variables with the correct data.
     * @param id - int value being the item type id.
     * @param type  - String value being the item type name.
     */
    public ItemType(int id, String type)
    {
        this.itemType_id = id;
        this.itemTypeUnknown = type;
    }

    /**
     * Accessor method to retrieve the item type ID.
     * @return - int value being the item type ID.
     */
    public int getItemType_id() {
        return itemType_id;
    }

    /**
     * Accessor method to set the item type ID.
     * @param itemType_id - int value being the item type ID.
     */
    public void setItemType_id(int itemType_id) {
        this.itemType_id = itemType_id;
    }

    /**
     * Accessor method to retrieve the item type name.
     * @return - String value being the item type name.
     */
    public String getItemType() {
        return itemTypeUnknown;
    }

    /**
     * Accessor method to set the item type name.
     * @param itemType - String value being the item type name.
     */
    public void setItemType(String itemType) {
        this.itemTypeUnknown = itemType;
    }

    
}
