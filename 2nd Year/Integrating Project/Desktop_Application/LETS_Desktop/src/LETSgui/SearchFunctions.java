/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LETSgui;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author PRCSA
 */
public class SearchFunctions {
  
    

    /**
     * this is a search function that is enabled when a search is made
     * @param table - JTable being the table to set the row sorter on
     * @param searchField - The text field the search is going to be.
     */
    
    
    public void searchFunction(JTable table, JTextField searchField)
    {
        TableModel model = table.getModel();
        TableRowSorter sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        RowFilter<TableModel, Object> rf;

        rf = RowFilter.regexFilter("(?i)"+searchField.getText(), 1);
        sorter.setRowFilter(rf);
    }
}
