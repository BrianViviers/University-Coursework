/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonSyntaxException;
import entities.Member;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pagefillers.MemberAdverts;

/**
 * Servlet called when the My Adverts page is loaded.
 *
 * @author PRCSA
 */
@WebServlet(name = "MyAdverts", urlPatterns = {"/MyAdverts"})
public class MyAdverts extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(true);
            StringBuilder sb = new StringBuilder(1000);
            
            // Create an ExecutorService to handle multi-threaded code 
            ExecutorService executor = Executors.newFixedThreadPool(10);
            Member member = (Member) session.getAttribute("member");

            Callable<String> currentAds = new GetCurrentdverts(member);
            Future<String> futureCurrAds = executor.submit(currentAds);
            
            // Create a string with HTML table and fill it with current
            // adverts from the web service response.
            sb.append("<table class=\"table table-hover advert-tables\"><tbody>");
            sb.append(futureCurrAds.get());
            sb.append("</tbody></table>");
            request.setAttribute("currentAds", sb.toString());

            // Create a string with HTML table and fill it with past
            // adverts from the web service response.
            sb = new StringBuilder(1000);
            Callable<String> pastAds = new GetPastdverts(member);
            Future<String> futurePastAds = executor.submit(pastAds);
            sb.append("<table class=\"table table-hover advert-tables\"><tbody>");
            sb.append(futurePastAds.get());
            sb.append("</tbody></table>");
            request.setAttribute("pastAds", sb.toString());

            executor.shutdown();

            request.getRequestDispatcher("myAdverts.jsp").forward(request, response);
        } catch (InterruptedException | ExecutionException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(MyAdverts.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    /**
     * Inner class used to get current adverts from the database
     */
    public class GetCurrentdverts implements Callable<String> {

        private final Member member;

        /**
         * Constructor creates a new instance of GetCurrentdverts
         * @param member - Member object containing the details of the logged in member
         */
        public GetCurrentdverts(Member member) {
            this.member = member;
        }

        @Override
        public String call() throws Exception {
            String html;
            MemberAdverts adverts = new MemberAdverts();

            html = adverts.getMemberAdverts("currentadverts", member);

            return html;
        }
    }

    /**
     * Inner class used to get past adverts from the database
     */
    public class GetPastdverts implements Callable<String> {

        private final Member member;

        /**
         * Constructor creates a new instance of GetPastdverts
         * @param member - Member object containing the details of the logged in member
         */
        public GetPastdverts( Member member) {
            this.member = member;
        }

        @Override
        public String call() throws Exception {
            String html;
            MemberAdverts adverts = new MemberAdverts();

            html = adverts.getMemberAdverts("pastadverts", member);

            return html;
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
