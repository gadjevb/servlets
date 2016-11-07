package com.clouway.servlets;

import com.clouway.core.HistoryRepository;
import com.clouway.core.Template;
import com.clouway.http.servlets.TransactionHistoryServlet;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TransactionHistoryServletTest {
    private JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);
    private HistoryRepository repository = context.mock(HistoryRepository.class);
    private TransactionHistoryServlet historyServlet = new TransactionHistoryServlet(repository);

    @Test
    public void happyPath() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        historyServlet.doGet(request,response);
        String page = stringWriter.toString();
        assertThat(page, containsString("History"));
    }
}
