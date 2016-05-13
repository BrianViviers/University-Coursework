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
 * REST Web Service for allowing a member to remove one of their adverts.
 *
 * @author BrianV
 */
@Stateless
@Path("removeadvert")
public class RemoveAdvertREST {

    /**
     * Creates a new instance of RemoveAdvertREST
     */
    public RemoveAdvertREST() {
    }

    /**
     * Get Web Service for allowing a member to remove one of their adverts.
     *
     * @param id - String representing the advert ID of the advert to be
     * removed.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response RemoveAdvert(@PathParam("id") String id) {
        if (id == null || id.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        DBConnector db = new DBConnector();
        int success = -1;
        try {
            db.createConnection();
            success = db.RemoveAdvert(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RemoveAdvertREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (success > 0) {
            return Response.ok("Advert removed.").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to remove the advert").build();
        }
    }
}
