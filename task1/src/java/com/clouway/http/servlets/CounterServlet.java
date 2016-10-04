package com.clouway.http.servlets;

import com.clouway.http.core.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class CounterServlet extends HttpServlet {
    private Template template;
    private Map<String, Integer> links;
    private Integer temporary;

    public CounterServlet(Map<String, Integer> links) {
        this.links = links;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") != null) {
            if (request.getParameter("id").equals("one")) {
                temporary = links.get("one");
                links.put("one", ++temporary);
            } else if (request.getParameter("id").equals("two")) {
                temporary = links.get("two");
                links.put("two", ++temporary);
            } else if (request.getParameter("id").equals("three")) {
                temporary = links.get("three");
                links.put("three", ++temporary);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("src/java/com/clouway/http/resources/Counter.html"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            template = new Template(sb.toString());
            template.put("link1", links.get("one").toString());
            template.put("link2", links.get("two").toString());
            template.put("link3", links.get("three").toString());

            String page = template.evaluate();

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(page);
        }
    }
}
