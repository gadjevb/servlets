package com.clouway.persistent.adapter.jdbc;

import com.clouway.core.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import com.google.inject.Provider;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {

  @Override
  public Connection get() {
    Connection connection = null;
    try {
      connection = ConnectionPool.get().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}