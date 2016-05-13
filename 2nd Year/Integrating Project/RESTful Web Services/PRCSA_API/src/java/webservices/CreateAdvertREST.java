/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Advert;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for creating a new advert.
 *
 * @author BrianV
 */
@Stateless
@Path("createadvert")
public class CreateAdvertREST {

    /**
     * Creates a new instance of CreateAdvertREST
     */
    public CreateAdvertREST() {
    }

    /**
     * POST method for creating an advert in the database.
     *
     * @param content - String representing the JSON Advert object.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    public Response createAdvert(String content) {
        Advert advert;
        Gson gson = new Gson();
        advert = gson.fromJson(content, new TypeToken<Advert>() {
        }.getType());
        
        Long result = -1l;
        if (advert != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.createAdvert(advert);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(CreateAdvertREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0l) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}