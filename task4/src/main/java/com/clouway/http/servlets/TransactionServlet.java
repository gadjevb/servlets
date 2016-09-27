package com.clouway.http.servlets;

import com.clouway.http.core.HtmlReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TransactionServlet extends HttpServlet {
    HtmlReader reader = new HtmlReader();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String mainPage = reader.read("src/main/java/com/clouway/http/resources/transaction.html");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(mainPage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String mainPage = "";

        if(request.getParameter("action").equals("proceed")){
            mainPage = reader.read("src/main/java/com/clouway/http/resources/success.html");
        }else if(request.getParameter("action").equals("cancel")){
            mainPage = reader.read("src/main/java/com/clouway/http/resources/personal.html");
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(mainPage);
    }
}
