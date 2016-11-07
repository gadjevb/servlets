package com.clouway.http.servlets;

import com.clouway.bankrepository.PersistentCustomerRepository;
import com.clouway.core.*;
import com.clouway.persistent.adapter.jdbc.ConnectionProvider;
import com.clouway.persistent.datastore.DataStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TransactionPageServlet extends HttpServlet {
    private Map<String, Object> values;
    private ServletPageRenderer pageRenderer;
    private String insufficientFunds = "<div class=\"alert alert-danger\"><strong>Insufficient funds!</strong></div>";
    private CustomerRepository customerRepository;

    public TransactionPageServlet() {

    }

    public TransactionPageServlet(ServletPageRenderer pageRenderer, CustomerRepository customerRepository) {
        this.pageRenderer = pageRenderer;
        this.customerRepository = customerRepository;
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
        HttpSession session = request.getSession(false);
        values.put("username", session.getAttribute("username").toString());
        values.put("warning", "");
        pageRenderer.renderPage("transaction.html", values, response, HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String username = session.getAttribute("username").toString();
        String type = request.getParameter("transaction");
        Optional<Customer> optCustomer = customerRepository.getByName(username);
        if (!optCustomer.isPresent()) {
            response.sendRedirect("/main");
        }
        if (type.equals("deposit")) {
            Customer customer = optCustomer.get();
            Integer amount = Integer.parseInt(request.getParameter("amount"));
            customerRepository.updateBalance(customer.name, customer.balance + amount);
            response.sendRedirect("/personal");
        }
        if (type.equals("withdraw")) {
            Customer customer = optCustomer.get();
            Integer amount = Integer.parseInt(request.getParameter("amount"));
            if (amount > customer.balance) {
                values.put("username", username);
                values.put("warning", insufficientFunds);
                pageRenderer.renderPage("transaction.html", values, response, HttpServletResponse.SC_BAD_REQUEST);
            } else {
                customerRepository.updateBalance(customer.name, customer.balance - amount);
                response.sendRedirect("/personal");
            }
        }
    }
}
