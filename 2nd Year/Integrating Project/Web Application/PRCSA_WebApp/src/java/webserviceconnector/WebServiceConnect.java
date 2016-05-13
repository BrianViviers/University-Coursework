/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceconnector;

import entities.Member;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Class which handles every request to the RESTful web services.
 *
 * @author BrianV
 */
public class WebServiceConnect {

    /**
     * Default constructor creates a new instance of WebServiceConnect
     */
    public WebServiceConnect() {
    }

    // Bypassing the SSL verification to execute our code successfully
    // Without this and due to not having a verified certificate then 
    // any request sent to the web service will not be verified.
    static {
        disableSSLVerification();
    }

    /**
     * Method used for bypassing SSL verification
     */
    public static void disableSSLVerification() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (sc != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }

        HostnameVerifier allHostsValid = (String hostname, SSLSession session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * Sends a GET request to the RESTful web services. It requires that a
     * member be logged in to the system.
     *
     * @param Url - String representing the URL of the web service to call
     * @param member - Member object containing the email/hashed password of the
     * logged in member to send with the request in order to be verified.
     * @return - String representing the content returned by the web service
     * usually in JSON format.
     * @throws IOException
     */
    public String getWebServiceResponse(String Url, Member member) throws IOException {
        try {
            // Combine the members email and hashed password and save it in a HTTP
            // header as a base64 string. Used for Basic authentication.
            String authString = member.getEmail() + ":" + member.getPassword();
            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
            HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
            con.setRequestProperty("Authorization", "Basic " + authStringEnc);
            return doGet(con);
        } catch (NullPointerException ex) {
            Logger.getLogger(WebServiceConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "There was a server error";
    }

    /**
     * Sends a GET request to the RESTful web services.This method is used for
     * calling web services that do not require a member to be verified first.
     *
     * @param Url - String representing the URL of the web service to call
     * @return - String representing the content returned by the web service
     * usually in JSON format.
     * @throws IOException
     */
    public String getWebServiceResponseNoLogin(String Url) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
        return doGet(con);
    }

    /**
     * Sends a POST request to the RESTful web services. It requires that a
     * member be logged in to the system so that their details can be verified
     * by the web services.
     *
     * @param Url - String representing the URL of the web service to call.
     * @param content
     * @param member
     * @return - String representing the content returned by the web service
     * usually in JSON format.
     * @throws IOException
     */
    public String postContentWithResponse(String Url, String content, Member member) throws IOException {
        try {
            // Combine the members email and hashed password and save it in a HTTP
            // header as a base64 string. Used for Basic authentication.
            String authString = member.getEmail() + ":" + member.getPassword();
            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
            HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
            con.setRequestProperty("Authorization", "Basic " + authStringEnc);
            return doPost(con, content);
        } catch (NullPointerException ex) {
            Logger.getLogger(WebServiceConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "There was a server error";
    }

    /**
     * Sends a POST request to the RESTful web services.This method is used for
     * calling web services that do not require a member to be verified first.
     *
     * @param Url - String representing the URL of the web service to call.
     * @param content
     * @return - String representing the content returned by the web service
     * usually in JSON format.
     * @throws IOException
     */
    public String postContentWithResponseNoLogin(String Url, String content) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
        return doPost(con, content);
    }

    private String doPost(HttpsURLConnection con, String content) throws IOException {
        String responseStr = "";
        if (con != null) {

            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setChunkedStreamingMode(0);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(content);
                wr.flush();
            }

            InputStream is;
            try {
                is = con.getInputStream();
            } catch (IOException e) {
                is = con.getErrorStream();
            }
            if (is != null) {
                try (BufferedReader buffread = new BufferedReader(new InputStreamReader(is))) {
                    String recv;
                    while ((recv = buffread.readLine()) != null) {
                        responseStr += recv;
                    }
                }
            }
        }
        return responseStr;
    }

    private String doGet(HttpsURLConnection con) throws IOException {
        String responseStr = "";

        if (con != null) {

            InputStream is;
            try {
                is = con.getInputStream();
            } catch (IOException e) {
                is = con.getErrorStream();
            }
            if (is != null) {
                try (BufferedReader buffread = new BufferedReader(new InputStreamReader(is))) {
                    String recv;
                    while ((recv = buffread.readLine()) != null) {
                        responseStr += recv;
                    }
                }
            }
        }
        return responseStr;
    }
}
