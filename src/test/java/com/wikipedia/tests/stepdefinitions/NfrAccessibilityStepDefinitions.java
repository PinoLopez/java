package com.wikipedia.tests.stepdefinitions;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class NfrAccessibilityStepDefinitions {

    private final TestContext context;

    public NfrAccessibilityStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Then("the page has no critical axe-core accessibility violations")
    public void noAccessibilityViolations() {
        Results results       = new AxeBuilder().analyze(context.getDriver());
        List<Rule> critical   = results.getViolations().stream()
            .filter(r -> "critical".equalsIgnoreCase(r.getImpact()))
            .collect(Collectors.toList());
        String summary = critical.stream()
            .map(r -> "  [" + r.getImpact() + "] "
                + r.getId() + ": " + r.getDescription())
            .collect(Collectors.joining("\n"));
        // Log non-critical violations as warnings without failing the test
        results.getViolations().stream()
            .filter(r -> !"critical".equalsIgnoreCase(r.getImpact()))
            .forEach(r -> System.out.println("  [WARN axe " + r.getImpact()
                + "] " + r.getId() + ": " + r.getDescription()));
        assertTrue(critical.isEmpty(),
            critical.size()
            + " CRITICAL accessibility violation(s) found on "
            + context.getDriver().getCurrentUrl() + ":\n" + summary);
    }
}
