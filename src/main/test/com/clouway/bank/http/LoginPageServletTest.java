package com.clouway.bank.http;

import com.clouway.bank.adapter.http.LoginPageServlet;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class LoginPageServletTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private HttpServletRequest request = context.mock(HttpServletRequest.class);
  private HttpServletResponse response = context.mock(HttpServletResponse.class);

  private PrintWriter writer;
  private ByteArrayOutputStream out;

  @Before
  public void setUp() throws Exception {
    out = new ByteArrayOutputStream();
    writer = new PrintWriter(out);

  }

  @Test
  public void happyPath() throws Exception {
    LoginPageServlet servlet = new LoginPageServlet();

    context.checking(new Expectations() {{
      oneOf(response).getWriter();
      will(returnValue(writer));

      oneOf(request).getParameter("errorMessage");
      will(returnValue(""));
    }});
    servlet.doGet(request, response);

    String result = out.toString();

    assertThat(result, containsString("<legend>Login</legend>"));
  }

  @Test
  public void displayError() throws Exception {
    LoginPageServlet pageServlet = new LoginPageServlet();

    context.checking(new Expectations() {{
      oneOf(response).getWriter();
      will(returnValue(writer));

      oneOf(request).getParameter("errorMessage");
      will(returnValue("Error"));
    }});
    pageServlet.doGet(request, response);

    String result = out.toString();

    assertThat(result, containsString("Error"));
  }
}
