/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Transaction;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
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
 * REST Web Service for getting all transaction a member has on their account.
 *
 * @author BrianV
 */
@Stateless
@Path("membertransactions")
public class GetTransactionsREST {

    /**
     * Creates a new instance of GetTransactionsREST
     */
    public GetTransactionsREST() {
    }

    /**
     * Error response whenever the URL does not contain a transaction type.
     *
     * @return - Server response indicating failure with a message.
     */
    @GET
    public Response getMemberTransactionssError() {
        return Response.status(Response.Status.NO_CONTENT).entity("URL needs to contain a transaction type. e.g incoming/id").build();
    }

    /**
     * Get Web Service for getting all the transactions that a member has where
     * they are receiving money.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("incoming/{id}")
    public Response getIncomingTransactions(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Transaction> memberTransactions = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberTransactions = db.GetTransactionsIncoming(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberTransactions == null || memberTransactions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberTransactions);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get Web Service for getting all the transactions that a member has where
     * they are paying out money.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("outgoing/{id}")
    public Response getOutgoingTransactions(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Transaction> memberTransactions = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberTransactions = db.GetTransactionsOutgoing(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberTransactions == null || memberTransactions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberTransactions);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get Web Service for getting all the transactions that a member has where
     * they have received money and the transaction is completed.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("compincoming/{id}")
    public Response getIncomingCompletedTransactions(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Transaction> memberTransactions = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberTransactions = db.GetTransactionsCompletedIncoming(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberTransactions == null || memberTransactions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberTransactions);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get Web Service for getting all the transactions that a member has where
     * they have paid out money and the transaction is completed.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("compoutgoing/{id}")
    public Response getOutgoingCompletedTransactions(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Transaction> memberTransactions = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberTransactions = db.GetTransactionsCompletedOutgoing(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberTransactions == null || memberTransactions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberTransactions);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("advertid/{id}")
    public Response getTransactionsByAdvertID(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Advert ID cannot be blank").build();
        }
        List<Transaction> memberTransactions = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberTransactions = db.GetTransactionsByAdvertID(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetTransactionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberTransactions == null || memberTransactions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No transactions found for advert ID: " + id).build();
        }
        String json = new Gson().toJson(memberTransactions);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
