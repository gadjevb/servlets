package com.clouway.bankrepository;

import com.clouway.core.HistoryRepository;
import com.clouway.core.Transaction;
import com.clouway.persistent.datastore.DataStore;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentHistoryRepository implements HistoryRepository {
    private DataStore dataStore;

    public PersistentHistoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }


    @Override
    public List<Transaction> getBalanceHistory(String username, int offset, int limit) throws SQLException {
        String query = "select * from balance_history where name='" + username + "' order by operation_date offset " + offset + " limit " + limit + ";";
        return dataStore.fetchRows(query, set -> {
            try {
                return new Transaction(set.getInt(1), set.getTimestamp(2), set.getString(3), set.getString(4), set.getInt(5));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
