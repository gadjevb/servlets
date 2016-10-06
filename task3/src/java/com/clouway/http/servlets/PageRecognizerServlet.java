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
    private final LinkedHashMap<String, String> values;
    private Template template;
    private String pageForUser;
    private final String message;

    public PageRecognizerServlet(LinkedHashMap<String, String> values, String message) {
        this.message = message;
        this.values = values;
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String id = request.getParameter("radio");

        try {
            pageForUser = Files.toString(new File("src/java/com/clouway/http/resources/GenericPage.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        template = new Template(pageForUser);
        String[] params = values.get(id).split(" ");
        if(params.length > 3){
            template.put(params[0], params[1]);
            template.put(params[2], message);
            values.put(id, params[0] + " " + params[1] + " " + params[2]);
        }else{
            template.put(params[0], params[1]);
            template.put(params[2], "");
        }
        pageForUser = template.evaluate();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageForUser);
    }
}
