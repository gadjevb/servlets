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
public class ButtonPageServlet extends HttpServlet {
    private final LinkedHashMap<String, String> links;
    private Template template;
    private String pageForUser;

    public ButtonPageServlet(LinkedHashMap<String, String> links) {
        this.links = links;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String page = request.getRequestURI();

        try {
            pageForUser = Files.toString(new File("src/java/com/clouway/http/resources/ButtonPage.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        template = new Template(pageForUser);
        String[] params = links.get(page).split(" ");
        template.put(params[0], params[1]);
        template.put(params[2], params[3]);
        pageForUser = template.evaluate();


        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageForUser);
    }
}
