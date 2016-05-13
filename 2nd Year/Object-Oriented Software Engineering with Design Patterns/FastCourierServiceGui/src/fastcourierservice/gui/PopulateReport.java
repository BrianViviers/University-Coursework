/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastcourierservice.gui;

import datamodel.Customer;
import datamodel.CustomerList;
import datamodel.Delivery;
import datamodel.DeliveryStatus;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Class which creates a thread for filling the reports tables in the 
 * background so that the front end application can continue to be used.
 * @author Brian Viviers
 */
public class PopulateReport extends SwingWorker<Void, String[]> implements IPopulateReport {

    private CustomerList customers;
    private ArrayList<DeliveryStatus> delStatusList;
    private DefaultTableModel newModel;
    private JTable reportTable;
    private Double dblGrandTotal;
    private Boolean minimalReport;
    private DeliveryStatus filter;

    /**
     * Constructor which creates a new object which extends SwingWorker.
     * @param table - JTable which is the table to be filled.
     * @param list - CustomerList containing all customers.
     * @param statusList - ArrayList<DeliveryStatus> containing all the delivery
     * statuses for this report.
     * @param minimal - Boolean True if only showing customers with deliveries, False
     * otherwise.
     * @param filter - DeliveryStatus being the status by which to filter the report.
     */
    public PopulateReport(JTable table, CustomerList list,
            ArrayList<DeliveryStatus> statusList, Boolean minimal, DeliveryStatus filter) {
        this.customers = list;
        this.delStatusList = statusList;
        this.newModel = new DefaultTableModel();
        this.reportTable = table;
        this.minimalReport = minimal;
        this.filter = filter;
        dblGrandTotal = 0.0d;
    }

    @Override
    protected void process(List<String[]> chunks) {
        for (Object[] row : chunks) {
            newModel.addRow(row);
        }
        this.reportTable.setModel(newModel);
    }

    @Override
    protected void done() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        reportTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        reportTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        if (newModel.getRowCount() > 0) {
            String[] blankRow = {"", "", "", ""};
            String[] finalRow = new String[3];
            finalRow[0] = "<html><b>Report Total</b></html>";
            finalRow[1] = "";
            finalRow[2] = "<html><b>" + (new DecimalFormat("0.00")).format(dblGrandTotal)
                    + "</b></html>";
            newModel.addRow(blankRow);
            newModel.addRow(finalRow);
        } else {
            newModel.setRowCount(0);
        }
    }

    @Override
    public void populateReport() {
        try {
            this.execute();
        } catch (Exception ex) {
            Logger.getLogger(PopulateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        int countDel = 0;
        Boolean aCustHasDeliveries = false;

        if (null != this.customers) {
            newModel.addColumn("<html><b>Customer Name</b></html>");
            newModel.addColumn("<html><b>No. of Deliveries</b></html>");
            newModel.addColumn("<html><b>Customer Total Value</b></html>");
            Customer cust;

            for (int i = 0; i < customers.getSize(); i++) {
                cust = customers.getCustomerAt(i);
                if (minimalReport) {
                    if (null != filter) {
                        if (cust.getDeliveriesByStatus(filter).size() > 0) {
                            fillTable(cust, countDel);
                            aCustHasDeliveries = true;
                        }
                    } else {
                        for (DeliveryStatus delStat : delStatusList) {
                            if (cust.getDeliveriesByStatus(delStat).size() > 0) {
                                fillTable(cust, countDel);
                                aCustHasDeliveries = true;
                                break;
                            }
                        }
                    }
                } else {
                    fillTable(cust, countDel);
                    aCustHasDeliveries = true;
                }
            }
            if (!aCustHasDeliveries) {
                String[] rowData = {"", "", "", ""};
                publish(rowData);
            }
        }
        return null;
    }

    private void fillTable(Customer cust, int countDel) {
        Delivery del;
        double dblTotal = 0.0d;
        for (int j = 0; j < cust.getNoOfDeliveries(); j++) {
            del = cust.getDeliveryAt(j);
            if (null != filter) {
                if (del.getStatus().equals(filter)) {
                    dblTotal += del.getTotalCost();
                    countDel++;
                }
                //double test = testMultiThreading();
            } else {
                for (DeliveryStatus delStat : delStatusList) {
                    if (del.getStatus().equals(delStat)) {
                        dblTotal += del.getTotalCost();
                        countDel++;
                    }
                    //double test = testMultiThreading();
                }
            }
        }
        dblGrandTotal += dblTotal;
        String[] rowData = new String[3];
        rowData[0] = cust.getFullName();
        rowData[1] = Integer.toString(countDel);
        rowData[2] = (new DecimalFormat("0.00")).format(dblTotal);
        publish(rowData);
    }

    /* 
     * USED TO TEST WHETHER MULTITHREADING WORKS
     */
    private double testMultiThreading() {
        double z = 0.0;
        for (int b = 0; b < 29999; b++) {
            for (int c = 0; c < 99999; c++) {
                for (int k = 0; k < 99999; k++) {
                    double x = 10000;
                    double y = x * 2000 * 9999;
                    z = x * y;
                }
            }
        }
        return z;
    }
}