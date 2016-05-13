/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import entities.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet called when a member wants to delete one of their adverts.
 *
 * @author PRCSA
 */
@WebServlet(name = "RemoveAdvert", urlPatterns = {"/RemoveAdvert"})
public class RemoveAdvert extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        final AppServletContextListener context = new AppServletContextListener();
        final String restURL = context.getApiURL();
        final WebServiceConnect webService = new WebServiceConnect();

        // Get the advert ID of the advert to remove.
        String advertID = request.getParameter("advertID");
        Member member = (Member) session.getAttribute("member");

        // Send the ID to the remove advert web service and verify that 
        // the member can remove the advert.
        String responseStr = webService.getWebServiceResponse(restURL
                + "removeadvert/" + advertID, member);

        if (responseStr.startsWith("Advert removed")) {
            String message = "Your advert has been removed.";
            session.setAttribute("return_message", message);
            response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
        } else {
            String message = "There was a problem removing your advert.";
            session.setAttribute("return_message", message);
            response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
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
