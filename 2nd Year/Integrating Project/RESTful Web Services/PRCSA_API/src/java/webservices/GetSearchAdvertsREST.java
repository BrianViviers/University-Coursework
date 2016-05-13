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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;
import entities.Advert;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.ws.rs.PathParam;

/**
 * REST Web Service for getting adverts that a member has searched for using
 * words or phrases.
 *
 * @author BrianV
 */
@Stateless
@Path("searchadverts")
public class GetSearchAdvertsREST {

    /**
     * Creates a new instance of GetSearchAdvertsREST
     */
    public GetSearchAdvertsREST() {
    }

    /**
     * Get Web Service for getting adverts that a member has searched for using
     * words or phrases.
     *
     * @param tags - String representing the words or phrases entered into
     * search.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{tags}")
    public Response getSearchedAdverts(@PathParam("tags") String tags) {
        if (tags == null) {
            return Response.serverError().entity("Tags cannot be blank").build();
        }
        List<Advert> adsList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            adsList = db.getSearchedForAdverts(URLDecoder.decode(tags, "UTF-8"));
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GetSearchAdvertsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (adsList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No adverts found with search terms: " + tags).build();
        }
        String json = new Gson().toJson(adsList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
