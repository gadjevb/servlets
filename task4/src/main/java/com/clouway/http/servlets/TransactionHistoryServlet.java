package com.clouway.http.servlets;

import com.clouway.bankrepository.PersistentHistoryRepository;
import com.clouway.core.HistoryRepository;
import com.clouway.core.Transaction;
import com.clouway.persistent.adapter.jdbc.ConnectionProvider;
import com.clouway.persistent.datastore.DataStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TransactionHistoryServlet extends HttpServlet {
    private Integer offset;
    private Integer limit;
    private Boolean nextButton = true;
    private Boolean backButton = false;
    private HistoryRepository historyRepository;

    public TransactionHistoryServlet() {

    }

    public TransactionHistoryServlet(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void init() throws ServletException {
        ConnectionProvider provider = new ConnectionProvider("bank", "postgres", "123");
        DataStore dataStore = new DataStore(provider);
        historyRepository = new PersistentHistoryRepository(dataStore);
        offset = 0;
        limit = 20;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("activated");
        String button = request.getParameter("action");
        System.out.println(button);
        if (button != null) {
            if (button.equals("Next")) {
                offset += limit;
                backButton = true;
            }
            if (button.equals("Back") && offset >= 20) {
                offset -= limit;
                nextButton = true;
                if(offset == 0){
                    backButton = false;
                }
            }
        }
        String transaction = "";
        HttpSession session = request.getSession(false);
        String username = session.getAttribute("username").toString();
        try {
            List<Transaction> history = historyRepository.getBalanceHistory(username, offset, limit);
            transaction = generateHtmlList(history);

            request.setAttribute("transaction", transaction);
            request.setAttribute("next", "active");
            request.setAttribute("back", "active");
            if (nextButton == false) {
                request.setAttribute("next", "inactive");
            }
            if (backButton == false) {
                request.setAttribute("back", "inactive");
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/historyView");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateHtmlList(List<Transaction> list) {
        String transactions = "";
        int i;
        for (i = 0; i < list.size(); i++) {
            transactions = transactions + "<li>" + list.get(i).toString() + "</li><br>";
        }
        if (i < 20) {
            nextButton = false;
        }
        return transactions;
    }
}
