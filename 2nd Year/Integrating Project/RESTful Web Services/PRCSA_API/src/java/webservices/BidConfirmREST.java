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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * Web Service to confirm a bid.
 *
 * @author BrianV
 */
@Stateless
@Path("bidconfirm")
public class BidConfirmREST {

    /**
     * Default constructor
     */
    public BidConfirmREST() {
    }

    /**
     * POST web service method to receive a post and action a bid as accepted.
     *
     * @param content - String representing the Bid object in JSON format.
     * @return - A server response indicating if web service was successful or
     * not.
     */
    @POST
    @Path("accept")
    public Response UpdateBidAccept(String content) {
        Bid bid;
        Gson gson = new Gson();
        bid = gson.fromJson(content, new TypeToken<Bid>() {
        }.getType());

        int result = -1;
        if (bid != null) {

            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateBidAccept(bid);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                Logger.getLogger(BidConfirmREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Bid updated").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * POST web service method to receive a post and action a bid as rejected.
     *
     * @param content - String representing the Bid object in JSON format.
     * @return - A server response indicating if web service was successful or
     * not.
     */
    @POST
    @Path("reject")
    public Response UpdateBidReject(String content) {
        Bid bid;
        Gson gson = new Gson();
        bid = gson.fromJson(content, new TypeToken<Bid>() {
        }.getType());

        int result = -1;
        if (bid != null) {

            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateBidReject(bid);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BidConfirmREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Bid updated").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
