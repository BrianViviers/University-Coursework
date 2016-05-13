package servlets;

import applicationconfig.AppServletContextListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import entities.Advert;
import entities.Member;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webserviceconnector.WebServiceConnect;

/**
 * Servlet which is called when a member wants to publish an advert.
 *
 * @author PRCSA
 */
@WebServlet(name = "CreateAdvert", urlPatterns = {"/CreateAdvert"})
public class CreateAdvert extends HttpServlet {

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

        AppServletContextListener context = new AppServletContextListener();
        final String restURL = context.getApiURL();
        HttpSession session = request.getSession(true);
        Member member = (Member) session.getAttribute("member");
        
        // Create a new advert object and fill it with the details 
        // the member entered.
        Advert advert = new Advert();
        advert.setTitle(request.getParameter("title"));
        advert.setDescription(request.getParameter("description"));
        advert.setCategory(request.getParameter("category"));
        advert.setAdvert_type(request.getParameter("advert_type"));
        advert.setItem_type(request.getParameter("item_type"));
        advert.setCost(Integer.parseInt(request.getParameter("credits")));
        advert.setMember_id(member.getId());
        advert.setTransport(Boolean.parseBoolean(request.getParameter("transport")));

        int newAdvertID;
        try {
            // Convert the Advert object to JSON
            String json = new Gson().toJson(advert);

            // Send the JSON string to the web service.
            WebServiceConnect webService = new WebServiceConnect();
            String responseStr = webService.postContentWithResponse(restURL
                    + "createadvert", json, member);

            // If the advert was created successfully in the database the 
            // the ID of the advert is returned.
            newAdvertID = Integer.parseInt(responseStr);

            // If the advert was created successfully then add the image
            if (newAdvertID > 0) {
                // Get the image data from the parameter.
                String image = request.getParameter("image");

                if (!image.isEmpty()) {
                    // If the user didn't upload their own image then a default 
                    // is used which is in the img folder.
                    if (image.startsWith("img/")) {
                        // Read in the image file and convert to byte array
                        String appRoot = request.getServletContext().getRealPath("/");
                        File file = new File(appRoot + image);
                        FileInputStream fin = null;
                        try {
                            fin = new FileInputStream(file);
                            byte fileContent[] = new byte[(int) file.length()];
                            fin.read(fileContent);
                            image = Base64.getEncoder().encodeToString(fileContent);
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found" + e);
                        } catch (IOException ioe) {
                            System.out.println("Exception while reading file " + ioe);
                        } finally {
                            try {
                                if (fin != null) {
                                    fin.close();
                                }
                            } catch (IOException ioe) {
                                System.out.println("Error while closing stream: " + ioe);
                            }
                        }
                    } else {
                        // The user uploaded an image so remove the header data
                        // of the image before inserting into the database.
                        image = image.replace("data:image/jpeg;base64,", "");
                    }

                    Gson jsonImage = new Gson();

                    JsonObject jObject = new JsonObject();
                    jObject.addProperty("advert_id", newAdvertID);
                    jObject.addProperty("image", image);

                    // Send the image to the web service to upload with the advert.
                    String toSend = jsonImage.toJson(jObject);
                    responseStr = webService.postContentWithResponse(restURL
                            + "uploadadvertimage", toSend, member);

                    if (!responseStr.isEmpty()) {
                        if (Integer.parseInt(responseStr) > -1) {
                            String message = "Your advert has been created successfully.";
                            session.setAttribute("return_message", message);
                            response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
                        } else {
                            String message = "There was an error adding your image to the advert.";
                            session.setAttribute("return_message", message);
                            response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
                        }
                    }
                }
            } else {
                String message = "Your advert has not been created. Please try again later.";
                session.setAttribute("return_message", message);
                response.sendRedirect(request.getServletContext().getContextPath() + "/MemberHome");
            }
        } catch (IOException | NumberFormatException | NullPointerException | JsonSyntaxException ex) {
            Logger.getLogger(CreateAdvert.class.getName()).log(Level.SEVERE, null, ex);
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
