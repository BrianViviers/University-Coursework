package applicationconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * A class which holds the URL's to the RESTful API and the Web App.
 *
 * @author PRCSA
 */
@WebListener
public class AppServletContextListener implements ServletContextListener {

    private final String apiURL = "https://eeyore.fost.plymouth.ac.uk:8282/PRCSA_API/resources/";
    //private final String apiURL = "https://localhost:8181/PRCSA_API/resources/";

    private final String webAppURLInsecure = "http://localhost:8080/PRCSA_WebApp/";

    private final String webAppURL = "https://localhost:8181/PRCSA_WebApp/";

    /**
     * Retrieves the secure URL to the RESTful API.
     *
     * @return - String representing the RESTful API URL.
     */
    public String getApiURL() {
        return apiURL;
    }

    /**
     * Retrieves the secure URL to the Web Application.
     *
     * @return - String representing the Web ApplicationURL .
     */
    public String getWebAppURL() {
        return webAppURL;
    }

    /**
     * Retrieves the insecure URL to the Web Application.
     *
     * @return - String representing the Web Application URL.
     */
    public String getWebAppURLInsecure() {
        return webAppURLInsecure;
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
