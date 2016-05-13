/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Bid;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for creating a new Bid.
 *
 * @author BrianV
 */
@Stateless
@Path("createbid")
public class CreateBidREST {

    /**
     * Creates a new instance of CreateBidREST
     */
    public CreateBidREST() {
    }

    /**
     * Post web services to create a bid in the database.
     *
     * @param content - String representing the JSON Bid object.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    public Response createBid(String content) {

        Bid bid;
        Gson gson = new Gson();
        bid = gson.fromJson(content, new TypeToken<Bid>() {
        }.getType());

        Long result = -1l;
        if (bid != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.createBid(bid);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(CreateBidREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0l) {
                return Response.ok("Bid created").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
