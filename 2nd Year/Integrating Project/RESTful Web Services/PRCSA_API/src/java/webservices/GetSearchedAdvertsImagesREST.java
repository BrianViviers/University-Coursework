/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URLDecoder;
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
 * REST Web Service for getting all the images for adverts that a member has
 * searched for using words or phrases.
 *
 * @author BrianV
 */
@Stateless
@Path("searchadvertsimages")
public class GetSearchedAdvertsImagesREST {

    /**
     * Creates a new instance of GetSearchedAdvertsImagesREST
     */
    public GetSearchedAdvertsImagesREST() {
    }

    /**
     * Get Web Service for getting all the images for adverts that a member has
     * searched for using words or phrases.
     *
     * @param tags - String representing the words or phrases entered into
     * search.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{tags}")
    public Response getMemberCurrentAdvertImages(@PathParam("tags") String tags) {
        DBConnector db = new DBConnector();
        List<String[]> imageData = null;
        try {
            db.createConnection();
            imageData = db.getSearchedAdvertsImages(URLDecoder.decode(tags, "UTF-8"));
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(GetSearchedAdvertsImagesREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        String json = new Gson().toJson(imageData);

        if (imageData != null) {
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No image found.").build();
        }
    }
}
