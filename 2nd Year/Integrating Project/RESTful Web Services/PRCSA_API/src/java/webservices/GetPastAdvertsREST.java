/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.ArrayList;
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
import entities.Advert;

/**
 * REST Web Service for getting all the past adverts that are no longer active.
 *
 * @author BrianV
 */
@Stateless
@Path("pastadverts")
public class GetPastAdvertsREST {

    /**
     * Creates a new instance of GetPastAdvertsREST
     */
    public GetPastAdvertsREST() {
    }

    /**
     * Get Web Service for getting all the past adverts that are no longer
     * active for a specific member.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response getMemberPastAdverts(@PathParam("id") Long id) {
        if (id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        List<Advert> adsList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            adsList = db.getMemberPastAdverts(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetPastAdvertsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (adsList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No adverts found for ID: " + id).build();
        }
        String json = new Gson().toJson(adsList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
