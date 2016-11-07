package com.clouway.http.servlets;

import com.clouway.core.HtmlServletPageRenderer;
import com.clouway.core.ServletPageRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TransactionHistoryViewServlet extends HttpServlet {
    private ServletPageRenderer pageRenderer;

    public TransactionHistoryViewServlet() {

    }

    @Override
    public void init() throws ServletException {
        pageRenderer = new HtmlServletPageRenderer();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> values = new HashMap();

        String history = request.getAttribute("transaction").toString();
        String next = request.getAttribute("next").toString();
        String back = request.getAttribute("back").toString();
        values.put("username", "Borislav");
        values.put("history", history);

        if (next.equals("inactive")) {
            values.put("nActive", "disabled=\"disabled\"");
        } else {
            values.put("nActive", "");
        }
        if (back.equals("inactive")) {
            values.put("bActive", "disabled=\"disabled\"");
        } else {
            values.put("bActive", "");
        }

        try {
            pageRenderer.renderPage("history.html", values, response, HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
