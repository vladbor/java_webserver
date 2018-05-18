package servlets;

import resources.TestResourceI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AdminServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final TestResourceI resourceServer;

    public AdminServlet(TestResourceI resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        String path = request.getParameter("path");

        if (path == null || path.isEmpty()) {
            logger.info("No filepath in parameter...");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            logger.info("Path to file: " + path);
            response.setStatus(HttpServletResponse.SC_OK);
        }

// Здесь разбираем XML-ку


        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceServer.setName(resource.getName());
        resourceServer.setAge(resource.getAge());
    }
}
