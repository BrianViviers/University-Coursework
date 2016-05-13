/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
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
 * REST Web Service for getting all the images relating to current active
 * adverts.
 *
 * @author BrianV
 */
@Stateless
@Path("currentadvertsimages")
public class GetCurrentAdvertImagesREST {

    /**
     * Creates a new instance of GetCurrentAdvertImagesREST
     */
    public GetCurrentAdvertImagesREST() {
    }

    /**
     * Get web service for getting all the images for a member's current active
     * adverts.
     *
     * @param id
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response getMemberCurrentAdvertImages(@PathParam("id") Long id) {
        DBConnector db = new DBConnector();
        List<String[]> imageData;
        try {
            db.createConnection();
            imageData = db.getMemberCurrentAdvertsImages(id);
            db.closeConnection();

            String json = new Gson().toJson(imageData);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(GetCurrentAdvertImagesREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NO_CONTENT).entity("No image found.").build();
    }

    /**
     * Get web service for getting all the images for every members current
     * active adverts.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    public Response getAllCurrentAdvertImages() {
        DBConnector db = new DBConnector();
        List<String[]> imageData;
        try {
            db.createConnection();
            imageData = db.getAllCurrentAdvertsImages();
            db.closeConnection();

            String json = new Gson().toJson(imageData);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(GetCurrentAdvertImagesREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NO_CONTENT).entity("No image found.").build();
    }
}
