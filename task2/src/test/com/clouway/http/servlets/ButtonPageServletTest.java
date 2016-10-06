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
public class ButtonPageServletTest {
    public JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);

    private LinkedHashMap<String, String> links = new LinkedHashMap();

    @Before
    public void setUp(){
        links.put("/red", "type danger value red color rgb(255,0,0) name RED");
        links.put("/green", "type success value green color rgb(0,255,0) name GREEN");
        links.put("/blue", "type primary value blue color rgb(0,0,255) name BLUE");
    }

    @Test
    public void happyPath() throws ServletException, IOException {
        ButtonPageServlet buttonPageServlet = new ButtonPageServlet(links);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        context.checking(new Expectations() {
            {
                oneOf(request).getRequestURI();
                will(returnValue("/red"));

                oneOf(response).setContentType("text/html");
                oneOf(response).setStatus(200);
                oneOf(response).getWriter();
                will(returnValue(printWriter));
            }
        });

        buttonPageServlet.doGet(request, response);
        String page = stringWriter.toString();
        CharSequence firstExpected = "btn btn-danger btn-lg";
        CharSequence secondExpected = "red";
        assertTrue(page.contains(firstExpected));
        assertTrue(page.contains(secondExpected));
    }
}
