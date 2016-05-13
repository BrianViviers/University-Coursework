/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonSyntaxException;
import entities.Advert;
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
import pagefillers.GetAdvertById;
import pagefillers.MemberBids;

/**
 * Servlet called when a member click on one of their own adverts.
 *
 * @author PRCSA
 */
@WebServlet(name = "ViewMyAdvert", urlPatterns = {"/ViewMyAdvert"})
public class ViewMyAdvert extends HttpServlet {

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

            // Get the adverts ID from the web page parameters.
            String advertID = request.getParameter("advertID");
            Advert advert;
            
            // Start an new thread and get all the bids on the advert
            Callable<String> bidsOnAdCallable = new MemberBids(advertID, member);
            Future<String> futureBidsOnAd = executor.submit(bidsOnAdCallable);
            
            // While the thread above is running get the actaul advert details.
            GetAdvertById object = new GetAdvertById();
            String appRoot = request.getServletContext().getRealPath("/");
            advert = object.GetAdvert(appRoot, advertID);
            
            // Set the advert and the bids into request attributes.
            request.setAttribute("advert", advert);
            String html = futureBidsOnAd.get();
            request.setAttribute("bids", html);

            executor.shutdown();
            request.getRequestDispatcher("viewMyAdvert.jsp").forward(request, response);
        } catch (InterruptedException | ExecutionException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(ViewMyAdvert.class.getName()).log(Level.SEVERE, null, ex);
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
