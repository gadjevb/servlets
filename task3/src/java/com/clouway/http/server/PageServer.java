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
        LinkedHashMap<String, String> values = new LinkedHashMap();
        values.put("red", "color rgb(255,0,0) greeting true");
        values.put("green", "color rgb(0,255,0) greeting true");
        values.put("blue", "color rgb(0,0,255) greeting true");
        String messageForUser = "You came here for the first time, welcome!";

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new ButtonPageServlet()), "/main");
        context.addServlet(new ServletHolder(new PageRecognizerServlet(values, messageForUser)), "/generic");

        server.start();
        server.join();
    }
}
