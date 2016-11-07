package com.clouway.repository;

import com.clouway.bankrepository.PersistentHistoryRepository;
import com.clouway.core.Transaction;
import com.clouway.persistent.adapter.jdbc.ConnectionProvider;
import com.clouway.persistent.datastore.DataStore;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentHistoryRepositoryTest {
    private ConnectionProvider provider = new ConnectionProvider("bank", "postgres", "123");
    private DataStore dataStore = new DataStore(provider);
    private PersistentHistoryRepository historyRepository = new PersistentHistoryRepository(dataStore);

    @Before
    public void setUp() throws Exception {
        try {
            Connection connection = provider.get();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE balance_history;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void happyPath() throws SQLException {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        addHistory(timestamp);

        List<Transaction> actual = historyRepository.getBalanceHistory("Borislav",0,20);
        List<Transaction> expected = new ArrayList();

        for(int i = 0; i < 20; i++){
            expected.add(new Transaction(1,timestamp,"Borislav","Deposit",50));
        }

        assertTrue(actual.equals(expected));
    }

    @Test
    public void halfEmptyPage() throws SQLException {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        addHistory(timestamp);

        List<Transaction> actual = historyRepository.getBalanceHistory("Borislav",20,20);
        List<Transaction> expected = new ArrayList();

        for(int i = 0; i < 13; i++){
            expected.add(new Transaction(1,timestamp,"Borislav","Deposit",50));
        }

        assertTrue(actual.equals(expected));
    }

    private void addHistory(Timestamp timestamp) throws SQLException {
        Connection connection = provider.get();
        Statement statement = connection.createStatement();
        for(int i = 0; i < 33; i++){
            statement.executeUpdate("insert into balance_history(id,operation_date,name,operation,amount) values(1,'" + timestamp + "','Borislav','Deposit',50);");
        }
    }
}
