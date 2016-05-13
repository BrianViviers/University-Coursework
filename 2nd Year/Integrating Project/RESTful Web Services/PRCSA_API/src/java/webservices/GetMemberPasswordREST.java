/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
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
 * Web service to get a member's hashed password.
 *
 * @author BrianV
 */
@Stateless
@Path("memberpassword")
public class GetMemberPasswordREST {

    /**
     * Creates a new instance of GetMemberPasswordREST
     */
    public GetMemberPasswordREST() {
    }

    /**
     * Error response when an incorrect format has been sent in the URL to this
     * web service.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    public Response getMemberPasswordError() {
        return Response.status(Response.Status.NO_CONTENT).entity("Email cannot be blank").build();
    }

    /**
     * Get web service for getting the member's hashed password from the
     * database.
     *
     * @param email - String representing the member's email used to identify
     * the member.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{email}")
    public Response getMemberPasswordByEmail(@PathParam("email") String email) {
        if (email == null || email.trim().length() == 0) {
            return Response.serverError().entity("Email cannot be blank").build();
        }
        String hashPass = "";
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            hashPass = db.getMemberPassword(email);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetMemberPasswordREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hashPass.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No password found for email: " + email).build();
        }
        String json = new Gson().toJson(hashPass);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
