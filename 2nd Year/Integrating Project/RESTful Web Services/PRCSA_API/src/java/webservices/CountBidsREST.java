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
 * A class of web services that get a count for different bid states.
 *
 * @author BrianV
 */
@Stateless
@Path("countbids")
public class CountBidsREST {

    /**
     * Default constructor
     */
    public CountBidsREST() {
    }

    /**
     * GET web service which gets a count for incoming bids.
     *
     * @param id - Long representing the members ID to whom the bids belong.
     * @return - Integer representing a count of incoming bids.
     */
    @GET
    @Path("incoming/{id}")
    public Response countBidsNeedAction(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        int countBids = -1;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            countBids = db.countBidsNeedAction(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CountBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (countBids == -1) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }

        return Response.ok(countBids, MediaType.TEXT_PLAIN).build();
    }

    /**
     * GET web service which gets a count for accepted bids.
     *
     * @param id - Long representing the members ID to whom the bids belong.
     * @return - Integer representing a count of accepted bids.
     */
    @GET
    @Path("accepted/{id}")
    public Response countAcceptedBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        int countBids = -1;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            countBids = db.countAcceptedBids(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CountBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (countBids == -1) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }

        return Response.ok(countBids, MediaType.TEXT_PLAIN).build();
    }

    /**
     * GET web service which gets a count for refused bids.
     *
     * @param id - Long representing the members ID to whom the bids belong.
     * @return - Integer representing a count of refused bids.
     */
    @GET
    @Path("refused/{id}")
    public Response countRejectedBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        int countBids = -1;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            countBids = db.countRejectedBids(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CountBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (countBids == -1) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }

        return Response.ok(countBids, MediaType.TEXT_PLAIN).build();
    }
}
