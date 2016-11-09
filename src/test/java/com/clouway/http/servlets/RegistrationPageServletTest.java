package com.clouway.http.servlets;

import com.clouway.FakeHttpServletRequest;
import com.clouway.FakeHttpServletResponse;
import com.clouway.core.Account;
import com.clouway.core.AccountRepository;
import com.clouway.core.ServletPageRenderer;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class RegistrationPageServletTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private FakeHttpServletRequest request = new FakeHttpServletRequest();
  private FakeHttpServletResponse response = new FakeHttpServletResponse();
  private ByteArrayOutputStream stream;
  private RegistrationPageServlet servlet;
  private PrintWriter writer;

  private AccountRepository repo = context.mock(AccountRepository.class);
  private ServletPageRenderer servletResponseWriter = context.mock(ServletPageRenderer.class);

  @Before
  public void setUp() throws Exception {
    servlet = new RegistrationPageServlet(repo, servletResponseWriter);
    stream = new ByteArrayOutputStream();
    writer = new PrintWriter(stream);
  }

  @Test
  public void happyPath() throws Exception {
    response.setWriter(writer);

    context.checking(new Expectations() {{
      oneOf(servletResponseWriter).renderPage("register.html", Collections.singletonMap("error", ""), response);
    }});

    servlet.doGet(request, response);
  }

  @Test
  public void takenUsername() throws Exception {
    request.setParameter("name", "Johnathan");
    request.setParameter("password", "password");
    response.setWriter(writer);

    context.checking(new Expectations() {{
      oneOf(repo).getByName("Johnathan");
      will(returnValue(Optional.of(new Account("Johnathan", "password", 0))));

      oneOf(servletResponseWriter).renderPage("register.html", Collections.singletonMap("error", "Username is taken"), response);
    }});

    servlet.doPost(request, response);
  }

  @Test
  public void wrongInput() throws Exception {
    request.setParameter("name", "John");
    request.setParameter("password","password");
    response.setWriter(writer);

    context.checking(new Expectations() {{
      oneOf(servletResponseWriter).renderPage("register.html", Collections.singletonMap("error", "Wrong input"), response);
    }});

    servlet.doPost(request, response);
  }

  @Test
  public void register() throws Exception {
    request.setParameter("name", "Johnathan");
    request.setParameter("password", "password");
    response.setWriter(writer);

    context.checking(new Expectations() {{
      oneOf(repo).getByName("Johnathan");
      will(returnValue(Optional.empty()));
      oneOf(repo).register(new Account("Johnathan", "password", 0));
    }});

    servlet.doPost(request, response);
    assertThat(response.getRedirect(), is("/login"));
  }
}