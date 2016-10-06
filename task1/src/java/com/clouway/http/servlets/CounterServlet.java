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
public class CounterServlet extends HttpServlet {
    private Template template;
    private LinkedHashMap<String, String> links;
    private Integer temporary;
    private String page;

    public CounterServlet(LinkedHashMap<String, String> links) {
        this.links = links;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String param = request.getParameter("id");

        if (param != null) {
            if(param.equals("one") || param.equals("two") || param.equals("three")) {
                temporary = Integer.parseInt(links.get(param));
                links.put(param, Integer.toString(++temporary));
            }
        }

        try {
            page = Files.toString(new File("src/java/com/clouway/http/resources/Counter.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        template = new Template(page, links);
        page = template.evaluate();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }
}
