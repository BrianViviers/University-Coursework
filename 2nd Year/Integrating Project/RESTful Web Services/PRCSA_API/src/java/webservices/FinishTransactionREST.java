/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Review;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for finalising a transaction.
 *
 * @author BrianV
 */
@Stateless
@Path("finishtransaction")
public class FinishTransactionREST {

    /**
     * Creates a new instance of FinishTransactionREST
     */
    public FinishTransactionREST() {
    }

    /**
     * Post web services for finalising a transaction.
     *
     * @param content - String representing a JSON Review object.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    public Response FinishTransaction(String content) {
        Review review;
        Gson gson = new Gson();
        review = gson.fromJson(content, new TypeToken<Review>() {
        }.getType());

        int result = -1;
        if (review != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.FinishTransaction(review);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FinishTransactionREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > -1) {
                return Response.ok("Transaction updated").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating the transaction.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
