/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Member;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for registering a new member into the system
 *
 * @author BrianV
 */
@Stateless
@Path("registermember")
public class RegisterMemberREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegisterMember
     */
    public RegisterMemberREST() {
    }

    /**
     * POST method for creating a new entry in the database with a 
     * new member details.
     *
     * @param content - String representing a JSON Member object.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    public Response RegisterMember(String content) {
        Member member;
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());
        
        String result = "";
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.createMember(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(RegisterMemberREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result.equals("Success")) {
                return Response.ok("Created member").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
