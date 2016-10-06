package com.clouway.http.server;

import com.clouway.http.servlets.ButtonPageServlet;
import com.clouway.http.servlets.PageRecognizerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.LinkedHashMap;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PageServer {
    public static void main(String[] args) throws Exception {
        LinkedHashMap<String, String> links = new LinkedHashMap();
        links.put("/red", "type danger value red color rgb(255,0,0) name RED");
        links.put("/green", "type success value green color rgb(0,255,0) name GREEN");
        links.put("/blue", "type primary value blue color rgb(0,0,255) name BLUE");

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new PageRecognizerServlet(links)), "/recognize");
        context.addServlet(new ServletHolder(new ButtonPageServlet(links)), "/red");
        context.addServlet(new ServletHolder(new ButtonPageServlet(links)), "/green");
        context.addServlet(new ServletHolder(new ButtonPageServlet(links)), "/blue");
        server.start();
        server.join();
    }
}
