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
 * REST Web Service for getting all the images relating to past adverts that are
 * no longer active.
 *
 * @author BrianV
 */
@Stateless
@Path("pastadvertsimages")
public class GetPastAdvertsImagesREST {

    /**
     * Creates a new instance of GetPastAdvertsImagesREST
     */
    public GetPastAdvertsImagesREST() {
    }

    /**
     * Get Web Service for getting all the images relating to past adverts that
     * are no longer active for a specific member.
     *
     * @param id - Long representing the member's ID
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{id}")
    public Response getMemberPastAdvertsImages(@PathParam("id") Long id) {
        DBConnector db = new DBConnector();
        List<String[]> imageData = null;
        try {
            db.createConnection();
            imageData = db.getMemberPastAdvertsImages(id);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(GetPastAdvertsImagesREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        String json = new Gson().toJson(imageData);

        if (imageData != null) {
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No image found.").build();
        }
    }
}
