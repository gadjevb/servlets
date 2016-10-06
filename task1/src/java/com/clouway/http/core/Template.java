package com.clouway.http.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Template {
    private final Map<String, String> placeHolderToValue;
    private final String templateValue;

    public Template(String templateValue, LinkedHashMap placeHolderToValue) {
        this.placeHolderToValue = placeHolderToValue;
        this.templateValue = templateValue;
    }

    public String evaluate() {
        String evaluationResult = templateValue;
        for (String placeHolder : placeHolderToValue.keySet()) {
            evaluationResult = evaluationResult.replaceAll("\\$\\{" + placeHolder + "\\}", placeHolderToValue.get(placeHolder));
        }

        return evaluationResult;
    }
}
