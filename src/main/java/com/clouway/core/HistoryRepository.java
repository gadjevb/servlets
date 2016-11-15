package com.clouway.core;

import java.sql.SQLException;
import java.util.List;

/**
 * This {@code HistoryRepository} interface provides the methods
 * to be implemented for work with the Balance_history table in the Bank database
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface HistoryRepository {

    /**
     * Returns the transaction history of the user
     *
     * @return  List of Transaction objects
     * @param username used to match the user whose transaction history
     * @param offset used to skip the first records
     * @param limit the number of records to be returned
     */
    List<Transaction> getBalanceHistory(String username, int offset, int limit);
}

