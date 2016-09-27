package com.clouway.http.server;

import com.clouway.http.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class BankServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MainServlet.class, "/main");
        handler.addServletWithMapping(LoginServlet.class, "/login");
        handler.addServletWithMapping(RegisterServlet.class, "/register");
        handler.addServletWithMapping(PersonalServlet.class, "/personal");
        handler.addServletWithMapping(TransactionServlet.class, "/transaction");
        handler.addServletWithMapping(SuccessServlet.class, "/success");
        server.start();
        server.join();
    }
}
