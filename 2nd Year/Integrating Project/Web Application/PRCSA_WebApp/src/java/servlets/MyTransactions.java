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
import pagefillers.MemberTransactions;

/**
 * Servlet called when the 'My Transactions' page is loaded.
 *
 * @author PRCSA
 */
@WebServlet(name = "MyTransactions", urlPatterns = {"/MyTransactions"})
public class MyTransactions extends HttpServlet {

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
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(true);
            ExecutorService executor = Executors.newFixedThreadPool(10);

            Member member = (Member) session.getAttribute("member");

            // The following 8 lines of code starts of 4 threads to get
            // 4 different types of transactions loaded at the same time 
            // to increase page loading times. 
            Callable<String> inTransCallable = new MemberTransactions(member, "incoming");
            Future<String> futureInTrans = executor.submit(inTransCallable);

            Callable<String> inCompTransCallable = new MemberTransactions(member, "compincoming");
            Future<String> futureInCompTrans = executor.submit(inCompTransCallable);

            Callable<String> outTransCallable = new MemberTransactions(member, "outgoing");
            Future<String> futureOutTrans = executor.submit(outTransCallable);

            Callable<String> outCompTransCallable = new MemberTransactions(member, "compoutgoing");
            Future<String> futureOutCompTrans = executor.submit(outCompTransCallable);

            // Format all web service responses into HTML and set it into
            // the request attributes.
            request.setAttribute("incoming", fillTransTable("incoming", futureInTrans));
            request.setAttribute("incomingCompleted", fillTransTable("compincoming", futureInCompTrans));
            request.setAttribute("outgoing", fillTransTable("outgoing", futureOutTrans));
            request.setAttribute("outgoingCompleted", fillTransTable("compoutgoing", futureOutCompTrans));

            executor.shutdown();
            request.getRequestDispatcher("myTransactions.jsp").forward(request, response);
        } catch (InterruptedException | ExecutionException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(GuestRules.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    // Method to fill a HTML table with the returned transactions from the web service.
    private String fillTransTable(String whichTransaction, Future<String> future) throws InterruptedException, ExecutionException {
        StringBuilder sb = new StringBuilder(500);
        sb.append("<div class=\"table-responsive\">");
        sb.append("<table class=\"table table-striped\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Advert Title</th>");

        if (whichTransaction.equals("incoming") || whichTransaction.equals("compincoming")) {
            sb.append("<th>Payer</th>");
        } else {
            sb.append("<th>Payee</th>");
        }
        sb.append("<th>Credits</th>");

        if (whichTransaction.equalsIgnoreCase("compincoming")
                || whichTransaction.equalsIgnoreCase("compoutgoing")) {
            sb.append("<th>Date Completed</th>");
            sb.append("<th>Review Rating</th>");
            sb.append("<th>Review</th>");
        } else if (whichTransaction.equalsIgnoreCase("incoming")) {
            sb.append("<th>Payment Status</th>");
        } else if (whichTransaction.equalsIgnoreCase("outgoing")) {
            sb.append("<th></th>");
        }
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append(future.get());
        sb.append("</tbody></table></div>");

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
