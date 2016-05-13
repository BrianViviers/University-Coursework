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
 * Web service for getting the image belonging to a single advert.
 *
 * @author BrianV
 */
@Stateless
@Path("advertimagesbyid")
public class GetAdvertImageByIdREST {

    /**
     * Creates a new instance of GetAdvertImageByIdREST
     */
    public GetAdvertImageByIdREST() {
    }

    /**
     * Get web services for getting the image of an advert by its ID.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response getAdvertImagesByID(@PathParam("id") Long id) {
        DBConnector db = new DBConnector();
        String[] image;
        try {
            db.createConnection();
            image = db.getAdvertImageById(id);
            db.closeConnection();

            String json = new Gson().toJson(image);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetAdvertImageByIdREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.NO_CONTENT).entity("No image found.").build();
    }
}
