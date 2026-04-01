package com.app.qa.coresuite.stepdefinitions;

import com.app.qa.coresuite.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.results.Results;

import static org.junit.jupiter.api.Assertions.*;

public class NfrAccessibilityStepDefinitions {
    
    private final TestContext context;
    private Results axeResults;
    
    public NfrAccessibilityStepDefinitions(TestContext context) {
        this.context = context;
    }
    
    @Given("the CoreSuite login page is open for accessibility audit")
    public void theCoreSuiteLoginPageIsOpenForAccessibilityAudit() {
        context.getLoginPage().open(context.getBaseUrl());
    }
    
    @When("axe-core runs an accessibility scan")
    public void axeCoreRunsAnAccessibilityScan() {
        AxeBuilder axeBuilder = new AxeBuilder();
        axeResults = axeBuilder.analyze(context.getDriver());
    }
    
    @Then("there are no critical accessibility violations")
    public void thereAreNoCriticalAccessibilityViolations() {
        assertNotNull(axeResults);
        // Filter for critical violations - using getViolations() which returns List<Violation>
        long criticalViolations = axeResults.getViolations().stream()
            .filter(v -> v.getImpact() != null && v.getImpact().equalsIgnoreCase("critical"))
            .count();
        assertEquals(0, criticalViolations, 
            "Found " + criticalViolations + " critical accessibility violations");
    }
    
    @Then("the total violation count is below {int}")
    public void theTotalViolationCountIsBelow(int maxViolations) {
        assertNotNull(axeResults);
        int totalViolations = axeResults.getViolations().size();
        assertTrue(totalViolations < maxViolations,
            "Found " + totalViolations + " violations, expected less than " + maxViolations);
    }
}
