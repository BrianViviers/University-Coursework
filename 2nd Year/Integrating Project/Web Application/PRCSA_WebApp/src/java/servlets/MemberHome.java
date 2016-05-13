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
import pagefillers.GetBidsCount;
import pagefillers.GetTransactionsCount;
import pagefillers.MemberAdverts;

/**
 * Servlet called when the member's home page is requested.
 *
 * @author PRCSA
 */
@WebServlet(name = "MemberHome", urlPatterns = {"/MemberHome"})
public class MemberHome extends HttpServlet {

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
        String html;
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(true);
            ExecutorService executor = Executors.newFixedThreadPool(10);

            Member member = (Member) session.getAttribute("member");

            // The following line of code call multiple web services in
            // different threads in order to speed up the load process.
            //---------------------------------------------------------
            Callable<String> incBidCallable = new GetBidsCount("incoming", member);
            Future<String> futureIncBidCnt = executor.submit(incBidCallable);

            Callable<String> accBidCallable = new GetBidsCount("accepted", member);
            Future<String> futureAccBidCnt = executor.submit(accBidCallable);

            Callable<String> refBidCallable = new GetBidsCount("refused", member);
            Future<String> futureRefBidCnt = executor.submit(refBidCallable);

            Callable<String> transactionsCallable = new GetTransactionsCount("outgoing", member);
            Future<String> futureTransactionsCnt = executor.submit(transactionsCallable);

            Callable<String> callable = new GetAdverts(member);
            Future<String> future = executor.submit(callable);
            html = "<table class=\"table table-hover advert-tables\">\n"
                    + "<tbody>";
            html += future.get();
            html += "</tbody>\n"
                    + "</table>";

            // Fill request attributes with the HTML formatted responses from
            // the web services.
            request.setAttribute("incomingCount", futureIncBidCnt.get());
            request.setAttribute("acceptedCount", futureAccBidCnt.get());
            request.setAttribute("refusedCount", futureRefBidCnt.get());
            request.setAttribute("outgoingTransactions", futureTransactionsCnt.get());
            request.setAttribute("memberAdverts", html);
            
            // Shutdown the multi-thread executor.
            executor.shutdown();
            request.getRequestDispatcher("memberHome.jsp").forward(request, response);
        } catch (InterruptedException | ExecutionException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(MemberHome.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    /**
     * Inner class used to get adverts of the member in a separate thread.
     */
    public class GetAdverts implements Callable<String> {

        private final Member member;

        /**
         * Constructor creates a new instance of GetAdverts
         * @param member - Member object holding the logged in members details.
         */
        public GetAdverts(Member member) {
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
