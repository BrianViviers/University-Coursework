/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pagefillers.AllRules;

/**
 * Servlet called when either of the rules pages (guest or members) is opened.
 *
 * @author PRCSA
 */
@WebServlet(name = "GuestRules", urlPatterns = {"/GuestRules"})
public class GuestRules extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            // Fill a string with a HTML table
            StringBuilder sb = new StringBuilder(1000);
            sb.append("<table class=\"table table-striped\">");
            sb.append("    <thead>");
            sb.append("        <tr>");
            sb.append("            <th>Rule No.</th>");
            sb.append("            <th>Rule Description</th>");
            sb.append("        </tr>");
            sb.append("    </thead>");
            sb.append("    <tbody>");
            
            // Get the rules from the web service by calling the AllRules class.
            AllRules object = new AllRules();
            sb.append(object.getAllRules());
            
            sb.append("    </tbody>");
            sb.append("</table>");

            // Add the rules to the request attributes.
            request.setAttribute("rules", sb.toString());
            request.getRequestDispatcher("guestRules.jsp").forward(request, response);
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(GuestRules.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
