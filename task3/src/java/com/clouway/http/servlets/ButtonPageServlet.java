package com.clouway.http.servlets;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ButtonPageServlet extends HttpServlet {
    private String pageForUser;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        try {
            pageForUser = Files.toString(new File("src/java/com/clouway/http/resources/MainPage.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageForUser);
    }
}
