/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Review;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Web service to get all the reviews given to a member.
 *
 * @author BrianV
 */
@Stateless
@Path("memberreviews")
public class GetMemberReviewsREST {

    /**
     * Creates a new instance of GetMemberReviewsREST
     */
    public GetMemberReviewsREST() {
    }

    /**
     * Get web service for getting all the reviews given to a member.
     *
     * @param memberID - String representing the member's ID.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("{memberID}")
    public Response getMemberReviews(@PathParam("memberID") String memberID) {
        if (memberID == null || "".equalsIgnoreCase(memberID)) {
            return Response.status(Response.Status.NO_CONTENT).entity("ID cannot be blank").build();
        }
        List<Review> reviewList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            reviewList = db.GetMemberReviews(memberID);
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetMemberReviewsREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (reviewList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No member reviews found").build();
        }
        String json = new Gson().toJson(reviewList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
