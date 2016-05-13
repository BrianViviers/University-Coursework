/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.util.ArrayList;

/**
 * This class represents a list of categories.
 * @author PRCSA
 */
public class CategoryList {
    private ArrayList<Category> catList;
    
    /**
     * Default constructor initialising variables.
     */
    public CategoryList()
    {
        this.catList = new ArrayList<>();
    }
    
    /**
     * Method to add a category type to the list of categories.
     * @param newCategory - Category value being the category to add.
     */
    public void addCatType(Category newCategory)
    {
        if(this.catList == null)
        {
            catList = new ArrayList<>();
        }
        if(newCategory != null)
        {
            this.catList.add(newCategory);
        }
    }
    
    /**
     * Method to get a category based on it's index in the ArrayList<>
     * @param index - int value being the index of the category in the list.
     * @return - Category at the specified index.
     */
    public Category getCategoryAt(int index)
    {
        Category catAt = null;
        if(null != this.catList)
        {
            catAt =  catList.get(index);
        }
        return catAt;
    }
    
    /**
     * Method to return the size of the category list.
     * @return - int value being the size of the category list.
     */
    public int amountOfCategories()
    {
        return catList.size();
    }
    
    /**
     * Method to turn the category list into a String array.
     * @return - String Array storing the values of the category list.
     */
    public String[] categoryListToArray()
    {
        String[] catArray = null;
        if (catList != null) {
            catArray = new String[catList.size()];

            for (int i = 0; i < catList.size(); i++) {
                catArray[i] = catList.get(i).getCategory();
            }
        }
        return catArray;
    }
    
    /**
     * Clears the category list.
     */
    public void clear()
    {
        if(this.catList.size() >= 0)
        {
        this.catList.clear();
        }
    }
}
