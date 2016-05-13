/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * A class with a web service to get a count on transactions.
 *
 * @author BrianV
 */
@Stateless
@Path("counttransactions")
public class CountTransactionsREST {

    /**
     * Default constructor
     */
    public CountTransactionsREST() {
    }

    /**
     * GET web service which gets a count of the number of transactions a member
     * still need to finalise.
     *
     * @param id - Long representing the ID of the member to who the
     * transactions belong.
     * @return - Integer representing the count of transactions.
     */
    @GET
    @Path("outgoing/{id}")
    public Response countTransactionsNeedPayment(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        int countTransactions = -1;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            countTransactions = db.countTransactionsNeedPaying(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CountTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (countTransactions == -1) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for member ID: " + id).build();
        }

        return Response.ok(countTransactions, MediaType.TEXT_PLAIN).build();
    }
}
