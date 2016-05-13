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
import pagefillers.MemberBids;

/**
 * Servlet called when the 'My Bids' page is requested
 *
 * @author PRCSA
 */
@WebServlet(name = "MyBids", urlPatterns = {"/MyBids"})
public class MyBids extends HttpServlet {

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
            ExecutorService executor = Executors.newFixedThreadPool(10);
            Member member = (Member) session.getAttribute("member");

            // The following 8 lines of code starts of 4 threads to get
            // 4 different types of bids loaded at the same time to increase page
            // loading times. 
            Callable<String> inBidsCallable = new MemberBids(member, "incoming");
            Future<String> futureInBids = executor.submit(inBidsCallable);

            Callable<String> outBidsCallable = new MemberBids(member, "outgoing");
            Future<String> futureOutBids = executor.submit(outBidsCallable);

            Callable<String> refusedBidsCallable = new MemberBids(member, "refused");
            Future<String> futureRefusedBids = executor.submit(refusedBidsCallable);

            Callable<String> acceptedBidsCallable = new MemberBids(member, "accepted");
            Future<String> futureAcceptedBids = executor.submit(acceptedBidsCallable);

            // Format all web service responses into HTML and set it into
            // the request attributes.
            request.setAttribute("incoming", FillBidsTable("incoming", futureInBids));
            request.setAttribute("outgoing", FillBidsTable("outgoing", futureOutBids));
            request.setAttribute("refused", FillBidsTable("refused", futureRefusedBids));
            request.setAttribute("accepted", FillBidsTable("accepted", futureAcceptedBids));
            executor.shutdown();
            request.getRequestDispatcher("myBids.jsp").forward(request, response);

        } catch (InterruptedException | ExecutionException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(MyBids.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    // Method to fill a HTML table with the returned bids from the web service.
    private String FillBidsTable(String whichBids, Future<String> future) throws InterruptedException, ExecutionException {
        StringBuilder sb = new StringBuilder(1000);

        sb.append("<div class=\"table-responsive\">");
        sb.append("<table class=\"table table-striped ");

        if (whichBids.equalsIgnoreCase("incoming")) {
            sb.append("tableRowHover");
        }
        sb.append("\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Advert Title</th>");
        sb.append("<th>Message</th>");

        if (whichBids.equalsIgnoreCase("refused") || whichBids.equalsIgnoreCase("accepted")) {
            sb.append("<th>Return Message</th>");
        }

        sb.append("<th>Bid Date</th>");
        sb.append("<th>Member Name</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append(future.get());
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</div>");

        return sb.toString();
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
