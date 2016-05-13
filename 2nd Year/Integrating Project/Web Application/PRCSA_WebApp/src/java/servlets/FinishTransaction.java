/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import entities.Member;
import entities.Review;
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
 * Servlet called when a member has set one of their transactions as completed.
 *
 * @author PRCSA
 */
@WebServlet(name = "FinishTransaction", urlPatterns = {"/FinishTransaction"})
public class FinishTransaction extends HttpServlet {

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

            // Create a Review object to hold the review the member 
            // has given for another member when finishing a transaction.
            Review review = new Review();
            
            // Fill the review object.
            review.setTransactionID(Long.parseLong(request.getParameter("transactionID")));
            review.setReviewValue(Integer.parseInt(request.getParameter("rating")));
            review.setReviewText(request.getParameter("review_text"));

            // Convert review object to JSON
            String json = new Gson().toJson(review);

            Member member = (Member) session.getAttribute("member");
            
            // Send review object to the web service.
            String responseStr = webService.postContentWithResponse(restURL
                    + "finishtransaction/", json, member);

            if (responseStr.startsWith("Transaction updated")) {
                String message = "Your transaction has been finalised.";
                session.setAttribute("return_message", message);
                request.getRequestDispatcher("/MemberHome").forward(request, response);
            } else {
                String message = "There was a problem finalising your transaction.";
                session.setAttribute("return_message", message);
                request.getRequestDispatcher("/MemberHome").forward(request, response);
            }
        } catch (NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(FinishTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
