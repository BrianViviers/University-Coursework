package filters;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * This filter is run on every page request to check whether 
 * there is a member logged in. Certain pages are not checked.
 *
 * @author PRCSA
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     *
     * @param req
     * @param res
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        // Don't authenticate when any of these web services are called.
        String requestUrl = request.getRequestURL().toString();
        if (requestUrl.equalsIgnoreCase("http://localhost:8080/PRCSA_WebApp/")
                || requestUrl.equalsIgnoreCase("https://localhost:8181/PRCSA_WebApp/")
                || requestUrl.contains("index")
                || requestUrl.contains("login")
                || requestUrl.contains("Register")
                || requestUrl.contains("Browse")
                || requestUrl.contains("guestHelp")
                || requestUrl.contains("GuestRules")
                || requestUrl.contains("css")
                || requestUrl.contains("js")
                || requestUrl.contains("fonts")
                || requestUrl.contains("img")
                || requestUrl.contains("UserImages")
                || requestUrl.contains("EmailAdmin")
                || requestUrl.contains("IncludeJSP")
                || requestUrl.contains("VerifyLogin")) {
            try {
                chain.doFilter(request, response);
            } catch (Throwable t) {
                // If an exception is thrown somewhere down the filter chain,
                // we still want to execute our after processing, and then
                // rethrow the problem after that.
                t.printStackTrace();
            }
        } else {
            try {
                if (session == null || session.getAttribute("member") == null) {
                    request.getRequestDispatcher("serverError.jsp").forward(request, response);
                } else {
                    chain.doFilter(req, res);
                }
            } catch (IOException | ServletException ex) {
                Logger.getLogger(LoginFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }
}
