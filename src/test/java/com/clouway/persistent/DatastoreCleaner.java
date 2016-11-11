package com.clouway.persistent;

import com.clouway.persistent.adapter.jdbc.ConnectionProvider;
import com.clouway.persistent.datastore.DataStore;

import java.sql.Connection;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class DatastoreCleaner {

  private final ConnectionProvider provider = new ConnectionProvider();
  private final DataStore dataStore = new DataStore(provider);
  private final String[] table;

  public DatastoreCleaner(String... table) {
    this.table = table;
  }

  public void perform() {
    dataStore.setUpConnection(true, Connection.TRANSACTION_READ_COMMITTED);
    for (String each : table) {
      dataStore.update("truncate " + each);
    }
  }
}
