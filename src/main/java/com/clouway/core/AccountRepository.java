package com.clouway.core;

import java.util.Optional;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public interface AccountRepository {
  void setUpConnection(Boolean autoCommit, Integer isolationLevel);

  void register(Account account);

  Optional<Account> getByName(String name);

  void commit();

  void rollback();
}

