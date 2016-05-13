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
 * Servlet called when either of the two rules pages (Guest or Member) is
 * requested.
 *
 * @author PRCSA
 */
@WebServlet(name = "Rules", urlPatterns = {"/Rules"})
public class Rules extends HttpServlet {

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
            // Create a string holding a HTML table 
            // and fill the table with all the rules
            StringBuilder sb = new StringBuilder(1000);
            sb.append("<table class=\"table table-striped\">");
            sb.append("    <thead>");
            sb.append("        <tr>");
            sb.append("            <th>Rule No.</th>");
            sb.append("            <th>Rule Description</th>");
            sb.append("        </tr>");
            sb.append("    </thead>");
            sb.append("    <tbody>");
            AllRules object = new AllRules();
            sb.append(object.getAllRules());
            sb.append("    </tbody>");
            sb.append("</table>");

            // Return the rules to the web page.
            request.setAttribute("rules", sb.toString());
            request.getRequestDispatcher("rules.jsp").forward(request, response);
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(Rules.class.getName()).log(Level.SEVERE, null, ex);
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
