package com.clouway.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Validator {

  public Boolean validateName(String sequence) {
    Boolean valid = false;
    Pattern pattern = Pattern.compile("\\w{6,50}");
    Matcher matcher = pattern.matcher(sequence);

    if (matcher.find()) {
      String validSequence = matcher.group();
      if (validSequence.equals(sequence)) {
        valid = true;
      }
    }

    return valid;
  }

  public Boolean validatePassword(String sequence) {
    Boolean valid = false;
    Pattern pattern = Pattern.compile("\\w{6,18}");
    Matcher matcher = pattern.matcher(sequence);

    if (matcher.find()) {
      String validSequence = matcher.group();
      if (validSequence.equals(sequence)) {
        valid = true;
      }
    }

    return valid;
  }
}
