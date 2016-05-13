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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for allowing a member to update different parts of their
 * account details.
 *
 * @author BrianV
 */
@Stateless
@Path("updatemember")
public class UpdateMemberDetailsREST {

    /**
     * Creates a new instance of UpdateMemberDetailsREST
     */
    public UpdateMemberDetailsREST() {
    }

    /**
     * Post web service for updating a member's name
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new name.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("name")
    public Response UpdateMemberName(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        int result = -1;
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberName(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Updated member").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your details.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * Post web service for updating a member's date of birth.
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new date of birth.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("dob")
    public Response UpdateMemberDob(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        int result = -1;
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberDob(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Date of Birth Updated").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your details.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * Post web service for updating a member's email.
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new email.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("email")
    public Response UpdateMemberEmail(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        String result = "";
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberEmail(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result.equalsIgnoreCase("Success")) {
                return Response.ok("Updated member").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result).build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * Post web service for updating a member's password.
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new password.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("password")
    public Response UpdateMemberPassword(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        int result = -1;
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberPassword(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Password updated successfully").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your password.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * Post web service for updating a member's telephone number.
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new telephone number.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("telephone")
    public Response UpdateMemberContact(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        int result = -1;
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberContact(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Updated member").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your details.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }

    /**
     * Post web service for updating a member's physical address.
     *
     * @param content - String representing a JSON member object containing the
     * member's ID and new physical address.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    @Path("address")
    public Response UpdateMemberAddress(String content) {
        Member member;
        Gson gson = new Gson();
        member = gson.fromJson(content, new TypeToken<Member>() {
        }.getType());

        int result = -1;
        if (member != null) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.UpdateMemberAddress(member);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UpdateMemberDetailsREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > 0) {
                return Response.ok("Updated member").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your details.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
