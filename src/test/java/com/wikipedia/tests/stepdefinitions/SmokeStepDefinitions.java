package com.wikipedia.tests.stepdefinitions;

import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class SmokeStepDefinitions {

    private final TestContext context;

    public SmokeStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Then("the page title contains {string}")
    public void pageTitleContains(String expected) {
        String actual = context.getDriver().getTitle();
        assertTrue(actual.contains(expected),
            "Expected page title to contain '" + expected
            + "' but found: '" + actual + "'");
    }
}
