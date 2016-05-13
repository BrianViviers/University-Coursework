/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Bid;
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
 * Web service for getting bids that are at different stages of completion.
 *
 * @author BrianV
 */
@Stateless
@Path("memberbids")
public class GetBidsREST {

    /**
     * Creates a new instance of GetBidsREST
     */
    public GetBidsREST() {
    }

    /**
     * An error response for when a request is in the incorrect format.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    public Response getMemberBidsError() {
        return Response.status(Response.Status.NO_CONTENT).entity("Missing the type of bid before the ID e.g. incoming/id.").build();
    }

    /**
     * Get web service for getting a member's bid that have been sent to the
     * member.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("incoming/{id}")
    public Response GetIncomingBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Bid> memberBids = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberBids = db.GetBidsIncoming(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberBids == null || memberBids.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberBids);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting a member's bid they have sent to other members.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("outgoing/{id}")
    public Response GetOutgoingBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Bid> memberBids = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberBids = db.GetBidsOutgoing(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberBids == null || memberBids.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberBids);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting a member's bids that they previously sent
     * and have now been accepted by another member.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("accepted/{id}")
    public Response GetAcceptedBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Bid> memberBids = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberBids = db.GetBidsAccepted(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberBids == null || memberBids.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberBids);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting a member's bids that they previously sent
     * and have now been rejected by another member.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("refused/{id}")
    public Response GetRefusedBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Member ID cannot be blank").build();
        }
        List<Bid> memberBids = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberBids = db.GetBidsRefused(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberBids == null || memberBids.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberBids);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting a member's bids that are for a specific advert only.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("advertid/{id}")
    public Response GetAdvertBids(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Advert ID cannot be blank").build();
        }
        List<Bid> memberBids = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberBids = db.GetBidsByAdvertID(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetBidsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberBids == null || memberBids.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bids found for advert ID: " + id).build();
        }
        String json = new Gson().toJson(memberBids);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
