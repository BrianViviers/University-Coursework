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
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for recording that a member has logged into the system on
 * the current date and time.
 *
 * @author BrianV
 */
@Stateless
@Path("recordsession")
public class RecordUserSessionsREST {

    /**
     * Creates a new instance of RecordUserSessionsREST
     */
    public RecordUserSessionsREST() {
    }

    /**
     * Get Web Service which takes the member's ID and records that they have
     * logged into the system on the current date and time.
     *
     * @param id - String representing the member's ID who has logged in.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response recordUserSession(@PathParam("id") String id) {
        if (id == null || id.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            db.recordUserSession(Long.parseLong(id));
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RecordUserSessionsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok("Session recorded.").build();
    }
}
