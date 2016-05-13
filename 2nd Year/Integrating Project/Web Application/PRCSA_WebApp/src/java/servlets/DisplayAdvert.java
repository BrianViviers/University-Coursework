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

/**
 * Servlet which is called when an advert is in the process of being created.
 * This will display the final advert preview page.
 *
 * @author PRCSA
 */
@WebServlet(name = "DisplayAdvert", urlPatterns = {"/DisplayAdvert"})
public class DisplayAdvert extends HttpServlet {

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
        
        // Get all the parameters and pass them to the preview page.
        // A servlet needs to be used here as the image is too large for the URL.
        try {
            if (request.getParameter("transport").equalsIgnoreCase("true")) {
                request.setAttribute("transport", "<br><br><br><b>Transport is Included.</b>");
                request.setAttribute("transportBool", "true");
            } else {
                request.setAttribute("transport", "<br><br><br><b>Transport is not Included.</b>");
                request.setAttribute("transportBool", "false");
            }

            request.setAttribute("textTitle", request.getParameter("textTitle"));
            request.setAttribute("textCredits", request.getParameter("textCredits"));
            request.setAttribute("textDescription", request.getParameter("textDescription"));
            request.setAttribute("image", request.getParameter("image"));
            request.setAttribute("advertType", request.getParameter("advertType"));
            request.setAttribute("itemType", request.getParameter("itemType"));
            request.setAttribute("category", request.getParameter("category"));

            request.getRequestDispatcher("displayAdvert.jsp").forward(request, response);
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(DisplayAdvert.class.getName()).log(Level.SEVERE, null, ex);
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
