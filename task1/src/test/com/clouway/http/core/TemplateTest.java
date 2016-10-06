package com.clouway.http.core;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TemplateTest {

    private LinkedHashMap<String, String> links;

    @Test
    public void singleVariable() {
        links  = new LinkedHashMap();
        links.put("name", "John");
        Template template = new Template("Hello, ${name}", links);

        String result = template.evaluate();

        assertTrue(result.equals("Hello, John"));
    }

    @Test
    public void manyVariables() {
        links  = new LinkedHashMap();
        links.put("name", "John");
        links.put("goodToKnowThis", "good to know that you are here.");
        Template template = new Template("Hello, ${name} it's ${goodToKnowThis}", links);

        String result = template.evaluate();

        assertTrue(result.equals("Hello, John it's good to know that you are here."));
    }

}
