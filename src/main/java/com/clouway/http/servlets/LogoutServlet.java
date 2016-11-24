package com.clouway.http.servlets;

import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
@Singleton
public class LogoutServlet extends HttpServlet {
  private SessionsRepository sessions;

  @Inject
  public LogoutServlet(SessionsRepository sessions) {
    this.sessions = sessions;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    for (Cookie cookie : req.getCookies()) {
      if ("SID".equals(cookie.getName())) {
        Optional<Session> possibleSession = sessions.findBySID(cookie.getValue());
          if (possibleSession.isPresent()) {
            sessions.deleteByID(possibleSession.get().id);
            cookie.setValue(null);
            cookie.setMaxAge(-1);
          }
        }
      }

      resp.sendRedirect("/login");
    }
  }