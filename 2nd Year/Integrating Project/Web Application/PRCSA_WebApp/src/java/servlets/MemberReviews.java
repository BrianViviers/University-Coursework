/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import entities.Member;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pagefillers.GetMemberReviews;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet used to get a specific members reviews and ratings. Called when the
 * member reviews page is loaded.
 *
 * @author PRCSA
 */
@WebServlet(name = "MemberReviews", urlPatterns = {"/MemberReviews"})
public class MemberReviews extends HttpServlet {

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

        Member member = (Member) session.getAttribute("member");
        String memberID = request.getParameter("memberID");
        try {
            AppServletContextListener context = new AppServletContextListener();
            WebServiceConnect webService = new WebServiceConnect();

            // Create the url of the web service to call
            String url = context.getApiURL() + "memberdetails/id_" + memberID;

            // Call the web service
            String webserviceResponse = webService.getWebServiceResponse(url, member);
            if (!webserviceResponse.startsWith("No ")) {

                // If a response was received the create a member object and fill it
                // with the returned member's details.
                Member members;
                Gson gson = new Gson();
                members = gson.fromJson(webserviceResponse, new TypeToken<Member>() {
                }.getType());

                if (members.getBalance() != null) {
                    // Get the member's reviews from the database
                    GetMemberReviews memberReviews = new GetMemberReviews(memberID, member, members.getBalance().toString());
                    String reviews = memberReviews.GetMemberReviews();

                    request.setAttribute("reviews", reviews);
                    request.setAttribute("memberName", members.getForename() + " " + members.getSurname());
                    request.getRequestDispatcher("memberReviews.jsp").forward(request, response);
                } else {
                    request.setAttribute("reviews", "<p class='text-info'>Failed to get reviews.</p>");
                    request.getRequestDispatcher("memberReviews.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("reviews", "<p class='text-info'>Failed to get reviews.</p>");
                request.getRequestDispatcher("memberReviews.jsp").forward(request, response);
            }
        } catch (IOException | NumberFormatException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(MemberReviews.class.getName()).log(Level.SEVERE, null, ex);
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
