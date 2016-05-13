/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import entities.Bid;
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
import webserviceconnector.WebServiceConnect;

/**
 * Servlet which is called when a bid is being confirmed by a member.
 *
 * @author PRCSA
 */
@WebServlet(name = "BidConfirm", urlPatterns = {"/BidConfirm"})
public class BidConfirm extends HttpServlet {

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
            HttpSession session = request.getSession(true);
            AppServletContextListener context = new AppServletContextListener();

            final String restURL = context.getApiURL();

            WebServiceConnect webService = new WebServiceConnect();

            // Get all the parameters sent by the web page form.
            Bid bid = new Bid();
            
            bid.setOffereeID(Long.parseLong(request.getParameter("offereeId")));
            bid.setOffererID(Long.parseLong(request.getParameter("offererId")));
            bid.setAdvertID(Long.parseLong(request.getParameter("advertID")));
            bid.setAdvertTypeID(Integer.parseInt(request.getParameter("advertTypeID")));

            String action = request.getParameter("action");

            // Check what the user wanted to do to the bid.
            String whichWebService;
            if (action.equalsIgnoreCase("accept")) {
                bid.setID(Long.parseLong(request.getParameter("bidIDAccept")));
                whichWebService = "accept";
                bid.setReturnMessage(request.getParameter("return_message_accept"));
            } else {
                bid.setID(Long.parseLong(request.getParameter("bidIDReject")));
                whichWebService = "reject";
                bid.setReturnMessage(request.getParameter("return_message_reject"));
            }

            String json = new Gson().toJson(bid);

            Member member = (Member) session.getAttribute("member");

            String responseStr = webService.postContentWithResponse(restURL
                    + "bidconfirm/" + whichWebService + "/", json, member);

            if (responseStr.equalsIgnoreCase("Bid updated")) {
                String message = "Your bid has been updated.";
                session.setAttribute("return_message", message);

                responseStr = webService.getWebServiceResponse(restURL + "memberdetails/"
                        + member.getEmail(), member);
                String hashedPass = member.getPassword();
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                member = gson.fromJson(responseStr, new TypeToken<Member>() {
                }.getType());

                // Keep the hashed password with the member. 
                // Used when calling web services
                member.setPassword(hashedPass);

                session.removeAttribute("member");
                session.setAttribute("member", member);
                response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
            } else {
                String message = "There was a problem updating your bid.";
                session.setAttribute("return_message", message);
                response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
            }
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(BidConfirm.class.getName()).log(Level.SEVERE, null, ex);
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
