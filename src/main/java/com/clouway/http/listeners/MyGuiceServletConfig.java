package com.clouway.http.listeners;

import com.clouway.core.*;
import com.clouway.http.filters.SecurityFilter;
import com.clouway.http.servlets.*;
import com.clouway.persistent.adapter.jdbc.RepositoryModule;
import com.google.inject.*;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class MyGuiceServletConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new RepositoryModule(), new CoreModule(), new ServletModule() {

              @Override
              protected void configureServlets() {
                filter("/*").through(SecurityFilter.class);
                serve("/register").with(RegistrationPageServlet.class);
                serve("/login").with(LoginPageServlet.class);
                serve("/").with(AccountPageServlet.class);
                serve("/transaction").with(TransactionPageServlet.class);
                serve("/history").with(TransactionHistoryPageServlet.class);
                serve("/logout").with(LogoutServlet.class);
              }

            }
    );
  }

}
