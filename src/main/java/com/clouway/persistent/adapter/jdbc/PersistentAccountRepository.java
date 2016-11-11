package com.clouway.persistent.adapter.jdbc;


import com.clouway.core.Account;
import com.clouway.core.AccountRepository;
import com.clouway.persistent.datastore.DataStore;
import com.clouway.persistent.datastore.RowFetcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class PersistentAccountRepository implements AccountRepository {
  private final DataStore dataStore;

  public PersistentAccountRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void setUpConnection(Boolean autoCommit, Integer isolationLevel) {
    dataStore.setUpConnection(autoCommit, isolationLevel);
  }

  @Override
  public void register(Account account) {
    String query = "insert into accounts values(null,?,?,?)";
    dataStore.update(query, account.name, account.password, account.amount);
  }

  @Override
  public Optional<Account> getByName(String name) {
    String query = "select * from accounts where Name='" + name + "'";
    List<Account> accounts = dataStore.fetchRows(query, new RowFetcher<Account>() {
      @Override
      public Account fetchRow(ResultSet resultSet) {
        try {
          return new Account(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return null;
      }
    });
    return accounts.isEmpty() ? Optional.empty() : Optional.ofNullable(accounts.iterator().next());
  }

  @Override
  public void commit() {
    dataStore.commit();
  }

  @Override
  public void rollback() {
    dataStore.rollback();
  }
}