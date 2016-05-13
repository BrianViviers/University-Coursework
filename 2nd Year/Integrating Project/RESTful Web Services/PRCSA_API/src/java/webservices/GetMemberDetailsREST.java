/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Member;
import java.sql.SQLException;
import java.text.ParseException;
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
 * REST Web Service for getting a members details.
 *
 * @author BrianV
 */
@Stateless
@Path("memberdetails")
public class GetMemberDetailsREST {

    /**
     * Creates a new instance of GetMemberDetailsREST
     */
    public GetMemberDetailsREST() {
    }

    /**
     * Error response when an incorrect format has been sent in the URL to this
     * web service.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    public Response getmemberDetailsError() {
        return Response.serverError().entity("ID/Email cannot be blank").build();
    }

    /**
     * Get web service for getting all the members details using their ID
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("id_{id}")
    public Response getMemberDetailsById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        Member memberDetails = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberDetails = db.getMemberAccountDetailsById(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberDetails == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No member details found for member ID: " + id).build();
        }
        String json = new Gson().toJson(memberDetails);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting all the members details using their email
     *
     * @param email - String representing the member's email.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{email}")
    public Response getMemberDetailsByEmail(@PathParam("email") String email) {
        if (email == null || email.trim().length() == 0) {
            return Response.status(Response.Status.NO_CONTENT).entity("Email cannot be blank").build();
        }
        Member memberDetails = null;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberDetails = db.getMemberAccountDetailsByEmail(email);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(GetMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberDetails == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No member details found for member email: " + email).build();
        }
        String json = new Gson().toJson(memberDetails);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
