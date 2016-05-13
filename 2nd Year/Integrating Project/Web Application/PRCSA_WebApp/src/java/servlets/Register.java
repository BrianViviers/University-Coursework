/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import entities.Member;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mindrot.BCrypt;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet called when a member clicks the register button on the register page.
 *
 * @author PRCSA
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

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

        // Get the password entered by the user and hash it with salt added.
        String password = request.getParameter("password");
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try {
            final String UK_FORMAT = "dd/MM/yyyy";
            final String US_FORMAT = "MM/dd/yyyy";

            String newDateString;

            Member member = new Member();

            // Convert the date from UK format to US 
            // format to enter in the database.
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(UK_FORMAT);
                Date d = sdf.parse(request.getParameter("dob"));
                sdf.applyPattern(US_FORMAT);
                newDateString = sdf.format(d);
                java.sql.Date dateOfBirth = new java.sql.Date(new SimpleDateFormat(US_FORMAT)
                        .parse(newDateString).getTime());
                member.setDob(dateOfBirth);
            } catch (ParseException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Fill a member object with all the new members details.
            member.setForename(request.getParameter("firstname"));
            member.setSurname(request.getParameter("surname"));
            member.setEmail(request.getParameter("email"));
            member.setPassword(hashed);
            member.setContact_number(request.getParameter("telephone"));
            member.setAddline1(request.getParameter("addline1"));
            member.setAddline2(request.getParameter("addline2"));
            member.setCity(request.getParameter("city"));
            member.setPostcode(request.getParameter("postcode"));

            // Convert the member object to JSON.
            String json = new Gson().toJson(member);

            // Connect to the web service and pass in the new member details
            WebServiceConnect webService = new WebServiceConnect();
            String responseStr = webService.postContentWithResponseNoLogin(restURL
                    + "registermember", json);

            if (responseStr.equals("Created member")) {
                String message = "Your account has been created succesfully.\r\nPlease login.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", responseStr);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
