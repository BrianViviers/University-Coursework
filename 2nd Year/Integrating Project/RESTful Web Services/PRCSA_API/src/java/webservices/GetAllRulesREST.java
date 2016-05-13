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
import entities.Rule;

/**
 * Web service for getting all the rules in the database.
 *
 * @author BrianV
 */
@Stateless
@Path("allrules")
public class GetAllRulesREST {

    /**
     * Creates a new instance of GetAllRulesREST
     */
    public GetAllRulesREST() {
    }

    /**
     * Get web services for getting all the rules in the database.
     *
     * @return - Server response indicating success or failure with a message.
     */
    @GET
    public Response getAllRules() {
        List<Rule> rulesList = new ArrayList<>();
        DBConnector db = new DBConnector();
        try {
            db.createConnection();
            rulesList = db.getAllRules();
            db.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GetAllRulesREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (rulesList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No current rules found").build();
        }
        String json = new Gson().toJson(rulesList);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
