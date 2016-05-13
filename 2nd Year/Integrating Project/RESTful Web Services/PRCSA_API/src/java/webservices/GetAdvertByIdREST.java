/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Advert;
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
 * Web service for getting a member's current active adverts.
 *
 * @author BrianV
 */
@Stateless
@Path("advertbyid")
public class GetAdvertByIdREST {

    /**
     * Creates a new instance of GetAdvertByIdREST
     */
    public GetAdvertByIdREST() {
    }

    /**
     * Get web services for getting a members current active adverts.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response getMemberCurrentAdverts(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        Advert advert;
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            advert = db.getAdvertByID(id);
            db.closeConnection();

            String json = new Gson().toJson(advert);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetAdvertByIdREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.NOT_FOUND).entity("No adverts found for ID: " + id).build();
    }
}
