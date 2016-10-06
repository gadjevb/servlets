package com.clouway.http.servlets;

import com.clouway.http.core.Template;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PageRecognizerServlet extends HttpServlet {
    private final LinkedHashMap<String, String> links;
    private Template template;
    private String pageForUser;

    public PageRecognizerServlet(LinkedHashMap<String, String> links) {
        this.links = links;
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String id = request.getParameter("action");

        try {
            pageForUser = Files.toString(new File("src/java/com/clouway/http/resources/RecognizerPage.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        template = new Template(pageForUser);
        String[] params = links.get("/"+id).split(" ");
        template.put(params[4], params[5]);
        template.put(params[6], params[7]);
        pageForUser = template.evaluate();


        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageForUser);
    }
}
