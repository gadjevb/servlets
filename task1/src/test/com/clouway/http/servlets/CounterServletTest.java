package com.clouway.http.servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class CounterServletTest {

    public JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);

    private LinkedHashMap<String, String> links = new LinkedHashMap();

    @Before
    public void setUp(){
        links.put("one" ,"0");
        links.put("two" ,"0");
        links.put("three" ,"0");
    }

    @Test
    public void happyPath() throws ServletException, IOException {
        CounterServlet counter = new CounterServlet(links);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        context.checking(new Expectations() {
            {
                oneOf(request).getParameter("id");
                will(returnValue("one"));

                oneOf(response).setContentType("text/html");
                oneOf(response).setStatus(200);
                oneOf(response).getWriter();
                will(returnValue(printWriter));
            }
        });

        counter.doGet(request, response);
        String page = stringWriter.toString();
        CharSequence expected = "Clicks Link1 1";
        assertTrue(page.contains(expected));
    }
}
