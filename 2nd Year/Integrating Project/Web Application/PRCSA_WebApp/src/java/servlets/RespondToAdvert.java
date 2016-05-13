/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import entities.Bid;
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
 * Servlet called when a member sends a bid to another member about an advert
 * they have posted.
 *
 * @author PRCSA
 */
@WebServlet(name = "RespondToAdvert", urlPatterns = {"/RespondToAdvert"})
public class RespondToAdvert extends HttpServlet {

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
        AppServletContextListener context = new AppServletContextListener();

        final String restURL = context.getApiURL();

        WebServiceConnect webService = new WebServiceConnect();
        
        // Create a new bid object and fill it with the details required of a bid.
        Bid bid = new Bid();
        bid.setOffereeID(Long.parseLong(request.getParameter("advertOwnerID")));
        bid.setOffererID(Long.parseLong(request.getParameter("memberID")));
        bid.setAdvertID(Long.parseLong(request.getParameter("advertID")));
        bid.setAdvertTypeID(Integer.parseInt(request.getParameter("advertTypeID")));
        bid.setText(request.getParameter("message"));

        Member member = (Member) session.getAttribute("member");

        // Convert the bid object to JSON.
        String json = new Gson().toJson(bid);
        
        // Send the bid to the web service.
        String responseStr = webService.postContentWithResponse(restURL
                + "createbid", json, member);

        if (responseStr.equalsIgnoreCase("Bid created")) {
            String message = "Your bid has been sent successfully.";
            session.setAttribute("return_message", message);
            response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
        } else {
            String message = "There was a problem sending your bid.";
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
