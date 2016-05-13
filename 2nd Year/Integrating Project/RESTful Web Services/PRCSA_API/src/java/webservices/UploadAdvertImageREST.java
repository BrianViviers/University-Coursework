/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import prcsadatabase.DBConnector;

/**
 * REST Web Service for uploading an image to an advert in the database.
 *
 * @author BrianV
 */
@Stateless
@Path("uploadadvertimage")
public class UploadAdvertImageREST {

    /**
     * Creates a new instance of UploadAdvertImageREST
     */
    public UploadAdvertImageREST() {
    }

    /**
     * POST web service for uploading an image to an existing advert.
     *
     * @param content - String array containing the new image and the advert ID
     * to which the image will be uploaded.
     * @return - Server response indicating success or failure with a message.
     */
    @POST
    public Response UploadImage(String content) {
        JsonObject newObj = new JsonParser().parse(content).getAsJsonObject();
        int result = -1;
        if (!newObj.isJsonNull()) {
            DBConnector db = new DBConnector();
            try {
                db.createConnection();
                result = db.uploadImage(newObj);
                db.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UploadAdvertImageREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result > -1) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("There was a problem updating your details.").build();
            }
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No content was received.").build();
        }
    }
}
