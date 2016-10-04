package com.clouway.http.core;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TemplateTest {

    @Test
    public void singleVariable() {
        Template template = new Template("Hello, ${name}");
        template.put("name", "John");

        String result = template.evaluate();

        assertTrue(result.equals("Hello, John"));
    }

    @Test
    public void manyVariables() {
        Template template = new Template("Hello, ${name} it's ${goodToKnowThis}");

        template.put("name", "John");
        template.put("goodToKnowThis", "good to know that you are here.");

        String result = template.evaluate();

        assertTrue(result.equals("Hello, John it's good to know that you are here."));
    }

}
