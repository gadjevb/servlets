package com.clouway.core;

import com.google.inject.Inject;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class RegexValidator implements Validator<String> {
  private String matcher;

  @Inject
  public RegexValidator(String matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean check(String value) {
    return value != null && value.matches(matcher);
  }
}
