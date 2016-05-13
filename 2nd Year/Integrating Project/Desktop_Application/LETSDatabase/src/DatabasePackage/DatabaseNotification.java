/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabasePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JButton;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;



/**
 * Database Notification class used to obtain a database notification
 * @author PRCSA
 */
public class DatabaseNotification {

    /* Creates a connection to the database */
    private OracleConnection conn;

    public int value;
    private JButton notificationA;
    private JButton notificationM;


    public int currentPane;

    /**
     * Default Constructor the database notification package.
     */
    public DatabaseNotification() {
        value = 0;
        notificationM = new JButton();
        notificationA = new JButton();
        currentPane = 0;
    }

    /**
     * Another Default Constructor for the Database Notification
     * @param notificationMember - Button stored on the memebr pane to identify the user of a change in the database.
     * @param notificationAdvert - Button stored on the advert pane to identify the user of a change in the database.
     * @param selectedIndex - int value being the current pane the user is viewing.
     */
    public DatabaseNotification(JButton notificationMember, JButton notificationAdvert, int selectedIndex) {
        this();
        this.notificationA = notificationAdvert;
        this.notificationM = notificationMember;
        this.currentPane = selectedIndex;
    }

    /**
     * Create a connection to the database
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public OracleConnection createConnection() throws SQLException, ClassNotFoundException {
        OracleDriver dr = new OracleDriver();

        Properties props = new Properties();
        props.setProperty("user", "PRCSA");
        props.setProperty("password", "dreamTeam#15");
        String url = "jdbc:oracle:thin:@tom.uopnet.plymouth.ac.uk:1522:ORCL12c";

        return (OracleConnection) dr.connect(url, props);
    }
    
    /* Settings used to the database change notification class, includes which tables to view and the
    properties assigned to the connection.
    */
    public void run() throws SQLException, ClassNotFoundException {
        conn = createConnection();
        conn.setAutoCommit(true);
        Properties prop = new Properties();
        /*Sets the properties of the connection**************************************/
        prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
        prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");
        prop.setProperty(OracleConnection.DCN_BEST_EFFORT, "true");
        DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);
        /****************************************************************************/
        try {
            DCN list = new DCN(this);
            dcr.addListener(list);

            try ( //Add Objects in the registration:
                    Statement stmt = conn.createStatement()) {
                ((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
                /*Select all tables****************************************************************/
                ResultSet rs = stmt.executeQuery("SELECT * FROM MEMBERS,ADVERTS,OFFERS,TRANSACTIONS");
                /***********************************************************************************/
                while (rs.next()) {
                }
                String[] tableNames = dcr.getTables();
                
                for (String tableName : tableNames) {
                    System.out.println(tableName + " is part of the registration.");
                }
                rs.close();
            }
        } catch (SQLException ex) {
            if (conn != null) {
                conn.unregisterDatabaseChangeNotification(dcr);
            }
            throw ex;
        } finally {
            try {
                conn.close();
            } catch (Exception innerex) {
                innerex.printStackTrace();
            }
        }
    }

    class DCN implements DatabaseChangeListener {

        DatabaseNotification demo;

        DCN(DatabaseNotification dem) {
            demo = dem;
        }

        @Override
        public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
            Thread t = Thread.currentThread();
            value++;
            if (currentPane == 0) {
                notificationM.setText(String.valueOf(value));
                notificationM.setEnabled(true);
            } else if (currentPane == 1) {
                notificationA.setText(String.valueOf(value));
                notificationA.setEnabled(true);
            }

            synchronized (demo) {
                demo.notify();
            }
        }
    }
}
