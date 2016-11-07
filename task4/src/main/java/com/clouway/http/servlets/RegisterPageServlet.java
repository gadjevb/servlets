package com.clouway.http.servlets;

import com.clouway.bankrepository.PersistentCustomerRepository;
import com.clouway.core.*;
import com.clouway.persistent.adapter.jdbc.ConnectionProvider;
import com.clouway.persistent.datastore.DataStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class RegisterPageServlet extends HttpServlet {
    private Map<String, Object> values;
    private ServletPageRenderer pageRenderer;
    private String usernameTaken = "<div class=\"alert alert-danger\"><strong>Username is taken, please try another!</strong></div>";
    private CustomerRepository customerRepository;

    public RegisterPageServlet() {

    }

    public RegisterPageServlet(CustomerRepository customerRepository, ServletPageRenderer pageRenderer) {
        this.customerRepository = customerRepository;
        this.pageRenderer = pageRenderer;
    }

    @Override
    public void init() throws ServletException {
        ConnectionProvider provider = new ConnectionProvider("bank", "postgres", "123");
        DataStore dataStore = new DataStore(provider);
        customerRepository = new PersistentCustomerRepository(dataStore);
        pageRenderer = new HtmlServletPageRenderer();
        values = new HashMap<>();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        values.put("warning", "");
        pageRenderer.renderPage("register.html", values, response, HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        Optional<Customer> customer = customerRepository.getByName(username);
        try {
            if (!customer.isPresent()) {
                customerRepository.register(new Customer(null, username, password, 0));

                response.sendRedirect("/");
            } else {
                values.put("warning", usernameTaken);
                pageRenderer.renderPage("register.html", values, response, HttpServletResponse.SC_OK);
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
