/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonSyntaxException;
import entities.Member;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pagefillers.GetAdverts;

/**
 * Servlet which is called when a the browse adverts page has been requested by
 * either members or guests.
 *
 * @author PRCSA
 */
@WebServlet(name = "BrowseAdverts", urlPatterns = {"/BrowseAdverts"})
public class BrowseAdverts extends HttpServlet {

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
            StringBuilder sb = new StringBuilder(1000);

            HttpSession session = request.getSession(true);
            Member member = (Member) session.getAttribute("member");
            
            // Create a HTML table to fill with adverts
            sb.append("<div class=\"row placeholders\">");
            sb.append("<div class=\"table-responsive\">\n");
            sb.append("<table class=\"table table-hover advert-tables\">\n");
            sb.append("<tbody>\n");

            // Called GetAdverts class which calls the web service.
            GetAdverts object = new GetAdverts();

            String tags = request.getParameter("searched");
            String guestBrowse = request.getParameter("no_user");
            Boolean guestBrowsing = false;
            if (guestBrowse != null) {
                guestBrowsing = true;
            }
            // If the user searched for adverts the there will be a tags parameter.
            if (tags == null || "".equals(tags)) {
                sb.append(object.getAdverts(member, "currentadverts", guestBrowsing));
            } else {
                sb.append(object.getAdverts(member, "searchadverts", guestBrowsing, tags));
            }

            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("</div>");
            sb.append("</div>");

            request.setAttribute("adverts", sb.toString());
            if (guestBrowse == null) {
                request.getRequestDispatcher("browseAdverts.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("guestBrowseAdverts.jsp").forward(request, response);
            }
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(BrowseAdverts.class.getName()).log(Level.SEVERE, null, ex);
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
