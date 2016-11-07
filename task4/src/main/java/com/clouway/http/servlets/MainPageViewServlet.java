package com.clouway.http.servlets;

import com.clouway.core.HtmlServletPageRenderer;
import com.clouway.core.ServletPageRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class MainPageViewServlet extends HttpServlet {
    private ServletPageRenderer pageRenderer;

    public MainPageViewServlet() {

    }

    public MainPageViewServlet(ServletPageRenderer pageRenderer) {
        this.pageRenderer = pageRenderer;
    }

    @Override
    public void init() throws ServletException {
        pageRenderer = new HtmlServletPageRenderer();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageRenderer.renderPage("index.html", Collections.emptyMap(), response, HttpServletResponse.SC_OK);
    }
}
