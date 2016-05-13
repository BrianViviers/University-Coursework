/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet called when a guest or member sends an email to the administrator
 * using the functionality provided on the help pages.
 *
 * @author PRCSA
 */
@WebServlet(name = "EmailAdmin", urlPatterns = {"/EmailAdmin"})
public class EmailAdmin extends HttpServlet {

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
        String messageBody = request.getParameter("body");
        String messageSubject = request.getParameter("subject");
        String whichContact = request.getParameter("whichContact");

        if (whichContact.startsWith("member")) {
            String senderMemberID = request.getParameter("memberID");
            messageBody += "\n\nSender Member ID: " + senderMemberID;
        } else {
            String senderEmail = request.getParameter("email");
            messageBody += "\n\nSender Member email: " + senderEmail;
        }

        // Recipient's email ID needs to be mentioned.
        String to = "prcsa.dreamteam@outlook.com";

        // Sender's email ID needs to be mentioned
        String from = "prcsa.dreamteam@outlook.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        String username = "prcsa.dreamteam@outlook.com";
        String password = "dreamTeam#15";
        
        // Set the mail user
        properties.setProperty("mail.user", username);

        // Set the mail password
        properties.setProperty("mail.password", password);

        Session sess;
        sess = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(sess);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(messageSubject, "UTF-8");

            // Now set the actual message
            message.setText(messageBody, "UTF-8");

            // Send message
            Transport.send(message);
            String return_message = "Your email has been sent.";
            session.setAttribute("return_message", return_message);
            if (whichContact.startsWith("member")) {
                response.sendRedirect("help.jsp");
            } else {
                response.sendRedirect("guestHelp.jsp");
            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
            String return_message = "There was a problem sending your email.";
            session.setAttribute("return_message", return_message);
            if (whichContact.startsWith("member")) {
                response.sendRedirect("help.jsp");
            } else {
                response.sendRedirect("guestHelp.jsp");
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
