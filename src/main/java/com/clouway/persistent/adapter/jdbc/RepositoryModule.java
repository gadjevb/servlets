package com.clouway.persistent.adapter.jdbc;

import com.clouway.core.AccountRepository;
import com.clouway.core.DailyActivityRepository;
import com.clouway.core.HistoryRepository;
import com.clouway.core.SessionsRepository;
import com.google.inject.AbstractModule;

import java.sql.Connection;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class RepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AccountRepository.class).to(PersistentAccountRepository.class);
    bind(DailyActivityRepository.class).to(PersistentDailyActivityRepository.class);
    bind(HistoryRepository.class).to(PersistentHistoryRepository.class);
    bind(SessionsRepository.class).to(PersistentSessionRepository.class);
    bind(Connection.class).toProvider(ConnectionProvider.class);
  }

}
