package com.clouway.http.server;

import com.clouway.http.servlets.CounterServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class CounterServer {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> links = new HashMap();
        links.put("one" ,0);
        links.put("two" ,0);
        links.put("three" ,0);

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new CounterServlet(links)), "/*");
        server.start();
        server.join();
    }
}
