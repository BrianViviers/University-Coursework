/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mindrot.BCrypt;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet is used when a user tries to log into the system.
 *
 * @author PRCSA
 */
@WebServlet(name = "VerifyLogin", urlPatterns = {"/VerifyLogin"})
public class VerifyLogin extends HttpServlet {

    AppServletContextListener context = new AppServletContextListener();

    private final String restURL = context.getApiURL();

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
        String responseStr;

        // Test whether TLS is in operation.
        // System.out.println(request.isSecure());
        
        
        //Create a web service request object
        WebServiceConnect webService = new WebServiceConnect();
        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");

        Member member = new Member();

        // Get the password associated with the entered email
        responseStr = webService.getWebServiceResponseNoLogin(restURL + "memberpassword/" + email);

        if (responseStr.startsWith("No password found for email") || responseStr.trim().length() == 0) {
            String message = "There was a problem trying to sign in";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {

            // Remove the backslash added by the response.
            String hashedPass = responseStr.replace("\"", "");
            
            // Compare the users entered password with the 
            // hashed password from the database.
            if (BCrypt.checkpw(password, hashedPass)) {
                member.setEmail(email);
                member.setPassword(hashedPass);

                // Get the member's ID from their email address
                responseStr = webService.getWebServiceResponse(restURL + "memberdetails/" + email, member);

                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                member = gson.fromJson(responseStr, new TypeToken<Member>() {
                }.getType());

                member.setPassword(hashedPass);

                // Record this session in the database
                webService.getWebServiceResponse(restURL
                        + "recordsession/" + member.getId().toString(), member);

                HttpSession session = request.getSession(true);
                session.setAttribute("member", member);
                response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
            } else {
                String message = "Invalid email or password.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
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
