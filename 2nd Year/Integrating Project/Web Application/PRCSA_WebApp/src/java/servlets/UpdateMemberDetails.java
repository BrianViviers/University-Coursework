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
import entities.Member;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mindrot.BCrypt;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet called whenever a member tries to update any of their details from
 * their account page.
 *
 * @author PRCSA
 */
@WebServlet(name = "UpdateMemberDetails", urlPatterns = {"/UpdateMemberDetails"})
public class UpdateMemberDetails extends HttpServlet {

    AppServletContextListener context = new AppServletContextListener();

    private final String restURL = context.getApiURL();
    private Member loggedInMember;

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
        
        // Get the logged in members details.
        loggedInMember = (Member) session.getAttribute("member");

        // Fill a string with which details the user wants to update.
        String whichDetails = request.getParameter("whichDetails");
        String responseStr = "";
        Member member = new Member();

        // Check which details the user wants to update
        // and run the relevant method.
        try {
            switch (whichDetails) {
                case "changeName":
                    responseStr = ChangeName(member, request);
                    break;
                case "changePassword":
                    ChangePassword(member, request, response);
                    return;
                case "changeEmail":
                    responseStr = ChangeEmail(member, request, response);
                    break;
                case "changeContact":
                    responseStr = ChangeContact(member, request);
                    break;
                case "changeAddress":
                    responseStr = ChangeAddress(member, request);
                    break;
                case "changeDOB":
                    ChangeDateOfBirth(member, request, response);
                    return;
            }

            WebServiceConnect webService = new WebServiceConnect();

            if (responseStr.equalsIgnoreCase("Updated member")) {
                String message = "Your details have been updated successfully.";
                session.setAttribute("return_message", message);

                // If update was successful then get the new members 
                // details from the database 
                responseStr = webService.getWebServiceResponse(restURL + "memberdetails/"
                        + loggedInMember.getEmail(), loggedInMember);
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                member = gson.fromJson(responseStr, new TypeToken<Member>() {
                }.getType());

                // Keep the hashed password with the member. 
                // Used when calling web services
                member.setPassword(loggedInMember.getPassword());

                // Save the new details to the session member object
                session.removeAttribute("member");
                session.setAttribute("member", member);

                response.sendRedirect("myAccount.jsp");
            } else if (responseStr.equalsIgnoreCase("Email address already in use.")) {
                session.setAttribute("return_message", "Email address already in use.");
                response.sendRedirect("myAccount.jsp");
            } else {
                String message = "There was a problem updating your details. Please try again later.";
                session.setAttribute("return_message", message);
                response.sendRedirect("myAccount.jsp");
            }
        } catch (IOException | JsonSyntaxException | NullPointerException ex) {
            Logger.getLogger(UpdateMemberDetails.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("serverError.jsp").forward(request, response);
        }
    }

    
    // Method to update a members name
    private String ChangeName(Member member, HttpServletRequest request) throws IOException {
        member.setForename(request.getParameter("forename"));
        member.setSurname(request.getParameter("surname"));
        member.setId(Long.parseLong(request.getParameter("memberID")));

        String json = new Gson().toJson(member);

        // Connect to the web service and pass in the updated member name
        WebServiceConnect webService = new WebServiceConnect();
        String responseStr = webService.postContentWithResponse(restURL
                + "updatemember/name", json, loggedInMember);

        return responseStr;
    }

    // Method to update a members password
    private void ChangePassword(Member member, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        WebServiceConnect webService = new WebServiceConnect();
        HttpSession session = request.getSession(true);

        String email = request.getParameter("email").toLowerCase();
        String old_password = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        String responseStr = webService.getWebServiceResponse(restURL + "memberpassword/" + email, loggedInMember);

        if (responseStr.startsWith("No password found for email") || responseStr.trim().length() == 0) {
            String message = "There was a problem retrieving your details.";
            session.setAttribute("return_message", message);
            response.sendRedirect("myAccount.jsp");
        } else {
            // Remove the backslash added by the response.
            responseStr = responseStr.replace("\"", "");
            
            // Compare the users entered password with the 
            // hashed password from the database.
            if (BCrypt.checkpw(old_password, responseStr)) {
                String hashed = BCrypt.hashpw(new_password, BCrypt.gensalt(12));
                member.setPassword(hashed);
                member.setEmail(email);
                String json = new Gson().toJson(member);

                // Connect to the web service and pass in the updated member password
                responseStr = webService.postContentWithResponse(restURL
                        + "updatemember/password", json, loggedInMember);

                if (responseStr.equals("Password updated successfully")) {
                    String message = "Password updated successfully.";
                    session.setAttribute("return_message", message);

                    loggedInMember.setPassword(hashed);
                    session.removeAttribute("member");
                    session.setAttribute("member", loggedInMember);

                    response.sendRedirect("myAccount.jsp");
                } else {
                    String message = "There was a problem updating your password. Please try again.";
                    session.setAttribute("return_message", message);
                    response.sendRedirect("myAccount.jsp");
                }
            } else {
                String message = "Incorrect current password entered.";
                session.setAttribute("return_message", message);
                response.sendRedirect("myAccount.jsp");
            }
        }
    }

    // Method to update a members email address
    private String ChangeEmail(Member member, HttpServletRequest request, HttpServletResponse response) throws IOException {
        member.setEmail(request.getParameter("email").toLowerCase());
        member.setId(Long.parseLong(request.getParameter("memberID")));

        String json = new Gson().toJson(member);

        // Connect to the web service and pass in the updated member password
        WebServiceConnect webService = new WebServiceConnect();
        String responseStr = webService.postContentWithResponse(restURL
                + "updatemember/email", json, loggedInMember);

        if (responseStr.equalsIgnoreCase("Updated member")) {
            loggedInMember.setEmail(request.getParameter("email").toLowerCase());
        }

        return responseStr;
    }

    // Method to update a members telephone number
    private String ChangeContact(Member member, HttpServletRequest request) throws IOException {
        member.setContact_number(request.getParameter("contactNo"));
        member.setId(Long.parseLong(request.getParameter("memberID")));

        String json = new Gson().toJson(member);

        // Connect to the web service and pass in the updated member contact no.
        WebServiceConnect webService = new WebServiceConnect();
        String responseStr = webService.postContentWithResponse(restURL
                + "updatemember/telephone", json, loggedInMember);

        return responseStr;
    }

    // Method to update a members physical address.
    private String ChangeAddress(Member member, HttpServletRequest request) throws IOException {
        member.setAddline1(request.getParameter("addline1"));
        member.setAddline2(request.getParameter("addline2"));
        member.setCity(request.getParameter("city"));
        member.setPostcode(request.getParameter("postcode"));
        member.setId(Long.parseLong(request.getParameter("memberID")));

        String json = new Gson().toJson(member);

        // Connect to the web service and pass in the updated member address
        WebServiceConnect webService = new WebServiceConnect();
        String responseStr = webService.postContentWithResponse(restURL
                + "updatemember/address", json, loggedInMember);

        return responseStr;
    }

    // Method to update a members date of birth.
    private void ChangeDateOfBirth(Member member, HttpServletRequest request, HttpServletResponse response) {
        final String UK_FORMAT = "dd/MM/yyyy";
        final String US_FORMAT = "MM/dd/yyyy";

        HttpSession session = request.getSession(true);
        String newDateString;
        String responseStr;

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
            member.setId(Long.parseLong(request.getParameter("memberID")));

            String json = new Gson().toJson(member);

            // Connect to the web service and pass in the updated member address
            WebServiceConnect webService = new WebServiceConnect();
            responseStr = webService.postContentWithResponse(restURL
                    + "updatemember/dob", json, loggedInMember);

            if (responseStr.equalsIgnoreCase("date of birth updated")) {
                responseStr = webService.getWebServiceResponse(restURL + "memberdetails/"
                        + loggedInMember.getEmail(), loggedInMember);
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                member = gson.fromJson(responseStr, new TypeToken<Member>() {
                }.getType());

                // Keep the hashed password with the member. 
                // Used when calling web services
                member.setPassword(loggedInMember.getPassword());

                session.removeAttribute("member");
                session.setAttribute("member", member);
                session.setAttribute("return_message", "Date of Birth Updated");
                response.sendRedirect("myAccount.jsp");
            }
        } catch (ParseException ex) {

            try {
                responseStr = "Date is in an incorrect format.";
                session.setAttribute("return_message", responseStr);
                response.sendRedirect("myAccount.jsp");
            } catch (IOException ex1) {
                Logger.getLogger(UpdateMemberDetails.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            Logger.getLogger(UpdateMemberDetails.class.getName()).log(Level.SEVERE, null, ex);
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
