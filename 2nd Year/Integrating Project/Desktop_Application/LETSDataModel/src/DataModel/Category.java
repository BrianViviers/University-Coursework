/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;


/**
 * This class represents a category.
 * @author PRCSA
 */
public class Category {
    
    private int category_id;
    private String categoryName;
    
    /**
     * Default constructor to initialise variables.
     */
    public Category()
    {
        this.categoryName = "UKNOWN";
        this.category_id = 0;
    }
    
    /**
     * Default constructor to assign variables associated data.
     * @param id - int value being the id of the category.
     * @param category - String value being the category name.
     */
    public Category(int id, String category)
    {
        this.category_id = id;
        this.categoryName = category;
    }

    /**
     * Accessor method to retrieve the category ID.
     * @return - int value being the category ID.
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * Accessor method to set the category ID.
     * @param category_id - int value being the category ID.
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * Accessor method to retrieve the category name.
     * @return - String value being the category name.
     */
    public String getCategory() {
        return categoryName;
    }

    /**
     * Accessor method to set the category name.
     * @param category - String value being the category name.
     */
    public void setCategory(String category) {
        this.categoryName = category;
    }
    
}
