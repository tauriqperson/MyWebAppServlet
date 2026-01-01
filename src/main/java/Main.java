import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        //Get port from environment variable or use default
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(webPort));
        tomcat.getConnector(); //Trigger the creation of the default connector

        //Set up the web application context
        String webappDirLocation = "src/main/webapp/";
        Context ctx = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        //Declare an alternative location for compiled classes
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        WebResourceSet resourceSet = new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/");
        resources.addPreResources(resourceSet);
        ctx.setResources(resources);

        System.out.println("Starting embedded Tomcat server on port: " + webPort);
        System.out.println("Application available at: http://localhost:" + webPort + "/MyWebAppServlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}
