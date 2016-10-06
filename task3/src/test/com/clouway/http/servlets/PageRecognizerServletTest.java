package com.clouway.http.servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PageRecognizerServletTest {
    public JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);

    private LinkedHashMap<String, String> values = new LinkedHashMap();
    private String messageForUser = "You came here for the first time, welcome!";

    @Before
    public void setUp(){
        values.put("red", "color rgb(255,0,0) greeting true");
        values.put("green", "color rgb(0,255,0) greeting true");
        values.put("blue", "color rgb(0,0,255) greeting true");
    }

    @Test
    public void happyPath() throws ServletException, IOException {
        PageRecognizerServlet recognizerServlet = new PageRecognizerServlet(values, messageForUser);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        context.checking(new Expectations() {
            {
                oneOf(request).getParameter("radio");
                will(returnValue("red"));

                oneOf(response).setContentType("text/html");
                oneOf(response).setStatus(200);
                oneOf(response).getWriter();
                will(returnValue(printWriter));
            }
        });

        recognizerServlet.doGet(request, response);
        String page = stringWriter.toString();
        CharSequence firstExpected = "background: rgb(255,0,0);";
        CharSequence secondExpected = "You came here for the first time, welcome!";
        assertTrue(page.contains(firstExpected));
        assertTrue(page.contains(secondExpected));
    }

    @Test
    public void severalVisits() throws ServletException, IOException {
        PageRecognizerServlet recognizerServlet = new PageRecognizerServlet(values, messageForUser);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        context.checking(new Expectations() {
            {
                oneOf(request).getParameter("radio");
                will(returnValue("red"));

                oneOf(response).setContentType("text/html");
                oneOf(response).setStatus(200);
                oneOf(response).getWriter();
                will(returnValue(printWriter));

                oneOf(request).getParameter("radio");
                will(returnValue("red"));

                oneOf(response).setContentType("text/html");
                oneOf(response).setStatus(200);
                oneOf(response).getWriter();
                will(returnValue(printWriter));
            }
        });

        recognizerServlet.doGet(request, response);
        recognizerServlet.doGet(request, response);
        String page = stringWriter.toString();
        CharSequence firstExpected = "background: rgb(255,0,0);";
        CharSequence secondExpected = "<h1 style=\"margin: 0 auto; width:250px;\"></h1>";
        assertTrue(page.contains(firstExpected));
        assertTrue(page.contains(secondExpected));
    }
}
