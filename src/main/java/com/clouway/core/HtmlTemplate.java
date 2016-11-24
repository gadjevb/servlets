package com.clouway.core;

import com.google.inject.Inject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */

public class HtmlTemplate implements Template {
  private final Map<String, String> placeHolderToValue = new LinkedHashMap<String, String>();
  private final String templateValue;

  @Inject
  public HtmlTemplate(String templateValue) {
    this.templateValue = templateValue;
  }

  public void put(String placeHolder, String value) {
    placeHolderToValue.put(placeHolder, value);
  }

  public String evaluate() {
    // Missing template validation is omitted for shortening purposes.
    String evaluationResult = templateValue;
    for (String placeHolder : placeHolderToValue.keySet()) {
      evaluationResult = evaluationResult.replaceAll("\\$\\{" + placeHolder + "\\}", placeHolderToValue.get(placeHolder));
    }

    return evaluationResult;
  }
}