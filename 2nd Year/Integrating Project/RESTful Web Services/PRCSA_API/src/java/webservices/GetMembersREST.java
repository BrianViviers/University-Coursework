/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.Gson;
import entities.Member;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
 * Web service to get all the registered members.
 *
 * @author BrianV
 */
@Stateless
@Path("members")
public class GetMembersREST {

    /**
     * Creates a new instance of GetMembersREST
     */
    public GetMembersREST() {
    }

    /**
     * Get web service for getting all the registered members.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("all")
    public Response GetAllMembers() {
        List<Member> memberList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberList = db.GetAllMembers();
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetMembersREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No members found").build();
        }
        String json = new Gson().toJson(memberList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get web service for getting all the registered members using search
     * words.
     *
     * @param tags - String representing the search words entered to find a
     * member.
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    @Path("searched/{tags}")
    public Response GetSearchedMembers(@PathParam("tags") String tags) {
        if (tags == null) {
            return Response.serverError().entity("Tags cannot be blank").build();
        }
        List<Member> memberList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            memberList = db.GetSearchedForMembers(URLDecoder.decode(tags, "UTF-8"));
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GetMembersREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (memberList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No members found with name: " + tags).build();
        }
        String json = new Gson().toJson(memberList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
