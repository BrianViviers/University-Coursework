/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.ArrayList;

/**
 * This class represents a list of Item Types.
 *
 * @author PRCSA
 */
public class ItemTypeList {

    private ArrayList<ItemType> itemTypeList;

    /**
     * Default Constructor used to initialise variables.
     */
    public ItemTypeList() {
        itemTypeList = new ArrayList<>();
    }

    /**
     * Method used to add a new item type to the list of item types.
     *
     * @param newItemType - New item type value to add to the list of item
     * types.
     */
    public void addItemType(ItemType newItemType) {
        if (itemTypeList == null) {
            itemTypeList = new ArrayList();
        }
        if (newItemType != null) {

            itemTypeList.add(newItemType);
        }

    }

    /**
     * Method used to get an item type at a specific index.
     *
     * @param index - int value being the index of the ArrayList
     * @return - Item Type value being the item type at the specified index.
     */
    public ItemType getItemTypeAt(int index) {
        ItemType itemType = null;
        if (itemTypeList != null) {
            itemType = itemTypeList.get(index);
        }
        return itemType;
    }

    /**
     * Clears the item type list.
     */
    public void clear() {
        if (itemTypeList != null) {
            this.itemTypeList.clear();
        }
    }
    
    /**
     * Returns the size of the item type list.
     * @return - int value being the size of the list.
     */
    public int amountOfItemTypes()
    {
       return itemTypeList.size();
    }

}
