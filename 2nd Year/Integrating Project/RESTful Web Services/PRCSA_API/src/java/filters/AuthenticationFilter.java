/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import prcsadatabase.DBConnector;

/**
 * A filter which runs on every web service request to authenticate a member to
 * ensure only allowed members can access certain data.
 *
 * @author BrianV
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private HttpServletRequest httpRequest;

    public AuthenticationFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }
    }

    /**
     * On each web service call this method is run to authenticate that the
     * caller is allowed to retrieve the data related to the call.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("AuthenticationFilter:doFilter()");
        }

        // Test whether TLS is in operation.
        //System.out.println(request.isSecure());
        doBeforeProcessing(request, response);
        Throwable problem = null;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Don't authenticate when any of these web services are called.
        String requestUrl = req.getRequestURL().toString();
        if (requestUrl.contains("memberpassword")
                || requestUrl.contains("registermember")
                || requestUrl.contains("advertimagesbyid")
                || requestUrl.contains("advertbyid")
                || requestUrl.contains("currentadverts")
                || requestUrl.contains("searchadverts")
                || requestUrl.contains("allrules")) {
            try {
                chain.doFilter(request, response);
            } catch (Throwable t) {
                // If an exception is thrown somewhere down the filter chain,
                // we still want to execute our after processing, and then
                // rethrow the problem after that.
                problem = t;
                t.printStackTrace();
            }
        } else {
            // Decode the data back to original string
            try {

                String decoded;

                // Get the Authorisation Header from Request
                String header = req.getHeader("Authorization");

                // Header is in the format "Basic 3nc0dedDat4"
                // We need to extract data before decoding it back to original string
                String data = header.substring(header.indexOf(" ") + 1);

                byte[] bytes = Base64.getDecoder().decode(data);
                decoded = new String(bytes);
                String[] array = decoded.split(":");

                if (!array[0].isEmpty() && !array[1].isEmpty()) {
                    String hashPass = "";
                    DBConnector db = new DBConnector();
                    try {
                        db.createConnection();
                        hashPass = db.getMemberPassword(array[0]);
                        db.closeConnection();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!"".equals(hashPass)) {
                        if (hashPass.equals(array[1])) {
                            try {
                                chain.doFilter(request, response);
                            } catch (Throwable t) {
                                // If an exception is thrown somewhere down the filter chain,
                                // we still want to execute our after processing, and then
                                // rethrow the problem after that.
                                problem = t;
                                t.printStackTrace();
                            }
                        } else {
                            res.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
                        }
                    } else {
                        res.sendError(javax.servlet.http.HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    }
                } else {
                    res.sendError(javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } catch (Exception ex) {
                res.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
