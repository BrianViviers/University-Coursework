package com.richardimms.www.android0303.WebServices;

/**
 * Created by philip on 18/03/2015.
 */
import android.util.Base64;

import com.richardimms.www.android0303.DataModel.Member;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebService {

    public WebService() {
    }

    static{
        disableSSLVerification();
    }

    /**
     * Method used to disableSSLVerification.
     */
    public static void disableSSLVerification()
    {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        SSLContext sc = null;
        try
        {
            sc = SSLContext.getInstance("SSL");
            sc.init(null,trustAllCerts,new SecureRandom());
        }
        catch(KeyManagementException|NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        if(sc != null)
        {
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * Method used to get a Web Service Reponse.
     * @param Url - String value being the URL of the webservice.
     * @param member - Member object containing the member information, for verification.
     * @return - String value being the response from the web service.
     * @throws IOException
     */
    public String getWebServiceResponse(String Url, Member member) throws IOException
    {
        try {
            String authString = member.getEmail() + ":" + member.getPassword();

            String authStringEnc = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

            HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();

            con.setRequestProperty("Authorization", "Basic " + authStringEnc);

            return doGet(con);
        }
        catch(NullPointerException ex)
        {

        }
        return "There was a server error";
    }

    /**
     * Method used to get the web services response, without logging in (guests)
     * @param Url - String value being the URL of the web service.
     * @return - String value being the response from the web service.
     * @throws IOException
     */
    public String getWebServiceResponseNoLogin(String Url) throws IOException
    {
        HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
        return doGet(con);
    }

    /**
     * Method used to submit a get service.
     * @param con = SSL encrypted connection
     * @return - String value being the response from the web service for a GET command
     * @throws IOException
     */
    private String doGet(HttpsURLConnection con) throws IOException
    {
        String responseStr = "";

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
        return responseStr;
    }

    /**
     * Method used to POST content with a response.
     * @param Url - String being the URL of the webservice to use.
     * @param content - String value being the content to POST.
     * @param member - Member object being the current member for verification.
     * @return - String value being the response from the server for debugging/errors.
     * @throws IOException
     */
    public String postContentWithResponse(String Url, String content, Member member) throws IOException
    {
        try {
            String authString = member.getEmail() + ":" + member.getPassword();
            String authStringEnc = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);
            HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
            con.setRequestProperty("Authorization", "Basic " + authStringEnc);

            return doPost(con, content);
        }
        catch(NullPointerException ex)
        {

        }
        return "There was a server error";
    }

    /**
     * Method used to POST content without logging into the system.
     * @param Url - String value being the URL to POST to.
     * @param content - String value being the content to POST.
     * @return - String value being the response from the webservice.
     * @throws IOException
     */
    public String postContentWithResponseNoLogin(String Url, String content) throws IOException
    {
        HttpsURLConnection con = (HttpsURLConnection) new URL(Url).openConnection();
        return doPost(con, content);
    }

    /**
     * Method used to do a POST command.
     * @param con - SSL encrypted connection.
     * @param content - String value being the content to post.
     * @return - String value being the response from the webservice.
     * @throws IOException
     */
    public String doPost(HttpsURLConnection con, String content) throws IOException {
        String responseStr = "";
        if(con != null) {
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
}
